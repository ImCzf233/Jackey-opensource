package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagScope.class */
public interface BinaryTagScope extends AutoCloseable {
    @Override // java.lang.AutoCloseable
    void close() throws IOException;

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagScope$NoOp.class */
    public static class NoOp implements BinaryTagScope {
        static final NoOp INSTANCE = new NoOp();

        private NoOp() {
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagScope, java.lang.AutoCloseable
        public void close() {
        }
    }
}
