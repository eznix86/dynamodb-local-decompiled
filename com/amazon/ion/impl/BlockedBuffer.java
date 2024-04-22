/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.impl;

import com.amazon.ion.IonException;
import com.amazon.ion.impl.ByteWriter;
import com.amazon.ion.impl.IonBinary;
import com.amazon.ion.impl.SimpleByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

final class BlockedBuffer {
    ArrayList<bbBlock> _blocks;
    int _next_block_position;
    int _lastCapacity;
    int _buf_limit;
    int _version;
    int _mutation_version;
    Object _mutator;
    static final boolean test_with_no_version_checking = false;
    static boolean debugValidation = false;
    static int _defaultBlockSizeMin;
    static int _defaultBlockSizeUpperLimit;
    public int _blockSizeMin = _defaultBlockSizeMin;
    public int _blockSizeUpperLimit = _defaultBlockSizeUpperLimit;
    static int _validate_count;
    TreeSet<Monitor> _updatelist = new TreeSet<Monitor>(CompareMonitor.getComparator());

    void start_mutate(Object caller, int version) {
        if (this._mutation_version != 0 || this._mutator != null) {
            throw new BlockedBufferException("lock conflict");
        }
        if (version != this._version) {
            throw new BlockedBufferException("version conflict on update");
        }
        this._mutator = caller;
        this._mutation_version = version;
    }

    int end_mutate(Object caller) {
        if (this._version != this._mutation_version) {
            throw new BlockedBufferException("version mismatch failure");
        }
        if (caller != this._mutator) {
            throw new BlockedBufferException("caller mismatch failure");
        }
        this._version = this._mutation_version + 1;
        this._mutation_version = 0;
        this._mutator = null;
        return this._version;
    }

    boolean mutation_in_progress(Object caller, int version) {
        if (this._mutation_version != version) {
            throw new BlockedBufferException("unexpected update lock conflict");
        }
        if (caller != this._mutator) {
            throw new BlockedBufferException("caller mismatch failure");
        }
        return true;
    }

    int getVersion() {
        return this._version;
    }

    public static void resetParameters() {
        debugValidation = false;
        _defaultBlockSizeMin = 32768;
        _defaultBlockSizeUpperLimit = 32768;
    }

    static void setBlockSizeParameters(int min, int max, boolean intenseValidation) {
        debugValidation = intenseValidation;
        BlockedBuffer.setBlockSizeParameters(min, max);
    }

    public static void setBlockSizeParameters(int min, int max) {
        if (min < 0 || max < min) {
            throw new IllegalArgumentException();
        }
        _defaultBlockSizeMin = min;
        _defaultBlockSizeUpperLimit = max;
    }

    public BlockedBuffer() {
        this.start_mutate(this, 0);
        this.init(0, null);
        this.end_mutate(this);
    }

    public BlockedBuffer(int initialSize) {
        this.start_mutate(this, 0);
        this.init(initialSize, null);
        this.end_mutate(this);
    }

    public BlockedBuffer(byte[] data) {
        this.start_mutate(this, 0);
        this.init(0, new bbBlock(data));
        this._buf_limit = data.length;
        this.end_mutate(this);
    }

    public BlockedBuffer(InputStream data) throws IOException {
        IonBinary.Writer writer = new IonBinary.Writer(this);
        try {
            writer.write(data);
        } finally {
            data.close();
        }
    }

    public BlockedBuffer clone() {
        BlockedBuffer clone = new BlockedBuffer(this._buf_limit);
        int end = this._buf_limit;
        bbBlock dst_block = clone._blocks.get(0);
        int dst_offset = 0;
        int dst_limit = dst_block.blockCapacity();
        for (int ii = 0; ii < this._blocks.size(); ++ii) {
            bbBlock src_block = this._blocks.get(ii);
            if (src_block._limit < 1) continue;
            int src_end = src_block._limit + src_block._offset;
            int to_copy = src_block._limit;
            if (to_copy > dst_limit - dst_offset) {
                to_copy = dst_limit - dst_offset;
            }
            System.arraycopy(src_block._buffer, 0, dst_block._buffer, dst_offset, to_copy);
            assert ((dst_offset += to_copy) <= dst_limit);
            if (src_end >= end) break;
        }
        dst_block._limit = dst_offset;
        clone._buf_limit = dst_offset;
        return clone;
    }

    private bbBlock init(int initialSize, bbBlock initialBlock) {
        bbBlock b;
        this._lastCapacity = _defaultBlockSizeMin;
        this._blockSizeUpperLimit = _defaultBlockSizeUpperLimit;
        while (this._lastCapacity < initialSize && this._lastCapacity < this._blockSizeUpperLimit) {
            this.nextBlockSize(this, 0);
        }
        int count = initialSize / this._lastCapacity;
        if (initialBlock != null) {
            count = 1;
        }
        this._blocks = new ArrayList(count);
        if (initialBlock == null) {
            initialBlock = new bbBlock(this.nextBlockSize(this, 0));
        }
        this._blocks.add(initialBlock);
        this._next_block_position = 1;
        for (int need = initialSize - initialBlock.blockCapacity(); need > 0; need -= b.blockCapacity()) {
            b = new bbBlock(this.nextBlockSize(this, 0));
            b._idx = -1;
            this._blocks.add(b);
        }
        return initialBlock;
    }

    public final int size() {
        return this._buf_limit;
    }

