package net.ccbluex.liquidbounce.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;

/* compiled from: ClientRichPresence.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0010%\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0015J\u0006\u0010\u0018\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0019"}, m53d2 = {"Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "appID", "", "assets", "", "", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "showRichPresenceValue", "getShowRichPresenceValue", "()Z", "setShowRichPresenceValue", "(Z)V", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "loadConfiguration", "", "setup", "shutdown", "update", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/discord/ClientRichPresence.class */
public final class ClientRichPresence extends MinecraftInstance {
    @Nullable
    private IPCClient ipcClient;
    private long appID;
    private boolean running;
    private boolean showRichPresenceValue = true;
    @NotNull
    private final Map<String, String> assets = new LinkedHashMap();
    private final OffsetDateTime timestamp = OffsetDateTime.now();

    public final boolean getShowRichPresenceValue() {
        return this.showRichPresenceValue;
    }

    public final void setShowRichPresenceValue(boolean z) {
        this.showRichPresenceValue = z;
    }

    public final void setup() {
        try {
            this.running = true;
            loadConfiguration();
            this.ipcClient = new IPCClient(this.appID);
            IPCClient iPCClient = this.ipcClient;
            if (iPCClient != null) {
                iPCClient.setListener(new IPCListener() { // from class: net.ccbluex.liquidbounce.discord.ClientRichPresence$setup$1
                    @Override // com.jagrosh.discordipc.IPCListener
                    public void onReady(@Nullable IPCClient client) {
                        Thread.thread$default(false, false, null, null, 0, new ClientRichPresence$setup$1$onReady$1(ClientRichPresence.this), 31, null);
                    }

                    @Override // com.jagrosh.discordipc.IPCListener
                    public void onClose(@Nullable IPCClient client, @Nullable JSONObject json) {
                        ClientRichPresence.this.running = false;
                    }
                });
            }
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 == null) {
                return;
            }
            iPCClient2.connect(new DiscordBuild[0]);
        } catch (Throwable e) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", e);
        }
    }

    public final void update() {
        int i;
        int i2;
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setStartTimestamp(this.timestamp);
        if (this.assets.containsKey("new")) {
            builder.setLargeImage(this.assets.get("new"), "build b1");
        }
        ServerData serverData = MinecraftInstance.f362mc.func_147104_D();
        builder.setDetails(Display.isActive() ? (MinecraftInstance.f362mc.func_71387_A() || serverData != null) ? "Playing" : "Idle..." : "AFK");
        builder.setState(Intrinsics.stringPlus("IGN: ", MinecraftInstance.f362mc.field_71449_j.func_111285_a()));
        if (MinecraftInstance.f362mc.func_71387_A() || serverData != null) {
            String str = this.assets.get("astolfo");
            StringBuilder append = new StringBuilder().append("in ").append((Object) ((MinecraftInstance.f362mc.func_71387_A() || serverData == null) ? "Singleplayer" : serverData.field_78845_b)).append(" - ");
            Iterable $this$count$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
            if (!($this$count$iv instanceof Collection) || !((Collection) $this$count$iv).isEmpty()) {
                int count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    Module it = (Module) element$iv;
                    if (it.getState()) {
                        count$iv++;
                        if (count$iv < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                i = count$iv;
            } else {
                i = 0;
            }
            builder.setSmallImage(str, append.append(i).append('/').append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append(" enabled.").toString());
        } else {
            String str2 = this.assets.get("astolfo");
            StringBuilder sb = new StringBuilder();
            Iterable $this$count$iv2 = LiquidBounce.INSTANCE.getModuleManager().getModules();
            if (!($this$count$iv2 instanceof Collection) || !((Collection) $this$count$iv2).isEmpty()) {
                int count$iv2 = 0;
                for (Object element$iv2 : $this$count$iv2) {
                    Module it2 = (Module) element$iv2;
                    if (it2.getState()) {
                        count$iv2++;
                        if (count$iv2 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                i2 = count$iv2;
            } else {
                i2 = 0;
            }
            builder.setSmallImage(str2, sb.append(i2).append('/').append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append(" enabled.").toString());
        }
        IPCClient iPCClient = this.ipcClient;
        if ((iPCClient == null ? null : iPCClient.getStatus()) == PipeStatus.CONNECTED) {
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 == null) {
                return;
            }
            iPCClient2.sendRichPresence(builder.build());
        }
    }

    public final void shutdown() {
        IPCClient iPCClient = this.ipcClient;
        if ((iPCClient == null ? null : iPCClient.getStatus()) != PipeStatus.CONNECTED) {
            return;
        }
        try {
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 == null) {
                return;
            }
            iPCClient2.close();
        } catch (Throwable e) {
            ClientUtils.getLogger().error("Failed to close Discord RPC.", e);
        }
    }

    private final void loadConfiguration() {
        this.appID = 874149528486445106L;
        this.assets.put("new", "new");
        this.assets.put("astolfo", "astolfo");
    }
}
