package org.spongepowered.asm.service;

import java.io.IOException;
import org.spongepowered.asm.lib.tree.ClassNode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/IClassBytecodeProvider.class */
public interface IClassBytecodeProvider {
    byte[] getClassBytes(String str, String str2) throws IOException;

    byte[] getClassBytes(String str, boolean z) throws ClassNotFoundException, IOException;

    ClassNode getClassNode(String str) throws ClassNotFoundException, IOException;
}