    private void clear(Object caller, int version) {
        assert (this.mutation_in_progress(caller, version));
        this._buf_limit = 0;
        for (int ii = 0; ii < this._blocks.size(); ++ii) {
            this._blocks.get(ii).clearBlock();
        }
        bbBlock first = this._blocks.get(0);
        first._idx = 0;
        first._offset = 0;
        first._limit = 0;
        this._next_block_position = 1;
    }

    bbBlock truncate(Object caller, int version, int pos) {
        assert (this.mutation_in_progress(caller, version));
        if (0 > pos || pos > this._buf_limit) {
            throw new IllegalArgumentException();
        }
        bbBlock b = null;
        for (int idx = this._next_block_position - 1; idx >= 0; --idx) {
            b = this._blocks.get(idx);
            if (b._offset <= pos) break;
            b.clearBlock();
        }
        if (b == null) {
            throw new IllegalStateException("block missing at position " + pos);
        }
        this._next_block_position = b._idx + 1;
        b._limit = pos - b._offset;
        this._buf_limit = pos;
        b = this.findBlockForRead(pos, version, b, pos);
        return b;
    }

    private bbBlock addBlock(Object caller, int version, int idx, int offset, int needed) {
        int ii;
        assert (this.mutation_in_progress(caller, version));
        bbBlock newblock = null;
        for (ii = this._next_block_position; ii < this._blocks.size(); ++ii) {
            bbBlock tmpblock = this._blocks.get(this._next_block_position);
            if (tmpblock._buffer.length < needed) continue;
            this._blocks.remove(this._next_block_position);
            newblock = tmpblock;
            break;
        }
        if (newblock == null) {
            int bufcapacity = 0;
            if (needed > this._blockSizeUpperLimit) {
                bufcapacity = needed;
            } else {
                while (bufcapacity < needed) {
                    bufcapacity = this.nextBlockSize(caller, version);
                }
            }
            newblock = new bbBlock(bufcapacity);
        }
        if (idx == -1) {
            for (idx = 0; idx < this._next_block_position && this._blocks.get((int)idx)._offset >= 0 && offset < this._blocks.get((int)idx)._offset; ++idx) {
            }
        }
        newblock._idx = idx;
        newblock._offset = offset;
        this._blocks.add(idx, newblock);
        ++this._next_block_position;
        for (ii = idx + 1; ii < this._next_block_position; ++ii) {
            this._blocks.get((int)ii)._idx = ii;
        }
        return newblock;
    }

    private int nextBlockSize(Object caller, int version) {
        assert (this.mutation_in_progress(caller, version));
        if (this._lastCapacity == 0) {
            this._lastCapacity = this._blockSizeMin;
        } else if (this._lastCapacity < this._blockSizeUpperLimit) {
            this._lastCapacity *= 2;
        }
        return this._lastCapacity;
    }

    final bbBlock findBlockHelper(int pos, int lo, int hi) {
        if (hi - lo <= 3) {
            int ii;
            for (ii = lo; ii < hi; ++ii) {
                bbBlock block = this._blocks.get(ii);
                if (pos > block._offset + block._limit) continue;
                if (block.containsForRead(pos)) {
                    return block;
                }
                if (block._offset >= pos) break;
            }
            return this._blocks.get(ii - 1);
        }
        int mid = (hi + lo) / 2;
        bbBlock block = this._blocks.get(mid);
        assert (block != null);
        if (block._offset > pos) {
            return this.findBlockHelper(pos, lo, mid);
        }
        return this.findBlockHelper(pos, mid, hi);
    }

    bbBlock findBlockForRead(Object caller, int version, bbBlock curr, int pos) {
        boolean at_eof;
        assert (pos >= 0 && "buffer positions are never negative".length() > 0);
        if (pos > this._buf_limit) {
            throw new BlockedBufferException("invalid position");
        }
        assert (this._validate());
        if (curr != null) {
            if (curr.containsForRead(pos)) {
                return curr;
            }
            if (pos == this._buf_limit && pos - curr._offset == curr._limit) {
                return curr;
            }
        }
        boolean bl = at_eof = pos == this._buf_limit;
        if (at_eof) {
            bbBlock block = this._blocks.get(this._next_block_position - 1);
            if (block.containsForWrite(pos)) {
                return block;
            }
        } else {
            bbBlock block = this.findBlockHelper(pos, 0, this._next_block_position);
            return block;
        }
        throw new BlockedBufferException("valid position can't be found!");
    }

    bbBlock findBlockForWrite(Object caller, int version, bbBlock curr, int pos) {
        bbBlock block;
        assert (this.mutation_in_progress(caller, version));
        assert (pos >= 0 && "invalid position, positions must be >= 0".length() > 0);
        if (pos > this._buf_limit + 1) {
            throw new BlockedBufferException("writes must be contiguous");
        }
        assert (this._validate());
        if (curr != null && curr.hasRoomToWrite(pos, 1)) {
            bbBlock b;
            if (curr._offset + curr._limit == pos && curr._idx < this._next_block_position && (b = this._blocks.get(curr._idx + 1)).containsForWrite(pos)) {
                curr = b;
            }
            return curr;
        }
        if (pos == this._buf_limit) {
            assert (this._next_block_position > 0);
            block = this._blocks.get(this._next_block_position - 1);
        } else {
            block = curr != null && pos == curr._offset + curr._limit ? this._blocks.get(curr._idx + 1) : this.findBlockHelper(pos, 0, this._next_block_position);
        }
        assert (block != null);
        assert (block.containsForWrite(pos));
        if (block.hasRoomToWrite(pos, 1)) {
            return block;
        }
        if (block._idx < this._next_block_position - 1) {
            block = this._blocks.get(block._idx + 1);
            return block;
        }
        int newIdx = block._idx + 1;
        assert (newIdx == this._next_block_position);
        bbBlock ret = this.addBlock(caller, version, newIdx, pos, this.nextBlockSize(caller, version));
        return ret;
    }

