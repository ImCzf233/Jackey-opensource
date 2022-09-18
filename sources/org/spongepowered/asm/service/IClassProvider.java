package org.spongepowered.asm.service;

import java.net.URL;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/IClassProvider.class */
public interface IClassProvider {
    URL[] getClassPath();

    Class<?> findClass(String str) throws ClassNotFoundException;

    Class<?> findClass(String str, boolean z) throws ClassNotFoundException;

    Class<?> findAgentClass(String str, boolean z) throws ClassNotFoundException;
}
