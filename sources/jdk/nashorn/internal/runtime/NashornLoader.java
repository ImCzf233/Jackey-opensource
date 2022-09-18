package jdk.nashorn.internal.runtime;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.SecureClassLoader;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/NashornLoader.class */
abstract class NashornLoader extends SecureClassLoader {
    private static final String OBJECTS_PKG = "jdk.nashorn.internal.objects";
    private static final String RUNTIME_PKG = "jdk.nashorn.internal.runtime";
    private static final String RUNTIME_ARRAYS_PKG = "jdk.nashorn.internal.runtime.arrays";
    private static final String RUNTIME_LINKER_PKG = "jdk.nashorn.internal.runtime.linker";
    private static final String SCRIPTS_PKG = "jdk.nashorn.internal.scripts";
    private static final Permission[] SCRIPT_PERMISSIONS = {new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.scripts"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.arrays")};

    public NashornLoader(ClassLoader parent) {
        super(parent);
    }

    public static void checkPackageAccess(String name) {
        SecurityManager sm;
        int i = name.lastIndexOf(46);
        if (i != -1 && (sm = System.getSecurityManager()) != null) {
            String pkgName = name.substring(0, i);
            boolean z = true;
            switch (pkgName.hashCode()) {
                case -1427089809:
                    if (pkgName.equals(RUNTIME_PKG)) {
                        z = false;
                        break;
                    }
                    break;
                case -1051537505:
                    if (pkgName.equals(SCRIPTS_PKG)) {
                        z = true;
                        break;
                    }
                    break;
                case -342733909:
                    if (pkgName.equals(OBJECTS_PKG)) {
                        z = true;
                        break;
                    }
                    break;
                case 69590425:
                    if (pkgName.equals(RUNTIME_ARRAYS_PKG)) {
                        z = true;
                        break;
                    }
                    break;
                case 376089222:
                    if (pkgName.equals(RUNTIME_LINKER_PKG)) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                case true:
                case true:
                case true:
                    return;
                default:
                    sm.checkPackageAccess(pkgName);
                    return;
            }
        }
    }

    @Override // java.security.SecureClassLoader
    public PermissionCollection getPermissions(CodeSource codesource) {
        Permission[] permissionArr;
        Permissions permCollection = new Permissions();
        for (Permission perm : SCRIPT_PERMISSIONS) {
            permCollection.add(perm);
        }
        return permCollection;
    }

    public static ClassLoader createClassLoader(String classPath, ClassLoader parent) {
        URL[] urls = pathToURLs(classPath);
        return URLClassLoader.newInstance(urls, parent);
    }

    private static URL[] pathToURLs(String path) {
        String[] components = path.split(File.pathSeparator);
        URL[] urls = new URL[components.length];
        int count = 0;
        while (count < components.length) {
            URL url = fileToURL(new File(components[count]));
            if (url != null) {
                int i = count;
                count++;
                urls[i] = url;
            }
        }
        if (urls.length != count) {
            URL[] tmp = new URL[count];
            System.arraycopy(urls, 0, tmp, 0, count);
            urls = tmp;
        }
        return urls;
    }

    private static URL fileToURL(File file) {
        String name;
        try {
            name = file.getCanonicalPath();
        } catch (IOException e) {
            name = file.getAbsolutePath();
        }
        String name2 = name.replace(File.separatorChar, '/');
        if (!name2.startsWith("/")) {
            name2 = "/" + name2;
        }
        if (!file.isFile()) {
            name2 = name2 + "/";
        }
        try {
            return new URL("file", "", name2);
        } catch (MalformedURLException e2) {
            throw new IllegalArgumentException("file");
        }
    }
}
