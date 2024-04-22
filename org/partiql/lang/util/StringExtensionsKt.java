/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package org.partiql.lang.util;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.partiql.lang.util.StringExtensionsKt$codePointSequence$;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=2, d1={"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u00a8\u0006\u0004"}, d2={"codePointSequence", "Lkotlin/sequences/Sequence;", "", "", "lang"})
public final class StringExtensionsKt {
    @NotNull
    public static final Sequence<Integer> codePointSequence(@NotNull String $this$codePointSequence) {
        Intrinsics.checkParameterIsNotNull($this$codePointSequence, "$this$codePointSequence");
        String text = $this$codePointSequence;
        boolean bl = false;
        return new Sequence<Integer>(text){
            final /* synthetic */ String $text$inlined;
            {
                this.$text$inlined = string;
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public Iterator<Integer> iterator() {
                void pos;
                boolean bl = false;
                Ref.IntRef intRef = new Ref.IntRef();
                intRef.element = 0;
                return new Iterator<Integer>((Ref.IntRef)pos, this){
                    final /* synthetic */ Ref.IntRef $pos;
                    final /* synthetic */ codePointSequence$$inlined$Sequence$1 this$0;
                    {
                        this.$pos = $captured_local_variable$1;
                        this.this$0 = var2_2;
                    }

                    public boolean hasNext() {
                        return this.$pos.element < this.this$0.$text$inlined.length();
                    }

                    @NotNull
                    public Integer next() {
                        String string = this.this$0.$text$inlined;
                        int n = this.$pos.element;
                        boolean bl = false;
                        String string2 = string;
                        if (string2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        int cp = string2.codePointAt(n);
                        this.$pos.element += Character.charCount(cp);
                        return cp;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                    }
                };
            }
        };
    }
}

