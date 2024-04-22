/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval.io;

import com.amazon.ion.IonDecimal;
import com.amazon.ion.IonException;
import com.amazon.ion.IonFloat;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonNull;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonTimestamp;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.BindingCase;
import org.partiql.lang.eval.BindingName;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.ExprValueFactory;
import org.partiql.lang.eval.StandardNamesKt;
import org.partiql.lang.eval.StructOrdering;
import org.partiql.lang.eval.io.DelimitedValues;
import org.partiql.lang.eval.io.DelimitedValues$WhenMappings;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J8\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\fH\u0007J\f\u0010\u0018\u001a\u00020\n*\u00020\u0019H\u0002\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/eval/io/DelimitedValues;", "", "()V", "exprValue", "Lorg/partiql/lang/eval/ExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "input", "Ljava/io/Reader;", "delimiter", "", "hasHeader", "", "conversionMode", "Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode;", "writeTo", "", "ion", "Lcom/amazon/ion/IonSystem;", "output", "Ljava/io/Writer;", "value", "newline", "writeHeader", "csvStringValue", "Lcom/amazon/ion/IonValue;", "ConversionMode", "lang"})
public final class DelimitedValues {
    public static final DelimitedValues INSTANCE;

    @JvmStatic
    @NotNull
    public static final ExprValue exprValue(@NotNull ExprValueFactory valueFactory, @NotNull Reader input, @NotNull String delimiter, boolean hasHeader, @NotNull ConversionMode conversionMode) {
        List list;
        Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
        Intrinsics.checkParameterIsNotNull((Object)conversionMode, "conversionMode");
        BufferedReader reader = new BufferedReader(input);
        if (hasHeader) {
            String string = reader.readLine();
            if (string == null) {
                throw (Throwable)new IllegalArgumentException("Got EOF for header row");
            }
            String line = string;
            list = StringsKt.split$default((CharSequence)line, new String[]{delimiter}, false, 0, 6, null);
        } else {
            list = CollectionsKt.emptyList();
        }
        List columns = list;
        Sequence seq2 = SequencesKt.generateSequence((Function0)new Function0<ExprValue>(reader, valueFactory, delimiter, columns, conversionMode){
            final /* synthetic */ BufferedReader $reader;
            final /* synthetic */ ExprValueFactory $valueFactory;
            final /* synthetic */ String $delimiter;
            final /* synthetic */ List $columns;
            final /* synthetic */ ConversionMode $conversionMode;

            @Nullable
            public final ExprValue invoke() {
                String line = this.$reader.readLine();
                String string = line;
                return string == null ? null : this.$valueFactory.newStruct(SequencesKt.mapIndexed(StringsKt.splitToSequence$default((CharSequence)line, new String[]{this.$delimiter}, false, 0, 6, null), (Function2)new Function2<Integer, String, ExprValue>(this){
                    final /* synthetic */ exprValue.seq.1 this$0;

                    @NotNull
                    public final ExprValue invoke(int i, @NotNull String raw) {
                        Intrinsics.checkParameterIsNotNull(raw, "raw");
                        String name = i < this.this$0.$columns.size() ? (String)this.this$0.$columns.get(i) : StandardNamesKt.syntheticColumnName(i);
                        return ExprValueExtensionsKt.namedValue(this.this$0.$conversionMode.convert(this.this$0.$valueFactory, raw), this.this$0.$valueFactory.newString(name));
                    }
                    {
                        this.this$0 = var1_1;
                        super(2);
                    }
                }), StructOrdering.ORDERED);
            }
            {
                this.$reader = bufferedReader;
                this.$valueFactory = exprValueFactory;
                this.$delimiter = string;
                this.$columns = list;
                this.$conversionMode = conversionMode;
                super(0);
            }
        });
        return valueFactory.newBag(seq2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final String csvStringValue(@NotNull IonValue $this$csvStringValue) {
        IonType ionType = $this$csvStringValue.getType();
        if (ionType == null) throw (Throwable)new IllegalArgumentException("Delimited data column must not be " + (Object)((Object)$this$csvStringValue.getType()) + " type");
        switch (DelimitedValues$WhenMappings.$EnumSwitchMapping$0[ionType.ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                String string = ((Object)$this$csvStringValue).toString();
                return string;
            }
            case 7: 
            case 8: {
                String string = IonValueExtensionsKt.stringValue($this$csvStringValue);
                if (string != null) return string;
                string = ((Object)$this$csvStringValue).toString();
                return string;
            }
            default: {
                throw (Throwable)new IllegalArgumentException("Delimited data column must not be " + (Object)((Object)$this$csvStringValue.getType()) + " type");
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @JvmStatic
    public static final void writeTo(@NotNull IonSystem ion, @NotNull Writer output, @NotNull ExprValue value, @NotNull String delimiter, @NotNull String newline, boolean writeHeader) {
        Intrinsics.checkParameterIsNotNull(ion, "ion");
        Intrinsics.checkParameterIsNotNull(output, "output");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(delimiter, "delimiter");
        Intrinsics.checkParameterIsNotNull(newline, "newline");
        IonNull nullValue = ion.newNull();
        List<String> names = null;
        for (ExprValue row : value) {
            void $this$mapTo$iv$iv;
            List<String> colNames;
            if (ExprValueExtensionsKt.getOrderedNames(row) == null) {
                throw (Throwable)new IllegalArgumentException("Delimited data must be ordered tuple: " + row);
            }
            if (names == null) {
                names = colNames;
                if (writeHeader) {
                    CollectionsKt.joinTo$default(names, output, delimiter, null, null, 0, null, null, 124, null);
                    output.write(newline);
                }
            } else if (Intrinsics.areEqual(names, colNames) ^ true) {
                throw (Throwable)new IllegalArgumentException("Inconsistent row names: " + colNames + " != " + names);
            }
            Iterable $this$map$iv = names;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                Object col;
                void it;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                Object object = row.getBindings().get(new BindingName((String)it, BindingCase.SENSITIVE));
                if (object == null || (object = object.getIonValue()) == null) {
                    object = nullValue;
                }
                Object object2 = col = object;
                Intrinsics.checkExpressionValueIsNotNull(object2, "col");
                String string2 = INSTANCE.csvStringValue((IonValue)object2);
                collection.add(string2);
            }
            CollectionsKt.joinTo$default((List)destination$iv$iv, output, delimiter, null, null, 0, null, null, 124, null);
            output.write(newline);
        }
    }

    private DelimitedValues() {
    }

    static {
        DelimitedValues delimitedValues;
        INSTANCE = delimitedValues = new DelimitedValues();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&j\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode;", "", "(Ljava/lang/String;I)V", "convert", "Lorg/partiql/lang/eval/ExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "raw", "", "AUTO", "NONE", "lang"})
    public static final abstract class ConversionMode
    extends Enum<ConversionMode> {
        public static final /* enum */ ConversionMode AUTO;
        public static final /* enum */ ConversionMode NONE;
        private static final /* synthetic */ ConversionMode[] $VALUES;

        static {
            ConversionMode[] conversionModeArray = new ConversionMode[2];
            ConversionMode[] conversionModeArray2 = conversionModeArray;
            conversionModeArray[0] = AUTO = new AUTO("AUTO", 0);
            conversionModeArray[1] = NONE = new NONE("NONE", 1);
            $VALUES = conversionModeArray;
        }

        @NotNull
        public abstract ExprValue convert(@NotNull ExprValueFactory var1, @NotNull String var2);

        private ConversionMode() {
        }

        public /* synthetic */ ConversionMode(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static ConversionMode[] values() {
            return (ConversionMode[])$VALUES.clone();
        }

        public static ConversionMode valueOf(String string) {
            return Enum.valueOf(ConversionMode.class, string);
        }

        @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode$AUTO;", "Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode;", "convert", "Lorg/partiql/lang/eval/ExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "raw", "", "lang"})
        static final class AUTO
        extends ConversionMode {
            @Override
            @NotNull
            public ExprValue convert(@NotNull ExprValueFactory valueFactory, @NotNull String raw) {
                ExprValue exprValue2;
                Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
                Intrinsics.checkParameterIsNotNull(raw, "raw");
                try {
                    IonValue converted;
                    IonValue ionValue2 = converted = valueFactory.getIon().singleValue(raw);
                    exprValue2 = ionValue2 instanceof IonInt || ionValue2 instanceof IonFloat || ionValue2 instanceof IonDecimal || ionValue2 instanceof IonTimestamp ? valueFactory.newFromIonValue(converted) : valueFactory.newString(raw);
                } catch (IonException e) {
                    exprValue2 = valueFactory.newString(raw);
                }
                return exprValue2;
            }

            /*
             * WARNING - void declaration
             */
            AUTO() {
                void var1_1;
            }
        }

        @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2={"Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode$NONE;", "Lorg/partiql/lang/eval/io/DelimitedValues$ConversionMode;", "convert", "Lorg/partiql/lang/eval/ExprValue;", "valueFactory", "Lorg/partiql/lang/eval/ExprValueFactory;", "raw", "", "lang"})
        static final class NONE
        extends ConversionMode {
            @Override
            @NotNull
            public ExprValue convert(@NotNull ExprValueFactory valueFactory, @NotNull String raw) {
                Intrinsics.checkParameterIsNotNull(valueFactory, "valueFactory");
                Intrinsics.checkParameterIsNotNull(raw, "raw");
                return valueFactory.newString(raw);
            }

            /*
             * WARNING - void declaration
             */
            NONE() {
                void var1_1;
            }
        }
    }
}

