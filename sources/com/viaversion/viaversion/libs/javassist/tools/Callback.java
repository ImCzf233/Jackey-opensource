package com.viaversion.viaversion.libs.javassist.tools;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/Callback.class */
public abstract class Callback {
    public static Map<String, Callback> callbacks = new HashMap();
    private final String sourceCode;

    public abstract void result(Object[] objArr);

    public Callback(String src) {
        String uuid = UUID.randomUUID().toString();
        callbacks.put(uuid, this);
        this.sourceCode = "((javassist.tools.Callback) javassist.tools.Callback.callbacks.get(\"" + uuid + "\")).result(new Object[]{" + src + "});";
    }

    public String toString() {
        return sourceCode();
    }

    public String sourceCode() {
        return this.sourceCode;
    }

    public static void insertBefore(CtBehavior behavior, Callback callback) throws CannotCompileException {
        behavior.insertBefore(callback.toString());
    }

    public static void insertAfter(CtBehavior behavior, Callback callback) throws CannotCompileException {
        behavior.insertAfter(callback.toString(), false);
    }

    public static void insertAfter(CtBehavior behavior, Callback callback, boolean asFinally) throws CannotCompileException {
        behavior.insertAfter(callback.toString(), asFinally);
    }

    public static int insertAt(CtBehavior behavior, Callback callback, int lineNum) throws CannotCompileException {
        return behavior.insertAt(lineNum, callback.toString());
    }
}
