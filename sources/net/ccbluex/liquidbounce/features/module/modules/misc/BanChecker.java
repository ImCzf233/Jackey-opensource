package net.ccbluex.liquidbounce.features.module.modules.misc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.apache.log4j.helpers.FileWatchdog;

@ModuleInfo(name = "BanChecker", spacedName = "Ban Checker", description = "Checks for ban on Hypixel every minute and alert you if there is any.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/BanChecker.class */
public class BanChecker extends Module {
    public final BoolValue alertValue = new BoolValue("Alert", true);
    public final BoolValue serverCheckValue = new BoolValue("ServerCheck", true);
    public final IntegerValue alertTimeValue = new IntegerValue("Alert-Time", 10, 1, 50, " seconds");
    private String checkTag = "Idle...";
    private static String API_PUNISHMENT = m33aB("68747470733a2f2f6170692e706c616e636b652e696f2f6879706978656c2f76312f70756e6973686d656e745374617473");
    public static int WATCHDOG_BAN_LAST_MIN = 0;
    public static int LAST_TOTAL_STAFF = -1;
    public static int STAFF_BAN_LAST_MIN = 0;

    /* JADX WARN: Type inference failed for: r0v5, types: [net.ccbluex.liquidbounce.features.module.modules.misc.BanChecker$1] */
    public BanChecker() {
        new Thread("Hypixel-BanChecker") { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.BanChecker.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MSTimer checkTimer = new MSTimer();
                while (true) {
                    if (checkTimer.hasTimePassed(FileWatchdog.DEFAULT_DELAY)) {
                        try {
                            String apiContent = HttpUtils.get(BanChecker.API_PUNISHMENT);
                            JsonObject jsonObject = new JsonParser().parse(apiContent).getAsJsonObject();
                            if (jsonObject.get("success").getAsBoolean() && jsonObject.has("record")) {
                                JsonObject objectAPI = jsonObject.get("record").getAsJsonObject();
                                BanChecker.WATCHDOG_BAN_LAST_MIN = objectAPI.get("watchdog_lastMinute").getAsInt();
                                int staffBanTotal = objectAPI.get("staff_total").getAsInt();
                                if (staffBanTotal < BanChecker.LAST_TOTAL_STAFF) {
                                    staffBanTotal = BanChecker.LAST_TOTAL_STAFF;
                                }
                                if (BanChecker.LAST_TOTAL_STAFF == -1) {
                                    BanChecker.LAST_TOTAL_STAFF = staffBanTotal;
                                } else {
                                    BanChecker.STAFF_BAN_LAST_MIN = staffBanTotal - BanChecker.LAST_TOTAL_STAFF;
                                    BanChecker.LAST_TOTAL_STAFF = staffBanTotal;
                                }
                                BanChecker.this.checkTag = BanChecker.STAFF_BAN_LAST_MIN + "";
                                if (LiquidBounce.moduleManager.getModule(BanChecker.class).getState() && BanChecker.this.alertValue.get().booleanValue() && BanChecker.f362mc.field_71439_g != null && (!BanChecker.this.serverCheckValue.get().booleanValue() || BanChecker.this.isOnHypixel())) {
                                    if (BanChecker.STAFF_BAN_LAST_MIN > 0) {
                                        LiquidBounce.hud.addNotification(new Notification("Staffs banned " + BanChecker.STAFF_BAN_LAST_MIN + " players in the last minute!", BanChecker.STAFF_BAN_LAST_MIN > 3 ? Notification.Type.ERROR : Notification.Type.WARNING, BanChecker.this.alertTimeValue.get().intValue() * 1000));
                                    } else {
                                        LiquidBounce.hud.addNotification(new Notification("Staffs didn't ban any player in the last minute.", Notification.Type.SUCCESS, BanChecker.this.alertTimeValue.get().intValue() * 1000));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (LiquidBounce.moduleManager.getModule(BanChecker.class).getState() && BanChecker.this.alertValue.get().booleanValue() && BanChecker.f362mc.field_71439_g != null && (!BanChecker.this.serverCheckValue.get().booleanValue() || BanChecker.this.isOnHypixel())) {
                                LiquidBounce.hud.addNotification(new Notification("An error has occurred.", Notification.Type.ERROR, 1000L));
                            }
                        }
                        checkTimer.reset();
                    }
                }
            }
        }.start();
    }

    public boolean isOnHypixel() {
        return !f362mc.func_71387_A() && f362mc.func_147104_D().field_78845_b.contains("hypixel.net");
    }

    /* renamed from: aB */
    public static String m33aB(String str) {
        String result = new String();
        char[] charArray = str.toCharArray();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < charArray.length) {
                String st = "" + charArray[i2] + "" + charArray[i2 + 1];
                char ch = (char) Integer.parseInt(st, 16);
                result = result + ch;
                i = i2 + 2;
            } else {
                return result;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.checkTag;
    }
}