    int insert(Object caller, int version, bbBlock curr, int pos, int len) {
        assert (this.mutation_in_progress(caller, version));
        int neededSpace = len - curr.unusedBlockCapacity();
        if (neededSpace <= 0) {
            this.insertInCurrOnly(caller, version, curr, pos, len);
        } else {
            bbBlock next = null;
            if (curr._idx < this._next_block_position - 1) {
                next = this._blocks.get(curr._idx + 1);
            }
            if (next != null && neededSpace <= next.unusedBlockCapacity()) {
                this.insertInCurrAndNext(caller, version, curr, pos, len, next);
            } else {
                int lenNeededInLastAddedBlock = neededSpace % this._blockSizeUpperLimit;
                int tailLen = curr.bytesAvailableToRead(pos);
                if (lenNeededInLastAddedBlock < tailLen) {
                    lenNeededInLastAddedBlock = tailLen;
                }
                if (lenNeededInLastAddedBlock < neededSpace && neededSpace < this._blockSizeUpperLimit) {
                    lenNeededInLastAddedBlock = neededSpace;
                }
                bbBlock newblock = this.insertMakeNewTailBlock(caller, version, curr, lenNeededInLastAddedBlock);
                if (len <= curr.unusedBlockCapacity() + newblock.unusedBlockCapacity()) {
                    this.insertBlock(newblock);
                    this.insertInCurrAndNext(caller, version, curr, pos, len, newblock);
                } else {
                    this.insertAsManyBlocksAsNeeded(caller, version, curr, pos, len, newblock);
                }
            }
        }
        assert (this._validate());
        return len;
    }

    private int insertInCurrOnly(Object caller, int version, bbBlock curr, int pos, int len) {
        assert (this.mutation_in_progress(caller, version));
        assert (curr.unusedBlockCapacity() >= len);
        System.arraycopy(curr._buffer, curr.blockOffsetFromAbsolute(pos), curr._buffer, curr.blockOffsetFromAbsolute(pos) + len, curr.bytesAvailableToRead(pos));
        curr._limit += len;
        this.adjustOffsets(curr._idx, len, 0);
        this.notifyInsert(pos, len);
        return len;
    }

    private int insertInCurrAndNext(Object caller, int version, bbBlock curr, int pos, int len, bbBlock next) {
        int addedInCurr;
        int leftInCurr;
        int availableToRead;
        assert (this.mutation_in_progress(caller, version));
        assert (curr.unusedBlockCapacity() + next.unusedBlockCapacity() >= len);
        assert (curr.unusedBlockCapacity() < len);
        int tailInCurr = availableToRead = curr.bytesAvailableToRead(pos);
        int deltaOfNextData = len - curr.unusedBlockCapacity();
        int tailCopiedToNext = deltaOfNextData;
        if (tailCopiedToNext > availableToRead) {
            tailCopiedToNext = availableToRead;
        }
        if (next._limit > 0) {
            System.arraycopy(next._buffer, 0, next._buffer, deltaOfNextData, next._limit);
        }
        next._limit += deltaOfNextData;
        if (tailCopiedToNext > 0) {
            System.arraycopy(curr._buffer, curr._limit - tailCopiedToNext, next._buffer, deltaOfNextData - tailCopiedToNext, tailCopiedToNext);
        }
        if ((leftInCurr = tailInCurr - tailCopiedToNext) > 0) {
            int blockPosition = curr.blockOffsetFromAbsolute(pos);
            System.arraycopy(curr._buffer, blockPosition, curr._buffer, blockPosition + len, leftInCurr);
        }
        if ((addedInCurr = curr.unusedBlockCapacity()) > 0) {
            curr._limit += addedInCurr;
            next._offset += addedInCurr;
        }
        assert (curr.blockOffsetFromAbsolute(pos) + tailCopiedToNext + addedInCurr + leftInCurr == curr._limit);
        this.adjustOffsets(next._idx, len, 0);
        this.notifyInsert(pos, len);
        return len;
    }

    private bbBlock insertMakeNewTailBlock(Object caller, int version, bbBlock curr, int minimumBlockSize) {
        assert (this.mutation_in_progress(caller, version));
        int newblocksize = minimumBlockSize;
        if (newblocksize < this._blockSizeUpperLimit) {
            while ((newblocksize = this.nextBlockSize(caller, version)) < minimumBlockSize) {
            }
        }
        bbBlock newblock = new bbBlock(newblocksize);
        newblock._idx = curr._idx + 1;
        newblock._offset = curr._offset + curr._limit;
        return newblock;
    }

