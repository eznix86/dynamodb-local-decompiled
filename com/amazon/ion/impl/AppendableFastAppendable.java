/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.util._Private_FastAppendable;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

final class AppendableFastAppendable
implements _Private_FastAppendable,
Closeable,
Flushable {
    private final Appendable _out;

    AppendableFastAppendable(Appendable out) {
        out.getClass();
        this._out = out;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        this._out.append(csq);
        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        this._out.append(c);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        this._out.append(csq, start, end);
        return this;
    }

    @Override
    public final void appendAscii(char c) throws IOException {
        this._out.append(c);
    }

    @Override
    public final void appendAscii(CharSequence csq) throws IOException {
        this._out.append(csq);
    }

    @Override
    public final void appendAscii(CharSequence csq, int start, int end) throws IOException {
        this._out.append(csq, start, end);
    }

    @Override
    public final void appendUtf16(char c) throws IOException {
        this._out.append(c);
    }

    @Override
    public final void appendUtf16Surrogate(char leadSurrogate, char trailSurrogate) throws IOException {
        this._out.append(leadSurrogate);
        this._out.append(trailSurrogate);
    }

    @Override
    public void flush() throws IOException {
        if (this._out instanceof Flushable) {
            ((Flushable)((Object)this._out)).flush();
        }
    }

    @Override
    public void close() throws IOException {
        if (this._out instanceof Closeable) {
            ((Closeable)((Object)this._out)).close();
        }
    }
}

