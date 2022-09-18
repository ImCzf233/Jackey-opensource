package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackSpoofer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/PackSpoofer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
@ModuleInfo(name = "PackSpoofer", spacedName = "Pack Spoofer", description = "Prevents servers from forcing you to download their resource pack.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/PackSpoofer.class */
public final class PackSpoofer extends Module {
    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        String scheme;
        boolean isLevelProtocol;
        Intrinsics.checkNotNullParameter(event, "event");
        S48PacketResourcePackSend packet = event.getPacket();
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(AntiExploit.class);
        Intrinsics.checkNotNull(module);
        AntiExploit antiExploit = (AntiExploit) module;
        if (packet instanceof S48PacketResourcePackSend) {
            String url = packet.func_179783_a();
            String hash = packet.func_179784_b();
            try {
                scheme = new URI(url).getScheme();
                isLevelProtocol = Intrinsics.areEqual("level", scheme);
            } catch (URISyntaxException e) {
                ClientUtils.getLogger().error("Failed to handle resource pack", e);
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            }
            if (!Intrinsics.areEqual("http", scheme) && !Intrinsics.areEqual("https", scheme) && !isLevelProtocol) {
                throw new URISyntaxException(url, "Wrong protocol");
            }
            if (isLevelProtocol) {
                Intrinsics.checkNotNullExpressionValue(url, "url");
                if (StringsKt.contains$default((CharSequence) url, (CharSequence) "..", false, 2, (Object) null) || !StringsKt.endsWith$default(url, ".zip", false, 2, (Object) null)) {
                    String s2 = url.substring("level://".length());
                    Intrinsics.checkNotNullExpressionValue(s2, "this as java.lang.String).substring(startIndex)");
                    File file1 = new File(MinecraftInstance.f362mc.field_71412_D, "saves");
                    File file2 = new File(file1, s2);
                    if (!file2.isFile() || StringsKt.contains((CharSequence) url, (CharSequence) "liquidbounce", true)) {
                        if (antiExploit.getState() && antiExploit.getNotifyValue().get().booleanValue()) {
                            ClientUtils.displayChatMessage("§8[§9§lLiquidBounce+§8] §6Resourcepack exploit detected.");
                            ClientUtils.displayChatMessage(Intrinsics.stringPlus("§8[§9§lLiquidBounce+§8] §7Exploit target directory: §r", url));
                            throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
                        }
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                        event.cancelEvent();
                        return;
                    }
                }
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C19PacketResourcePackStatus(packet.func_179784_b(), C19PacketResourcePackStatus.Action.ACCEPTED));
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C19PacketResourcePackStatus(packet.func_179784_b(), C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
            event.cancelEvent();
        }
    }
}
