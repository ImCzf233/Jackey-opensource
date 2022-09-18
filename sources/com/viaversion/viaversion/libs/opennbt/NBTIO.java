package com.viaversion.viaversion.libs.opennbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/NBTIO.class */
public class NBTIO {
    public static CompoundTag readFile(String path) throws IOException {
        return readFile(new File(path));
    }

    public static CompoundTag readFile(File file) throws IOException {
        return readFile(file, true, false);
    }

    public static CompoundTag readFile(String path, boolean compressed, boolean littleEndian) throws IOException {
        return readFile(new File(path), compressed, littleEndian);
    }

    public static CompoundTag readFile(File file, boolean compressed, boolean littleEndian) throws IOException {
        InputStream in = new FileInputStream(file);
        if (compressed) {
            in = new GZIPInputStream(in);
        }
        Tag tag = readTag(in, littleEndian);
        if (!(tag instanceof CompoundTag)) {
            throw new IOException("Root tag is not a CompoundTag!");
        }
        return (CompoundTag) tag;
    }

    public static void writeFile(CompoundTag tag, String path) throws IOException {
        writeFile(tag, new File(path));
    }

    public static void writeFile(CompoundTag tag, File file) throws IOException {
        writeFile(tag, file, true, false);
    }

    public static void writeFile(CompoundTag tag, String path, boolean compressed, boolean littleEndian) throws IOException {
        writeFile(tag, new File(path), compressed, littleEndian);
    }

    public static void writeFile(CompoundTag tag, File file, boolean compressed, boolean littleEndian) throws IOException {
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        if (compressed) {
            out = new GZIPOutputStream(out);
        }
        writeTag(out, tag, littleEndian);
        out.close();
    }

    public static CompoundTag readTag(InputStream in) throws IOException {
        return readTag(in, false);
    }

    public static CompoundTag readTag(InputStream in, boolean littleEndian) throws IOException {
        return readTag(littleEndian ? new LittleEndianDataInputStream(in) : new DataInputStream(in));
    }

    public static CompoundTag readTag(DataInput in) throws IOException {
        int id = in.readByte();
        if (id != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", Integer.valueOf(id)));
        }
        in.skipBytes(in.readUnsignedShort());
        CompoundTag tag = new CompoundTag();
        tag.read(in);
        return tag;
    }

    public static void writeTag(OutputStream out, CompoundTag tag) throws IOException {
        writeTag(out, tag, false);
    }

    public static void writeTag(OutputStream out, CompoundTag tag, boolean littleEndian) throws IOException {
        writeTag(littleEndian ? new LittleEndianDataOutputStream(out) : new DataOutputStream(out), tag);
    }

