/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/DeprecationLevel;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "HIDDEN", "kotlin-stdlib"})
public final class DeprecationLevel
extends Enum<DeprecationLevel> {
    public static final /* enum */ DeprecationLevel WARNING = new DeprecationLevel();
    public static final /* enum */ DeprecationLevel ERROR = new DeprecationLevel();
    public static final /* enum */ DeprecationLevel HIDDEN = new DeprecationLevel();
    private static final /* synthetic */ DeprecationLevel[] $VALUES;

    public static DeprecationLevel[] values() {
        return (DeprecationLevel[])$VALUES.clone();
    }

    public static DeprecationLevel valueOf(String value) {
        return Enum.valueOf(DeprecationLevel.class, value);
    }

    static {
        $VALUES = deprecationLevelArray = new DeprecationLevel[]{DeprecationLevel.WARNING, DeprecationLevel.ERROR, DeprecationLevel.HIDDEN};
    }
}

