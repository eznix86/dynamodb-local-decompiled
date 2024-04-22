/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.util._Private_FastAppendable;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class _Private_FastAppendableDecorator
implements _Private_FastAppendable,
Closeable,
Flushable {
    private final _Private_FastAppendable myOutput;

    public _Private_FastAppendableDecorator(_Private_FastAppendable output) {
        this.myOutput = output;
    }

    @Override
    public void flush() throws IOException {
        if (this.myOutput instanceof Flushable) {
            ((Flushable)((Object)this.myOutput)).flush();
        }
    }

    @Override
    public void close() throws IOException {
        if (this.myOutput instanceof Closeable) {
            ((Closeable)((Object)this.myOutput)).close();
        }
    }

    @Override
    public Appendable append(char c) throws IOException {
        this.myOutput.append(c);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        this.myOutput.append(csq);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        this.myOutput.append(csq, start, end);
        return this;
    }

    @Override
    public void appendAscii(char c) throws IOException {
        this.myOutput.appendAscii(c);
    }

    @Override
    public void appendAscii(CharSequence csq) throws IOException {
        this.myOutput.appendAscii(csq);
    }

    @Override
    public void appendAscii(CharSequence csq, int start, int end) throws IOException {
        this.myOutput.appendAscii(csq, start, end);
    }

    @Override
    public void appendUtf16(char c) throws IOException {
        this.myOutput.appendUtf16(c);
    }

    @Override
    public void appendUtf16Surrogate(char leadSurrogate, char trailSurrogate) throws IOException {
        this.myOutput.appendUtf16Surrogate(leadSurrogate, trailSurrogate);
    }
}

