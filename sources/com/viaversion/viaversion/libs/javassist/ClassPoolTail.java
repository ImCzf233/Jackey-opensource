package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ClassPoolTail.class */
public final class ClassPoolTail {
    protected ClassPathList pathList = null;

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[class path: ");
        ClassPathList classPathList = this.pathList;
        while (true) {
            ClassPathList list = classPathList;
            if (list != null) {
                buf.append(list.path.toString());
                buf.append(File.pathSeparatorChar);
                classPathList = list.next;
            } else {
                buf.append(']');
                return buf.toString();
            }
        }
    }

    public synchronized ClassPath insertClassPath(ClassPath cp) {
        this.pathList = new ClassPathList(cp, this.pathList);
        return cp;
    }

    public synchronized ClassPath appendClassPath(ClassPath cp) {
        ClassPathList tail = new ClassPathList(cp, null);
        ClassPathList list = this.pathList;
        if (list == null) {
            this.pathList = tail;
        } else {
            while (list.next != null) {
                list = list.next;
            }
            list.next = tail;
        }
        return cp;
    }

    public synchronized void removeClassPath(ClassPath cp) {
        ClassPathList list = this.pathList;
        if (list != null) {
            if (list.path == cp) {
                this.pathList = list.next;
                return;
            }
            while (list.next != null) {
                if (list.next.path == cp) {
                    list.next = list.next.next;
                } else {
                    list = list.next;
                }
            }
        }
    }

    public ClassPath appendSystemPath() {
        if (ClassFile.MAJOR_VERSION < 53) {
            return appendClassPath(new ClassClassPath());
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return appendClassPath(new LoaderClassPath(cl));
    }

    public ClassPath insertClassPath(String pathname) throws NotFoundException {
        return insertClassPath(makePathObject(pathname));
    }

    public ClassPath appendClassPath(String pathname) throws NotFoundException {
        return appendClassPath(makePathObject(pathname));
    }

    private static ClassPath makePathObject(String pathname) throws NotFoundException {
        String lower = pathname.toLowerCase();
        if (lower.endsWith(".jar") || lower.endsWith(".zip")) {
            return new JarClassPath(pathname);
        }
        int len = pathname.length();
        if (len > 2 && pathname.charAt(len - 1) == '*' && (pathname.charAt(len - 2) == '/' || pathname.charAt(len - 2) == File.separatorChar)) {
            String dir = pathname.substring(0, len - 2);
            return new JarDirClassPath(dir);
        }
        return new DirClassPath(pathname);
    }

    public void writeClassfile(String classname, OutputStream out) throws NotFoundException, IOException, CannotCompileException {
        InputStream fin = openClassfile(classname);
        if (fin == null) {
            throw new NotFoundException(classname);
        }
        try {
            copyStream(fin, out);
            fin.close();
        } catch (Throwable th) {
            fin.close();
            throw th;
        }
    }

    public InputStream openClassfile(String classname) throws NotFoundException {
        InputStream ins = null;
        NotFoundException error = null;
        for (ClassPathList list = this.pathList; list != null; list = list.next) {
            try {
                ins = list.path.openClassfile(classname);
            } catch (NotFoundException e) {
                if (error == null) {
                    error = e;
                }
            }
            if (ins != null) {
                return ins;
            }
        }
        if (error != null) {
            throw error;
        }
        return null;
    }

    public URL find(String classname) {
        for (ClassPathList list = this.pathList; list != null; list = list.next) {
            URL url = list.path.find(classname);
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static byte[] readStream(InputStream fin) throws IOException {
        byte[] bArr = new byte[8];
        int bufsize = 4096;
        for (int i = 0; i < 8; i++) {
            bArr[i] = new byte[bufsize];
            int size = 0;
            do {
                int len = fin.read(bArr[i], size, bufsize - size);
                if (len >= 0) {
                    size += len;
                } else {
                    byte[] result = new byte[(bufsize - 4096) + size];
                    int s = 0;
                    for (int j = 0; j < i; j++) {
                        System.arraycopy(bArr[j], 0, result, s, s + 4096);
                        s = s + s + 4096;
                    }
                    System.arraycopy(bArr[i], 0, result, s, size);
                    return result;
                }
            } while (size < bufsize);
            bufsize *= 2;
        }
        throw new IOException("too much data");
    }

    public static void copyStream(InputStream fin, OutputStream fout) throws IOException {
        int bufsize = 4096;
        byte[] buf = null;
        for (int i = 0; i < 64; i++) {
            if (i < 8) {
                bufsize *= 2;
                buf = new byte[bufsize];
            }
            int size = 0;
            do {
                int len = fin.read(buf, size, bufsize - size);
                if (len >= 0) {
                    size += len;
                } else {
                    fout.write(buf, 0, size);
                    return;
                }
            } while (size < bufsize);
            fout.write(buf);
        }
        throw new IOException("too much data");
    }
}
