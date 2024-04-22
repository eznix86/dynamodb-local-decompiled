/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.Poolable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class Utf8StringEncoder
extends Poolable<Utf8StringEncoder> {
    private static final int SMALL_STRING_SIZE = 4096;
    final CharsetEncoder utf8Encoder = Charset.forName("UTF-8").newEncoder();
    final ByteBuffer utf8EncodingBuffer = ByteBuffer.allocate((int)(4096.0f * this.utf8Encoder.maxBytesPerChar()));
    final char[] charArray = new char[4096];
    final CharBuffer charBuffer = CharBuffer.wrap(this.charArray);

    Utf8StringEncoder(Pool<Utf8StringEncoder> pool) {
        super(pool);
    }

    public Result encode(String text) {
        CharBuffer stringData;
        ByteBuffer encodingBuffer;
        int length = text.length();
        if (length > 4096) {
            encodingBuffer = ByteBuffer.allocate((int)((float)text.length() * this.utf8Encoder.maxBytesPerChar()));
            char[] chars = new char[text.length()];
            text.getChars(0, text.length(), chars, 0);
            stringData = CharBuffer.wrap(chars);
        } else {
            encodingBuffer = this.utf8EncodingBuffer;
            encodingBuffer.clear();
            stringData = this.charBuffer;
            text.getChars(0, text.length(), this.charArray, 0);
            this.charBuffer.rewind();
            this.charBuffer.limit(text.length());
        }
        CoderResult coderResult = this.utf8Encoder.encode(stringData, encodingBuffer, true);
        if (!coderResult.isUnderflow()) {
            throw new IllegalArgumentException("Could not encode string as UTF8 bytes: " + text);
        }
        encodingBuffer.flip();
        int utf8Length = encodingBuffer.remaining();
        return new Result(utf8Length, encodingBuffer.array());
    }

    public static class Result {
        private final byte[] buffer;
        private final int encodedLength;

        public Result(int encodedLength, byte[] buffer) {
            this.encodedLength = encodedLength;
            this.buffer = buffer;
        }

        public byte[] getBuffer() {
            return this.buffer;
        }

        public int getEncodedLength() {
            return this.encodedLength;
        }
    }
}

