package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/convert/TransformAfter.class */
public class TransformAfter extends TransformBefore {
    public TransformAfter(Transformer next, CtMethod origMethod, CtMethod afterMethod) throws NotFoundException {
        super(next, origMethod, afterMethod);
    }

    @Override // com.viaversion.viaversion.libs.javassist.convert.TransformBefore
    protected int match2(int pos, CodeIterator iterator) throws BadBytecode {
        iterator.move(pos);
        iterator.insert(this.saveCode);
        iterator.insert(this.loadCode);
        iterator.setMark(iterator.insertGap(3));
        iterator.insert(this.loadCode);
        int pos2 = iterator.next();
        int p = iterator.getMark();
        iterator.writeByte(iterator.byteAt(pos2), p);
        iterator.write16bit(iterator.u16bitAt(pos2 + 1), p + 1);
        iterator.writeByte(184, pos2);
        iterator.write16bit(this.newIndex, pos2 + 1);
        iterator.move(p);
        return iterator.next();
    }
}
