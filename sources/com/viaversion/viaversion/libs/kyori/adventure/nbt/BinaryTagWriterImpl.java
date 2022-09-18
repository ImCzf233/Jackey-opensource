package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO;
import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagWriterImpl.class */
public final class BinaryTagWriterImpl implements BinaryTagIO.Writer {
    static final BinaryTagIO.Writer INSTANCE = new BinaryTagWriterImpl();

    BinaryTagWriterImpl() {
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        OutputStream os = Files.newOutputStream(path, new OpenOption[0]);
        try {
            write(tag, os, compression);
            if (os != null) {
                os.close();
            }
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        try {
            write(tag, (DataOutput) dos);
            dos.close();
        } catch (Throwable th) {
            try {
                dos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.mo138id());
        output.writeUTF("");
        BinaryTagTypes.COMPOUND.write(tag, output);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        OutputStream os = Files.newOutputStream(path, new OpenOption[0]);
        try {
            writeNamed(tag, os, compression);
            if (os != null) {
                os.close();
            }
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        try {
            writeNamed(tag, (DataOutput) dos);
            dos.close();
        } catch (Throwable th) {
            try {
                dos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO.Writer
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.mo138id());
        output.writeUTF(tag.getKey());
        BinaryTagTypes.COMPOUND.write(tag.getValue(), output);
    }
}