    public static void writeTag(DataOutput out, CompoundTag tag) throws IOException {
        out.writeByte(10);
        out.writeUTF("");
        tag.write(out);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/NBTIO$LittleEndianDataInputStream.class */
    public static final class LittleEndianDataInputStream extends FilterInputStream implements DataInput {
        private LittleEndianDataInputStream(InputStream in) {
            super(in);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] b) throws IOException {
            return this.in.read(b, 0, b.length);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] b, int off, int len) throws IOException {
            return this.in.read(b, off, len);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] b) throws IOException {
            readFully(b, 0, b.length);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] b, int off, int len) throws IOException {
            if (len < 0) {
                throw new IndexOutOfBoundsException();
            }
            int i = 0;
            while (true) {
                int pos = i;
                if (pos < len) {
                    int read = this.in.read(b, off + pos, len - pos);
                    if (read >= 0) {
                        i = pos + read;
                    } else {
                        throw new EOFException();
                    }
                } else {
                    return;
                }
            }
        }

        @Override // java.io.DataInput
        public int skipBytes(int n) throws IOException {
            int skipped;
            int total = 0;
            while (total < n && (skipped = (int) this.in.skip(n - total)) > 0) {
                total += skipped;
            }
            return total;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            int val = this.in.read();
            if (val < 0) {
                throw new EOFException();
            }
            return val != 0;
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            int val = this.in.read();
            if (val < 0) {
                throw new EOFException();
            }
            return (byte) val;
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            int val = this.in.read();
            if (val < 0) {
                throw new EOFException();
            }
            return val;
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            int b1 = this.in.read();
            int b2 = this.in.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            }
            return (short) (b1 | (b2 << 8));
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            int b1 = this.in.read();
            int b2 = this.in.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            }
            return b1 | (b2 << 8);
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            int b1 = this.in.read();
            int b2 = this.in.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            }
            return (char) (b1 | (b2 << 8));
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            int b1 = this.in.read();
            int b2 = this.in.read();
            int b3 = this.in.read();
            int b4 = this.in.read();
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new EOFException();
            }
            return b1 | (b2 << 8) | (b3 << 16) | (b4 << 24);
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            long b1 = this.in.read();
            long b2 = this.in.read();
            long b3 = this.in.read();
            long b4 = this.in.read();
            long b5 = this.in.read();
            long b6 = this.in.read();
            long b7 = this.in.read();
            long b8 = this.in.read();
            if ((b1 | b2 | b3 | b4 | b5 | b6 | b7 | b8) < 0) {
                throw new EOFException();
            }
            return b1 | (b2 << 8) | (b3 << 16) | (b4 << 24) | (b5 << 32) | (b6 << 40) | (b7 << 48) | (b8 << 56);
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            throw new UnsupportedOperationException("Use readUTF.");
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            byte[] bytes = new byte[readUnsignedShort()];
            readFully(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/NBTIO$LittleEndianDataOutputStream.class */
    public static final class LittleEndianDataOutputStream extends FilterOutputStream implements DataOutput {
        private LittleEndianDataOutputStream(OutputStream out) {
            super(out);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.DataOutput
        public synchronized void write(int b) throws IOException {
            this.out.write(b);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.DataOutput
        public synchronized void write(byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.out.flush();
        }

        @Override // java.io.DataOutput
        public void writeBoolean(boolean b) throws IOException {
            this.out.write(b ? 1 : 0);
        }

        @Override // java.io.DataOutput
        public void writeByte(int b) throws IOException {
            this.out.write(b);
        }

        @Override // java.io.DataOutput
        public void writeShort(int s) throws IOException {
            this.out.write(s & 255);
            this.out.write((s >>> 8) & 255);
        }

        @Override // java.io.DataOutput
        public void writeChar(int c) throws IOException {
            this.out.write(c & 255);
            this.out.write((c >>> 8) & 255);
        }

        @Override // java.io.DataOutput
        public void writeInt(int i) throws IOException {
            this.out.write(i & 255);
            this.out.write((i >>> 8) & 255);
            this.out.write((i >>> 16) & 255);
            this.out.write((i >>> 24) & 255);
        }

        @Override // java.io.DataOutput
        public void writeLong(long l) throws IOException {
            this.out.write((int) (l & 255));
            this.out.write((int) ((l >>> 8) & 255));
            this.out.write((int) ((l >>> 16) & 255));
            this.out.write((int) ((l >>> 24) & 255));
            this.out.write((int) ((l >>> 32) & 255));
            this.out.write((int) ((l >>> 40) & 255));
            this.out.write((int) ((l >>> 48) & 255));
            this.out.write((int) ((l >>> 56) & 255));
        }

        @Override // java.io.DataOutput
        public void writeFloat(float f) throws IOException {
            writeInt(Float.floatToIntBits(f));
        }

        @Override // java.io.DataOutput
        public void writeDouble(double d) throws IOException {
            writeLong(Double.doubleToLongBits(d));
        }

        @Override // java.io.DataOutput
        public void writeBytes(String s) throws IOException {
            int len = s.length();
            for (int index = 0; index < len; index++) {
                this.out.write((byte) s.charAt(index));
            }
        }

        @Override // java.io.DataOutput
        public void writeChars(String s) throws IOException {
            int len = s.length();
            for (int index = 0; index < len; index++) {
                char c = s.charAt(index);
                this.out.write(c & 255);
                this.out.write((c >>> '\b') & 255);
            }
        }

        @Override // java.io.DataOutput
        public void writeUTF(String s) throws IOException {
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            writeShort(bytes.length);
            write(bytes);
        }
    }
}
