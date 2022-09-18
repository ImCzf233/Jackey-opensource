package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/IOStreamUtil.class */
final class IOStreamUtil {
    private IOStreamUtil() {
    }

    public static InputStream closeShield(final InputStream stream) {
        return new InputStream() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.IOStreamUtil.1
            @Override // java.io.InputStream
            public int read() throws IOException {
                return stream.read();
            }

            @Override // java.io.InputStream
            public int read(final byte[] b) throws IOException {
                return stream.read(b);
            }

            @Override // java.io.InputStream
            public int read(final byte[] b, final int off, final int len) throws IOException {
                return stream.read(b, off, len);
            }
        };
    }

    public static OutputStream closeShield(final OutputStream stream) {
        return new OutputStream() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.IOStreamUtil.2
            @Override // java.io.OutputStream
            public void write(final int b) throws IOException {
                stream.write(b);
            }

            @Override // java.io.OutputStream
            public void write(final byte[] b) throws IOException {
                stream.write(b);
            }

            @Override // java.io.OutputStream
            public void write(final byte[] b, final int off, final int len) throws IOException {
                stream.write(b, off, len);
            }
        };
    }
}
