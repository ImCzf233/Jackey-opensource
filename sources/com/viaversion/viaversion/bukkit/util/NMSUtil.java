package com.viaversion.viaversion.bukkit.util;

import org.bukkit.Bukkit;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/util/NMSUtil.class */
public class NMSUtil {
    private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static final boolean DEBUG_PROPERTY = loadDebugProperty();

    private static boolean loadDebugProperty() {
        try {
            Class<?> serverClass = nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
            Object server = serverClass.getDeclaredMethod("getServer", new Class[0]).invoke(null, new Object[0]);
            return ((Boolean) serverClass.getMethod("isDebugging", new Class[0]).invoke(server, new Object[0])).booleanValue();
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static Class<?> nms(String className) throws ClassNotFoundException {
        return Class.forName(NMS + "." + className);
    }

    public static Class<?> nms(String className, String fallbackFullClassName) throws ClassNotFoundException {
        try {
            return Class.forName(NMS + "." + className);
        } catch (ClassNotFoundException e) {
            return Class.forName(fallbackFullClassName);
        }
    }

    public static Class<?> obc(String className) throws ClassNotFoundException {
        return Class.forName(BASE + "." + className);
    }

    public static String getVersion() {
        return BASE.substring(BASE.lastIndexOf(46) + 1);
    }

    public static boolean isDebugPropertySet() {
        return DEBUG_PROPERTY;
    }
}
