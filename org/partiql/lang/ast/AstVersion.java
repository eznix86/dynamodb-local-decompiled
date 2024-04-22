/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0001\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/ast/AstVersion;", "", "number", "", "(Ljava/lang/String;II)V", "getNumber", "()I", "V0", "Companion", "lang"})
public final class AstVersion
extends Enum<AstVersion> {
    public static final /* enum */ AstVersion V0;
    private static final /* synthetic */ AstVersion[] $VALUES;
    private final int number;
    @NotNull
    private static final String versionsAsString;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_4;
        Collection<Integer> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        V0 = new AstVersion(0);
        $VALUES = new AstVersion[]{V0};
        Companion = new Companion(null);
        AstVersion[] astVersionArray = AstVersion.values();
        AstVersion[] astVersionArray2 = $VALUES;
        boolean $i$f$map = false;
        void var2_3 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        void var5_6 = $this$mapTo$iv$iv;
        int n = ((void)var5_6).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var9_10 = item$iv$iv = var5_6[i];
            collection = destination$iv$iv;
            boolean bl = false;
            Integer n2 = it.number;
            collection.add(n2);
        }
        collection = (List)var3_4;
        AstVersion[] astVersionArray3 = astVersionArray2;
        versionsAsString = CollectionsKt.joinToString$default(collection, ", ", null, null, 0, null, null, 62, null);
    }

    public final int getNumber() {
        return this.number;
    }

    private AstVersion(int number) {
        this.number = number;
    }

    public static AstVersion[] values() {
        return (AstVersion[])$VALUES.clone();
    }

    public static AstVersion valueOf(String string) {
        return Enum.valueOf(AstVersion.class, string);
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/AstVersion$Companion;", "", "()V", "versionsAsString", "", "getVersionsAsString", "()Ljava/lang/String;", "lang"})
    public static final class Companion {
        @NotNull
        public final String getVersionsAsString() {
            return versionsAsString;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