    private int insertAsManyBlocksAsNeeded(Object caller, int version, bbBlock curr, int pos, int len, bbBlock newLastBlock) {
        assert (this.mutation_in_progress(caller, version));
        bbBlock oldCurr = curr;
        int oldPosition = curr.blockOffsetFromAbsolute(pos);
        int oldBlockTail = curr._limit - oldPosition;
        int newSpaceInCurr = curr.unusedBlockCapacity();
        curr._limit += newSpaceInCurr;
        int newoffset = curr._offset + curr._limit;
        int spaceNeededInMiddle = len - newSpaceInCurr - newLastBlock._buffer.length;
        int addedblocks = 0;
        bbBlock newblock = null;
        assert (spaceNeededInMiddle > 0);
        while (spaceNeededInMiddle > 0) {
            ++addedblocks;
            newblock = new bbBlock(this.nextBlockSize(caller, version));
            newblock._limit = newblock._buffer.length;
            if (newblock._limit > spaceNeededInMiddle) {
                newblock._limit = spaceNeededInMiddle;
            }
            newblock._idx = curr._idx + addedblocks;
            newblock._offset = newoffset;
            this._blocks.add(newblock._idx, newblock);
            spaceNeededInMiddle -= newblock._limit;
            newoffset += newblock._limit;
        }
        newblock = newLastBlock;
        newblock._limit = newblock._buffer.length;
        newblock._idx = curr._idx + ++addedblocks;
        newblock._offset = newoffset;
        this._blocks.add(newblock._idx, newblock);
        this.adjustOffsets(newblock._idx, len, addedblocks);
        this.notifyInsert(pos, len);
        if (oldBlockTail > 0) {
            System.arraycopy(oldCurr._buffer, oldPosition, newLastBlock._buffer, newLastBlock._limit - oldBlockTail, oldBlockTail);
        }
        return len;
    }

    private void insertBlock(bbBlock newblock) {
        this._blocks.add(newblock._idx, newblock);
        ++this._next_block_position;
        for (int ii = newblock._idx + 1; ii < this._next_block_position; ++ii) {
            ++this._blocks.get((int)ii)._idx;
        }
    }

    private void adjustOffsets(int lastidx, int addedBytes, int addedBlocks) {
        if (addedBytes != 0 || addedBlocks != 0) {
            this._next_block_position += addedBlocks;
            for (int ii = lastidx + 1; ii < this._next_block_position; ++ii) {
                bbBlock b = this._blocks.get(ii);
                b._offset += addedBytes;
                b._idx += addedBlocks;
            }
            this._buf_limit += addedBytes;
        }
    }

    bbBlock remove(Object caller, int version, bbBlock curr, int pos, int len) {
        assert (this.mutation_in_progress(caller, version));
        if (len == 0) {
            return curr;
        }
        if (len < 0 || pos + len > this._buf_limit) {
            throw new IllegalArgumentException();
        }
        int amountToRemove = len;
        int removedBlocks = 0;
        int startingIdx = curr._idx;
        int currIdx = curr._idx;
        bbBlock currBlock = curr;
        assert (curr._offset <= pos);
        assert (pos - curr._offset <= curr._limit);
        assert (this._validate());
        if (pos == 0 && len == this._buf_limit) {
            this.clear(caller, version);
            this.notifyRemove(0, len);
            return null;
        }
        int currBlockPosition = currBlock.blockOffsetFromAbsolute(pos);
        int removedFromThisBlock = currBlock._limit - currBlockPosition;
        if (removedFromThisBlock > amountToRemove) {
            removedFromThisBlock = amountToRemove;
        }
        if (removedFromThisBlock == currBlock._limit) {
            --startingIdx;
        } else {
            int moveAmount = currBlock._limit - currBlockPosition - removedFromThisBlock;
            if (moveAmount > 0) {
                System.arraycopy(currBlock._buffer, currBlock._limit - moveAmount, currBlock._buffer, currBlockPosition, moveAmount);
            }
            currBlock._limit -= removedFromThisBlock;
            if ((amountToRemove -= removedFromThisBlock) > 0) {
                currIdx = currBlock._idx + 1;
                currBlock = this._blocks.get(currIdx);
            }
        }
        while (amountToRemove > 0 && amountToRemove >= currBlock._limit) {
            amountToRemove -= currBlock._limit;
            bbBlock temp = currBlock;
            this._blocks.remove(currIdx);
            temp.clearBlock();
            this._blocks.add(temp);
            if (currIdx < this._next_block_position - ++removedBlocks) {
                currBlock = this._blocks.get(currIdx);
                continue;
            }
            if (currIdx > 0) {
                currBlock = this._blocks.get(--currIdx);
                continue;
            }
            throw new BlockedBufferException("fatal - no current block!");
        }
        if (amountToRemove > 0) {
            assert (amountToRemove < currBlock._limit);
            System.arraycopy(currBlock._buffer, amountToRemove, currBlock._buffer, 0, currBlock._limit - amountToRemove);
            assert (amountToRemove < currBlock._limit);
            currBlock._limit -= amountToRemove;
            currBlock._offset += amountToRemove;
        }
        this.adjustOffsets(startingIdx, -len, -removedBlocks);
        this.notifyRemove(pos, len);
        assert (this._validate());
        return currBlock;
    }

