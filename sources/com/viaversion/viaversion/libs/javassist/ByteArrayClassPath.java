package com.viaversion.viaversion.libs.javassist;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ByteArrayClassPath.class */
public class ByteArrayClassPath implements ClassPath {
    protected String classname;
    protected byte[] classfile;

    public ByteArrayClassPath(String name, byte[] classfile) {
        this.classname = name;
        this.classfile = classfile;
    }

    public String toString() {
        return "byte[]:" + this.classname;
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public InputStream openClassfile(String classname) {
        if (this.classname.equals(classname)) {
            return new ByteArrayInputStream(this.classfile);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public URL find(String classname) {
        if (this.classname.equals(classname)) {
            String cname = classname.replace('.', '/') + ".class";
            try {
                return new URL((URL) null, "file:/ByteArrayClassPath/" + cname, new BytecodeURLStreamHandler());
            } catch (MalformedURLException e) {
                return null;
            }
        }
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ByteArrayClassPath$BytecodeURLStreamHandler.class */
    private class BytecodeURLStreamHandler extends URLStreamHandler {
        private BytecodeURLStreamHandler() {
            ByteArrayClassPath.this = r4;
        }

        @Override // java.net.URLStreamHandler
        protected URLConnection openConnection(URL u) {
            return new BytecodeURLConnection(u);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ByteArrayClassPath$BytecodeURLConnection.class */
    private class BytecodeURLConnection extends URLConnection {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected BytecodeURLConnection(URL url) {
            super(url);
            ByteArrayClassPath.this = r4;
        }

        @Override // java.net.URLConnection
        public void connect() throws IOException {
        }

        @Override // java.net.URLConnection
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(ByteArrayClassPath.this.classfile);
        }

        @Override // java.net.URLConnection
        public int getContentLength() {
            return ByteArrayClassPath.this.classfile.length;
        }
    }
}
