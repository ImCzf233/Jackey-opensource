package org.spongepowered.asm.service;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/IGlobalPropertyService.class */
public interface IGlobalPropertyService {
    <T> T getProperty(String str);

    void setProperty(String str, Object obj);

    <T> T getProperty(String str, T t);

    String getPropertyString(String str, String str2);
}
