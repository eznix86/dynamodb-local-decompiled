/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.eval;

import com.amazon.ion.IonStruct;
import com.amazon.ion.IonValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.errors.Property;
import org.partiql.lang.errors.PropertyValueMap;
import org.partiql.lang.util.IonValueExtensionsKt;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bJ\t\u0010\f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n\u00a8\u0006\u001b"}, d2={"Lorg/partiql/lang/eval/NodeMetadata;", "", "struct", "Lcom/amazon/ion/IonStruct;", "(Lcom/amazon/ion/IonStruct;)V", "line", "", "column", "(JJ)V", "getColumn", "()J", "getLine", "component1", "component2", "copy", "equals", "", "other", "fillErrorContext", "Lorg/partiql/lang/errors/PropertyValueMap;", "errorContext", "hashCode", "", "toErrorContext", "properties", "toString", "", "lang"})
public final class NodeMetadata {
    private final long line;
    private final long column;

    @NotNull
    public final PropertyValueMap fillErrorContext(@NotNull PropertyValueMap errorContext) {
        Intrinsics.checkParameterIsNotNull(errorContext, "errorContext");
        if (errorContext.get(Property.LINE_NUMBER) == null && errorContext.get(Property.COLUMN_NUMBER) == null) {
            errorContext.set(Property.LINE_NUMBER, this.line);
            errorContext.set(Property.COLUMN_NUMBER, this.column);
        }
        return errorContext;
    }

    @Nullable
    public final PropertyValueMap toErrorContext() {
        return this.fillErrorContext(new PropertyValueMap(null, 1, null));
    }

    @NotNull
    public final PropertyValueMap toErrorContext(@NotNull PropertyValueMap properties) {
        Intrinsics.checkParameterIsNotNull(properties, "properties");
        return this.fillErrorContext(properties);
    }

    public final long getLine() {
        return this.line;
    }

    public final long getColumn() {
        return this.column;
    }

    public NodeMetadata(long line, long column) {
        this.line = line;
        this.column = column;
    }

    public NodeMetadata(@NotNull IonStruct struct) {
        Intrinsics.checkParameterIsNotNull(struct, "struct");
        IonValue ionValue2 = struct.get("line");
        Intrinsics.checkExpressionValueIsNotNull(ionValue2, "struct[\"line\"]");
        long l = IonValueExtensionsKt.longValue(ionValue2);
        IonValue ionValue3 = struct.get("column");
        Intrinsics.checkExpressionValueIsNotNull(ionValue3, "struct[\"column\"]");
        this(l, IonValueExtensionsKt.longValue(ionValue3));
    }

    public final long component1() {
        return this.line;
    }

    public final long component2() {
        return this.column;
    }

    @NotNull
    public final NodeMetadata copy(long line, long column) {
        return new NodeMetadata(line, column);
    }

    public static /* synthetic */ NodeMetadata copy$default(NodeMetadata nodeMetadata, long l, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            l = nodeMetadata.line;
        }
        if ((n & 2) != 0) {
            l2 = nodeMetadata.column;
        }
        return nodeMetadata.copy(l, l2);
    }

    @NotNull
    public String toString() {
        return "NodeMetadata(line=" + this.line + ", column=" + this.column + ")";
    }

    public int hashCode() {
        return Long.hashCode(this.line) * 31 + Long.hashCode(this.column);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NodeMetadata)) break block3;
                NodeMetadata nodeMetadata = (NodeMetadata)object;
                if (this.line != nodeMetadata.line || this.column != nodeMetadata.column) break block3;
            }
            return true;
        }
        return false;
    }
}

