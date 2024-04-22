/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.Decimal;
import com.amazon.ion.IonBool;
import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonLob;
import com.amazon.ion.IonSequence;
import com.amazon.ion.IonStruct;
import com.amazon.ion.IonSymbol;
import com.amazon.ion.IonText;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.SymbolToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Equivalence {
    private static final boolean PUBLIC_COMPARISON_API = false;
    private static final Configuration STRICT_CONFIGURATION = new Configuration(new Builder().withStrict(true));
    private static final Configuration NON_STRICT_CONFIGURATION = new Configuration(new Builder().withStrict(false));
    private final Configuration configuration;

    private Equivalence(Configuration configuration) {
        this.configuration = configuration;
    }

    private static int compareAnnotations(SymbolToken[] ann1, SymbolToken[] ann2) {
        int len = ann1.length;
        int result = len - ann2.length;
        if (result == 0) {
            for (int i = 0; result == 0 && i < len; ++i) {
                result = Equivalence.compareSymbolTokens(ann1[i], ann2[i]);
            }
        }
        return result;
    }

    private static int compareSymbolTokens(SymbolToken tok1, SymbolToken tok2) {
        String text1 = tok1.getText();
        String text2 = tok2.getText();
        if (text1 == null || text2 == null) {
            int sid2;
            if (text1 != null) {
                return 1;
            }
            if (text2 != null) {
                return -1;
            }
            int sid1 = tok1.getSid();
            if (sid1 < (sid2 = tok2.getSid())) {
                return -1;
            }
            if (sid1 > sid2) {
                return 1;
            }
            return 0;
        }
        return text1.compareTo(text2);
    }

    private static final Map<Field, Field> convertToMultiSet(IonStruct struct, Configuration configuration, int depth) {
        HashMap<Field, Field> structMultiSet = new HashMap<Field, Field>();
        for (IonValue val : struct) {
            Field item = new Field(val, configuration, depth);
            Field curr = structMultiSet.put(item, item);
            if (curr != null) {
                item.occurrences = curr.occurrences;
            }
            item.occurrences++;
        }
        return structMultiSet;
    }

    private static int compareStructs(IonStruct s1, IonStruct s2, Configuration configuration, int depth) {
        int result = s1.size() - s2.size();
        if (result == 0) {
            Map<Field, Field> s1MultiSet = Equivalence.convertToMultiSet(s1, configuration, depth);
            for (IonValue val : s2) {
                Field field = new Field(val, configuration, depth);
                Field mappedValue = s1MultiSet.get(field);
                if (mappedValue == null || mappedValue.occurrences == 0) {
                    return -1;
                }
                mappedValue.occurrences--;
            }
        }
        return result;
    }

    private static int compareSequences(IonSequence s1, IonSequence s2, Configuration configuration, int depth) {
        int result;
        block1: {
            result = s1.size() - s2.size();
            if (result != 0) break block1;
            Iterator<IonValue> iter1 = s1.iterator();
            Iterator<IonValue> iter2 = s2.iterator();
            while (iter1.hasNext() && (result = Equivalence.ionCompareToImpl(iter1.next(), iter2.next(), configuration, depth)) == 0) {
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int compareLobContents(IonLob lob1, IonLob lob2) {
        int in2;
        int in1 = lob1.byteSize();
        int result = in1 - (in2 = lob2.byteSize());
        if (result == 0) {
            InputStream stream1 = lob1.newInputStream();
            InputStream stream2 = lob2.newInputStream();
            try {
                try {
                    try {
                        while (result == 0) {
                            in1 = stream1.read();
                            in2 = stream2.read();
                            if (in1 == -1 || in2 == -1) {
                                if (in1 != -1) {
                                    result = 1;
                                }
                                if (in2 != -1) {
                                    result = -1;
                                }
                                break;
                            }
                            result = in1 - in2;
                        }
                    } finally {
                        stream1.close();
                    }
                } finally {
                    stream2.close();
                }
            } catch (IOException e) {
                throw new IonException(e);
            }
        }
        return result;
    }

    private static boolean ionEqualsImpl(IonValue v1, IonValue v2, Configuration configuration, int depth) {
        return Equivalence.ionCompareToImpl(v1, v2, configuration, depth) == 0;
    }

    private static int ionCompareToImpl(IonValue v1, IonValue v2, Configuration configuration, int depth) {
        IonType ty2;
        int result = 0;
        if (v1 == null || v2 == null) {
            if (v1 != null) {
                result = 1;
            }
            if (v2 != null) {
                result = -1;
            }
            return result;
        }
        IonType ty1 = v1.getType();
        result = ty1.compareTo(ty2 = v2.getType());
        if (result == 0) {
            boolean bo1 = v1.isNullValue();
            boolean bo2 = v2.isNullValue();
            if (bo1 || bo2) {
                if (!bo1) {
                    result = 1;
                }
                if (!bo2) {
                    result = -1;
                }
            } else {
                switch (ty1) {
                    case NULL: {
                        break;
                    }
                    case BOOL: {
                        if (((IonBool)v1).booleanValue()) {
                            result = ((IonBool)v2).booleanValue() ? 0 : 1;
                            break;
                        }
                        result = ((IonBool)v2).booleanValue() ? -1 : 0;
                        break;
                    }
                    case INT: {
                        result = ((IonInt)v1).bigIntegerValue().compareTo(((IonInt)v2).bigIntegerValue());
                        break;
                    }
                    case FLOAT: {
                        double double1 = ((IonFloat)v1).doubleValue();
                        double double2 = ((IonFloat)v2).doubleValue();
                        if (configuration.epsilon != null && (double1 == double2 || Math.abs(double1 - double2) <= configuration.epsilon)) {
                            result = 0;
                            break;
                        }
                        result = Double.compare(double1, double2);
                        break;
                    }
                    case DECIMAL: {
                        result = Decimal.equals(((IonDecimal)v1).decimalValue(), ((IonDecimal)v2).decimalValue()) ? 0 : 1;
                        break;
                    }
                    case TIMESTAMP: {
                        if (configuration.isStrict) {
                            result = ((IonTimestamp)v1).timestampValue().equals(((IonTimestamp)v2).timestampValue()) ? 0 : 1;
                            break;
                        }
                        result = ((IonTimestamp)v1).timestampValue().compareTo(((IonTimestamp)v2).timestampValue());
                        break;
                    }
                    case STRING: {
                        result = ((IonText)v1).stringValue().compareTo(((IonText)v2).stringValue());
                        break;
                    }
                    case SYMBOL: {
                        result = Equivalence.compareSymbolTokens(((IonSymbol)v1).symbolValue(), ((IonSymbol)v2).symbolValue());
                        break;
                    }
                    case BLOB: 
                    case CLOB: {
                        result = Equivalence.compareLobContents((IonLob)v1, (IonLob)v2);
                        break;
                    }
                    case STRUCT: {
                        if (depth >= configuration.maxComparisonDepth) {
                            throw new IonException("Cannot continue comparison: maximum comparison depth exceeded. This limit may be raised using Equivalence.Builder.");
                        }
                        result = Equivalence.compareStructs((IonStruct)v1, (IonStruct)v2, configuration, depth + 1);
                        break;
                    }
                    case LIST: 
                    case SEXP: 
                    case DATAGRAM: {
                        if (depth >= configuration.maxComparisonDepth) {
                            throw new IonException("Cannot continue comparison: maximum comparison depth exceeded. This limit may be raised using Equivalence.Builder.");
                        }
                        result = Equivalence.compareSequences((IonSequence)v1, (IonSequence)v2, configuration, depth + 1);
                    }
                }
            }
        }
        if (result == 0 && configuration.isStrict) {
            result = Equivalence.compareAnnotations(v1.getTypeAnnotationSymbols(), v2.getTypeAnnotationSymbols());
        }
        return result;
    }

    public static boolean ionEquals(IonValue v1, IonValue v2) {
        return Equivalence.ionEqualsImpl(v1, v2, STRICT_CONFIGURATION, 0);
    }

    public static boolean ionEqualsByContent(IonValue v1, IonValue v2) {
        return Equivalence.ionEqualsImpl(v1, v2, NON_STRICT_CONFIGURATION, 0);
    }

    public boolean ionValueEquals(IonValue v1, IonValue v2) {
        return Equivalence.ionEqualsImpl(v1, v2, this.configuration, 0);
    }

    static class Field {
        private final String name;
        private final IonValue value;
        private final Configuration configuration;
        private final int depth;
        private int occurrences;

        Field(IonValue value, Configuration configuration, int depth) {
            SymbolToken tok = value.getFieldNameSymbol();
            String name = tok.getText();
            if (name == null) {
                name = " -- UNKNOWN SYMBOL TEXT -- $" + tok.getSid();
            }
            this.name = name;
            this.value = value;
            this.configuration = configuration;
            this.depth = depth;
            this.occurrences = 0;
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public boolean equals(Object other) {
            Field sOther = (Field)other;
            return this.name.equals(sOther.name) && Equivalence.ionEqualsImpl(this.value, ((Field)other).value, this.configuration, this.depth);
        }
    }

    public static final class Builder {
        private boolean isStrict = true;
        private Double epsilon = null;
        private int maxComparisonDepth = 1000;

        public Builder withStrict(boolean isStrict) {
            this.isStrict = isStrict;
            return this;
        }

        public Builder withEpsilon(double epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public Builder withMaxComparisonDepth(int maxComparisonDepth) {
            if (maxComparisonDepth < 0) {
                throw new IllegalArgumentException("Max comparison depth must not be negative.");
            }
            this.maxComparisonDepth = maxComparisonDepth;
            return this;
        }

        public Equivalence build() {
            return new Equivalence(new Configuration(this));
        }
    }

    static final class Configuration {
        private final boolean isStrict;
        private final Double epsilon;
        private final int maxComparisonDepth;

        Configuration(Builder builder) {
            this.isStrict = builder.isStrict;
            this.epsilon = builder.epsilon;
            this.maxComparisonDepth = builder.maxComparisonDepth;
        }
    }
}

