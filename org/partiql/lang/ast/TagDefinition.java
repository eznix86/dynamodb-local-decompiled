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
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.ast.AstVersion;
import org.partiql.lang.ast.SexpValidationRules;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B)\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tB!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\nB/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\rB!\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\u0002\u0010\u0012R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0017"}, d2={"Lorg/partiql/lang/ast/TagDefinition;", "", "text", "", "version", "Lorg/partiql/lang/ast/AstVersion;", "arityFrom", "", "arityTo", "(Ljava/lang/String;Lorg/partiql/lang/ast/AstVersion;II)V", "(Ljava/lang/String;II)V", "versions", "", "(Ljava/lang/String;[Lorg/partiql/lang/ast/AstVersion;II)V", "tagText", "validationRules", "", "Lorg/partiql/lang/ast/SexpValidationRules;", "(Ljava/lang/String;Ljava/util/Map;)V", "getTagText", "()Ljava/lang/String;", "getValidationRules", "()Ljava/util/Map;", "lang"})
final class TagDefinition {
    @NotNull
    private final String tagText;
    @NotNull
    private final Map<AstVersion, SexpValidationRules> validationRules;

    @NotNull
    public final String getTagText() {
        return this.tagText;
    }

    @NotNull
    public final Map<AstVersion, SexpValidationRules> getValidationRules() {
        return this.validationRules;
    }

    public TagDefinition(@NotNull String tagText, @NotNull Map<AstVersion, SexpValidationRules> validationRules) {
        Intrinsics.checkParameterIsNotNull(tagText, "tagText");
        Intrinsics.checkParameterIsNotNull(validationRules, "validationRules");
        this.tagText = tagText;
        this.validationRules = validationRules;
    }

    public TagDefinition(@NotNull String text, @NotNull AstVersion version, int arityFrom, int arityTo) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull((Object)version, "version");
        this(text, MapsKt.mapOf(TuplesKt.to(version, new SexpValidationRules(arityFrom, arityTo))));
    }

    public /* synthetic */ TagDefinition(String string, AstVersion astVersion, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 8) != 0) {
            n2 = n;
        }
        this(string, astVersion, n, n2);
    }

    public TagDefinition(@NotNull String text, int arityFrom, int arityTo) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this(text, AstVersion.values(), arityFrom, arityTo);
    }

    public /* synthetic */ TagDefinition(String string, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 4) != 0) {
            n2 = n;
        }
        this(string, n, n2);
    }

    /*
     * WARNING - void declaration
     */
    public TagDefinition(@NotNull String text, @NotNull AstVersion[] versions, int arityFrom, int arityTo) {
        Collection<Pair<void, SexpValidationRules>> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(versions, "versions");
        AstVersion[] astVersionArray = versions;
        String string = text;
        TagDefinition tagDefinition = this;
        boolean $i$f$map = false;
        void var7_9 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        void var10_12 = $this$mapTo$iv$iv;
        int n = ((void)var10_12).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var14_16 = item$iv$iv = var10_12[i];
            collection = destination$iv$iv;
            boolean bl = false;
            Pair<void, SexpValidationRules> pair = new Pair<void, SexpValidationRules>(it, new SexpValidationRules(arityFrom, arityTo));
            collection.add(pair);
        }
        collection = (List)destination$iv$iv;
        tagDefinition(string, MapsKt.toMap((Iterable)collection));
    }

    public /* synthetic */ TagDefinition(String string, AstVersion[] astVersionArray, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 8) != 0) {
            n2 = n;
        }
        this(string, astVersionArray, n, n2);
    }
}

