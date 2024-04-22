/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.FunctionReference;
import kotlin.reflect.KFunction;

@SinceKotlin(version="1.7")
public class FunInterfaceConstructorReference
extends FunctionReference
implements Serializable {
    private final Class funInterface;

    public FunInterfaceConstructorReference(Class funInterface) {
        super(1);
        this.funInterface = funInterface;
    }

    @Override
    public boolean equals(Object o2) {
        if (this == o2) {
            return true;
        }
        if (!(o2 instanceof FunInterfaceConstructorReference)) {
            return false;
        }
        FunInterfaceConstructorReference other = (FunInterfaceConstructorReference)o2;
        return this.funInterface.equals(other.funInterface);
    }

    @Override
    public int hashCode() {
        return this.funInterface.hashCode();
    }

    @Override
    public String toString() {
        return "fun interface " + this.funInterface.getName();
    }

    @Override
    protected KFunction getReflected() {
        throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
    }
}

