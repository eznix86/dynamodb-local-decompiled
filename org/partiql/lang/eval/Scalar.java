/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.Timestamp;
import java.time.LocalDate;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.time.Time;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011J\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0016\u00a2\u0006\u0002\u0010\u0004J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u0012"}, d2={"Lorg/partiql/lang/eval/Scalar;", "", "booleanValue", "", "()Ljava/lang/Boolean;", "bytesValue", "", "dateValue", "Ljava/time/LocalDate;", "numberValue", "", "stringValue", "", "timeValue", "Lorg/partiql/lang/eval/time/Time;", "timestampValue", "Lcom/amazon/ion/Timestamp;", "Companion", "lang"})
public interface Scalar {
    @JvmField
    @NotNull
    public static final Scalar EMPTY;
    public static final Companion Companion;

    @Nullable
    public Boolean booleanValue();

    @Nullable
    public Number numberValue();

    @Nullable
    public Timestamp timestampValue();

    @Nullable
    public LocalDate dateValue();

    @Nullable
    public Time timeValue();

    @Nullable
    public String stringValue();

    @Nullable
    public byte[] bytesValue();

    static {
        Companion = new Companion(null);
        EMPTY = new Scalar(){

            @Nullable
            public Boolean booleanValue() {
                return DefaultImpls.booleanValue(this);
            }

            @Nullable
            public Number numberValue() {
                return DefaultImpls.numberValue(this);
            }

            @Nullable
            public Timestamp timestampValue() {
                return DefaultImpls.timestampValue(this);
            }

            @Nullable
            public LocalDate dateValue() {
                return DefaultImpls.dateValue(this);
            }

            @Nullable
            public Time timeValue() {
                return DefaultImpls.timeValue(this);
            }

            @Nullable
            public String stringValue() {
                return DefaultImpls.stringValue(this);
            }

            @Nullable
            public byte[] bytesValue() {
                return DefaultImpls.bytesValue(this);
            }
        };
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        @Nullable
        public static Boolean booleanValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static Number numberValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static Timestamp timestampValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static LocalDate dateValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static Time timeValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static String stringValue(Scalar $this) {
            return null;
        }

        @Nullable
        public static byte[] bytesValue(Scalar $this) {
            return null;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/Scalar$Companion;", "", "()V", "EMPTY", "Lorg/partiql/lang/eval/Scalar;", "lang"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

