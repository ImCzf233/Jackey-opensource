package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:org/scijava/nativelib/JniExtractor.class */
public interface JniExtractor {
    File extractJni(String str, String str2) throws IOException;

    void extractRegistered() throws IOException;
}
