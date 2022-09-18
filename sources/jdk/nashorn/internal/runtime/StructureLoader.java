package jdk.nashorn.internal.runtime;

import java.security.ProtectionDomain;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/StructureLoader.class */
public final class StructureLoader extends NashornLoader {
    private static final String SINGLE_FIELD_PREFIX = Compiler.binaryName(Compiler.SCRIPTS_PACKAGE) + '.' + CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
    private static final String DUAL_FIELD_PREFIX = Compiler.binaryName(Compiler.SCRIPTS_PACKAGE) + '.' + CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName();

    public StructureLoader(ClassLoader parent) {
        super(parent);
    }

    private static boolean isDualFieldStructure(String name) {
        return name.startsWith(DUAL_FIELD_PREFIX);
    }

    public static boolean isSingleFieldStructure(String name) {
        return name.startsWith(SINGLE_FIELD_PREFIX);
    }

    public static boolean isStructureClass(String name) {
        return isDualFieldStructure(name) || isSingleFieldStructure(name);
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (isDualFieldStructure(name)) {
            return generateClass(name, name.substring(DUAL_FIELD_PREFIX.length()), true);
        }
        if (isSingleFieldStructure(name)) {
            return generateClass(name, name.substring(SINGLE_FIELD_PREFIX.length()), false);
        }
        return super.findClass(name);
    }

    private Class<?> generateClass(String name, String descriptor, boolean dualFields) {
        Context context = Context.getContextTrusted();
        byte[] code = new ObjectClassGenerator(context, dualFields).generate(descriptor);
        return defineClass(name, code, 0, code.length, new ProtectionDomain(null, getPermissions(null)));
    }
}
