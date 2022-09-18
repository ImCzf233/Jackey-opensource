package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtField;
import com.viaversion.viaversion.libs.javassist.Modifier;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/convert/TransformReadField.class */
public class TransformReadField extends Transformer {
    protected String fieldname;
    protected CtClass fieldClass;
    protected boolean isPrivate;
    protected String methodClassname;
    protected String methodName;

    public TransformReadField(Transformer next, CtField field, String methodClassname, String methodName) {
        super(next);
        this.fieldClass = field.getDeclaringClass();
        this.fieldname = field.getName();
        this.methodClassname = methodClassname;
        this.methodName = methodName;
        this.isPrivate = Modifier.isPrivate(field.getModifiers());
    }

    public static String isField(ClassPool pool, ConstPool cp, CtClass fclass, String fname, boolean is_private, int index) {
        if (!cp.getFieldrefName(index).equals(fname)) {
            return null;
        }
        try {
            CtClass c = pool.get(cp.getFieldrefClassName(index));
            if (c == fclass || (!is_private && isFieldInSuper(c, fclass, fname))) {
                return cp.getFieldrefType(index);
            }
            return null;
        } catch (NotFoundException e) {
            return null;
        }
    }

    static boolean isFieldInSuper(CtClass clazz, CtClass fclass, String fname) {
        if (!clazz.subclassOf(fclass)) {
            return false;
        }
        try {
            CtField f = clazz.getField(fname);
            return f.getDeclaringClass() == fclass;
        } catch (NotFoundException e) {
            return false;
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.convert.Transformer
    public int transform(CtClass tclazz, int pos, CodeIterator iterator, ConstPool cp) throws BadBytecode {
        int c = iterator.byteAt(pos);
        if (c == 180 || c == 178) {
            int index = iterator.u16bitAt(pos + 1);
            String typedesc = isField(tclazz.getClassPool(), cp, this.fieldClass, this.fieldname, this.isPrivate, index);
            if (typedesc != null) {
                if (c == 178) {
                    iterator.move(pos);
                    iterator.writeByte(1, iterator.insertGap(1));
                    pos = iterator.next();
                }
                String type = "(Ljava/lang/Object;)" + typedesc;
                int mi = cp.addClassInfo(this.methodClassname);
                int methodref = cp.addMethodrefInfo(mi, this.methodName, type);
                iterator.writeByte(184, pos);
                iterator.write16bit(methodref, pos + 1);
                return pos;
            }
        }
        return pos;
    }
}
