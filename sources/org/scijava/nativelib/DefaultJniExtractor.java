package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:org/scijava/nativelib/DefaultJniExtractor.class */
public class DefaultJniExtractor extends BaseJniExtractor {
    private File nativeDir;

    public DefaultJniExtractor() throws IOException {
        super(null);
        init("tmplib");
    }

    public DefaultJniExtractor(Class libraryJarClass, String tmplib) throws IOException {
        super(libraryJarClass);
        init(tmplib);
    }

    void init(String tmplib) throws IOException {
        this.nativeDir = new File(System.getProperty("java.library.tmpdir", tmplib));
        this.nativeDir.mkdirs();
        if (!this.nativeDir.isDirectory()) {
            throw new IOException("Unable to create native library working directory " + this.nativeDir);
        }
    }

    @Override // org.scijava.nativelib.BaseJniExtractor
    public File getJniDir() {
        return this.nativeDir;
    }

    @Override // org.scijava.nativelib.BaseJniExtractor
    public File getNativeDir() {
        return this.nativeDir;
    }
}
