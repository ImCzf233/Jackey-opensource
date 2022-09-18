package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/SessionUtils.class */
public class SessionUtils extends MinecraftInstance implements Listenable {
    private static final MSTimer sessionTimer = new MSTimer();
    private static final MSTimer worldTimer = new MSTimer();
    public static long lastSessionTime = 0;
    public static long backupSessionTime = 0;
    public static long lastWorldTime = 0;
    private static boolean requireDelay = false;
    private static GuiScreen lastScreen = null;

    @EventTarget
    public void onWorld(WorldEvent event) {
        lastWorldTime = System.currentTimeMillis() - worldTimer.time;
        worldTimer.reset();
        if (event.getWorldClient() == null) {
            backupSessionTime = System.currentTimeMillis() - sessionTimer.time;
            requireDelay = true;
            return;
        }
        requireDelay = false;
    }

    @EventTarget
    public void onSession(SessionEvent event) {
        handleConnection();
    }

    @EventTarget
    public void onScreen(ScreenEvent event) {
        if (event.getGuiScreen() == null && lastScreen != null && ((lastScreen instanceof GuiDownloadTerrain) || (lastScreen instanceof GuiConnecting))) {
            handleReconnection();
        }
        lastScreen = event.getGuiScreen();
    }

    public static void handleConnection() {
        backupSessionTime = 0L;
        requireDelay = true;
        lastSessionTime = System.currentTimeMillis() - sessionTimer.time;
        if (lastSessionTime < 0) {
            lastSessionTime = 0L;
        }
        sessionTimer.reset();
    }

    public static void handleReconnection() {
        if (requireDelay) {
            sessionTimer.time = System.currentTimeMillis() - backupSessionTime;
        }
    }

    public static String getFormatSessionTime() {
        if (System.currentTimeMillis() - sessionTimer.time < 0) {
            sessionTimer.reset();
        }
        int realTime = ((int) (System.currentTimeMillis() - sessionTimer.time)) / 1000;
        int hours = realTime / 3600;
        int seconds = (realTime % 3600) % 60;
        int minutes = (realTime % 3600) / 60;
        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public static String getFormatLastSessionTime() {
        if (lastSessionTime < 0) {
            lastSessionTime = 0L;
        }
        int realTime = ((int) lastSessionTime) / 1000;
        int hours = realTime / 3600;
        int seconds = (realTime % 3600) % 60;
        int minutes = (realTime % 3600) / 60;
        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public static String getFormatWorldTime() {
        if (System.currentTimeMillis() - worldTimer.time < 0) {
            worldTimer.reset();
        }
        int realTime = ((int) (System.currentTimeMillis() - worldTimer.time)) / 1000;
        int hours = realTime / 3600;
        int seconds = (realTime % 3600) % 60;
        int minutes = (realTime % 3600) / 60;
        return hours + "h " + minutes + "m " + seconds + "s";
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}