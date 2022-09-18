package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

/* compiled from: MCF.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/MCF;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "wasDown", "", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"})
@ModuleInfo(name = "MCF", description = "Allows you to add a player as a friend by middle clicking them.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/MCF.class */
public final class MCF extends Module {
    private boolean wasDown;

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71462_r == null && this.wasDown && Mouse.isButtonDown(2)) {
            EntityPlayer entityPlayer = MinecraftInstance.f362mc.field_71476_x.field_72308_g;
            if (entityPlayer != null && (entityPlayer instanceof EntityPlayer)) {
                String playerName = ColorUtils.stripColor(entityPlayer.func_70005_c_());
                FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
                if (friendsConfig.isFriend(playerName)) {
                    friendsConfig.removeFriend(playerName);
                    LiquidBounce.INSTANCE.getFileManager().saveConfig(friendsConfig);
                    ClientUtils.displayChatMessage("§a§l" + ((Object) playerName) + "§c was removed from your friends.");
                    return;
                }
                friendsConfig.addFriend(playerName);
                LiquidBounce.INSTANCE.getFileManager().saveConfig(friendsConfig);
                ClientUtils.displayChatMessage("§a§l" + ((Object) playerName) + "§c was added to your friends.");
                return;
            }
            ClientUtils.displayChatMessage("§c§lError: §aYou need to select a player.");
        }
    }
}
