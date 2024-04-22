/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.LinkedList;

final class IonCharacterReader
extends PushbackReader {
    public static final int DEFAULT_BUFFER_SIZE = 12;
    public static final int BUFFER_PADDING = 1;
    private long m_consumed;
    private int m_line;
    private int m_column;
    private int m_size;
    private LinkedList<Integer> m_columns;

    public IonCharacterReader(Reader in, int size) {
        super(in, size + 1);
        assert (size > 0);
        this.m_consumed = 0L;
        this.m_line = 1;
        this.m_column = 0;
        this.m_columns = new LinkedList();
        this.m_size = size + 1;
    }

    public IonCharacterReader(Reader in) {
        this(in, 12);
    }

    public final long getConsumedAmount() {
        return this.m_consumed;
    }

    public final int getLineNumber() {
        return this.m_line;
    }

    public final int getColumn() {
        return this.m_column;
    }

    @Override
    public int read() throws IOException {
        int nextChar = super.read();
        if (nextChar != -1) {
            if (nextChar == 10) {
                ++this.m_line;
                this.pushColumn(this.m_column);
                this.m_column = 0;
            } else if (nextChar == 13) {
                int aheadChar = super.read();
                if (aheadChar != 10) {
                    this.unreadImpl(aheadChar, false);
                }
                ++this.m_line;
                this.pushColumn(this.m_column);
                this.m_column = 0;
                nextChar = 10;
            } else {
                ++this.m_column;
            }
            ++this.m_consumed;
        }
        return nextChar;
    }

    private final void pushColumn(int offset) {
        if (this.m_columns.size() == this.m_size) {
            this.m_columns.removeFirst();
        }
        this.m_columns.addLast(offset);
    }

    private final int popColumn() throws IOException {
        if (this.m_columns.isEmpty()) {
            throw new IOException("Cannot unread past buffer");
        }
        return this.m_columns.removeLast();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int readChar;
        assert (len >= 0);
        assert (off >= 0);
        int amountRead = 0;
        int endIndex = off + len;
        for (int index = off; index < endIndex && (readChar = this.read()) != -1; ++index) {
            cbuf[index] = (char)readChar;
            ++amountRead;
        }
        return amountRead == 0 ? -1 : amountRead;
    }

    @Override
    public long skip(long n) throws IOException {
        long charsLeft;
        assert (n > 0L);
        for (charsLeft = n; charsLeft > 0L && this.read() != -1; --charsLeft) {
        }
        return n - charsLeft;
    }

    @Override
    public void unread(char[] cbuf, int off, int len) throws IOException {
        assert (len >= 0);
        assert (off >= 0);
        int endIndex = off + len;
        for (int index = endIndex - 1; index >= off; --index) {
            this.unread(cbuf[index]);
        }
    }

    @Override
    public void unread(char[] cbuf) throws IOException {
        this.unread(cbuf, 0, cbuf.length);
    }

    @Override
    public void unread(int c) throws IOException {
        if (c == 13) {
            throw new IOException("Cannot unread a carriage return");
        }
        this.unreadImpl(c, true);
    }

    private void unreadImpl(int c, boolean updateCounts) throws IOException {
        if (c != -1) {
            if (updateCounts) {
                if (c == 10) {
                    --this.m_line;
                    this.m_column = this.popColumn();
                } else {
                    --this.m_column;
                }
                --this.m_consumed;
            }
            super.unread(c);
        }
    }
}

