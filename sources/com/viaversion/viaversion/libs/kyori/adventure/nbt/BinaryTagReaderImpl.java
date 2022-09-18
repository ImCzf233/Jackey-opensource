package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagReaderImpl.class */
public final class BinaryTagReaderImpl implements BinaryTagIO.Reader {
    private final long maxBytes;
    static final BinaryTagIO.Reader UNLIMITED = new BinaryTagReaderImpl(-1);
    static final BinaryTagIO.Reader DEFAULT_LIMIT = new BinaryTagReaderImpl(131082);

    public BinaryTagReaderImpl(final long maxBytes) {
        this.maxBytes = maxBytes;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    @NotNull
    public CompoundBinaryTag read(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        InputStream is = Files.newInputStream(path, new OpenOption[0]);
        try {
            CompoundBinaryTag read = read(is, compression);
            if (is != null) {
                is.close();
            }
            return read;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    @NotNull
    public CompoundBinaryTag read(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        try {
            CompoundBinaryTag read = read((DataInput) dis);
            dis.close();
            return read;
        } catch (Throwable th) {
            try {
                dis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    @NotNull
    public CompoundBinaryTag read(@NotNull DataInput input) throws IOException {
        if (!(input instanceof TrackingDataInput)) {
            input = new TrackingDataInput(input, this.maxBytes);
        }
        BinaryTagType<? extends BinaryTag> type = BinaryTagType.m139of(input.readByte());
        requireCompound(type);
        input.skipBytes(input.readUnsignedShort());
        return BinaryTagTypes.COMPOUND.read(input);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        InputStream is = Files.newInputStream(path, new OpenOption[0]);
        try {
            Map.Entry<String, CompoundBinaryTag> readNamed = readNamed(is, compression);
            if (is != null) {
                is.close();
            }
            return readNamed;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        try {
            Map.Entry<String, CompoundBinaryTag> readNamed = readNamed((DataInput) dis);
            dis.close();
            return readNamed;
        } catch (Throwable th) {
            try {
                dis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Reader
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final DataInput input) throws IOException {
        BinaryTagType<? extends BinaryTag> type = BinaryTagType.m139of(input.readByte());
        requireCompound(type);
        String name = input.readUTF();
        return new AbstractMap.SimpleImmutableEntry(name, BinaryTagTypes.COMPOUND.read(input));
    }

    private static void requireCompound(final BinaryTagType<? extends BinaryTag> type) throws IOException {
        if (type != BinaryTagTypes.COMPOUND) {
            throw new IOException(String.format("Expected root tag to be a %s, was %s", BinaryTagTypes.COMPOUND, type));
        }
    }
}
