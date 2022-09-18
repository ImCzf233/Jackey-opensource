package net.ccbluex.liquidbounce.p004ui.client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiScripts.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0003\u0018��2\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J \u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\u0018\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\fH\u0014R\u0012\u0010\u0004\u001a\u00060\u0005R\u00020��X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0017"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "GuiList", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiScripts */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiScripts.class */
public final class GuiScripts extends GuiScreen {
    @NotNull
    private final GuiScreen prevGui;
    private GuiList list;

    public GuiScripts(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    public void func_73866_w_() {
        this.list = new GuiList(this, this);
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            guiList = null;
        }
        guiList.func_148134_d(7, 8);
        GuiList guiList2 = this.list;
        if (guiList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            guiList2 = null;
        }
        guiList2.func_148144_a(-1, false, 0, 0);
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l - 80, this.field_146295_m - 65, 70, 20, "Back"));
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l - 80, 22 + 24, 70, 20, "Import"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 80, 22 + 48, 70, 20, "Delete"));
        this.field_146292_n.add(new GuiButton(3, this.field_146294_l - 80, 22 + 72, 70, 20, "Reload"));
        this.field_146292_n.add(new GuiButton(4, this.field_146294_l - 80, 22 + 96, 70, 20, "Folder"));
        this.field_146292_n.add(new GuiButton(5, this.field_146294_l - 80, 22 + 120, 70, 20, "Docs"));
        this.field_146292_n.add(new GuiButton(6, this.field_146294_l - 80, 22 + 144, 70, 20, "Find Scripts"));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            guiList = null;
        }
        guiList.func_148128_a(mouseX, mouseY, partialTicks);
        func_73732_a(Fonts.font40, "§9§lScripts", this.field_146294_l / 2, 28, 16777215);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                try {
                    File file = MiscUtils.openFileChooser();
                    if (file == null) {
                        return;
                    }
                    String fileName = file.getName();
                    Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
                    if (StringsKt.endsWith$default(fileName, ".js", false, 2, (Object) null)) {
                        LiquidBounce.INSTANCE.getScriptManager().importScript(file);
                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        return;
                    } else if (StringsKt.endsWith$default(fileName, ".zip", false, 2, (Object) null)) {
                        ZipFile zipFile = new ZipFile(file);
                        Enumeration entries = zipFile.entries();
                        ArrayList scriptFiles = new ArrayList();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            String entryName = entry.getName();
                            File entryFile = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), entryName);
                            if (entry.isDirectory()) {
                                entryFile.mkdir();
                            } else {
                                InputStream fileStream = zipFile.getInputStream(entry);
                                FileOutputStream fileOutputStream = new FileOutputStream(entryFile);
                                IOUtils.copy(fileStream, fileOutputStream);
                                fileOutputStream.close();
                                fileStream.close();
                                Intrinsics.checkNotNullExpressionValue(entryName, "entryName");
                                if (!StringsKt.contains$default((CharSequence) entryName, (CharSequence) "/", false, 2, (Object) null)) {
                                    scriptFiles.add(entryFile);
                                }
                            }
                        }
                        ArrayList $this$forEach$iv = scriptFiles;
                        for (Object element$iv : $this$forEach$iv) {
                            File scriptFile = (File) element$iv;
                            LiquidBounce.INSTANCE.getScriptManager().loadScript(scriptFile);
                        }
                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                        return;
                    } else {
                        MiscUtils.showErrorPopup("Wrong file extension.", "The file extension has to be .js or .zip");
                        return;
                    }
                } catch (Throwable t) {
                    ClientUtils.getLogger().error("Something went wrong while importing a script.", t);
                    MiscUtils.showErrorPopup(t.getClass().getName(), t.getMessage());
                    return;
                }
            case 2:
                try {
                    GuiList guiList = this.list;
                    if (guiList == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("list");
                        guiList = null;
                    }
                    if (guiList.getSelectedSlot$LiquidBounce() != -1) {
                        List<Script> scripts = LiquidBounce.INSTANCE.getScriptManager().getScripts();
                        GuiList guiList2 = this.list;
                        if (guiList2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("list");
                            guiList2 = null;
                        }
                        Script script = scripts.get(guiList2.getSelectedSlot$LiquidBounce());
                        LiquidBounce.INSTANCE.getScriptManager().deleteScript(script);
                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                        return;
                    }
                    return;
                } catch (Throwable t2) {
                    ClientUtils.getLogger().error("Something went wrong while deleting a script.", t2);
                    MiscUtils.showErrorPopup(t2.getClass().getName(), t2.getMessage());
                    return;
                }
            case 3:
                try {
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
                    LiquidBounce.INSTANCE.getScriptManager().loadScripts();
                    LiquidBounce.INSTANCE.getScriptManager().enableScripts();
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
                    LiquidBounce.INSTANCE.setStarting(false);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    return;
                } catch (Throwable t3) {
                    ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", t3);
                    MiscUtils.showErrorPopup(t3.getClass().getName(), t3.getMessage());
                    return;
                }
            case 4:
                try {
                    Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
                    return;
                } catch (Throwable t4) {
                    ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", t4);
                    MiscUtils.showErrorPopup(t4.getClass().getName(), t4.getMessage());
                    return;
                }
            case 5:
                try {
                    Desktop.getDesktop().browse(new URL("https://liquidbounce.net/docs/ScriptAPI/Getting%20Started").toURI());
                    return;
                } catch (Exception e) {
                    return;
                }
            case 6:
                try {
                    Desktop.getDesktop().browse(new URL("https://forum.ccbluex.net/viewforum.php?id=16").toURI());
                    return;
                } catch (Exception e2) {
                    return;
                }
            default:
                return;
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
        } else {
            super.func_73869_a(typedChar, keyCode);
        }
    }

    public void func_146274_d() {
        super.func_146274_d();
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            guiList = null;
        }
        guiList.func_178039_p();
    }

    /* compiled from: GuiScripts.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0082\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0014J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\r\u0010\u0014\u001a\u00020\u0006H��¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0006H\u0014J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0006H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0018"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "gui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;Lnet/minecraft/client/gui/GuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "doubleClick", "", "var3", "getSelectedSlot", "getSelectedSlot$LiquidBounce", "getSize", "isSelected", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.GuiScripts$GuiList */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList.class */
    private final class GuiList extends GuiSlot {
        private int selectedSlot;
        final /* synthetic */ GuiScripts this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GuiList(@NotNull GuiScripts this$0, GuiScreen gui) {
            super(this$0.field_146297_k, gui.field_146294_l, gui.field_146295_m, 40, gui.field_146295_m - 40, 30);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(gui, "gui");
            this.this$0 = this$0;
        }

        protected boolean func_148131_a(int id) {
            return this.selectedSlot == id;
        }

        public final int getSelectedSlot$LiquidBounce() {
            if (this.selectedSlot > LiquidBounce.INSTANCE.getScriptManager().getScripts().size()) {
                return -1;
            }
            return this.selectedSlot;
        }

        protected int func_148127_b() {
            return LiquidBounce.INSTANCE.getScriptManager().getScripts().size();
        }

        public void func_148144_a(int id, boolean doubleClick, int var3, int var4) {
            this.selectedSlot = id;
        }

        protected void func_180791_a(int id, int x, int y, int var4, int var5, int var6) {
            Script script = LiquidBounce.INSTANCE.getScriptManager().getScripts().get(id);
            this.this$0.func_73732_a(Fonts.fontSFUI40, "§9" + script.getScriptName() + " §7v" + script.getScriptVersion(), this.field_148155_a / 2, y + 3, Color.LIGHT_GRAY.getRGB());
            this.this$0.func_73732_a(Fonts.fontSFUI40, Intrinsics.stringPlus("by §c", ArraysKt.joinToString$default(script.getScriptAuthors(), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)), this.field_148155_a / 2, y + 16, Color.LIGHT_GRAY.getRGB());
        }

        protected void func_148123_a() {
        }
    }
}
