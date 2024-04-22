/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import java.util.Map;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.eval.Bindings;
import org.partiql.lang.eval.BindingsExtensionsKt;
import org.partiql.lang.eval.Environment$WhenMappings;
import org.partiql.lang.eval.EvaluationSession;
import org.partiql.lang.eval.ExprValue;
import org.partiql.lang.eval.ExprValueExtensionsKt;
import org.partiql.lang.eval.Group;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 02\u00020\u0001:\u000201BM\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c0\u0003\u00a2\u0006\u0002\b\u0017J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\u0015\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\nH\u00c6\u0003JU\u0010\u001c\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\r\u0010 \u001a\u00020\u0000H\u0000\u00a2\u0006\u0002\b!J\r\u0010\"\u001a\u00020\u0000H\u0000\u00a2\u0006\u0002\b#J\t\u0010$\u001a\u00020%H\u00d6\u0001J1\u0010&\u001a\u00020\u00002\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010(\u001a\u00020)2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\nH\u0000\u00a2\u0006\u0002\b+J\r\u0010,\u001a\u00020\u0000H\u0000\u00a2\u0006\u0002\b-J\t\u0010.\u001a\u00020/H\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u00062"}, d2={"Lorg/partiql/lang/eval/Environment;", "", "locals", "Lorg/partiql/lang/eval/Bindings;", "Lorg/partiql/lang/eval/ExprValue;", "current", "session", "Lorg/partiql/lang/eval/EvaluationSession;", "groups", "", "Lorg/partiql/lang/eval/Group;", "currentGroup", "(Lorg/partiql/lang/eval/Bindings;Lorg/partiql/lang/eval/Bindings;Lorg/partiql/lang/eval/EvaluationSession;Ljava/util/Map;Lorg/partiql/lang/eval/Group;)V", "getCurrent", "()Lorg/partiql/lang/eval/Bindings;", "getCurrentGroup", "()Lorg/partiql/lang/eval/Group;", "getGroups", "()Ljava/util/Map;", "getLocals$lang", "getSession", "()Lorg/partiql/lang/eval/EvaluationSession;", "component1", "component1$lang", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "flipToGlobalsFirst", "flipToGlobalsFirst$lang", "flipToLocals", "flipToLocals$lang", "hashCode", "", "nest", "newLocals", "currentMode", "Lorg/partiql/lang/eval/Environment$CurrentMode;", "newGroup", "nest$lang", "nestQuery", "nestQuery$lang", "toString", "", "Companion", "CurrentMode", "lang"})
public final class Environment {
    @NotNull
    private final Bindings<ExprValue> locals;
    @NotNull
    private final Bindings<ExprValue> current;
    @NotNull
    private final EvaluationSession session;
    @NotNull
    private final Map<ExprValue, Group> groups;
    @Nullable
    private final Group currentGroup;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final Environment nest$lang(@NotNull Bindings<ExprValue> newLocals, @NotNull CurrentMode currentMode, @Nullable Group newGroup) {
        Bindings<ExprValue> bindings2;
        Intrinsics.checkParameterIsNotNull(newLocals, "newLocals");
        Intrinsics.checkParameterIsNotNull((Object)currentMode, "currentMode");
        Bindings<ExprValue> derivedLocals = BindingsExtensionsKt.delegate(newLocals, this.locals);
        switch (Environment$WhenMappings.$EnumSwitchMapping$0[currentMode.ordinal()]) {
            case 1: {
                bindings2 = derivedLocals;
                break;
            }
            case 2: {
                bindings2 = BindingsExtensionsKt.delegate(this.session.getGlobals(), derivedLocals);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        Bindings<ExprValue> newCurrent = bindings2;
        return Environment.copy$default(this, derivedLocals, newCurrent, null, null, newGroup, 12, null);
    }

    public static /* synthetic */ Environment nest$lang$default(Environment environment, Bindings bindings2, CurrentMode currentMode, Group group2, int n, Object object) {
        if ((n & 2) != 0) {
            currentMode = CurrentMode.LOCALS;
        }
        if ((n & 4) != 0) {
            group2 = environment.currentGroup;
        }
        return environment.nest$lang(bindings2, currentMode, group2);
    }

    @NotNull
    public final Environment nestQuery$lang() {
        Map map2 = Environment.Companion.createGroupMap();
        Group group2 = null;
        return Environment.copy$default(this, null, null, null, map2, group2, 7, null);
    }

    @NotNull
    public final Environment flipToLocals$lang() {
        return Environment.copy$default(this, null, this.locals, null, null, null, 29, null);
    }

    @NotNull
    public final Environment flipToGlobalsFirst$lang() {
        return Environment.copy$default(this, null, BindingsExtensionsKt.delegate(this.session.getGlobals(), this.locals), null, null, null, 29, null);
    }

    @NotNull
    public final Bindings<ExprValue> getLocals$lang() {
        return this.locals;
    }

    @NotNull
    public final Bindings<ExprValue> getCurrent() {
        return this.current;
    }

    @NotNull
    public final EvaluationSession getSession() {
        return this.session;
    }

    @NotNull
    public final Map<ExprValue, Group> getGroups() {
        return this.groups;
    }

    @Nullable
    public final Group getCurrentGroup() {
        return this.currentGroup;
    }

    public Environment(@NotNull Bindings<ExprValue> locals, @NotNull Bindings<ExprValue> current, @NotNull EvaluationSession session, @NotNull Map<ExprValue, Group> groups2, @Nullable Group currentGroup) {
        Intrinsics.checkParameterIsNotNull(locals, "locals");
        Intrinsics.checkParameterIsNotNull(current, "current");
        Intrinsics.checkParameterIsNotNull(session, "session");
        Intrinsics.checkParameterIsNotNull(groups2, "groups");
        this.locals = locals;
        this.current = current;
        this.session = session;
        this.groups = groups2;
        this.currentGroup = currentGroup;
    }

    public /* synthetic */ Environment(Bindings bindings2, Bindings bindings3, EvaluationSession evaluationSession, Map map2, Group group2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bindings3 = bindings2;
        }
        if ((n & 8) != 0) {
            map2 = Environment.Companion.createGroupMap();
        }
        if ((n & 0x10) != 0) {
            group2 = null;
        }
        this(bindings2, bindings3, evaluationSession, map2, group2);
    }

    @NotNull
    public final Bindings<ExprValue> component1$lang() {
        return this.locals;
    }

    @NotNull
    public final Bindings<ExprValue> component2() {
        return this.current;
    }

    @NotNull
    public final EvaluationSession component3() {
        return this.session;
    }

    @NotNull
    public final Map<ExprValue, Group> component4() {
        return this.groups;
    }

    @Nullable
    public final Group component5() {
        return this.currentGroup;
    }

    @NotNull
    public final Environment copy(@NotNull Bindings<ExprValue> locals, @NotNull Bindings<ExprValue> current, @NotNull EvaluationSession session, @NotNull Map<ExprValue, Group> groups2, @Nullable Group currentGroup) {
        Intrinsics.checkParameterIsNotNull(locals, "locals");
        Intrinsics.checkParameterIsNotNull(current, "current");
        Intrinsics.checkParameterIsNotNull(session, "session");
        Intrinsics.checkParameterIsNotNull(groups2, "groups");
        return new Environment(locals, current, session, groups2, currentGroup);
    }

    public static /* synthetic */ Environment copy$default(Environment environment, Bindings bindings2, Bindings bindings3, EvaluationSession evaluationSession, Map map2, Group group2, int n, Object object) {
        if ((n & 1) != 0) {
            bindings2 = environment.locals;
        }
        if ((n & 2) != 0) {
            bindings3 = environment.current;
        }
        if ((n & 4) != 0) {
            evaluationSession = environment.session;
        }
        if ((n & 8) != 0) {
            map2 = environment.groups;
        }
        if ((n & 0x10) != 0) {
            group2 = environment.currentGroup;
        }
        return environment.copy(bindings2, bindings3, evaluationSession, map2, group2);
    }

    @NotNull
    public String toString() {
        return "Environment(locals=" + this.locals + ", current=" + this.current + ", session=" + this.session + ", groups=" + this.groups + ", currentGroup=" + this.currentGroup + ")";
    }

    public int hashCode() {
        Bindings<ExprValue> bindings2 = this.locals;
        Bindings<ExprValue> bindings3 = this.current;
        EvaluationSession evaluationSession = this.session;
        Map<ExprValue, Group> map2 = this.groups;
        Group group2 = this.currentGroup;
        return ((((bindings2 != null ? bindings2.hashCode() : 0) * 31 + (bindings3 != null ? bindings3.hashCode() : 0)) * 31 + (evaluationSession != null ? evaluationSession.hashCode() : 0)) * 31 + (map2 != null ? ((Object)map2).hashCode() : 0)) * 31 + (group2 != null ? group2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Environment)) break block3;
                Environment environment = (Environment)object;
                if (!Intrinsics.areEqual(this.locals, environment.locals) || !Intrinsics.areEqual(this.current, environment.current) || !Intrinsics.areEqual(this.session, environment.session) || !Intrinsics.areEqual(this.groups, environment.groups) || !Intrinsics.areEqual(this.currentGroup, environment.currentGroup)) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lorg/partiql/lang/eval/Environment$CurrentMode;", "", "(Ljava/lang/String;I)V", "LOCALS", "GLOBALS_THEN_LOCALS", "lang"})
    public static final class CurrentMode
    extends Enum<CurrentMode> {
        public static final /* enum */ CurrentMode LOCALS;
        public static final /* enum */ CurrentMode GLOBALS_THEN_LOCALS;
        private static final /* synthetic */ CurrentMode[] $VALUES;

        static {
            CurrentMode[] currentModeArray = new CurrentMode[2];
            CurrentMode[] currentModeArray2 = currentModeArray;
            currentModeArray[0] = LOCALS = new CurrentMode();
            currentModeArray[1] = GLOBALS_THEN_LOCALS = new CurrentMode();
            $VALUES = currentModeArray;
        }

        public static CurrentMode[] values() {
            return (CurrentMode[])$VALUES.clone();
        }

        public static CurrentMode valueOf(String string) {
            return Enum.valueOf(CurrentMode.class, string);
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0002J\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2={"Lorg/partiql/lang/eval/Environment$Companion;", "", "()V", "createGroupMap", "Ljava/util/TreeMap;", "Lorg/partiql/lang/eval/ExprValue;", "Lorg/partiql/lang/eval/Group;", "standard", "Lorg/partiql/lang/eval/Environment;", "lang"})
    public static final class Companion {
        @NotNull
        public final Environment standard() {
            return new Environment(Bindings.Companion.empty(), null, EvaluationSession.Companion.standard(), null, null, 26, null);
        }

        private final TreeMap<ExprValue, Group> createGroupMap() {
            return new TreeMap<ExprValue, Group>(ExprValueExtensionsKt.getDEFAULT_COMPARATOR());
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

