package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/mojang/Blackboard.class */
public class Blackboard implements IGlobalPropertyService {
    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final <T> T getProperty(String key) {
        return (T) Launch.blackboard.get(key);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final void setProperty(String key, Object value) {
        Launch.blackboard.put(key, value);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final <T> T getProperty(String key, T defaultValue) {
        T t = (T) Launch.blackboard.get(key);
        return t != null ? t : defaultValue;
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final String getPropertyString(String key, String defaultValue) {
        Object value = Launch.blackboard.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}
