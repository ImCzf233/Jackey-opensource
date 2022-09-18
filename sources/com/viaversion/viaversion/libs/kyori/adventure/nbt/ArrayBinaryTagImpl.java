package com.viaversion.viaversion.libs.kyori.adventure.nbt;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ArrayBinaryTagImpl.class */
abstract class ArrayBinaryTagImpl extends AbstractBinaryTag implements ArrayBinaryTag {
    public static void checkIndex(final int index, final int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}
