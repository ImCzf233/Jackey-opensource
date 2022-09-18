package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagScope;
import java.io.DataInput;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/TrackingDataInput.class */
public final class TrackingDataInput implements DataInput, BinaryTagScope {
    private static final int MAX_DEPTH = 512;
    private final DataInput input;
    private final long maxLength;
    private long counter;
    private int depth;

    public TrackingDataInput(final DataInput input, final long maxLength) {
        this.input = input;
        this.maxLength = maxLength;
    }

    public static BinaryTagScope enter(final DataInput input) throws IOException {
        if (input instanceof TrackingDataInput) {
            return ((TrackingDataInput) input).enter();
        }
        return BinaryTagScope.NoOp.INSTANCE;
    }

    public static BinaryTagScope enter(final DataInput input, final long expectedSize) throws IOException {
        if (input instanceof TrackingDataInput) {
            return ((TrackingDataInput) input).enter(expectedSize);
        }
        return BinaryTagScope.NoOp.INSTANCE;
    }

    public DataInput input() {
        return this.input;
    }

    public TrackingDataInput enter(final long expectedSize) throws IOException {
        int i = this.depth;
        this.depth = i + 1;
        if (i > 512) {
            throw new IOException("NBT read exceeded maximum depth of 512");
        }
        ensureMaxLength(expectedSize);
        return this;
    }

    public TrackingDataInput enter() throws IOException {
        int i = this.depth;
        this.depth = i + 1;
        if (i > 512) {
            throw new IOException("NBT read exceeded maximum depth of 512");
        }
        ensureMaxLength(0L);
        return this;
    }

    public void exit() throws IOException {
        this.depth--;
        ensureMaxLength(0L);
    }

    private void ensureMaxLength(final long expected) throws IOException {
        if (this.maxLength > 0 && this.counter + expected > this.maxLength) {
            throw new IOException("The read NBT was longer than the maximum allowed size of " + this.maxLength + " bytes!");
        }
    }

    @Override // java.io.DataInput
    public void readFully(final byte[] array) throws IOException {
        this.counter += array.length;
        this.input.readFully(array);
    }

    @Override // java.io.DataInput
    public void readFully(final byte[] array, final int off, final int len) throws IOException {
        this.counter += len;
        this.input.readFully(array, off, len);
    }

    @Override // java.io.DataInput
    public int skipBytes(final int n) throws IOException {
        return this.input.skipBytes(n);
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        this.counter++;
        return this.input.readBoolean();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        this.counter++;
        return this.input.readByte();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        this.counter++;
        return this.input.readUnsignedByte();
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        this.counter += 2;
        return this.input.readShort();
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        this.counter += 2;
        return this.input.readUnsignedShort();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        this.counter += 2;
        return this.input.readChar();
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        this.counter += 4;
        return this.input.readInt();
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        this.counter += 8;
        return this.input.readLong();
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        this.counter += 4;
        return this.input.readFloat();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        this.counter += 8;
        return this.input.readDouble();
    }

    @Override // java.io.DataInput
    @Nullable
    public String readLine() throws IOException {
        String result = this.input.readLine();
        if (result != null) {
            this.counter += result.length() + 1;
        }
        return result;
    }

    @Override // java.io.DataInput
    @NotNull
    public String readUTF() throws IOException {
        String result = this.input.readUTF();
        this.counter += (result.length() * 2) + 2;
        return result;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagScope, java.lang.AutoCloseable
    public void close() throws IOException {
        exit();
    }
}
