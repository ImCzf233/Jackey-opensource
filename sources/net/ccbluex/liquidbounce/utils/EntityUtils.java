package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/EntityUtils.class */
public final class EntityUtils extends MinecraftInstance {
    public static boolean targetInvisible = false;
    public static boolean targetPlayer = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = false;
    public static boolean targetDead = false;

    public static boolean isSelected(Entity entity, boolean canAttackCheck) {
        if (entity instanceof EntityLivingBase) {
            if ((targetDead || entity.func_70089_S()) && entity != f362mc.field_71439_g) {
                if (targetInvisible || !entity.func_82150_aj()) {
                    if (!targetPlayer || !(entity instanceof EntityPlayer)) {
                        return (targetMobs && isMob(entity)) || (targetAnimals && isAnimal(entity));
                    }
                    EntityLivingBase entityLivingBase = (EntityPlayer) entity;
                    if (canAttackCheck) {
                        if (AntiBot.isBot(entityLivingBase)) {
                            return false;
                        }
                        if ((isFriend(entityLivingBase) && !LiquidBounce.moduleManager.getModule(NoFriends.class).getState()) || entityLivingBase.func_175149_v()) {
                            return false;
                        }
                        Teams teams = (Teams) LiquidBounce.moduleManager.getModule(Teams.class);
                        return !teams.getState() || !teams.isInYourTeam(entityLivingBase);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static boolean isFriend(Entity entity) {
        return (entity instanceof EntityPlayer) && entity.func_70005_c_() != null && LiquidBounce.fileManager.friendsConfig.isFriend(ColorUtils.stripColor(entity.func_70005_c_()));
    }

    public static boolean isAnimal(Entity entity) {
        return (entity instanceof EntityAnimal) || (entity instanceof EntitySquid) || (entity instanceof EntityGolem) || (entity instanceof EntityBat);
    }

    public static boolean isMob(Entity entity) {
        return (entity instanceof EntityMob) || (entity instanceof EntityVillager) || (entity instanceof EntitySlime) || (entity instanceof EntityGhast) || (entity instanceof EntityDragon);
    }

    public static String getName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.func_178854_k() != null ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a(networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
    }

    public static int getPing(EntityPlayer entityPlayer) {
        NetworkPlayerInfo networkPlayerInfo;
        if (entityPlayer == null || (networkPlayerInfo = f362mc.func_147114_u().func_175102_a(entityPlayer.func_110124_au())) == null) {
            return 0;
        }
        return networkPlayerInfo.func_178853_c();
    }
}
