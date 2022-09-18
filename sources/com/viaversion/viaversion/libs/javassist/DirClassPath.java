package com.viaversion.viaversion.libs.javassist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* compiled from: ClassPoolTail.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/DirClassPath.class */
final class DirClassPath implements ClassPath {
    String directory;

    public DirClassPath(String dirName) {
        this.directory = dirName;
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public InputStream openClassfile(String classname) {
        try {
            char sep = File.separatorChar;
            String filename = this.directory + sep + classname.replace('.', sep) + ".class";
            return new FileInputStream(filename.toString());
        } catch (FileNotFoundException | SecurityException e) {
            return null;
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public URL find(String classname) {
        char sep = File.separatorChar;
        String filename = this.directory + sep + classname.replace('.', sep) + ".class";
        File f = new File(filename);
        if (f.exists()) {
            try {
                return f.getCanonicalFile().toURI().toURL();
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e2) {
                return null;
            }
        }
        return null;
    }

    public String toString() {
        return this.directory;
    }
}
