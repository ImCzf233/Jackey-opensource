package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/nbt/BinaryTagIO.class */
public final class BinaryTagIO {
    private BinaryTagIO() {
    }

    public static CompoundTag readPath(Path path) throws IOException {
        return readInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    public static CompoundTag readInputStream(InputStream input) throws IOException {
        DataInputStream dis = new DataInputStream(input);
        Throwable th = null;
        try {
            CompoundTag readDataInput = readDataInput(dis);
            if (dis != null) {
                if (0 != 0) {
                    try {
                        dis.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    dis.close();
                }
            }
            return readDataInput;
        } finally {
        }
    }

    public static CompoundTag readCompressedPath(Path path) throws IOException {
        return readCompressedInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    public static CompoundTag readCompressedInputStream(InputStream input) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(input)));
        Throwable th = null;
        try {
            CompoundTag readDataInput = readDataInput(dis);
            if (dis != null) {
                if (0 != 0) {
                    try {
                        dis.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    dis.close();
                }
            }
            return readDataInput;
        } finally {
        }
    }

    public static CompoundTag readDataInput(DataInput input) throws IOException {
        byte type = input.readByte();
        if (type != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", Byte.valueOf(type)));
        }
        input.skipBytes(input.readUnsignedShort());
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.read(input);
        return compoundTag;
    }

    public static void writePath(CompoundTag tag, Path path) throws IOException {
        writeOutputStream(tag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeOutputStream(CompoundTag tag, OutputStream output) throws IOException {
        DataOutputStream dos = new DataOutputStream(output);
        Throwable th = null;
        try {
            writeDataOutput(tag, dos);
            if (dos == null) {
                return;
            }
            if (0 != 0) {
                try {
                    dos.close();
                    return;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    return;
                }
            }
            dos.close();
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                if (dos != null) {
                    if (th3 != null) {
                        try {
                            dos.close();
                        } catch (Throwable th5) {
                            th3.addSuppressed(th5);
                        }
                    } else {
                        dos.close();
                    }
                }
                throw th4;
            }
        }
    }

    public static void writeCompressedPath(CompoundTag tag, Path path) throws IOException {
        writeCompressedOutputStream(tag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeCompressedOutputStream(CompoundTag tag, OutputStream output) throws IOException {
        DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(output));
        Throwable th = null;
        try {
            writeDataOutput(tag, dos);
            if (dos == null) {
                return;
            }
            if (0 != 0) {
                try {
                    dos.close();
                    return;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    return;
                }
            }
            dos.close();
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                if (dos != null) {
                    if (th3 != null) {
                        try {
                            dos.close();
                        } catch (Throwable th5) {
                            th3.addSuppressed(th5);
                        }
                    } else {
                        dos.close();
                    }
                }
                throw th4;
            }
        }
    }

    public static void writeDataOutput(CompoundTag tag, DataOutput output) throws IOException {
        output.writeByte(10);
        output.writeUTF("");
        tag.write(output);
    }

    public static CompoundTag readString(String input) throws IOException {
        try {
            CharBuffer buffer = new CharBuffer(input);
            TagStringReader parser = new TagStringReader(buffer);
            CompoundTag tag = parser.compound();
            if (buffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            }
            return tag;
        } catch (StringTagParseException ex) {
            throw new IOException(ex);
        }
    }

    public static String writeString(CompoundTag tag) throws IOException {
        StringBuilder sb = new StringBuilder();
        TagStringWriter emit = new TagStringWriter(sb);
        Throwable th = null;
        try {
            emit.writeTag(tag);
            if (emit != null) {
                if (0 != 0) {
                    try {
                        emit.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    emit.close();
                }
            }
            return sb.toString();
        } finally {
        }
    }
}
