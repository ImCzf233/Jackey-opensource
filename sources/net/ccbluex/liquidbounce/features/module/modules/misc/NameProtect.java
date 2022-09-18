package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(name = "NameProtect", spacedName = "Name Protect", description = "Changes player names client-side.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/NameProtect.class */
public class NameProtect extends Module {
    private final TextValue fakeNameValue = new TextValue("FakeName", "&cMe");
    private final TextValue allFakeNameValue = new TextValue("AllPlayersFakeName", "Censored");
    public final BoolValue selfValue = new BoolValue("Yourself", true);
    public final BoolValue tagValue = new BoolValue("Tag", false);
    public final BoolValue allPlayersValue = new BoolValue("AllPlayers", false);
    public final BoolValue skinProtectValue = new BoolValue("SkinProtect", false);
    public final BoolValue customSkinValue = new BoolValue("CustomSkin", false, () -> {
        return this.skinProtectValue.get();
    });
    public ResourceLocation skinImage;

    public NameProtect() {
        File skinFile = new File(LiquidBounce.fileManager.dir, "cskin.png");
        if (skinFile.isFile()) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(skinFile));
                if (bufferedImage == null) {
                    return;
                }
                this.skinImage = new ResourceLocation(LiquidBounce.CLIENT_NAME.toLowerCase() + "/cskin.png");
                f362mc.func_110434_K().func_110579_a(this.skinImage, new DynamicTexture(bufferedImage));
                ClientUtils.getLogger().info("Loaded custom skin for NameProtect.");
            } catch (Exception e) {
                ClientUtils.getLogger().error("Failed to load custom skin.", e);
            }
        }
    }

    @EventTarget
    public void onText(TextEvent event) {
        if (f362mc.field_71439_g == null || event.getText().contains("§8[§9§lJackey§8] §3") || event.getText().startsWith("/") || event.getText().startsWith(LiquidBounce.commandManager.getPrefix() + "")) {
            return;
        }
        for (FriendsConfig.Friend friend : LiquidBounce.fileManager.friendsConfig.getFriends()) {
            event.setText(StringUtils.replace(event.getText(), friend.getPlayerName(), ColorUtils.translateAlternateColorCodes(friend.getAlias()) + "§f"));
        }
        event.setText(StringUtils.replace(event.getText(), f362mc.field_71439_g.func_70005_c_(), this.selfValue.get().booleanValue() ? this.tagValue.get().booleanValue() ? StringUtils.injectAirString(f362mc.field_71439_g.func_70005_c_()) + " §7(§r" + ColorUtils.translateAlternateColorCodes(this.fakeNameValue.get() + "§r§7)") : ColorUtils.translateAlternateColorCodes(this.fakeNameValue.get()) + "§r" : f362mc.field_71439_g.func_70005_c_()));
        if (this.allPlayersValue.get().booleanValue()) {
            for (NetworkPlayerInfo playerInfo : f362mc.func_147114_u().func_175106_d()) {
                event.setText(StringUtils.replace(event.getText(), playerInfo.func_178845_a().getName(), ColorUtils.translateAlternateColorCodes(this.allFakeNameValue.get()) + "§f"));
            }
        }
    }
}
