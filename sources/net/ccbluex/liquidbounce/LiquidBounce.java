package net.ccbluex.liquidbounce;

import kotlin.Metadata;
import kotlin.concurrent.Thread;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventManager;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.features.module.modules.render.PostProcessing;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.features.special.MacroManager;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p004ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import net.ccbluex.liquidbounce.tabs.BlocksTab;
import net.ccbluex.liquidbounce.tabs.ExploitsTab;
import net.ccbluex.liquidbounce.tabs.HeadsTab;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryHelper;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SessionUtils;
import net.ccbluex.liquidbounce.utils.misc.sound.TipSoundManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LiquidBounce.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010X\u001a\u00020YJ\u0006\u0010Z\u001a\u00020YR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n��\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086.¢\u0006\u000e\n��\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0086.¢\u0006\u000e\n��\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0086.¢\u0006\u000e\n��\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020-X\u0086.¢\u0006\u000e\n��\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\u000203X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b2\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u000208X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u000203X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b>\u00104\"\u0004\b?\u00106R\u001a\u0010@\u001a\u00020AX\u0086.¢\u0006\u000e\n��\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0086.¢\u0006\u000e\n��\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u0086.¢\u0006\u000e\n��\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020SX\u0086.¢\u0006\u000e\n��\u001a\u0004\bT\u0010U\"\u0004\bV\u0010W¨\u0006["}, m53d2 = {"Lnet/ccbluex/liquidbounce/LiquidBounce;", "", "()V", "CLIENT_CLOUD", "", "CLIENT_CREATOR", "CLIENT_NAME", "CLIENT_VERSION", "background", "Lnet/minecraft/util/ResourceLocation;", "getBackground", "()Lnet/minecraft/util/ResourceLocation;", "setBackground", "(Lnet/minecraft/util/ResourceLocation;)V", "clickGui", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "getClickGui", "()Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "setClickGui", "(Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;)V", "clientRichPresence", "Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "getClientRichPresence", "()Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "setClientRichPresence", "(Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;)V", "commandManager", "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "getCommandManager", "()Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "setCommandManager", "(Lnet/ccbluex/liquidbounce/features/command/CommandManager;)V", "eventManager", "Lnet/ccbluex/liquidbounce/event/EventManager;", "getEventManager", "()Lnet/ccbluex/liquidbounce/event/EventManager;", "setEventManager", "(Lnet/ccbluex/liquidbounce/event/EventManager;)V", "fileManager", "Lnet/ccbluex/liquidbounce/file/FileManager;", "getFileManager", "()Lnet/ccbluex/liquidbounce/file/FileManager;", "setFileManager", "(Lnet/ccbluex/liquidbounce/file/FileManager;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "setHud", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "isStarting", "", "()Z", "setStarting", "(Z)V", "lastTick", "", "getLastTick", "()J", "setLastTick", "(J)V", "mainMenuPrep", "getMainMenuPrep", "setMainMenuPrep", "moduleManager", "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "getModuleManager", "()Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "setModuleManager", "(Lnet/ccbluex/liquidbounce/features/module/ModuleManager;)V", "postProcessor", "Lnet/ccbluex/liquidbounce/features/module/modules/render/PostProcessing;", "getPostProcessor", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/PostProcessing;", "setPostProcessor", "(Lnet/ccbluex/liquidbounce/features/module/modules/render/PostProcessing;)V", "scriptManager", "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "getScriptManager", "()Lnet/ccbluex/liquidbounce/script/ScriptManager;", "setScriptManager", "(Lnet/ccbluex/liquidbounce/script/ScriptManager;)V", "tipSoundManager", "Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "getTipSoundManager", "()Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "setTipSoundManager", "(Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;)V", "startClient", "", "stopClient", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/LiquidBounce.class */
public final class LiquidBounce {
    @NotNull
    public static final LiquidBounce INSTANCE = new LiquidBounce();
    @NotNull
    public static final String CLIENT_NAME = "Jackey";
    @NotNull
    public static final String CLIENT_VERSION = "b1";
    @NotNull
//不是，哥们，水影是你写的还是咋的？。
    public static final String CLIENT_CREATOR = "826685288";
    @NotNull
    public static final String CLIENT_CLOUD = "https://wysi-foundation.github.io/LiquidCloud/LiquidBounce";
    private static boolean isStarting;
    private static boolean mainMenuPrep;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static FileManager fileManager;
    public static ScriptManager scriptManager;
    public static TipSoundManager tipSoundManager;
    public static HUD hud;
    public static ClickGui clickGui;
    @Nullable
    private static ResourceLocation background;
    public static ClientRichPresence clientRichPresence;
    public static PostProcessing postProcessor;
    private static long lastTick;

    private LiquidBounce() {
    }

    public final boolean isStarting() {
        return isStarting;
    }

    public final void setStarting(boolean z) {
        isStarting = z;
    }

    public final boolean getMainMenuPrep() {
        return mainMenuPrep;
    }

    public final void setMainMenuPrep(boolean z) {
        mainMenuPrep = z;
    }

    @NotNull
    public final ModuleManager getModuleManager() {
        ModuleManager moduleManager2 = moduleManager;
        if (moduleManager2 != null) {
            return moduleManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        return null;
    }

    public final void setModuleManager(@NotNull ModuleManager moduleManager2) {
        Intrinsics.checkNotNullParameter(moduleManager2, "<set-?>");
        moduleManager = moduleManager2;
    }

    @NotNull
    public final CommandManager getCommandManager() {
        CommandManager commandManager2 = commandManager;
        if (commandManager2 != null) {
            return commandManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        return null;
    }

    public final void setCommandManager(@NotNull CommandManager commandManager2) {
        Intrinsics.checkNotNullParameter(commandManager2, "<set-?>");
        commandManager = commandManager2;
    }

    @NotNull
    public final EventManager getEventManager() {
        EventManager eventManager2 = eventManager;
        if (eventManager2 != null) {
            return eventManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        return null;
    }

    public final void setEventManager(@NotNull EventManager eventManager2) {
        Intrinsics.checkNotNullParameter(eventManager2, "<set-?>");
        eventManager = eventManager2;
    }

    @NotNull
    public final FileManager getFileManager() {
        FileManager fileManager2 = fileManager;
        if (fileManager2 != null) {
            return fileManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        return null;
    }

    public final void setFileManager(@NotNull FileManager fileManager2) {
        Intrinsics.checkNotNullParameter(fileManager2, "<set-?>");
        fileManager = fileManager2;
    }

    @NotNull
    public final ScriptManager getScriptManager() {
        ScriptManager scriptManager2 = scriptManager;
        if (scriptManager2 != null) {
            return scriptManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
        return null;
    }

    public final void setScriptManager(@NotNull ScriptManager scriptManager2) {
        Intrinsics.checkNotNullParameter(scriptManager2, "<set-?>");
        scriptManager = scriptManager2;
    }

    @NotNull
    public final TipSoundManager getTipSoundManager() {
        TipSoundManager tipSoundManager2 = tipSoundManager;
        if (tipSoundManager2 != null) {
            return tipSoundManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tipSoundManager");
        return null;
    }

    public final void setTipSoundManager(@NotNull TipSoundManager tipSoundManager2) {
        Intrinsics.checkNotNullParameter(tipSoundManager2, "<set-?>");
        tipSoundManager = tipSoundManager2;
    }

    @NotNull
    public final HUD getHud() {
        HUD hud2 = hud;
        if (hud2 != null) {
            return hud2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hud");
        return null;
    }

    public final void setHud(@NotNull HUD hud2) {
        Intrinsics.checkNotNullParameter(hud2, "<set-?>");
        hud = hud2;
    }

    @NotNull
    public final ClickGui getClickGui() {
        ClickGui clickGui2 = clickGui;
        if (clickGui2 != null) {
            return clickGui2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("clickGui");
        return null;
    }

    public final void setClickGui(@NotNull ClickGui clickGui2) {
        Intrinsics.checkNotNullParameter(clickGui2, "<set-?>");
        clickGui = clickGui2;
    }

    @Nullable
    public final ResourceLocation getBackground() {
        return background;
    }

    public final void setBackground(@Nullable ResourceLocation resourceLocation) {
        background = resourceLocation;
    }

    @NotNull
    public final ClientRichPresence getClientRichPresence() {
        ClientRichPresence clientRichPresence2 = clientRichPresence;
        if (clientRichPresence2 != null) {
            return clientRichPresence2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("clientRichPresence");
        return null;
    }

    public final void setClientRichPresence(@NotNull ClientRichPresence clientRichPresence2) {
        Intrinsics.checkNotNullParameter(clientRichPresence2, "<set-?>");
        clientRichPresence = clientRichPresence2;
    }

    @NotNull
    public final PostProcessing getPostProcessor() {
        PostProcessing postProcessing = postProcessor;
        if (postProcessing != null) {
            return postProcessing;
        }
        Intrinsics.throwUninitializedPropertyAccessException("postProcessor");
        return null;
    }

    public final void setPostProcessor(@NotNull PostProcessing postProcessing) {
        Intrinsics.checkNotNullParameter(postProcessing, "<set-?>");
        postProcessor = postProcessing;
    }

    public final long getLastTick() {
        return lastTick;
    }

    public final void setLastTick(long j) {
        lastTick = j;
    }

    public final void startClient() {
        isStarting = true;
        ClientUtils.getLogger().info("Starting Jackey build b1");
        lastTick = System.currentTimeMillis();
        setFileManager(new FileManager());
        setEventManager(new EventManager());
        getEventManager().registerListener(new RotationUtils());
        getEventManager().registerListener(new AntiForge());
        getEventManager().registerListener(new BungeeCordSpoof());
        getEventManager().registerListener(new InventoryUtils());
        getEventManager().registerListener(InventoryHelper.INSTANCE);
        getEventManager().registerListener(new PacketUtils());
        getEventManager().registerListener(new SessionUtils());
        getEventManager().registerListener(MacroManager.INSTANCE);
        setClientRichPresence(new ClientRichPresence());
        setCommandManager(new CommandManager());
        Fonts.loadFonts();
        setTipSoundManager(new TipSoundManager());
        setModuleManager(new ModuleManager());
        getModuleManager().registerModules();
        Module module = getModuleManager().get(PostProcessing.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.PostProcessing");
        }
        setPostProcessor((PostProcessing) module);
        try {
            Remapper.INSTANCE.loadSrg();
            setScriptManager(new ScriptManager());
            getScriptManager().loadScripts();
            getScriptManager().enableScripts();
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to load scripts.", throwable);
        }
        getCommandManager().registerCommands();
        getFileManager().loadConfigs(getFileManager().modulesConfig, getFileManager().valuesConfig, getFileManager().accountsConfig, getFileManager().friendsConfig, getFileManager().xrayConfig);
        setClickGui(new ClickGui());
        getFileManager().loadConfig(getFileManager().clickGuiConfig);
        if (ClassUtils.INSTANCE.hasForge()) {
            new BlocksTab();
            new ExploitsTab();
            new HeadsTab();
        }
        setHud(HUD.Companion.createDefault());
        getFileManager().loadConfig(getFileManager().hudConfig);
        GuiAltManager.Companion.loadActiveGenerators();
        if (getClientRichPresence().getShowRichPresenceValue()) {
            Thread.thread$default(false, false, null, null, 0, LiquidBounce$startClient$1.INSTANCE, 31, null);
        }
        ClientUtils.getLogger().info("Finished loading LiquidBounce+ in " + (System.currentTimeMillis() - lastTick) + "ms.");
        isStarting = false;
    }

    public final void stopClient() {
        getEventManager().callEvent(new ClientShutdownEvent());
        getFileManager().saveAllConfigs();
        getClientRichPresence().shutdown();
    }
}