    public boolean _validate() {
        bbBlock b;
        int idx;
        int pos = 0;
        boolean err = false;
        if (++_validate_count % 128 != 0) {
            return true;
        }
        if (_validate_count == 28) {
            err = _validate_count < 0;
        }
        for (idx = 0; idx < this._blocks.size(); ++idx) {
            b = this._blocks.get(idx);
            if (b._idx == -1) break;
            if (b._idx != idx) {
                System.out.println("block " + idx + ": index is wrong, it is " + b._idx + " it should be " + idx);
                err = true;
            }
            if (b._offset != pos) {
                System.out.println("block " + idx + ": starting offset is wrong, it is " + b._offset + " should be " + pos);
                err = true;
            } else if (b._limit < 0 || b._limit > b._buffer.length) {
                System.out.println("block " + idx + ": limit is out of range, it is " + b._limit + " should be between 0 and " + b._buffer.length);
                err = true;
            } else if (b._limit == 0 && (b._idx != this._next_block_position - 1 || b._offset != this._buf_limit)) {
                System.out.println("block " + idx + ": has a ZERO limit");
                err = true;
            }
            pos += b._limit;
        }
        if (idx != this._next_block_position) {
            System.out.println("next block position is wrong, is " + this._next_block_position + " should be " + idx);
            err = true;
        }
        ++idx;
        while (idx < this._blocks.size()) {
            b = this._blocks.get(idx);
            if (b._offset != -1) {
                System.out.println("block " + idx + ": (in freed range) has non -1 offset, offset is " + b._offset);
                err = true;
            }
            ++idx;
        }
        if (pos != this._buf_limit) {
            System.out.println("buffer _buf_limit: limit is incorrect, it is " + this._buf_limit + " should be " + pos);
            err = true;
        }
        if (this._next_block_position > 0) {
            bbBlock last = this._blocks.get(this._next_block_position - 1);
            if (last._offset + last._limit != this._buf_limit) {
                System.out.println("last block " + last._idx + " limit isn't _buf_limit (" + this._buf_limit + "):  calc'd last block limit is " + last._offset + " + " + last._limit + " = " + (last._offset + last._limit));
                err = true;
            }
        }
        if (this._buf_limit < 0 || this._buf_limit > 0 && this._next_block_position < 1) {
            System.out.println("this._buf_limit " + this._buf_limit + " is invalid");
            err = true;
        }
        if (err) {
            System.out.println("failed with validation count = " + _validate_count);
        }
        return !err;
    }

    public void notifyRegister(Monitor item) {
        this._updatelist.add(item);
    }

    public void notifyUnregister(Monitor item) {
        this._updatelist.remove(item);
    }

    public void notifyInsert(int pos, int len) {
        if (len == 0) {
            return;
        }
        PositionMonitor pm = new PositionMonitor(pos);
        SortedSet<Monitor> follows = this._updatelist.tailSet(pm);
        for (Monitor m : follows) {
            if (!m.notifyInsert(pos, len)) continue;
            follows.remove(m);
        }
    }

    public void notifyRemove(int pos, int len) {
        if (len == 0) {
            return;
        }
        PositionMonitor pm = new PositionMonitor(pos);
        SortedSet<Monitor> follows = this._updatelist.tailSet(pm);
        for (Monitor m : follows) {
            if (!m.notifyRemove(pos, len)) continue;
            follows.remove(m);
        }
    }

    static {
        BlockedBuffer.resetParameters();
    }

    public static class BufferedOutputStream
    extends OutputStream {
        BlockedBuffer _buffer;
        BlockedByteOutputStream _writer;

        public BufferedOutputStream() {
            this(new BlockedBuffer());
        }

        public BufferedOutputStream(BlockedBuffer buffer) {
            this._buffer = buffer;
            this._writer = new BlockedByteOutputStream(this._buffer);
        }

        public int byteSize() {
            return this._buffer.size();
        }

        public byte[] getBytes() throws IOException {
            int size = this.byteSize();
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(size);
            this.writeBytes(byteStream);
            byte[] bytes = byteStream.toByteArray();
            return bytes;
        }

        public int getBytes(byte[] bytes, int offset, int len) throws IOException {
            SimpleByteBuffer outbuf = new SimpleByteBuffer(bytes, offset, len);
            OutputStream writer = (OutputStream)((Object)outbuf.getWriter());
            int written = this.writeBytes(writer);
            return written;
        }

        public int writeBytes(OutputStream userstream) throws IOException {
            int pos;
            int len;
            int limit2 = this._buffer.size();
            int version = this._buffer.getVersion();
            bbBlock curr = null;
            this._buffer.start_mutate(this, version);
            for (pos = 0; pos < limit2; pos += len) {
                if ((curr = this._buffer.findBlockForRead(this, version, curr, pos)) == null) {
                    throw new IOException("buffer missing expected bytes");
                }
                len = curr.bytesAvailableToRead(pos);
                if (len <= 0) {
                    throw new IOException("buffer missing expected bytes");
                }
                userstream.write(curr._buffer, 0, len);
            }
            this._buffer.end_mutate(this);
            return pos;
        }

        @Override
        public void write(int b) throws IOException {
            this._writer.write(b);
        }

        @Override
        public void write(byte[] bytes) throws IOException {
            this.write(bytes, 0, bytes.length);
        }

        @Override
        public void write(byte[] bytes, int off, int len) throws IOException {
            this._writer.write(bytes, off, len);
        }
    }

    public static class BlockedBufferException
    extends IonException {
        private static final long serialVersionUID = 1582507845614969389L;

        public BlockedBufferException() {
        }

        public BlockedBufferException(String message) {
            super(message);
        }

        public BlockedBufferException(String message, Throwable cause) {
            super(message, cause);
        }

        public BlockedBufferException(Throwable cause) {
            super(cause);
        }
    }

