/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.RequiresOptIn;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;

@MustBeDocumented
@kotlin.annotation.Retention(value=AnnotationRetention.BINARY)
@kotlin.annotation.Target(allowedTargets={AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.TYPEALIAS})
@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2={"Lkotlin/time/ExperimentalTime;", "", "kotlin-stdlib"})
@RequiresOptIn(level=RequiresOptIn.Level.ERROR)
@SinceKotlin(version="1.3")
public @interface ExperimentalTime {
}

