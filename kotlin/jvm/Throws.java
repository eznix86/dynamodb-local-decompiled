/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;

@kotlin.annotation.Target(allowedTargets={AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CONSTRUCTOR})
@kotlin.annotation.Retention(value=AnnotationRetention.SOURCE)
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B$\u0012\"\u0010\u0002\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u0003\"\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004R\u001f\u0010\u0002\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/jvm/Throws;", "", "exceptionClasses", "", "Lkotlin/reflect/KClass;", "", "()[Ljava/lang/Class;", "kotlin-stdlib"})
public @interface Throws {
    public Class<? extends Throwable>[] exceptionClasses();
}

