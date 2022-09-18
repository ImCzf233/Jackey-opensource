package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import org.json.JSONObject;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/IPCListener.class */
public interface IPCListener {
    default void onPacketSent(IPCClient client, Packet packet) {
    }

    default void onPacketReceived(IPCClient client, Packet packet) {
    }

    default void onActivityJoin(IPCClient client, String secret) {
    }

    default void onActivitySpectate(IPCClient client, String secret) {
    }

    default void onActivityJoinRequest(IPCClient client, String secret, User user) {
    }

    default void onReady(IPCClient client) {
    }

    default void onClose(IPCClient client, JSONObject json) {
    }

    default void onDisconnect(IPCClient client, Throwable t) {
    }
}
