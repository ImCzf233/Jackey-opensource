package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.bytecode.AnnotationsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.Annotation;
import com.viaversion.viaversion.libs.javassist.bytecode.annotation.AnnotationsWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/ParameterAnnotationsAttribute.class */
public class ParameterAnnotationsAttribute extends AttributeInfo {
    public static final String visibleTag = "RuntimeVisibleParameterAnnotations";
    public static final String invisibleTag = "RuntimeInvisibleParameterAnnotations";

    public ParameterAnnotationsAttribute(ConstPool cp, String attrname, byte[] info) {
        super(cp, attrname, info);
    }

    public ParameterAnnotationsAttribute(ConstPool cp, String attrname) {
        this(cp, attrname, new byte[]{0});
    }

    public ParameterAnnotationsAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public int numParameters() {
        return this.info[0] & 255;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);
        try {
            copier.parameters();
            return new ParameterAnnotationsAttribute(newCp, getName(), copier.close());
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public Annotation[][] getAnnotations() {
        try {
            return new AnnotationsAttribute.Parser(this.info, this.constPool).parseParameters();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void setAnnotations(Annotation[][] params) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);
        try {
            writer.numParameters(params.length);
            for (Annotation[] anno : params) {
                writer.numAnnotations(anno.length);
                for (Annotation annotation : anno) {
                    annotation.write(writer);
                }
            }
            writer.close();
            set(output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    void renameClass(String oldname, String newname) {
        Map<String, String> map = new HashMap<>();
        map.put(oldname, newname);
        renameClass(map);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    void renameClass(Map<String, String> classnames) {
        AnnotationsAttribute.Renamer renamer = new AnnotationsAttribute.Renamer(this.info, getConstPool(), classnames);
        try {
            renamer.parameters();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    void getRefClasses(Map<String, String> classnames) {
        renameClass(classnames);
    }

    public String toString() {
        Annotation[][] aa = getAnnotations();
        StringBuilder sbuf = new StringBuilder();
        for (Annotation[] a : aa) {
            for (Annotation i : a) {
                sbuf.append(i.toString()).append(" ");
            }
            sbuf.append(", ");
        }
        return sbuf.toString().replaceAll(" (?=,)|, $", "");
    }
}
