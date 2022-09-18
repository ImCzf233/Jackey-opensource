package com.viaversion.viaversion.api.command;

import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/command/ViaCommandSender.class */
public interface ViaCommandSender {
    boolean hasPermission(String str);

    void sendMessage(String str);

    UUID getUUID();

    String getName();
}
