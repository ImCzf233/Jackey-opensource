package com.viaversion.viaversion.libs.javassist.tools;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.bytecode.analysis.FramePrinter;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/framedump.class */
public class framedump {
    private framedump() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java javassist.tools.framedump <fully-qualified class name>");
            return;
        }
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(args[0]);
        System.out.println("Frame Dump of " + clazz.getName() + CallSiteDescriptor.TOKEN_DELIMITER);
        FramePrinter.print(clazz, System.out);
    }
}
