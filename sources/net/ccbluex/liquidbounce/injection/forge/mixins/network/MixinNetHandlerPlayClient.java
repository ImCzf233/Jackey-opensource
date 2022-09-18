package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EntityDamageEvent;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiExploit;
import net.ccbluex.liquidbounce.features.module.modules.misc.Patcher;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({NetHandlerPlayClient.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetHandlerPlayClient.class */
public abstract class MixinNetHandlerPlayClient {
    @Shadow
    @Final
    private NetworkManager field_147302_e;
    @Shadow
    private Minecraft field_147299_f;
    @Shadow
    private WorldClient field_147300_g;
    @Shadow
    public int field_147304_c;

    @Shadow
    public abstract NetworkPlayerInfo func_175102_a(UUID uuid);

    @Inject(method = {"handleSpawnPlayer"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn, CallbackInfo callbackInfo) {
        if (Patcher.silentNPESP.get().booleanValue()) {
            try {
                PacketThreadUtil.func_180031_a(packetIn, (NetHandlerPlayClient) this, this.field_147299_f);
                double d0 = packetIn.func_148942_f() / 32.0d;
                double d1 = packetIn.func_148949_g() / 32.0d;
                double d2 = packetIn.func_148946_h() / 32.0d;
                float f = (packetIn.func_148941_i() * TokenId.EXOR_E) / 256.0f;
                float f1 = (packetIn.func_148945_j() * TokenId.EXOR_E) / 256.0f;
                EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(this.field_147299_f.field_71441_e, func_175102_a(packetIn.func_179819_c()).func_178845_a());
                int func_148942_f = packetIn.func_148942_f();
                entityotherplayermp.field_70118_ct = func_148942_f;
                double d = func_148942_f;
                entityotherplayermp.field_70142_S = d;
                entityotherplayermp.field_70169_q = d;
                int func_148949_g = packetIn.func_148949_g();
                entityotherplayermp.field_70117_cu = func_148949_g;
                double d3 = func_148949_g;
                entityotherplayermp.field_70137_T = d3;
                entityotherplayermp.field_70167_r = d3;
                int func_148946_h = packetIn.func_148946_h();
                entityotherplayermp.field_70116_cv = func_148946_h;
                double d4 = func_148946_h;
                entityotherplayermp.field_70136_U = d4;
                entityotherplayermp.field_70166_s = d4;
                int i = packetIn.func_148947_k();
                if (i == 0) {
                    entityotherplayermp.field_71071_by.field_70462_a[entityotherplayermp.field_71071_by.field_70461_c] = null;
                } else {
                    entityotherplayermp.field_71071_by.field_70462_a[entityotherplayermp.field_71071_by.field_70461_c] = new ItemStack(Item.func_150899_d(i), 1, 0);
                }
                entityotherplayermp.func_70080_a(d0, d1, d2, f, f1);
                this.field_147300_g.func_73027_a(packetIn.func_148943_d(), entityotherplayermp);
                List<DataWatcher.WatchableObject> list = packetIn.func_148944_c();
                if (list != null) {
                    entityotherplayermp.func_70096_w().func_75687_a(list);
                }
            } catch (Exception e) {
            }
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleCloseWindow"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void handleCloseWindow(S2EPacketCloseWindow packetIn, CallbackInfo callbackInfo) {
        if ((this.field_147299_f.field_71462_r instanceof ClickGui) || (this.field_147299_f.field_71462_r instanceof GuiHudDesigner) || (this.field_147299_f.field_71462_r instanceof GuiChat)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleResourcePack"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void handleResourcePack(S48PacketResourcePackSend p_handleResourcePack_1_, CallbackInfo callbackInfo) {
        String url = p_handleResourcePack_1_.func_179783_a();
        String hash = p_handleResourcePack_1_.func_179784_b();
        AntiExploit antiExploit = (AntiExploit) LiquidBounce.moduleManager.getModule(AntiExploit.class);
        try {
            String scheme = new URI(url).getScheme();
            boolean isLevelProtocol = "level".equals(scheme);
            if (!"http".equals(scheme) && !"https".equals(scheme) && !isLevelProtocol) {
                throw new URISyntaxException(url, "Wrong protocol");
            }
            if (isLevelProtocol && (url.contains("..") || !url.endsWith(".zip"))) {
                String s2 = url.substring("level://".length());
                File file1 = new File(this.field_147299_f.field_71412_D, "saves");
                File file2 = new File(file1, s2);
                if (file2.isFile() && !url.toLowerCase().contains("liquidbounce")) {
                    this.field_147302_e.func_179290_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
                    this.field_147302_e.func_179290_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                } else {
                    this.field_147302_e.func_179290_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
                if (antiExploit.getState() && antiExploit.getNotifyValue().get().booleanValue()) {
                    ClientUtils.displayChatMessage("§8[§9§lLiquidBounce+§8] §6Resourcepack exploit detected.");
                    ClientUtils.displayChatMessage("§8[§9§lLiquidBounce+§8] §7Exploit target directory: §r" + url);
                    throw new IllegalStateException("Invalid levelstorage resourcepack path");
                }
                callbackInfo.cancel();
            }
        } catch (IllegalStateException e) {
            ClientUtils.getLogger().error("Failed to handle resource pack", e);
            callbackInfo.cancel();
        } catch (URISyntaxException e2) {
            ClientUtils.getLogger().error("Failed to handle resource pack", e2);
            this.field_147302_e.func_179290_a(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleJoinGame"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void handleJoinGameWithAntiForge(S01PacketJoinGame packetIn, CallbackInfo callbackInfo) {
        if (!AntiForge.enabled || !AntiForge.blockFML || Minecraft.func_71410_x().func_71387_A()) {
            return;
        }
        PacketThreadUtil.func_180031_a(packetIn, (NetHandlerPlayClient) this, this.field_147299_f);
        this.field_147299_f.field_71442_b = new PlayerControllerMP(this.field_147299_f, (NetHandlerPlayClient) this);
        this.field_147300_g = new WorldClient((NetHandlerPlayClient) this, new WorldSettings(0L, packetIn.func_149198_e(), false, packetIn.func_149195_d(), packetIn.func_149196_i()), packetIn.func_149194_f(), packetIn.func_149192_g(), this.field_147299_f.field_71424_I);
        this.field_147299_f.field_71474_y.field_74318_M = packetIn.func_149192_g();
        this.field_147299_f.func_71403_a(this.field_147300_g);
        this.field_147299_f.field_71439_g.field_71093_bK = packetIn.func_149194_f();
        this.field_147299_f.func_147108_a(new GuiDownloadTerrain((NetHandlerPlayClient) this));
        this.field_147299_f.field_71439_g.func_145769_d(packetIn.func_149197_c());
        this.field_147304_c = packetIn.func_149193_h();
        this.field_147299_f.field_71439_g.func_175150_k(packetIn.func_179744_h());
        this.field_147299_f.field_71442_b.func_78746_a(packetIn.func_149198_e());
        this.field_147299_f.field_71474_y.func_82879_c();
        this.field_147302_e.func_179290_a(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).func_180714_a(ClientBrandRetriever.getClientModName())));
        callbackInfo.cancel();
    }

    @Inject(method = {"handleEntityMovement"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;onGround:Z")})
    private void handleEntityMovementEvent(S14PacketEntity packetIn, CallbackInfo callbackInfo) {
        Entity entity = packetIn.func_149065_a(this.field_147300_g);
        if (entity != null) {
            LiquidBounce.eventManager.callEvent(new EntityMovementEvent(entity));
        }
    }

    @Inject(method = {"handleEntityStatus"}, m23at = {@AbstractC1790At("HEAD")})
    public void handleDamagePacket(S19PacketEntityStatus packetIn, CallbackInfo callbackInfo) {
        EntityPlayer func_149161_a;
        if (packetIn.func_149160_c() == 2 && (func_149161_a = packetIn.func_149161_a(this.field_147300_g)) != null) {
            LiquidBounce.eventManager.callEvent(new EntityDamageEvent(func_149161_a));
            if (func_149161_a instanceof EntityPlayer) {
                LiquidBounce.hud.handleDamage(func_149161_a);
            }
        }
    }

    @Redirect(method = {"handleUpdateSign"}, slice = @Slice(from = @AbstractC1790At(value = "CONSTANT", args = {"stringValue=Unable to locate sign at "}, ordinal = 0)), m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;addChatMessage(Lnet/minecraft/util/IChatComponent;)V", ordinal = 0))
    private void removeDebugMessage(EntityPlayerSP instance, IChatComponent component) {
    }

    @Inject(method = {"handleAnimation"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleAnimation(S0BPacketAnimation s0BPacketAnimation, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityTeleport"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityTeleport(S18PacketEntityTeleport s18PacketEntityTeleport, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityMovement"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityMovement(S14PacketEntity s14PacketEntity, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityHeadLook"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityHeadLook(S19PacketEntityHeadLook s19PacketEntityHeadLook, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityProperties"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityProperties(S20PacketEntityProperties s20PacketEntityProperties, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityMetadata"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityMetadata(S1CPacketEntityMetadata s1CPacketEntityMetadata, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleEntityEquipment"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleEntityEquipment(S04PacketEntityEquipment s04PacketEntityEquipment, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleDestroyEntities"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleDestroyEntities(S13PacketDestroyEntities s13PacketDestroyEntities, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleScoreboardObjective"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void handleScoreboardObjective(S3BPacketScoreboardObjective s3BPacketScoreboardObjective, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147300_g, callbackInfo);
    }

    @Inject(method = {"handleConfirmTransaction"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/S32PacketConfirmTransaction;getWindowId()I", ordinal = 0)}, cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void handleConfirmTransaction(S32PacketConfirmTransaction s32PacketConfirmTransaction, CallbackInfo callbackInfo, Container container, EntityPlayer entityPlayer) {
        cancelIfNull(entityPlayer, callbackInfo);
    }

    @Inject(method = {"handleSoundEffect"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V")}, cancellable = true)
    private void handleSoundEffect(S29PacketSoundEffect s29PacketSoundEffect, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147299_f.field_71441_e, callbackInfo);
    }

    @Inject(method = {"handleTimeUpdate"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V")}, cancellable = true)
    private void handleTimeUpdate(S03PacketTimeUpdate s03PacketTimeUpdate, CallbackInfo callbackInfo) {
        cancelIfNull(this.field_147299_f.field_71441_e, callbackInfo);
    }

    private <T> void cancelIfNull(T t, CallbackInfo callbackInfo) {
        if (t == null) {
            callbackInfo.cancel();
        }
    }
}
