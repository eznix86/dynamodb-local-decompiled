/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.util;

import com.amazon.ion.IonValue;
import com.amazon.ion.system.IonTextWriterBuilder;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.util.ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings;
import org.partiql.lang.util.ExprValueFormatter;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \f2\u00020\u0001:\u0003\f\r\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lorg/partiql/lang/util/ConfigurableExprValueFormatter;", "Lorg/partiql/lang/util/ExprValueFormatter;", "config", "Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;", "(Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;)V", "formatTo", "", "value", "Lorg/partiql/lang/eval/ExprValue;", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "Companion", "Configuration", "PrettyFormatter", "lang"})
public final class ConfigurableExprValueFormatter
implements ExprValueFormatter {
    private final Configuration config;
    @NotNull
    private static final ConfigurableExprValueFormatter pretty;
    @NotNull
    private static final ConfigurableExprValueFormatter standard;
    public static final Companion Companion;

    @Override
    public void formatTo(@NotNull ExprValue value, @NotNull Appendable out) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(out, "out");
        new PrettyFormatter(out, this.config).recursivePrettyPrint(value);
    }

    public ConfigurableExprValueFormatter(@NotNull Configuration config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.config = config;
    }

    static {
        Companion = new Companion(null);
        pretty = new ConfigurableExprValueFormatter(new Configuration("  ", "\n"));
        standard = new ConfigurableExprValueFormatter(new Configuration("", ""));
    }

    @Override
    @NotNull
    public String format(@NotNull ExprValue value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return ExprValueFormatter.DefaultImpls.format(this, value);
    }

    @NotNull
    public static final ConfigurableExprValueFormatter getPretty() {
        Companion companion = Companion;
        return pretty;
    }

    @NotNull
    public static final ConfigurableExprValueFormatter getStandard() {
        Companion companion = Companion;
        return standard;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;", "", "indentation", "", "containerValueSeparator", "(Ljava/lang/String;Ljava/lang/String;)V", "getContainerValueSeparator", "()Ljava/lang/String;", "getIndentation", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "lang"})
    public static final class Configuration {
        @NotNull
        private final String indentation;
        @NotNull
        private final String containerValueSeparator;

        @NotNull
        public final String getIndentation() {
            return this.indentation;
        }

        @NotNull
        public final String getContainerValueSeparator() {
            return this.containerValueSeparator;
        }

        public Configuration(@NotNull String indentation, @NotNull String containerValueSeparator) {
            Intrinsics.checkParameterIsNotNull(indentation, "indentation");
            Intrinsics.checkParameterIsNotNull(containerValueSeparator, "containerValueSeparator");
            this.indentation = indentation;
            this.containerValueSeparator = containerValueSeparator;
        }

        @NotNull
        public final String component1() {
            return this.indentation;
        }

        @NotNull
        public final String component2() {
            return this.containerValueSeparator;
        }

        @NotNull
        public final Configuration copy(@NotNull String indentation, @NotNull String containerValueSeparator) {
            Intrinsics.checkParameterIsNotNull(indentation, "indentation");
            Intrinsics.checkParameterIsNotNull(containerValueSeparator, "containerValueSeparator");
            return new Configuration(indentation, containerValueSeparator);
        }

        public static /* synthetic */ Configuration copy$default(Configuration configuration, String string, String string2, int n, Object object) {
            if ((n & 1) != 0) {
                string = configuration.indentation;
            }
            if ((n & 2) != 0) {
                string2 = configuration.containerValueSeparator;
            }
            return configuration.copy(string, string2);
        }

        @NotNull
        public String toString() {
            return "Configuration(indentation=" + this.indentation + ", containerValueSeparator=" + this.containerValueSeparator + ")";
        }

        public int hashCode() {
            String string = this.indentation;
            String string2 = this.containerValueSeparator;
            return (string != null ? string.hashCode() : 0) * 31 + (string2 != null ? string2.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Configuration)) break block3;
                    Configuration configuration = (Configuration)object;
                    if (!Intrinsics.areEqual(this.indentation, configuration.indentation) || !Intrinsics.areEqual(this.containerValueSeparator, configuration.containerValueSeparator)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J6\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000f0\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0019\u001a\u00020\u000fH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0015\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001a"}, d2={"Lorg/partiql/lang/util/ConfigurableExprValueFormatter$PrettyFormatter;", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "config", "Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;", "(Ljava/lang/Appendable;Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;)V", "getConfig", "()Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Configuration;", "currentIndentation", "", "getOut", "()Ljava/lang/Appendable;", "prettyPrintContainer", "", "value", "Lorg/partiql/lang/eval/ExprValue;", "openingMarker", "", "closingMarker", "prettyPrintElement", "Lkotlin/Function1;", "prettyPrintIonLiteral", "recursivePrettyPrint", "writeIndentation", "lang"})
    private static final class PrettyFormatter {
        private int currentIndentation;
        @NotNull
        private final Appendable out;
        @NotNull
        private final Configuration config;

        public final void recursivePrettyPrint(@NotNull ExprValue value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            switch (ConfigurableExprValueFormatter$PrettyFormatter$WhenMappings.$EnumSwitchMapping$0[value.getType().ordinal()]) {
                case 1: {
                    this.out.append("MISSING");
                    break;
                }
                case 2: {
                    this.out.append("NULL");
                    break;
                }
                case 3: {
                    this.out.append(String.valueOf(value.getScalar().booleanValue()));
                    break;
                }
                case 4: 
                case 5: {
                    this.out.append(String.valueOf(value.getScalar().numberValue()));
                    break;
                }
                case 6: {
                    this.out.append('\'' + value.getScalar().stringValue() + '\'');
                    break;
                }
                case 7: {
                    this.out.append(String.valueOf(value.getScalar().dateValue()));
                    break;
                }
                case 8: {
                    this.out.append(String.valueOf(value.getScalar().timeValue()));
                    break;
                }
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: {
                    this.prettyPrintIonLiteral(value);
                    break;
                }
                case 15: {
                    PrettyFormatter.prettyPrintContainer$default(this, value, "[", "]", null, 8, null);
                    break;
                }
                case 16: {
                    PrettyFormatter.prettyPrintContainer$default(this, value, "<<", ">>", null, 8, null);
                    break;
                }
                case 17: {
                    this.prettyPrintContainer(value, "{", "}", (Function1<? super ExprValue, Unit>)new Function1<ExprValue, Unit>(this){
                        final /* synthetic */ PrettyFormatter this$0;

                        public final void invoke(@NotNull ExprValue v) {
                            Intrinsics.checkParameterIsNotNull(v, "v");
                            ExprValue exprValue2 = ExprValueExtensionsKt.getName(v);
                            if (exprValue2 == null) {
                                Intrinsics.throwNpe();
                            }
                            String fieldName = exprValue2.getScalar().stringValue();
                            this.this$0.getOut().append('\'' + fieldName + "': ");
                            this.this$0.recursivePrettyPrint(v);
                        }
                        {
                            this.this$0 = prettyFormatter;
                            super(1);
                        }
                    });
                }
            }
        }

        private final void prettyPrintContainer(ExprValue value, String openingMarker, String closingMarker, Function1<? super ExprValue, Unit> prettyPrintElement) {
            Iterator<ExprValue> iterator2 = value.iterator();
            if (iterator2.hasNext()) {
                this.out.append(openingMarker).append(this.config.getContainerValueSeparator());
                ++this.currentIndentation;
                ExprValue firstElement = iterator2.next();
                this.writeIndentation();
                prettyPrintElement.invoke(firstElement);
                Iterator<ExprValue> $this$forEach$iv = iterator2;
                boolean $i$f$forEach = false;
                Iterator<ExprValue> iterator3 = $this$forEach$iv;
                boolean bl = false;
                Iterator<ExprValue> iterator4 = iterator3;
                while (iterator4.hasNext()) {
                    ExprValue element$iv;
                    ExprValue v = element$iv = iterator4.next();
                    boolean bl2 = false;
                    this.out.append(",");
                    CharSequence charSequence = this.config.getContainerValueSeparator();
                    boolean bl3 = false;
                    if (charSequence.length() == 0) {
                        this.out.append(" ");
                    } else {
                        this.out.append(this.config.getContainerValueSeparator());
                    }
                    this.writeIndentation();
                    prettyPrintElement.invoke(v);
                }
                --this.currentIndentation;
                this.out.append(this.config.getContainerValueSeparator());
                this.writeIndentation();
                this.out.append(closingMarker);
            } else {
                this.out.append(openingMarker).append(closingMarker);
            }
        }

        static /* synthetic */ void prettyPrintContainer$default(PrettyFormatter prettyFormatter, ExprValue exprValue2, String string, String string2, Function1 function1, int n, Object object) {
            if ((n & 8) != 0) {
                function1 = new Function1<ExprValue, Unit>(prettyFormatter){
                    final /* synthetic */ PrettyFormatter this$0;

                    public final void invoke(@NotNull ExprValue v) {
                        Intrinsics.checkParameterIsNotNull(v, "v");
                        this.this$0.recursivePrettyPrint(v);
                    }
                    {
                        this.this$0 = prettyFormatter;
                        super(1);
                    }
                };
            }
            prettyFormatter.prettyPrintContainer(exprValue2, string, string2, function1);
        }

        private final void prettyPrintIonLiteral(ExprValue value) {
            IonValue ionValue2 = value.getIonValue();
            this.out.append("`");
            ionValue2.writeTo(IonTextWriterBuilder.standard().build(this.out));
            this.out.append("`");
        }

        private final void writeIndentation() {
            CharSequence charSequence = this.config.getIndentation();
            boolean bl = false;
            if (charSequence.length() > 0) {
                Iterable $this$forEach$iv = new IntRange(1, this.currentIndentation);
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv.iterator();
                while (iterator2.hasNext()) {
                    int element$iv;
                    int $noName_0 = element$iv = ((IntIterator)iterator2).nextInt();
                    boolean bl2 = false;
                    this.out.append(this.config.getIndentation());
                }
            }
        }

        @NotNull
        public final Appendable getOut() {
            return this.out;
        }

        @NotNull
        public final Configuration getConfig() {
            return this.config;
        }

        public PrettyFormatter(@NotNull Appendable out, @NotNull Configuration config) {
            Intrinsics.checkParameterIsNotNull(out, "out");
            Intrinsics.checkParameterIsNotNull(config, "config");
            this.out = out;
            this.config = config;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lorg/partiql/lang/util/ConfigurableExprValueFormatter$Companion;", "", "()V", "pretty", "Lorg/partiql/lang/util/ConfigurableExprValueFormatter;", "pretty$annotations", "getPretty", "()Lorg/partiql/lang/util/ConfigurableExprValueFormatter;", "standard", "standard$annotations", "getStandard", "lang"})
    public static final class Companion {
        @JvmStatic
        public static /* synthetic */ void pretty$annotations() {
        }

        @NotNull
        public final ConfigurableExprValueFormatter getPretty() {
            return pretty;
        }

        @JvmStatic
        public static /* synthetic */ void standard$annotations() {
        }

        @NotNull
        public final ConfigurableExprValueFormatter getStandard() {
            return standard;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

