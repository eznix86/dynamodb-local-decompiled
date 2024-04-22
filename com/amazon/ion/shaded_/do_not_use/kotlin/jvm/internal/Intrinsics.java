/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.shaded_.do_not_use.kotlin.jvm.internal;

import java.util.Arrays;

public class Intrinsics {
    private Intrinsics() {
    }

    public static void checkNotNull(Object object) {
        if (object == null) {
            Intrinsics.throwJavaNpe();
        }
    }

    public static void checkNotNull(Object object, String message) {
        if (object == null) {
            Intrinsics.throwJavaNpe(message);
        }
    }

    public static void throwJavaNpe() {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException());
    }

    public static void throwJavaNpe(String message) {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(message));
    }

    public static void checkNotNullParameter(Object value, String paramName) {
        if (value == null) {
            Intrinsics.throwParameterIsNullNPE(paramName);
        }
    }

    private static void throwParameterIsNullNPE(String paramName) {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(Intrinsics.createParameterIsNullExceptionMessage(paramName)));
    }

    private static String createParameterIsNullExceptionMessage(String paramName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String thisClassName = Intrinsics.class.getName();
        int i = 0;
        while (!stackTraceElements[i].getClassName().equals(thisClassName)) {
            ++i;
        }
        while (stackTraceElements[i].getClassName().equals(thisClassName)) {
            ++i;
        }
        StackTraceElement caller = stackTraceElements[i];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        return "Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + paramName;
    }

    private static <T extends Throwable> T sanitizeStackTrace(T throwable) {
        return Intrinsics.sanitizeStackTrace(throwable, Intrinsics.class.getName());
    }

    static <T extends Throwable> T sanitizeStackTrace(T throwable, String classNameToDrop) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;
        for (int i = 0; i < size; ++i) {
            if (!classNameToDrop.equals(stackTrace[i].getClassName())) continue;
            lastIntrinsic = i;
        }
        StackTraceElement[] newStackTrace = Arrays.copyOfRange(stackTrace, lastIntrinsic + 1, size);
        throwable.setStackTrace(newStackTrace);
        return throwable;
    }
}

