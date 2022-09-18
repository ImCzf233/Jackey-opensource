package jdk.nashorn.api.scripting;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Source;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/URLReader.class */
public final class URLReader extends Reader {
    private final URL url;

    /* renamed from: cs */
    private final Charset f223cs;
    private Reader reader;

    public URLReader(URL url) {
        this(url, (Charset) null);
    }

    public URLReader(URL url, String charsetName) {
        this(url, Charset.forName(charsetName));
    }

    public URLReader(URL url, Charset cs) {
        this.url = (URL) Objects.requireNonNull(url);
        this.f223cs = cs;
    }

    @Override // java.io.Reader
    public int read(char[] cbuf, int off, int len) throws IOException {
        return getReader().read(cbuf, off, len);
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        getReader().close();
    }

    public URL getURL() {
        return this.url;
    }

    public Charset getCharset() {
        return this.f223cs;
    }

    private Reader getReader() throws IOException {
        synchronized (this.lock) {
            if (this.reader == null) {
                this.reader = new CharArrayReader(Source.readFully(this.url, this.f223cs));
            }
        }
        return this.reader;
    }
}
