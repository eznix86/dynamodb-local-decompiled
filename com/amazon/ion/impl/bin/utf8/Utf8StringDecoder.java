/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl.bin.utf8;

import com.amazon.ion.IonException;
import com.amazon.ion.impl.bin.utf8.Pool;
import com.amazon.ion.impl.bin.utf8.Poolable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class Utf8StringDecoder
extends Poolable<Utf8StringDecoder> {
    private static final int UTF8_BUFFER_SIZE_IN_BYTES = 4096;
    private final CharBuffer reusableUtf8DecodingBuffer = CharBuffer.allocate(4096);
    private final CharsetDecoder utf8CharsetDecoder = Charset.forName("UTF-8").newDecoder();
    private CharBuffer utf8DecodingBuffer;

    Utf8StringDecoder(Pool<Utf8StringDecoder> pool) {
        super(pool);
    }

    public void prepareDecode(int numberOfBytes) {
        this.utf8CharsetDecoder.reset();
        this.utf8DecodingBuffer = this.reusableUtf8DecodingBuffer;
        if (numberOfBytes > this.reusableUtf8DecodingBuffer.capacity()) {
            this.utf8DecodingBuffer = CharBuffer.allocate(numberOfBytes);
        }
    }

    public void partialDecode(ByteBuffer utf8InputBuffer, boolean endOfInput) {
        CoderResult coderResult = this.utf8CharsetDecoder.decode(utf8InputBuffer, this.utf8DecodingBuffer, endOfInput);
        if (coderResult.isError()) {
            throw new IonException("Illegal value encountered while validating UTF-8 data in input stream. " + coderResult.toString());
        }
    }

    public String finishDecode() {
        this.utf8DecodingBuffer.flip();
        return this.utf8DecodingBuffer.toString();
    }

    public String decode(ByteBuffer utf8InputBuffer, int numberOfBytes) {
        this.prepareDecode(numberOfBytes);
        this.utf8DecodingBuffer.position(0);
        this.utf8DecodingBuffer.limit(this.utf8DecodingBuffer.capacity());
        this.partialDecode(utf8InputBuffer, true);
        return this.finishDecode();
    }
}

