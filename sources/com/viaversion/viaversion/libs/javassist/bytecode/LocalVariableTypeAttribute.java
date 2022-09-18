package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/LocalVariableTypeAttribute.class */
public class LocalVariableTypeAttribute extends LocalVariableAttribute {
    public static final String tag = "LocalVariableTypeTable";

    public LocalVariableTypeAttribute(ConstPool cp) {
        super(cp, "LocalVariableTypeTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }

    public LocalVariableTypeAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    private LocalVariableTypeAttribute(ConstPool cp, byte[] dest) {
        super(cp, "LocalVariableTypeTable", dest);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.LocalVariableAttribute
    String renameEntry(String desc, String oldname, String newname) {
        return SignatureAttribute.renameClass(desc, oldname, newname);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.LocalVariableAttribute
    String renameEntry(String desc, Map<String, String> classnames) {
        return SignatureAttribute.renameClass(desc, classnames);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.LocalVariableAttribute
    LocalVariableAttribute makeThisAttr(ConstPool cp, byte[] dest) {
        return new LocalVariableTypeAttribute(cp, dest);
    }
}
