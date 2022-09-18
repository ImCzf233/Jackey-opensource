package org.scijava.nativelib;

import java.io.IOException;

/* loaded from: Jackey Client b2.jar:org/scijava/nativelib/NativeLoader.class */
public class NativeLoader {
    private static JniExtractor jniExtractor;

    static {
        jniExtractor = null;
        try {
            if (NativeLoader.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
                jniExtractor = new DefaultJniExtractor();
            } else {
                jniExtractor = new WebappJniExtractor("Classloader");
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void loadLibrary(String libname) throws IOException {
        System.load(jniExtractor.extractJni("", libname).getAbsolutePath());
    }

    public static void extractRegistered() throws IOException {
        jniExtractor.extractRegistered();
    }

    public static JniExtractor getJniExtractor() {
        return jniExtractor;
    }

    public static void setJniExtractor(JniExtractor jniExtractor2) {
        jniExtractor = jniExtractor2;
    }
}
