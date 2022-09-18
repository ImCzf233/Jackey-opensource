package com.viaversion.viaversion.libs.javassist.bytecode;

/* compiled from: ExceptionTable.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/ExceptionTableEntry.class */
class ExceptionTableEntry {
    int startPc;
    int endPc;
    int handlerPc;
    int catchType;

    public ExceptionTableEntry(int start, int end, int handle, int type) {
        this.startPc = start;
        this.endPc = end;
        this.handlerPc = handle;
        this.catchType = type;
    }
}
