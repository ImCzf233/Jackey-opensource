package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagIO.class */
public final class BinaryTagIO {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagIO$Compression.class */
    public static abstract class Compression {
        public static final Compression NONE = new Compression() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public InputStream decompress(@NotNull final InputStream is) {
                return is;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public OutputStream compress(@NotNull final OutputStream os) {
                return os;
            }

            public String toString() {
                return "Compression.NONE";
            }
        };
        public static final Compression GZIP = new Compression() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public InputStream decompress(@NotNull final InputStream is) throws IOException {
                return new GZIPInputStream(is);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public OutputStream compress(@NotNull final OutputStream os) throws IOException {
                return new GZIPOutputStream(os);
            }

            public String toString() {
                return "Compression.GZIP";
            }
        };
        public static final Compression ZLIB = new Compression() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public InputStream decompress(@NotNull final InputStream is) {
                return new InflaterInputStream(is);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Compression
            @NotNull
            public OutputStream compress(@NotNull final OutputStream os) {
                return new DeflaterOutputStream(os);
            }

            public String toString() {
                return "Compression.ZLIB";
            }
        };

        @NotNull
        public abstract InputStream decompress(@NotNull final InputStream is) throws IOException;

        @NotNull
        public abstract OutputStream compress(@NotNull final OutputStream os) throws IOException;
    }

    private BinaryTagIO() {
    }

    static {
        BinaryTagTypes.COMPOUND.mo138id();
    }

    @NotNull
    public static Reader unlimitedReader() {
        return BinaryTagReaderImpl.UNLIMITED;
    }

    @NotNull
    public static Reader reader() {
        return BinaryTagReaderImpl.DEFAULT_LIMIT;
    }

    @NotNull
    public static Reader reader(final long sizeLimitBytes) {
        if (sizeLimitBytes <= 0) {
            throw new IllegalArgumentException("The size limit must be greater than zero");
        }
        return new BinaryTagReaderImpl(sizeLimitBytes);
    }

    @NotNull
    public static Writer writer() {
        return BinaryTagWriterImpl.INSTANCE;
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readPath(@NotNull final Path path) throws IOException {
        return reader().read(path);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readInputStream(@NotNull final InputStream input) throws IOException {
        return reader().read(input);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedPath(@NotNull final Path path) throws IOException {
        return reader().read(path, Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedInputStream(@NotNull final InputStream input) throws IOException {
        return reader().read(input, Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readDataInput(@NotNull final DataInput input) throws IOException {
        return reader().read(input);
    }

    @Deprecated
    public static void writePath(@NotNull final CompoundBinaryTag tag, @NotNull final Path path) throws IOException {
        writer().write(tag, path);
    }

    @Deprecated
    public static void writeOutputStream(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output) throws IOException {
        writer().write(tag, output);
    }

    @Deprecated
    public static void writeCompressedPath(@NotNull final CompoundBinaryTag tag, @NotNull final Path path) throws IOException {
        writer().write(tag, path, Compression.GZIP);
    }

    @Deprecated
    public static void writeCompressedOutputStream(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output) throws IOException {
        writer().write(tag, output, Compression.GZIP);
    }

    @Deprecated
    public static void writeDataOutput(@NotNull final CompoundBinaryTag tag, @NotNull final DataOutput output) throws IOException {
        writer().write(tag, output);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagIO$Reader.class */
    public interface Reader {
        @NotNull
        CompoundBinaryTag read(@NotNull final Path path, @NotNull final Compression compression) throws IOException;

        @NotNull
        CompoundBinaryTag read(@NotNull final InputStream input, @NotNull final Compression compression) throws IOException;

        @NotNull
        CompoundBinaryTag read(@NotNull final DataInput input) throws IOException;

        Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final Path path, @NotNull final Compression compression) throws IOException;

        Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final InputStream input, @NotNull final Compression compression) throws IOException;

        Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final DataInput input) throws IOException;

        @NotNull
        default CompoundBinaryTag read(@NotNull final Path path) throws IOException {
            return read(path, Compression.NONE);
        }

        @NotNull
        default CompoundBinaryTag read(@NotNull final InputStream input) throws IOException {
            return read(input, Compression.NONE);
        }

        default Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final Path path) throws IOException {
            return readNamed(path, Compression.NONE);
        }

        default Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final InputStream input) throws IOException {
            return readNamed(input, Compression.NONE);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagIO$Writer.class */
    public interface Writer {
        void write(@NotNull final CompoundBinaryTag tag, @NotNull final Path path, @NotNull final Compression compression) throws IOException;

        void write(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output, @NotNull final Compression compression) throws IOException;

        void write(@NotNull final CompoundBinaryTag tag, @NotNull final DataOutput output) throws IOException;

        void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final Path path, @NotNull final Compression compression) throws IOException;

        void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final OutputStream output, @NotNull final Compression compression) throws IOException;

        void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final DataOutput output) throws IOException;

        default void write(@NotNull final CompoundBinaryTag tag, @NotNull final Path path) throws IOException {
            write(tag, path, Compression.NONE);
        }

        default void write(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output) throws IOException {
            write(tag, output, Compression.NONE);
        }

        default void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final Path path) throws IOException {
            writeNamed(tag, path, Compression.NONE);
        }

        default void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final OutputStream output) throws IOException {
            writeNamed(tag, output, Compression.NONE);
        }
    }
}
