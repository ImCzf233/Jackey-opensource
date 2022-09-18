package jdk.internal.dynalink.beans;

import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CheckRestrictedPackage.class */
class CheckRestrictedPackage {
    private static final AccessControlContext NO_PERMISSIONS_CONTEXT = createNoPermissionsContext();

    CheckRestrictedPackage() {
    }

    public static boolean isRestrictedClass(Class<?> clazz) {
        final String name;
        final int i;
        if (!Modifier.isPublic(clazz.getModifiers())) {
            return true;
        }
        final SecurityManager sm = System.getSecurityManager();
        if (sm == null || (i = (name = clazz.getName()).lastIndexOf(46)) == -1) {
            return false;
        }
        try {
            AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: jdk.internal.dynalink.beans.CheckRestrictedPackage.1
                @Override // java.security.PrivilegedAction
                public Void run() {
                    sm.checkPackageAccess(name.substring(0, i));
                    return null;
                }
            }, NO_PERMISSIONS_CONTEXT);
            return false;
        } catch (SecurityException e) {
            return true;
        }
    }

    private static AccessControlContext createNoPermissionsContext() {
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, new Permissions())});
    }
}
