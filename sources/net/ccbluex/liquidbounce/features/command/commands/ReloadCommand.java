package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.misc.sound.TipSoundManager;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReloadCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ReloadCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ReloadCommand.class */
public final class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", new String[]{"configreload"});
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        chat("Reloading...");
        chat("§c§lReloading commands...");
        LiquidBounce.INSTANCE.setCommandManager(new CommandManager());
        LiquidBounce.INSTANCE.getCommandManager().registerCommands();
        LiquidBounce.INSTANCE.setStarting(true);
        LiquidBounce.INSTANCE.getScriptManager().disableScripts();
        LiquidBounce.INSTANCE.getScriptManager().unloadScripts();
        Iterator<Module> it = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();
        while (it.hasNext()) {
            Module module = it.next();
            ModuleManager moduleManager = LiquidBounce.INSTANCE.getModuleManager();
            Intrinsics.checkNotNullExpressionValue(module, "module");
            moduleManager.generateCommand$LiquidBounce(module);
        }
        chat("§c§lReloading scripts...");
        LiquidBounce.INSTANCE.getScriptManager().loadScripts();
        LiquidBounce.INSTANCE.getScriptManager().enableScripts();
        chat("§c§lReloading fonts...");
        Fonts.loadFonts();
        chat("§c§lReloading toggle audio files...");
        LiquidBounce.INSTANCE.setTipSoundManager(new TipSoundManager());
        chat("§c§lReloading modules...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        LiquidBounce.INSTANCE.setStarting(false);
        chat("§c§lReloading values...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        chat("§c§lReloading accounts...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
        chat("§c§lReloading friends...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().friendsConfig);
        chat("§c§lReloading xray...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
        chat("§c§lReloading HUD...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
        chat("§c§lReloading ClickGUI...");
        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
        LiquidBounce.INSTANCE.setStarting(false);
        chat("Reloaded.");
    }
}
