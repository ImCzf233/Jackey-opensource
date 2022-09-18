package org.apache.log4j.spi;

import java.io.Writer;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/NullWriter.class */
class NullWriter extends Writer {
    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    @Override // java.io.Writer
    public void write(char[] cbuf, int off, int len) {
    }
}
