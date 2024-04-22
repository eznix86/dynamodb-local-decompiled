/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.errors;

import com.amazon.ion.IonValue;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.errors.PropertyType;
import org.partiql.lang.errors.PropertyValue$WhenMappings;
import org.partiql.lang.syntax.TokenType;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0015"}, d2={"Lorg/partiql/lang/errors/PropertyValue;", "", "type", "Lorg/partiql/lang/errors/PropertyType;", "(Lorg/partiql/lang/errors/PropertyType;)V", "getType", "()Lorg/partiql/lang/errors/PropertyType;", "value", "getValue", "()Ljava/lang/Object;", "integerValue", "", "ionValue", "Lcom/amazon/ion/IonValue;", "longValue", "", "stringValue", "", "toString", "tokenTypeValue", "Lorg/partiql/lang/syntax/TokenType;", "lang"})
public abstract class PropertyValue {
    @NotNull
    private final PropertyType type;

    @NotNull
    public String stringValue() {
        throw (Throwable)new IllegalArgumentException("Property value is of type " + (Object)((Object)this.type) + " and not String");
    }

    public long longValue() {
        throw (Throwable)new IllegalArgumentException("Property value is of type " + (Object)((Object)this.type) + " and not Long");
    }

    @NotNull
    public TokenType tokenTypeValue() {
        throw (Throwable)new IllegalArgumentException("Property value is of type " + (Object)((Object)this.type) + " and not TokenType");
    }

    public int integerValue() {
        throw (Throwable)new IllegalArgumentException("Property value is of type " + (Object)((Object)this.type) + " and not Integer");
    }

    @NotNull
    public IonValue ionValue() {
        throw (Throwable)new IllegalArgumentException("Property value is of type " + (Object)((Object)this.type) + " and not IonValue");
    }

    @NotNull
    public final Object getValue() {
        Object object;
        switch (PropertyValue$WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()]) {
            case 1: {
                object = this.longValue();
                break;
            }
            case 2: {
                object = this.stringValue();
                break;
            }
            case 3: {
                object = this.integerValue();
                break;
            }
            case 4: {
                object = this.tokenTypeValue();
                break;
            }
            case 5: {
                object = this.ionValue();
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return object;
    }

    @NotNull
    public String toString() {
        String string;
        switch (PropertyValue$WhenMappings.$EnumSwitchMapping$1[this.type.ordinal()]) {
            case 1: {
                Object object = this.getValue();
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.amazon.ion.IonValue");
                }
                String string2 = ((IonValue)object).toPrettyString();
                string = string2;
                Intrinsics.checkExpressionValueIsNotNull(string2, "(value as IonValue).toPrettyString()");
                break;
            }
            default: {
                string = this.getValue().toString();
            }
        }
        return string;
    }

    @NotNull
    public final PropertyType getType() {
        return this.type;
    }

    public PropertyValue(@NotNull PropertyType type) {
        Intrinsics.checkParameterIsNotNull((Object)type, "type");
        this.type = type;
    }
}

