/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class ResizingPipedInputStream
extends InputStream {
    private static final NotificationConsumer NO_OP_NOTIFICATION_CONSUMER = new NotificationConsumer(){

        @Override
        public void bytesConsolidatedToStartOfBuffer(int leftShiftAmount) {
        }
    };
    private static final int SINGLE_BYTE_MASK = 255;
    private final int initialBufferSize;
    private final int maximumBufferSize;
    private final boolean useBoundary;
    private NotificationConsumer notificationConsumer = NO_OP_NOTIFICATION_CONSUMER;
    private byte[] buffer;
    private ByteBuffer byteBuffer;
    private int capacity;
    private int readIndex = 0;
    private int writeIndex = 0;
    private int available = 0;
    private int size = 0;
    private int boundary = 0;

    public ResizingPipedInputStream(int initialBufferSize) {
        this(initialBufferSize, 0x7FFFFFF7, false);
    }

    ResizingPipedInputStream(int initialBufferSize, int maximumBufferSize, boolean useBoundary) {
        if (initialBufferSize < 1) {
            throw new IllegalArgumentException("Initial buffer size must be at least 1.");
        }
        if (maximumBufferSize > 0x7FFFFFF7) {
            throw new IllegalArgumentException("Initial buffer size must be at most 2147483639.");
        }
        if (maximumBufferSize < initialBufferSize) {
            throw new IllegalArgumentException("Maximum buffer size cannot be less than the initial buffer size.");
        }
        this.initialBufferSize = initialBufferSize;
        this.maximumBufferSize = maximumBufferSize;
        this.capacity = initialBufferSize;
        this.buffer = new byte[initialBufferSize];
        this.byteBuffer = ByteBuffer.wrap(this.buffer, 0, this.capacity);
        this.useBoundary = useBoundary;
    }

    private void moveBytesToStartOfBuffer(byte[] destinationBuffer) {
        if (this.size > 0) {
            System.arraycopy(this.buffer, this.readIndex, destinationBuffer, 0, this.size);
        }
        if (this.readIndex > 0) {
            this.notificationConsumer.bytesConsolidatedToStartOfBuffer(this.readIndex);
        }
        this.readIndex = 0;
        this.boundary = this.available;
        this.writeIndex = this.size;
    }

    private int freeSpaceAtEndOfBuffer() {
        return this.capacity - this.writeIndex;
    }

    private void ensureSpaceInBuffer(int minimumNumberOfBytesRequired) {
        if (this.size < 1 || this.freeSpaceAtEndOfBuffer() < minimumNumberOfBytesRequired) {
            int shortfall = minimumNumberOfBytesRequired - this.freeSpaceAtEndOfBuffer() - this.readIndex;
            if (shortfall <= 0) {
                this.moveBytesToStartOfBuffer(this.buffer);
            } else {
                int amountToGrow = Math.max(this.initialBufferSize, shortfall);
                if (this.capacity + amountToGrow > this.maximumBufferSize && this.capacity + (amountToGrow = shortfall) > this.maximumBufferSize) {
                    throw new BufferOverflowException();
                }
                byte[] newBuffer = new byte[this.buffer.length + amountToGrow];
                this.moveBytesToStartOfBuffer(newBuffer);
                this.capacity += amountToGrow;
                this.buffer = newBuffer;
                this.byteBuffer = ByteBuffer.wrap(this.buffer, this.readIndex, this.capacity);
            }
        }
    }

    public void receive(int b) {
        this.ensureSpaceInBuffer(1);
        this.buffer[this.writeIndex] = (byte)b;
        ++this.writeIndex;
        ++this.size;
        if (!this.useBoundary) {
            this.extendBoundary(1);
        }
    }

    public void receive(byte[] b, int off, int len) {
        this.ensureSpaceInBuffer(len);
        System.arraycopy(b, off, this.buffer, this.writeIndex, len);
        this.writeIndex += len;
        this.size += len;
        if (!this.useBoundary) {
            this.extendBoundary(len);
        }
    }

    public void receive(byte[] b) {
        this.receive(b, 0, b.length);
    }

    public int receive(InputStream input, int len) throws IOException {
        int numberOfBytesRead;
        this.ensureSpaceInBuffer(len);
        try {
            numberOfBytesRead = input.read(this.buffer, this.writeIndex, len);
        } catch (EOFException e) {
            numberOfBytesRead = -1;
        }
        if (numberOfBytesRead > 0) {
            this.writeIndex += numberOfBytesRead;
            this.size += numberOfBytesRead;
        } else {
            numberOfBytesRead = 0;
        }
        if (!this.useBoundary) {
            this.extendBoundary(numberOfBytesRead);
        }
        return numberOfBytesRead;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (b.length == 0 || len == 0) {
            return 0;
        }
        if (this.available < 1) {
            return -1;
        }
        int bytesToRead = Math.min(this.available, len);
        System.arraycopy(this.buffer, this.readIndex, b, off, bytesToRead);
        this.readIndex += bytesToRead;
        this.available -= bytesToRead;
        this.size -= bytesToRead;
        return bytesToRead;
    }

    public void copyTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.buffer, this.readIndex, this.available);
    }

    void seekTo(int index) {
        int amount = index - this.readIndex;
        this.available -= amount;
        this.size -= amount;
        this.readIndex = index;
    }

    int getReadIndex() {
        return this.readIndex;
    }

    int getWriteIndex() {
        return this.writeIndex;
    }

    void rewind(int previousReadIndex, int previousAvailable) {
        this.readIndex = previousReadIndex;
        this.available = previousAvailable;
        this.boundary = previousReadIndex + previousAvailable;
        this.size = this.writeIndex - this.readIndex;
    }

    void truncate(int previousWriteIndex, int previousAvailable) {
        this.writeIndex = previousWriteIndex;
        this.available = previousAvailable;
        this.boundary = this.writeIndex;
        this.size = previousAvailable;
    }

    @Override
    public long skip(long n) {
        if (n < 1L || this.available < 1) {
            return 0L;
        }
        int bytesSkipped = (int)Math.min((long)this.available, n);
        this.readIndex += bytesSkipped;
        this.available -= bytesSkipped;
        this.size -= bytesSkipped;
        return bytesSkipped;
    }

    @Override
    public int available() {
        return this.available;
    }

    int size() {
        return this.size;
    }

    int availableBeyondBoundary() {
        return this.size - this.available;
    }

    int getBoundary() {
        return this.boundary;
    }

    void extendBoundary(int numberOfBytes) {
        this.boundary += numberOfBytes;
        this.available += numberOfBytes;
    }

    @Override
    public int read() {
        if (this.available < 1) {
            return -1;
        }
        byte b = this.buffer[this.readIndex];
        ++this.readIndex;
        --this.available;
        --this.size;
        return b & 0xFF;
    }

    int peek(int index) {
        return this.buffer[index] & 0xFF;
    }

    public int capacity() {
        return this.capacity;
    }

    int getInitialBufferSize() {
        return this.initialBufferSize;
    }

    void clear() {
        this.readIndex = 0;
        this.writeIndex = 0;
        this.available = 0;
        this.boundary = 0;
        this.size = 0;
    }

    ByteBuffer getByteBuffer(int position, int limit2) {
        this.byteBuffer.limit(this.capacity);
        this.byteBuffer.position(position);
        this.byteBuffer.limit(limit2);
        return this.byteBuffer;
    }

    void copyBytes(int position, byte[] destination, int destinationOffset, int length) {
        System.arraycopy(this.buffer, position, destination, destinationOffset, length);
    }

    void consolidate(int fromPosition, int toPosition) {
        if (fromPosition > this.writeIndex || fromPosition > this.boundary || toPosition < this.readIndex) {
            throw new IllegalArgumentException("Tried to consolidate using an index that violates the constraints.");
        }
        int indexShift = fromPosition - toPosition;
        System.arraycopy(this.buffer, fromPosition, this.buffer, toPosition, this.writeIndex - fromPosition);
        this.size -= indexShift;
        this.available -= indexShift;
        this.writeIndex -= indexShift;
        this.boundary -= indexShift;
    }

    void registerNotificationConsumer(NotificationConsumer consumer) {
        this.notificationConsumer = consumer;
    }

    static interface NotificationConsumer {
        public void bytesConsolidatedToStartOfBuffer(int var1);
    }
}

