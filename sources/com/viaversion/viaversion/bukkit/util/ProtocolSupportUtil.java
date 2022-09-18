package com.viaversion.viaversion.bukkit.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/util/ProtocolSupportUtil.class */
public class ProtocolSupportUtil {
    private static Method protocolVersionMethod;
    private static Method getIdMethod;

    static {
        protocolVersionMethod = null;
        getIdMethod = null;
        try {
            protocolVersionMethod = Class.forName("protocolsupport.api.ProtocolSupportAPI").getMethod("getProtocolVersion", Player.class);
            getIdMethod = Class.forName("protocolsupport.api.ProtocolVersion").getMethod("getId", new Class[0]);
        } catch (Exception e) {
        }
    }

    public static int getProtocolVersion(Player player) {
        if (protocolVersionMethod == null) {
            return -1;
        }
        try {
            Object version = protocolVersionMethod.invoke(null, player);
            return ((Integer) getIdMethod.invoke(version, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return -1;
        }
    }
}
