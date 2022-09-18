package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.world.WorldSettings;

@ModuleInfo(name = "AntiBot", spacedName = "Anti Bot", description = "Prevents KillAura from attacking AntiCheat bots.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiBot.class */
public class AntiBot extends Module {
    private final BoolValue czechHekValue = new BoolValue("CzechMatrix", false);
    private final BoolValue czechHekPingCheckValue = new BoolValue("PingCheck", true, () -> {
        return this.czechHekValue.get();
    });
    private final BoolValue czechHekGMCheckValue = new BoolValue("GamemodeCheck", true, () -> {
        return this.czechHekValue.get();
    });
    private final BoolValue tabValue = new BoolValue("Tab", true);
    private final ListValue tabModeValue = new ListValue("TabMode", new String[]{"Equals", "Contains"}, "Contains");
    private final BoolValue entityIDValue = new BoolValue("EntityID", true);
    private final BoolValue colorValue = new BoolValue("Color", false);
    private final BoolValue livingTimeValue = new BoolValue("LivingTime", false);
    private final IntegerValue livingTimeTicksValue = new IntegerValue("LivingTimeTicks", 40, 1, 200);
    private final BoolValue groundValue = new BoolValue("Ground", true);
    private final BoolValue airValue = new BoolValue("Air", false);
    private final BoolValue invalidGroundValue = new BoolValue("InvalidGround", true);
    private final BoolValue swingValue = new BoolValue("Swing", false);
    private final BoolValue healthValue = new BoolValue("Health", false);
    private final BoolValue invalidHealthValue = new BoolValue("InvalidHealth", false);
    private final FloatValue minHealthValue = new FloatValue("MinHealth", 0.0f, 0.0f, 100.0f);
    private final FloatValue maxHealthValue = new FloatValue("MaxHealth", 20.0f, 0.0f, 100.0f);
    private final BoolValue derpValue = new BoolValue("Derp", true);
    private final BoolValue wasInvisibleValue = new BoolValue("WasInvisible", false);
    private final BoolValue armorValue = new BoolValue("Armor", false);
    private final BoolValue pingValue = new BoolValue("Ping", false);
    private final BoolValue needHitValue = new BoolValue("NeedHit", false);
    private final BoolValue duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
    private final BoolValue drvcValue = new BoolValue("ReverseCheck", true, () -> {
        return this.duplicateInWorldValue.get();
    });
    private final BoolValue duplicateInTabValue = new BoolValue("DuplicateInTab", false);
    private final BoolValue experimentalNPCDetection = new BoolValue("ExperimentalNPCDetection", false);
    private final BoolValue illegalName = new BoolValue("IllegalName", false);
    private final BoolValue removeFromWorld = new BoolValue("RemoveFromWorld", false);
    private final IntegerValue removeIntervalValue = new IntegerValue("Remove-Interval", 20, 1, 100, " tick");
    private final BoolValue debugValue = new BoolValue("Debug", false);
    private final List<Integer> ground = new ArrayList();
    private final List<Integer> air = new ArrayList();
    private final Map<Integer, Integer> invalidGround = new HashMap();
    private final List<Integer> swing = new ArrayList();
    private final List<Integer> invisible = new ArrayList();
    private final List<Integer> hitted = new ArrayList();
    private boolean wasAdded;

    public AntiBot() {
        this.wasAdded = f362mc.field_71439_g != null;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        clearAll();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g != null && f362mc.field_71441_e != null && this.removeFromWorld.get().booleanValue() && f362mc.field_71439_g.field_70173_aa > 0 && f362mc.field_71439_g.field_70173_aa % this.removeIntervalValue.get().intValue() == 0) {
            List<EntityPlayer> ent = new ArrayList<>();
            for (EntityPlayerSP entityPlayerSP : f362mc.field_71441_e.field_73010_i) {
                if (entityPlayerSP != f362mc.field_71439_g && isBot(entityPlayerSP)) {
                    ent.add(entityPlayerSP);
                }
            }
            if (ent.isEmpty()) {
                return;
            }
            for (EntityPlayer e : ent) {
                f362mc.field_71441_e.func_72900_e(e);
                if (this.debugValue.get().booleanValue()) {
                    ClientUtils.displayChatMessage("§7[§a§lAnti Bot§7] §fRemoved §r" + e.func_70005_c_() + " §fdue to it being a bot.");
                }
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (f362mc.field_71439_g == null || f362mc.field_71441_e == null) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (this.czechHekValue.get().booleanValue()) {
            if (packet instanceof S41PacketServerDifficulty) {
                this.wasAdded = false;
            }
            if (packet instanceof S38PacketPlayerListItem) {
                S38PacketPlayerListItem packetListItem = event.getPacket();
                S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData) packetListItem.func_179767_a().get(0);
                if (data.func_179962_a() != null && data.func_179962_a().getName() != null) {
                    if (!this.wasAdded) {
                        this.wasAdded = data.func_179962_a().getName().equals(f362mc.field_71439_g.func_70005_c_());
                    } else if (!f362mc.field_71439_g.func_175149_v() && !f362mc.field_71439_g.field_71075_bZ.field_75101_c && ((!this.czechHekPingCheckValue.get().booleanValue() || data.func_179963_b() != 0) && (!this.czechHekGMCheckValue.get().booleanValue() || data.func_179960_c() != WorldSettings.GameType.NOT_SET))) {
                        event.cancelEvent();
                        if (this.debugValue.get().booleanValue()) {
                            ClientUtils.displayChatMessage("§7[§a§lAnti Bot/§6Matrix§7] §fPrevented §r" + data.func_179962_a().getName() + " §ffrom spawning.");
                        }
                    }
                }
            }
        }
        if (packet instanceof S14PacketEntity) {
            S14PacketEntity packetEntity = event.getPacket();
            Entity entity = packetEntity.func_149065_a(f362mc.field_71441_e);
            if (entity instanceof EntityPlayer) {
                if (packetEntity.func_179742_g() && !this.ground.contains(Integer.valueOf(entity.func_145782_y()))) {
                    this.ground.add(Integer.valueOf(entity.func_145782_y()));
                }
                if (!packetEntity.func_179742_g() && !this.air.contains(Integer.valueOf(entity.func_145782_y()))) {
                    this.air.add(Integer.valueOf(entity.func_145782_y()));
                }
                if (packetEntity.func_179742_g()) {
                    if (entity.field_70167_r != entity.field_70163_u) {
                        this.invalidGround.put(Integer.valueOf(entity.func_145782_y()), Integer.valueOf(this.invalidGround.getOrDefault(Integer.valueOf(entity.func_145782_y()), 0).intValue() + 1));
                    }
                } else {
                    int currentVL = this.invalidGround.getOrDefault(Integer.valueOf(entity.func_145782_y()), 0).intValue() / 2;
                    if (currentVL <= 0) {
                        this.invalidGround.remove(Integer.valueOf(entity.func_145782_y()));
                    } else {
                        this.invalidGround.put(Integer.valueOf(entity.func_145782_y()), Integer.valueOf(currentVL));
                    }
                }
                if (entity.func_82150_aj() && !this.invisible.contains(Integer.valueOf(entity.func_145782_y()))) {
                    this.invisible.add(Integer.valueOf(entity.func_145782_y()));
                }
            }
        }
        if (packet instanceof S0BPacketAnimation) {
            S0BPacketAnimation packetAnimation = event.getPacket();
            Entity entity2 = f362mc.field_71441_e.func_73045_a(packetAnimation.func_148978_c());
            if ((entity2 instanceof EntityLivingBase) && packetAnimation.func_148977_d() == 0 && !this.swing.contains(Integer.valueOf(entity2.func_145782_y()))) {
                this.swing.add(Integer.valueOf(entity2.func_145782_y()));
            }
        }
    }

    @EventTarget
    public void onAttack(AttackEvent e) {
        Entity entity = e.getTargetEntity();
        if ((entity instanceof EntityLivingBase) && !this.hitted.contains(Integer.valueOf(entity.func_145782_y()))) {
            this.hitted.add(Integer.valueOf(entity.func_145782_y()));
        }
    }

    @EventTarget
    public void onWorld(WorldEvent event) {
        clearAll();
    }

    private void clearAll() {
        this.hitted.clear();
        this.swing.clear();
        this.ground.clear();
        this.invalidGround.clear();
        this.invisible.clear();
    }

    public static boolean isBot(EntityLivingBase entity) {
        AntiBot antiBot;
        if (!(entity instanceof EntityPlayer) || entity == f362mc.field_71439_g || (antiBot = (AntiBot) LiquidBounce.moduleManager.getModule(AntiBot.class)) == null || !antiBot.getState()) {
            return false;
        }
        if (antiBot.experimentalNPCDetection.get().booleanValue() && (entity.func_145748_c_().func_150260_c().toLowerCase().contains("npc") || entity.func_145748_c_().func_150260_c().toLowerCase().contains("cit-"))) {
            return true;
        }
        if (antiBot.illegalName.get().booleanValue() && (entity.func_70005_c_().contains(" ") || entity.func_145748_c_().func_150260_c().contains(" "))) {
            return true;
        }
        if (antiBot.colorValue.get().booleanValue() && !entity.func_145748_c_().func_150254_d().replace("§r", "").contains("§")) {
            return true;
        }
        if (antiBot.livingTimeValue.get().booleanValue() && entity.field_70173_aa < antiBot.livingTimeTicksValue.get().intValue()) {
            return true;
        }
        if (antiBot.groundValue.get().booleanValue() && !antiBot.ground.contains(Integer.valueOf(entity.func_145782_y()))) {
            return true;
        }
        if (antiBot.airValue.get().booleanValue() && !antiBot.air.contains(Integer.valueOf(entity.func_145782_y()))) {
            return true;
        }
        if (antiBot.swingValue.get().booleanValue() && !antiBot.swing.contains(Integer.valueOf(entity.func_145782_y()))) {
            return true;
        }
        if (antiBot.invalidHealthValue.get().booleanValue() && entity.func_110143_aJ() == Double.NaN) {
            return true;
        }
        if (antiBot.healthValue.get().booleanValue() && (entity.func_110143_aJ() > antiBot.maxHealthValue.get().floatValue() || entity.func_110143_aJ() < antiBot.minHealthValue.get().floatValue())) {
            return true;
        }
        if (antiBot.entityIDValue.get().booleanValue() && (entity.func_145782_y() >= 1000000000 || entity.func_145782_y() <= -1)) {
            return true;
        }
        if (antiBot.derpValue.get().booleanValue() && (entity.field_70125_A > 90.0f || entity.field_70125_A < -90.0f)) {
            return true;
        }
        if (antiBot.wasInvisibleValue.get().booleanValue() && antiBot.invisible.contains(Integer.valueOf(entity.func_145782_y()))) {
            return true;
        }
        if (antiBot.armorValue.get().booleanValue()) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.field_71071_by.field_70460_b[0] == null && player.field_71071_by.field_70460_b[1] == null && player.field_71071_by.field_70460_b[2] == null && player.field_71071_by.field_70460_b[3] == null) {
                return true;
            }
        }
        if (antiBot.pingValue.get().booleanValue()) {
            EntityPlayer player2 = (EntityPlayer) entity;
            if (f362mc.func_147114_u().func_175102_a(player2.func_110124_au()) != null && f362mc.func_147114_u().func_175102_a(player2.func_110124_au()).func_178853_c() == 0) {
                return true;
            }
        }
        if (antiBot.needHitValue.get().booleanValue() && !antiBot.hitted.contains(Integer.valueOf(entity.func_145782_y()))) {
            return true;
        }
        if (antiBot.invalidGroundValue.get().booleanValue() && antiBot.invalidGround.getOrDefault(Integer.valueOf(entity.func_145782_y()), 0).intValue() >= 10) {
            return true;
        }
        if (antiBot.tabValue.get().booleanValue()) {
            boolean equals = antiBot.tabModeValue.get().equalsIgnoreCase("Equals");
            String targetName = ColorUtils.stripColor(entity.func_145748_c_().func_150254_d());
            if (targetName != null) {
                for (NetworkPlayerInfo networkPlayerInfo : f362mc.func_147114_u().func_175106_d()) {
                    String networkName = ColorUtils.stripColor(EntityUtils.getName(networkPlayerInfo));
                    if (networkName != null) {
                        if (equals) {
                            if (targetName.equals(networkName)) {
                                return false;
                            }
                        } else if (targetName.contains(networkName)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        if (antiBot.duplicateInWorldValue.get().booleanValue() && ((antiBot.drvcValue.get().booleanValue() && reverse(f362mc.field_71441_e.field_72996_f.stream()).filter(currEntity -> {
            return (currEntity instanceof EntityPlayer) && ((EntityPlayer) currEntity).getDisplayNameString().equals(((EntityPlayer) currEntity).getDisplayNameString());
        }).count() > 1) || f362mc.field_71441_e.field_72996_f.stream().filter(currEntity2 -> {
            return (currEntity2 instanceof EntityPlayer) && ((EntityPlayer) currEntity2).getDisplayNameString().equals(((EntityPlayer) currEntity2).getDisplayNameString());
        }).count() > 1)) {
            return true;
        }
        return (antiBot.duplicateInTabValue.get().booleanValue() && f362mc.func_147114_u().func_175106_d().stream().filter(networkPlayer -> {
            return entity.func_70005_c_().equals(ColorUtils.stripColor(EntityUtils.getName(networkPlayer)));
        }).count() > 1) || entity.func_70005_c_().isEmpty() || entity.func_70005_c_().equals(f362mc.field_71439_g.func_70005_c_());
    }

    private static <T> Stream<T> reverse(Stream<T> stream) {
        LinkedList<T> stack = new LinkedList<>();
        stack.getClass();
        stream.forEach(this::push);
        return stack.stream();
    }
}