    public static class BlockedByteOutputStream
    extends OutputStream {
        BlockedBuffer _buf;
        int _pos;
        bbBlock _curr;
        int _blockPosition;
        int _version;

        public BlockedByteOutputStream() {
            this._buf = new BlockedBuffer();
            this._version = this._buf.getVersion();
            this._set_position(0);
        }

        public BlockedByteOutputStream(BlockedBuffer bb) {
            this._buf = bb;
            this._version = this._buf.getVersion();
            this._set_position(0);
        }

        public BlockedByteOutputStream(BlockedBuffer bb, int off) {
            if (bb == null || off < 0 || off > bb.size()) {
                throw new IllegalArgumentException();
            }
            this._buf = bb;
            this._version = this._buf.getVersion();
            this._set_position(0);
        }

        public final int position() {
            return this._pos;
        }

        public final void sync() throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._version = this._buf.getVersion();
            this._pos = 0;
            this._curr = null;
        }

        public final boolean _validate() {
            return this._buf._validate();
        }

        public final BlockedByteOutputStream setPosition(int pos) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (pos < 0 || pos > this._buf.size()) {
                throw new IllegalArgumentException();
            }
            this._set_position(pos);
            this.fail_on_version_change();
            return this;
        }

        private final void _set_position(int pos) {
            this._pos = pos;
            this._curr = this._buf.findBlockForRead(this, this._version, this._curr, pos);
            this._blockPosition = this._pos - this._curr._offset;
        }

        @Override
        public final void close() throws IOException {
            this._buf = null;
            this._pos = -1;
        }

        @Override
        public final void write(int b) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._write(b);
            this._version = this._buf.end_mutate(this);
        }

        final void start_write() {
            this._buf.start_mutate(this, this._version);
        }

        final void end_write() {
            this._version = this._buf.end_mutate(this);
        }

        final void _write(int b) throws IOException {
            if (this.bytesAvailableToWriteInCurr(this._pos) < 1) {
                this._curr = this._buf.findBlockForWrite(this, this._version, this._curr, this._pos);
                assert (this._curr._offset == this._pos);
                this._blockPosition = 0;
            }
            this._curr._buffer[this._blockPosition++] = (byte)(b & 0xFF);
            ++this._pos;
            if (this._blockPosition > this._curr._limit) {
                this._curr._limit = this._blockPosition;
                if (this._pos > this._buf._buf_limit) {
                    this._buf._buf_limit = this._pos;
                }
            }
        }

        private final int bytesAvailableToWriteInCurr(int pos) {
            assert (this._curr != null);
            assert (this._curr._offset <= pos);
            assert (this._curr._offset + this._curr._limit >= pos);
            if (this._curr._idx < this._buf._next_block_position - 1) {
                return this._curr.bytesAvailableToRead(pos);
            }
            int ret = this._curr._buffer.length - (pos - this._curr._offset);
            return ret;
        }

        @Override
        public final void write(byte[] b, int off, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._write(b, off, len);
            this._version = this._buf.end_mutate(this);
        }

        private final void _write(byte[] b, int off, int len) {
            int end_b = off + len;
            while (off < end_b) {
                int writeInThisBlock = this.bytesAvailableToWriteInCurr(this._pos);
                if (writeInThisBlock > end_b - off) {
                    writeInThisBlock = end_b - off;
                }
                assert (writeInThisBlock >= 0);
                if (writeInThisBlock > 0) {
                    System.arraycopy(b, off, this._curr._buffer, this._blockPosition, writeInThisBlock);
                    off += writeInThisBlock;
                    this._pos += writeInThisBlock;
                    this._blockPosition += writeInThisBlock;
                    if (this._blockPosition > this._curr._limit) {
                        this._curr._limit = this._blockPosition;
                        if (this._pos > this._buf._buf_limit) {
                            this._buf._buf_limit = this._pos;
                        }
                    } else assert (this._pos <= this._buf._buf_limit);
                }
                if (off >= end_b) break;
                this._curr = this._buf.findBlockForWrite(this, this._version, this._curr, this._pos);
                this._blockPosition = this._curr.blockOffsetFromAbsolute(this._pos);
                assert (this._curr._offset == this._pos || off >= end_b);
            }
        }

        public final void write(InputStream bytestream) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._write(bytestream, -1);
            this._version = this._buf.end_mutate(this);
        }

        public final void write(InputStream bytestream, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._write(bytestream, len);
            this._version = this._buf.end_mutate(this);
        }

        private final void _write(InputStream bytestream, int len) throws IOException {
            int len_read;
            if (len == 0) {
                return;
            }
            int written = 0;
            boolean read_all = len == -1;
            do {
                int to_read;
                int writeInThisBlock = this.bytesAvailableToWriteInCurr(this._pos);
                assert (writeInThisBlock >= 0);
                int n = to_read = read_all ? writeInThisBlock : len;
                if (to_read > writeInThisBlock) {
                    to_read = writeInThisBlock;
                }
                if ((len_read = bytestream.read(this._curr._buffer, this._blockPosition, to_read)) == -1) break;
                if (len_read > 0) {
                    this._pos += len_read;
                    this._blockPosition += len_read;
                    if (this._blockPosition > this._curr._limit) {
                        this._curr._limit = this._blockPosition;
                        if (this._pos > this._buf._buf_limit) {
                            this._buf._buf_limit = this._pos;
                        }
                    } else assert (this._pos <= this._buf._buf_limit);
                }
                if (len_read == writeInThisBlock) {
                    this._curr = this._buf.findBlockForWrite(this, this._version, this._curr, this._pos);
                    this._blockPosition = this._curr.blockOffsetFromAbsolute(this._pos);
                    assert (this._curr._offset == this._pos || written < len_read);
                    continue;
                }
                assert (len_read < writeInThisBlock);
            } while (read_all || (len -= len_read) >= 1);
        }

        public final void insert(int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            if (len < 0) {
                throw new IllegalArgumentException();
            }
            if (len > 0) {
                this._buf.start_mutate(this, this._version);
                this._buf.insert(this, this._version, this._curr, this._pos, len);
                this._version = this._buf.end_mutate(this);
            }
        }

        public final void insert(byte b) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._buf.insert(this, this._version, this._curr, this._pos, 1);
            this._write(b);
            this._version = this._buf.end_mutate(this);
        }

        public final void insert(byte[] b, int off, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._buf.insert(this, this._version, this._curr, this._pos, len);
            this._write(b, off, len);
            this._version = this._buf.end_mutate(this);
        }

        public final void remove(int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._buf.start_mutate(this, this._version);
            this._curr = this._buf.remove(this, this._version, this._curr, this._pos, len);
            this._version = this._buf.end_mutate(this);
        }

        public final void truncate() throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            if (this._buf._buf_limit == this._pos) {
                return;
            }
            this._buf.start_mutate(this, this._version);
            this._curr = this._buf.truncate(this, this._version, this._pos);
            this._version = this._buf.end_mutate(this);
        }

        private final void fail_on_version_change() throws IOException {
            if (this._buf.getVersion() != this._version) {
                this.close();
                throw new BlockedBufferException("buffer has been changed!");
            }
        }
    }

    public static class BlockedByteInputStream
    extends InputStream {
        BlockedBuffer _buf;
        int _pos;
        int _mark;
        bbBlock _curr;
        int _blockPosition;
        int _version;

        public BlockedByteInputStream(BlockedBuffer bb) {
            this(0, bb);
        }

        public BlockedByteInputStream(BlockedBuffer bb, int pos) {
            this(pos, bb);
        }

        private BlockedByteInputStream(int pos, BlockedBuffer bb) {
            if (bb == null) {
                throw new IllegalArgumentException();
            }
            this._version = bb.getVersion();
            this._buf = bb;
            this._set_position(pos);
            this._mark = -1;
        }

        @Override
        public final void mark(int readlimit) {
            this._mark = this._pos;
        }

        @Override
        public final void reset() throws IOException {
            if (this._mark == -1) {
                throw new IOException("mark not set");
            }
            this._set_position(this._mark);
        }

        public final int position() {
            return this._pos;
        }

        public final void sync() throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this._version = this._buf.getVersion();
            this._curr = null;
            this._pos = 0;
        }

        public final boolean _validate() {
            return this._buf._validate();
        }

        public final BlockedByteInputStream setPosition(int pos) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (pos < 0 || pos > this._buf.size()) {
                throw new IllegalArgumentException();
            }
            this._set_position(pos);
            this.fail_on_version_change();
            return this;
        }

        private final void _set_position(int pos) {
            this._pos = pos;
            this._curr = this._buf.findBlockForRead(this, this._version, this._curr, pos);
            this._blockPosition = this._pos - this._curr._offset;
        }

        @Override
        public final void close() throws IOException {
            this._buf = null;
            this._pos = -1;
        }

        public final int writeTo(OutputStream out, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (this._pos > this._buf.size()) {
                throw new IllegalArgumentException();
            }
            int startingPos = this._pos;
            int localEnd = this._pos + len;
            if (localEnd > this._buf.size()) {
                localEnd = this._buf.size();
            }
            assert (this._curr.blockOffsetFromAbsolute(this._pos) == this._blockPosition);
            while (this._pos < localEnd) {
                boolean partial_read;
                int available = this._curr._limit - this._blockPosition;
                boolean bl = partial_read = available > localEnd - this._pos;
                if (partial_read) {
                    available = localEnd - this._pos;
                }
                out.write(this._curr._buffer, this._blockPosition, available);
                this._pos += available;
                if (partial_read) {
                    this._blockPosition += available;
                    break;
                }
                this._curr = this._buf.findBlockForRead(this, this._version, this._curr, this._pos);
                this._blockPosition = this._curr.blockOffsetFromAbsolute(this._pos);
            }
            this.fail_on_version_change();
            return this._pos - startingPos;
        }

        public final int writeTo(ByteWriter out, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (this._pos > this._buf.size()) {
                throw new IllegalArgumentException();
            }
            int startingPos = this._pos;
            int localEnd = this._pos + len;
            if (localEnd > this._buf.size()) {
                localEnd = this._buf.size();
            }
            assert (this._curr.blockOffsetFromAbsolute(this._pos) == this._blockPosition);
            while (this._pos < localEnd) {
                boolean partial_read;
                int available = this._curr._limit - this._blockPosition;
                boolean bl = partial_read = available > localEnd - this._pos;
                if (partial_read) {
                    available = localEnd - this._pos;
                }
                out.write(this._curr._buffer, this._blockPosition, available);
                this._pos += available;
                if (partial_read) {
                    this._blockPosition += available;
                    break;
                }
                this._curr = this._buf.findBlockForRead(this, this._version, this._curr, this._pos);
                this._blockPosition = this._curr.blockOffsetFromAbsolute(this._pos);
            }
            this.fail_on_version_change();
            return this._pos - startingPos;
        }

        @Override
        public final int read(byte[] bytes, int offset, int len) throws IOException {
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (this._pos > this._buf.size()) {
                throw new IllegalArgumentException();
            }
            int startingPos = this._pos;
            int localEnd = this._pos + len;
            if (localEnd > this._buf.size()) {
                localEnd = this._buf.size();
            }
            while (this._pos < localEnd) {
                bbBlock block = this._curr;
                int block_offset = this._blockPosition;
                int available = block._limit - this._blockPosition;
                if (available > localEnd - this._pos) {
                    available = localEnd - this._pos;
                    this._blockPosition += available;
                } else {
                    this._curr = this._buf.findBlockForRead(this, this._version, this._curr, this._pos + available);
                    this._blockPosition = 0;
                }
                System.arraycopy(block._buffer, block_offset, bytes, offset, available);
                this._pos += available;
                offset += available;
            }
            this.fail_on_version_change();
            return this._pos - startingPos;
        }

        @Override
        public final int read() throws IOException {
            if (this._buf == null) {
                throw new IOException("input stream is closed");
            }
            this.fail_on_version_change();
            if (this._pos >= this._buf.size()) {
                return -1;
            }
            if (this._blockPosition >= this._curr._limit) {
                this._curr = this._buf.findBlockForRead(this, this._version, this._curr, this._pos);
                this._blockPosition = 0;
            }
            int nextByte = 0xFF & this._curr._buffer[this._blockPosition];
            ++this._blockPosition;
            ++this._pos;
            this.fail_on_version_change();
            return nextByte;
        }

        private final void fail_on_version_change() throws IOException {
            if (this._buf.getVersion() != this._version) {
                this.close();
                throw new BlockedBufferException("buffer has been changed!");
            }
        }

        @Override
        public final long skip(long n) throws IOException {
            if (n < 0L || n > 0x7FFFFFF7L) {
                throw new IllegalArgumentException("we only handle buffer less than 2147483639 bytes in length");
            }
            if (this._buf == null) {
                throw new IOException("stream is closed");
            }
            this.fail_on_version_change();
            if (this._pos >= this._buf.size()) {
                return -1L;
            }
            int len = (int)n;
            if (len == 0) {
                return 0L;
            }
            int startingPos = this._pos;
            int localEnd = this._pos + len;
            if (localEnd > this._buf.size()) {
                localEnd = this._buf.size();
            }
            if (localEnd > this._blockPosition + this._curr._offset) {
                this._curr = this._buf.findBlockForRead(this, this._version, this._curr, localEnd);
            }
            this._blockPosition = localEnd - this._curr._offset;
            this._pos = localEnd;
            this.fail_on_version_change();
            return this._pos - startingPos;
        }
    }

    private static final class CompareMonitor
    implements Comparator<Monitor> {
        static CompareMonitor instance = new CompareMonitor();

        private CompareMonitor() {
        }

        static CompareMonitor getComparator() {
            return instance;
        }

        @Override
        public int compare(Monitor arg0, Monitor arg1) {
            return arg0.getMemberIdOffset() - arg1.getMemberIdOffset();
        }
    }

    private static final class PositionMonitor
    implements Monitor {
        int _pos;

        PositionMonitor(int pos) {
            this._pos = pos;
        }

        @Override
        public int getMemberIdOffset() {
            return this._pos;
        }

        @Override
        public boolean notifyInsert(int pos, int len) {
            return false;
        }

        @Override
        public boolean notifyRemove(int pos, int len) {
            return false;
        }
    }

    public static interface Monitor {
        public boolean notifyInsert(int var1, int var2);

        public boolean notifyRemove(int var1, int var2);

        public int getMemberIdOffset();
    }

    static final class bbBlock {
        public int _idx;
        public int _offset;
        public int _limit;
        public byte[] _buffer;

        public bbBlock(int capacity) {
            this._buffer = new byte[capacity];
        }

        bbBlock(byte[] buffer) {
            this._buffer = buffer;
            this._limit = buffer.length;
        }

        public bbBlock clearBlock() {
            this._idx = -1;
            this._offset = -1;
            this._limit = 0;
            return this;
        }

        final int blockCapacity() {
            assert (this._offset >= 0);
            return this._buffer.length;
        }

        final int unusedBlockCapacity() {
            assert (this._offset >= 0);
            return this._buffer.length - this._limit;
        }

        final int bytesAvailableToWrite(int pos) {
            assert (this._offset >= 0);
            return this._buffer.length - (pos - this._offset);
        }

        public final int bytesAvailableToRead(int pos) {
            assert (this._offset >= 0);
            return this._limit - (pos - this._offset);
        }

        final boolean hasRoomToWrite(int pos, int needed) {
            assert (this._offset >= 0);
            return needed <= this._buffer.length - (pos - this._offset);
        }

        final boolean containsForRead(int pos) {
            assert (this._offset >= 0);
            return pos >= this._offset && pos < this._offset + this._limit;
        }

        final boolean containsForWrite(int pos) {
            assert (this._offset >= 0);
            return pos >= this._offset && pos <= this._offset + this._limit;
        }

        final int blockOffsetFromAbsolute(int pos) {
            assert (this._offset >= 0);
            return pos - this._offset;
        }
    }
}

