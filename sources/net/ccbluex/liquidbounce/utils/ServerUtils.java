package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.p004ui.client.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/ServerUtils.class */
public final class ServerUtils extends MinecraftInstance {
    public static ServerData serverData;

    public static void connectToLastServer() {
        if (serverData == null) {
            return;
        }
        f362mc.func_147108_a(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), f362mc, serverData));
    }

    public static String getRemoteIp() {
        ServerData serverData2;
        if (f362mc.field_71441_e == null) {
            return "Undefined";
        }
        String serverIp = "Singleplayer";
        if (f362mc.field_71441_e.field_72995_K && (serverData2 = f362mc.func_147104_D()) != null) {
            serverIp = serverData2.field_78845_b;
        }
        return serverIp;
    }

    public static boolean isHypixelLobby() {
        if (f362mc.field_71441_e == null) {
            return false;
        }
        for (Entity entity : f362mc.field_71441_e.field_72996_f) {
            if (entity.func_70005_c_().startsWith("§e§l") && entity.func_70005_c_().equals("§e§lCLICK TO PLAY")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHypixelDomain(String s1) {
        char[] charArray;
        int chars = 0;
        for (char c : "www.hypixel.net".toCharArray()) {
            if (s1.contains(String.valueOf(c))) {
                chars++;
            }
        }
        return chars == "www.hypixel.net".length();
    }
}
