/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a8\u0006\u0013"}, d2={"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "getSuppressed", "", "ReflectThrowable", "kotlin-stdlib"})
public class PlatformImplementations {
    public void addSuppressed(@NotNull Throwable cause, @NotNull Throwable exception) {
        block0: {
            Intrinsics.checkNotNullParameter(cause, "cause");
            Intrinsics.checkNotNullParameter(exception, "exception");
            Method method = ReflectThrowable.addSuppressed;
            if (method == null) break block0;
            Object[] objectArray = new Object[]{exception};
            method.invoke(cause, objectArray);
        }
    }

    @NotNull
    public List<Throwable> getSuppressed(@NotNull Throwable exception) {
        Object object;
        block3: {
            block2: {
                List<Throwable> list;
                Intrinsics.checkNotNullParameter(exception, "exception");
                object = ReflectThrowable.getSuppressed;
                if (object == null || (object = ((Method)object).invoke(exception, new Object[0])) == null) break block2;
                List<Throwable> it = list = object;
                boolean bl = false;
                List<Throwable> list2 = ArraysKt.asList((Throwable[])it);
                object = list2;
                if (list2 != null) break block3;
            }
            object = CollectionsKt.emptyList();
        }
        return object;
    }

    @Nullable
    public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
        Intrinsics.checkNotNullParameter(matchResult, "matchResult");
        Intrinsics.checkNotNullParameter(name, "name");
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    @NotNull
    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lkotlin/internal/PlatformImplementations$ReflectThrowable;", "", "()V", "addSuppressed", "Ljava/lang/reflect/Method;", "getSuppressed", "kotlin-stdlib"})
    private static final class ReflectThrowable {
        @NotNull
        public static final ReflectThrowable INSTANCE;
        @JvmField
        @Nullable
        public static final Method addSuppressed;
        @JvmField
        @Nullable
        public static final Method getSuppressed;

        private ReflectThrowable() {
        }

        /*
         * Unable to fully structure code
         */
        static {
            block5: {
                block4: {
                    ReflectThrowable.INSTANCE = new ReflectThrowable();
                    throwableClass = Throwable.class;
                    throwableMethods = throwableClass.getMethods();
                    Intrinsics.checkNotNullExpressionValue(throwableMethods, "throwableMethods");
                    var2_2 = throwableMethods;
                    var4_4 = var2_2.length;
                    for (var3_3 = 0; var3_3 < var4_4; ++var3_3) {
                        it = var5_5 = var2_2[var3_3];
                        $i$a$-find-PlatformImplementations$ReflectThrowable$1 = false;
                        if (!Intrinsics.areEqual(it.getName(), "addSuppressed")) ** GOTO lbl-1000
                        v0 = it.getParameterTypes();
                        Intrinsics.checkNotNullExpressionValue(v0, "it.parameterTypes");
                        if (Intrinsics.areEqual(ArraysKt.singleOrNull((Object[])v0), throwableClass)) {
                            v1 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v1 = false;
                        }
                        if (!v1) continue;
                        v2 = var5_5;
                        break block4;
                    }
                    v2 = null;
                }
                ReflectThrowable.addSuppressed = v2;
                var2_2 = throwableMethods;
                var4_4 = var2_2.length;
                for (var3_3 = 0; var3_3 < var4_4; ++var3_3) {
                    it = var5_5 = var2_2[var3_3];
                    $i$a$-find-PlatformImplementations$ReflectThrowable$2 = false;
                    if (!Intrinsics.areEqual(it.getName(), "getSuppressed")) continue;
                    v3 = var5_5;
                    break block5;
                }
                v3 = null;
            }
            ReflectThrowable.getSuppressed = v3;
        }
    }
}

