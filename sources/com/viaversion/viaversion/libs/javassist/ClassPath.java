package com.viaversion.viaversion.libs.javassist;

import java.io.InputStream;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ClassPath.class */
public interface ClassPath {
    InputStream openClassfile(String str) throws NotFoundException;

    URL find(String str);
}
