/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package org.partiql.lang.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.partiql.lang.ast.AstNode;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.MetaContainer;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2={"Lorg/partiql/lang/ast/DateTimeType;", "Lorg/partiql/lang/ast/ExprNode;", "()V", "Date", "Time", "Lorg/partiql/lang/ast/DateTimeType$Date;", "Lorg/partiql/lang/ast/DateTimeType$Time;", "lang"})
public abstract class DateTimeType
extends ExprNode {
    private DateTimeType() {
        super(null);
    }

    public /* synthetic */ DateTimeType(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006 "}, d2={"Lorg/partiql/lang/ast/DateTimeType$Date;", "Lorg/partiql/lang/ast/DateTimeType;", "year", "", "month", "day", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(IIILorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getDay", "()I", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getMonth", "getYear", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "toString", "", "lang"})
    public static final class Date
    extends DateTimeType {
        @NotNull
        private final List<AstNode> children;
        private final int year;
        private final int month;
        private final int day;
        @NotNull
        private final MetaContainer metas;

        @Override
        @NotNull
        public List<AstNode> getChildren() {
            return this.children;
        }

        public final int getYear() {
            return this.year;
        }

        public final int getMonth() {
            return this.month;
        }

        public final int getDay() {
            return this.day;
        }

        @Override
        @NotNull
        public MetaContainer getMetas() {
            return this.metas;
        }

        public Date(int year2, int month, int day, @NotNull MetaContainer metas) {
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            super(null);
            this.year = year2;
            this.month = month;
            this.day = day;
            this.metas = metas;
            Date date = this;
            boolean bl = false;
            List list = CollectionsKt.emptyList();
            date.children = list;
        }

        public final int component1() {
            return this.year;
        }

        public final int component2() {
            return this.month;
        }

        public final int component3() {
            return this.day;
        }

        @NotNull
        public final MetaContainer component4() {
            return this.getMetas();
        }

        @NotNull
        public final Date copy(int year2, int month, int day, @NotNull MetaContainer metas) {
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            return new Date(year2, month, day, metas);
        }

        public static /* synthetic */ Date copy$default(Date date, int n, int n2, int n3, MetaContainer metaContainer, int n4, Object object) {
            if ((n4 & 1) != 0) {
                n = date.year;
            }
            if ((n4 & 2) != 0) {
                n2 = date.month;
            }
            if ((n4 & 4) != 0) {
                n3 = date.day;
            }
            if ((n4 & 8) != 0) {
                metaContainer = date.getMetas();
            }
            return date.copy(n, n2, n3, metaContainer);
        }

        @NotNull
        public String toString() {
            return "Date(year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", metas=" + this.getMetas() + ")";
        }

        public int hashCode() {
            MetaContainer metaContainer = this.getMetas();
            return ((Integer.hashCode(this.year) * 31 + Integer.hashCode(this.month)) * 31 + Integer.hashCode(this.day)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Date)) break block3;
                    Date date = (Date)object;
                    if (this.year != date.year || this.month != date.month || this.day != date.day || !Intrinsics.areEqual(this.getMetas(), date.getMetas())) break block3;
                }
                return true;
            }
            return false;
        }
    }

    @Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\t\u0010\"\u001a\u00020\nH\u00c6\u0003JV\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(H\u00d6\u0003J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001J\t\u0010*\u001a\u00020+H\u00d6\u0001R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006,"}, d2={"Lorg/partiql/lang/ast/DateTimeType$Time;", "Lorg/partiql/lang/ast/DateTimeType;", "hour", "", "minute", "second", "nano", "precision", "tz_minutes", "metas", "Lorg/partiql/lang/ast/MetaContainer;", "(IIIIILjava/lang/Integer;Lorg/partiql/lang/ast/MetaContainer;)V", "children", "", "Lorg/partiql/lang/ast/AstNode;", "getChildren", "()Ljava/util/List;", "getHour", "()I", "getMetas", "()Lorg/partiql/lang/ast/MetaContainer;", "getMinute", "getNano", "getPrecision", "getSecond", "getTz_minutes", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(IIIIILjava/lang/Integer;Lorg/partiql/lang/ast/MetaContainer;)Lorg/partiql/lang/ast/DateTimeType$Time;", "equals", "", "other", "", "hashCode", "toString", "", "lang"})
    public static final class Time
    extends DateTimeType {
        @NotNull
        private final List<AstNode> children;
        private final int hour;
        private final int minute;
        private final int second;
        private final int nano;
        private final int precision;
        @Nullable
        private final Integer tz_minutes;
        @NotNull
        private final MetaContainer metas;

        @Override
        @NotNull
        public List<AstNode> getChildren() {
            return this.children;
        }

        public final int getHour() {
            return this.hour;
        }

        public final int getMinute() {
            return this.minute;
        }

        public final int getSecond() {
            return this.second;
        }

        public final int getNano() {
            return this.nano;
        }

        public final int getPrecision() {
            return this.precision;
        }

        @Nullable
        public final Integer getTz_minutes() {
            return this.tz_minutes;
        }

        @Override
        @NotNull
        public MetaContainer getMetas() {
            return this.metas;
        }

        public Time(int hour, int minute, int second, int nano, int precision, @Nullable Integer tz_minutes, @NotNull MetaContainer metas) {
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            super(null);
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.nano = nano;
            this.precision = precision;
            this.tz_minutes = tz_minutes;
            this.metas = metas;
            Time time = this;
            boolean bl = false;
            List list = CollectionsKt.emptyList();
            time.children = list;
        }

        public /* synthetic */ Time(int n, int n2, int n3, int n4, int n5, Integer n6, MetaContainer metaContainer, int n7, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n7 & 0x20) != 0) {
                n6 = null;
            }
            this(n, n2, n3, n4, n5, n6, metaContainer);
        }

        public final int component1() {
            return this.hour;
        }

        public final int component2() {
            return this.minute;
        }

        public final int component3() {
            return this.second;
        }

        public final int component4() {
            return this.nano;
        }

        public final int component5() {
            return this.precision;
        }

        @Nullable
        public final Integer component6() {
            return this.tz_minutes;
        }

        @NotNull
        public final MetaContainer component7() {
            return this.getMetas();
        }

        @NotNull
        public final Time copy(int hour, int minute, int second, int nano, int precision, @Nullable Integer tz_minutes, @NotNull MetaContainer metas) {
            Intrinsics.checkParameterIsNotNull(metas, "metas");
            return new Time(hour, minute, second, nano, precision, tz_minutes, metas);
        }

        public static /* synthetic */ Time copy$default(Time time, int n, int n2, int n3, int n4, int n5, Integer n6, MetaContainer metaContainer, int n7, Object object) {
            if ((n7 & 1) != 0) {
                n = time.hour;
            }
            if ((n7 & 2) != 0) {
                n2 = time.minute;
            }
            if ((n7 & 4) != 0) {
                n3 = time.second;
            }
            if ((n7 & 8) != 0) {
                n4 = time.nano;
            }
            if ((n7 & 0x10) != 0) {
                n5 = time.precision;
            }
            if ((n7 & 0x20) != 0) {
                n6 = time.tz_minutes;
            }
            if ((n7 & 0x40) != 0) {
                metaContainer = time.getMetas();
            }
            return time.copy(n, n2, n3, n4, n5, n6, metaContainer);
        }

        @NotNull
        public String toString() {
            return "Time(hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", nano=" + this.nano + ", precision=" + this.precision + ", tz_minutes=" + this.tz_minutes + ", metas=" + this.getMetas() + ")";
        }

        public int hashCode() {
            Integer n = this.tz_minutes;
            MetaContainer metaContainer = this.getMetas();
            return (((((Integer.hashCode(this.hour) * 31 + Integer.hashCode(this.minute)) * 31 + Integer.hashCode(this.second)) * 31 + Integer.hashCode(this.nano)) * 31 + Integer.hashCode(this.precision)) * 31 + (n != null ? ((Object)n).hashCode() : 0)) * 31 + (metaContainer != null ? metaContainer.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Time)) break block3;
                    Time time = (Time)object;
                    if (this.hour != time.hour || this.minute != time.minute || this.second != time.second || this.nano != time.nano || this.precision != time.precision || !Intrinsics.areEqual(this.tz_minutes, time.tz_minutes) || !Intrinsics.areEqual(this.getMetas(), time.getMetas())) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

