package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thealtening.AltService;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.Thread;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.GuiChangeName;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.GuiLoginIntoAccount;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.GuiSessionLogin;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0007\u0018�� $2\u00020\u0001:\u0002$%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0007H\u0016J \u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0013H\u0016J\b\u0010\u001c\u001a\u00020\u0013H\u0016J\u0018\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0017H\u0016J \u0010!\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0017H\u0016J\b\u0010#\u001a\u00020\u0013H\u0016R\u0012\u0010\u0004\u001a\u00060\u0005R\u00020��X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n��R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006&"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "altsList", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "loginButton", "Lnet/minecraft/client/gui/GuiButton;", "randomButton", "randomCracked", "searchField", "Lnet/minecraft/client/gui/GuiTextField;", "status", "", "getStatus", "()Ljava/lang/String;", "setStatus", "(Ljava/lang/String;)V", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "updateScreen", "Companion", "GuiList", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager.class */
public final class GuiAltManager extends GuiScreen {
    @NotNull
    private final GuiScreen prevGui;
    @NotNull
    private String status = "§7Idle...";
    private GuiButton loginButton;
    private GuiButton randomButton;
    private GuiButton randomCracked;
    private GuiList altsList;
    private GuiTextField searchField;
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final AltService altService = new AltService();
    @NotNull
    private static final Map<String, Boolean> activeGenerators = new LinkedHashMap();

    public GuiAltManager(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    @NotNull
    public final String getStatus() {
        return this.status;
    }

    public final void setStatus(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.status = str;
    }

    public void func_73866_w_() {
        int i;
        int textFieldWidth = RangesKt.coerceAtLeast(this.field_146294_l / 8, 70);
        this.searchField = new GuiTextField(2, Fonts.font40, (this.field_146294_l - textFieldWidth) - 10, 10, textFieldWidth, 20);
        GuiTextField guiTextField = this.searchField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField = null;
        }
        guiTextField.func_146203_f(Integer.MAX_VALUE);
        this.altsList = new GuiList(this, this);
        GuiList guiList = this.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList.func_148134_d(7, 8);
        List $this$indexOfFirst$iv = LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts();
        Intrinsics.checkNotNullExpressionValue($this$indexOfFirst$iv, "fileManager.accountsConfig.accounts");
        int index$iv = 0;
        Iterator<MinecraftAccount> it = $this$indexOfFirst$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            Object item$iv = it.next();
            if (Intrinsics.areEqual(((MinecraftAccount) item$iv).getName(), this.field_146297_k.field_71449_j.func_111285_a())) {
                i = index$iv;
                break;
            }
            index$iv++;
        }
        int mightBeTheCurrentAccount = i;
        GuiList guiList2 = this.altsList;
        if (guiList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList2 = null;
        }
        guiList2.func_148144_a(mightBeTheCurrentAccount, false, 0, 0);
        GuiList guiList3 = this.altsList;
        if (guiList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList3 = null;
        }
        GuiList guiList4 = this.altsList;
        if (guiList4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList4 = null;
        }
        guiList3.func_148145_f(mightBeTheCurrentAccount * guiList4.func_148146_j());
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l - 80, 22 + 24, 70, 20, "Add"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 80, 22 + 48, 70, 20, "Remove"));
        this.field_146292_n.add(new GuiButton(7, this.field_146294_l - 80, 22 + 72, 70, 20, "Import"));
        this.field_146292_n.add(new GuiButton(12, this.field_146294_l - 80, 22 + 96, 70, 20, "Export"));
        this.field_146292_n.add(new GuiButton(8, this.field_146294_l - 80, 22 + 120, 70, 20, "Copy"));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l - 80, this.field_146295_m - 65, 70, 20, "Back"));
        List list = this.field_146292_n;
        GuiButton it2 = new GuiButton(3, 5, 22 + 24, 90, 20, "Login");
        this.loginButton = it2;
        list.add(it2);
        List list2 = this.field_146292_n;
        GuiButton it3 = new GuiButton(4, 5, 22 + 48, 90, 20, "Random");
        this.randomButton = it3;
        list2.add(it3);
        List list3 = this.field_146292_n;
        GuiButton it4 = new GuiButton(99, 5, 22 + 72, 90, 20, "Cracked");
        this.randomCracked = it4;
        list3.add(it4);
        this.field_146292_n.add(new GuiButton(6, 5, 22 + 96, 90, 20, "Direct Login"));
        this.field_146292_n.add(new GuiButton(10, 5, 22 + 120, 90, 20, "Session Login"));
        this.field_146292_n.add(new GuiButton(88, 5, 22 + 144, 90, 20, "Change Name"));
        if (activeGenerators.getOrDefault("thealtening", true).booleanValue()) {
            this.field_146292_n.add(new GuiButton(9, 5, 22 + 168 + 5, 90, 20, "TheAltening"));
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        String str;
        String str2;
        func_146278_c(0);
        GuiList guiList = this.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList.func_148128_a(mouseX, mouseY, partialTicks);
        Fonts.font40.drawCenteredString("AltManager", this.field_146294_l / 2.0f, 6.0f, 16777215);
        GameFontRenderer gameFontRenderer = Fonts.font35;
        GuiTextField guiTextField = this.searchField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField = null;
        }
        String func_146179_b = guiTextField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b, "searchField.text");
        if (func_146179_b.length() == 0) {
            str = LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts().size() + " Alts";
        } else {
            StringBuilder sb = new StringBuilder();
            GuiList guiList2 = this.altsList;
            if (guiList2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("altsList");
                guiList2 = null;
            }
            str = sb.append(guiList2.getAccounts().size()).append(" Search Results").toString();
        }
        gameFontRenderer.drawCenteredString(str, this.field_146294_l / 2.0f, 18.0f, 16777215);
        Fonts.font35.drawCenteredString(this.status, this.field_146294_l / 2.0f, 32.0f, 16777215);
        Fonts.font35.func_175063_a(Intrinsics.stringPlus("§7User: §a", this.field_146297_k.func_110432_I().func_111285_a()), 6.0f, 6.0f, 16777215);
        GameFontRenderer gameFontRenderer2 = Fonts.font35;
        if (altService.getCurrentService() == AltService.EnumAltService.THEALTENING) {
            str2 = "TheAltening";
        } else {
            UserUtils userUtils = UserUtils.INSTANCE;
            String func_148254_d = this.field_146297_k.func_110432_I().func_148254_d();
            Intrinsics.checkNotNullExpressionValue(func_148254_d, "mc.getSession().token");
            str2 = userUtils.isValidTokenOffline(func_148254_d) ? "Premium" : "Cracked";
        }
        gameFontRenderer2.func_175063_a(Intrinsics.stringPlus("§7Type: §a", str2), 6.0f, 15.0f, 16777215);
        GuiTextField guiTextField2 = this.searchField;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField2 = null;
        }
        guiTextField2.func_146194_f();
        GuiTextField guiTextField3 = this.searchField;
        if (guiTextField3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField3 = null;
        }
        String func_146179_b2 = guiTextField3.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(func_146179_b2, "searchField.text");
        if (func_146179_b2.length() == 0) {
            GuiTextField guiTextField4 = this.searchField;
            if (guiTextField4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("searchField");
                guiTextField4 = null;
            }
            if (!guiTextField4.func_146206_l()) {
                GameFontRenderer gameFontRenderer3 = Fonts.font40;
                GuiTextField guiTextField5 = this.searchField;
                if (guiTextField5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("searchField");
                    guiTextField5 = null;
                }
                gameFontRenderer3.func_175063_a("§7Search...", guiTextField5.field_146209_f + 4, 17.0f, 16777215);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public void func_146284_a(@NotNull GuiButton button) {
        String str;
        String str2;
        String str3;
        Intrinsics.checkNotNullParameter(button, "button");
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                this.field_146297_k.func_147108_a(new GuiLoginIntoAccount(this, false, 2, null));
                return;
            case 2:
                GuiList guiList = this.altsList;
                if (guiList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                if (guiList.getSelectedSlot() != -1) {
                    GuiList guiList2 = this.altsList;
                    if (guiList2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        guiList2 = null;
                    }
                    int selectedSlot = guiList2.getSelectedSlot();
                    GuiList guiList3 = this.altsList;
                    if (guiList3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        guiList3 = null;
                    }
                    if (selectedSlot < guiList3.func_148127_b()) {
                        AccountsConfig accountsConfig = LiquidBounce.INSTANCE.getFileManager().accountsConfig;
                        GuiList guiList4 = this.altsList;
                        if (guiList4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("altsList");
                            guiList4 = null;
                        }
                        List<MinecraftAccount> accounts = guiList4.getAccounts();
                        GuiList guiList5 = this.altsList;
                        if (guiList5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("altsList");
                            guiList5 = null;
                        }
                        accountsConfig.removeAccount(accounts.get(guiList5.getSelectedSlot()));
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
                        str3 = "§aThe account has been removed.";
                        this.status = str3;
                        return;
                    }
                }
                str3 = "§cSelect an account.";
                this.status = str3;
                return;
            case 3:
                GuiAltManager guiAltManager = this;
                GuiList guiList6 = this.altsList;
                if (guiList6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList6 = null;
                }
                MinecraftAccount it = guiList6.getSelectedAccount();
                if (it == null) {
                    str2 = "§cSelect an account.";
                } else {
                    GuiButton guiButton = this.loginButton;
                    if (guiButton == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                        guiButton = null;
                    }
                    guiButton.field_146124_l = false;
                    GuiButton guiButton2 = this.randomButton;
                    if (guiButton2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomButton");
                        guiButton2 = null;
                    }
                    guiButton2.field_146124_l = false;
                    GuiButton guiButton3 = this.randomCracked;
                    if (guiButton3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomCracked");
                        guiButton3 = null;
                    }
                    guiButton3.field_146124_l = false;
                    Companion.login(it, new GuiAltManager$actionPerformed$1$1(this), new GuiAltManager$actionPerformed$1$2(this), new GuiAltManager$actionPerformed$1$3(this));
                    guiAltManager = guiAltManager;
                    str2 = "§aLogging in...";
                }
                guiAltManager.status = str2;
                return;
            case 4:
                GuiAltManager guiAltManager2 = this;
                GuiList guiList7 = this.altsList;
                if (guiList7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList7 = null;
                }
                MinecraftAccount it2 = (MinecraftAccount) CollectionsKt.randomOrNull(guiList7.getAccounts(), Random.Default);
                if (it2 == null) {
                    str = "§cYou do not have any accounts.";
                } else {
                    GuiButton guiButton4 = this.loginButton;
                    if (guiButton4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                        guiButton4 = null;
                    }
                    guiButton4.field_146124_l = false;
                    GuiButton guiButton5 = this.randomButton;
                    if (guiButton5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomButton");
                        guiButton5 = null;
                    }
                    guiButton5.field_146124_l = false;
                    GuiButton guiButton6 = this.randomCracked;
                    if (guiButton6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomCracked");
                        guiButton6 = null;
                    }
                    guiButton6.field_146124_l = false;
                    Companion.login(it2, new GuiAltManager$actionPerformed$2$1(this), new GuiAltManager$actionPerformed$2$2(this), new GuiAltManager$actionPerformed$2$3(this));
                    guiAltManager2 = guiAltManager2;
                    str = "§aLogging in...";
                }
                guiAltManager2.status = str;
                return;
            case 6:
                this.field_146297_k.func_147108_a(new GuiLoginIntoAccount(this, true));
                return;
            case 7:
                File file = MiscUtils.openFileChooser();
                if (file == null) {
                    return;
                }
                Iterable $this$forEach$iv = FilesKt.readLines$default(file, null, 1, null);
                for (Object element$iv : $this$forEach$iv) {
                    List accountData = new Regex(CallSiteDescriptor.TOKEN_DELIMITER).split((String) element$iv, 2);
                    if (accountData.size() > 1) {
                        LiquidBounce.INSTANCE.getFileManager().accountsConfig.addMojangAccount(accountData.get(0), accountData.get(1));
                    } else if (accountData.get(0).length() < 16) {
                        LiquidBounce.INSTANCE.getFileManager().accountsConfig.addCrackedAccount(accountData.get(0));
                    }
                }
                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().accountsConfig);
                this.status = "§aThe accounts were imported successfully.";
                return;
            case 8:
                GuiList guiList8 = this.altsList;
                if (guiList8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList8 = null;
                }
                MinecraftAccount currentAccount = guiList8.getSelectedAccount();
                if (currentAccount == null) {
                    this.status = "§cSelect an account.";
                    return;
                }
                String formattedData = currentAccount instanceof MojangAccount ? ((MojangAccount) currentAccount).getEmail() + ':' + ((MojangAccount) currentAccount).getPassword() : currentAccount instanceof MicrosoftAccount ? ((MicrosoftAccount) currentAccount).getName() + ':' + ((MicrosoftAccount) currentAccount).getSession().getToken() : currentAccount.getName();
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(formattedData), (ClipboardOwner) null);
                this.status = "§aCopied account into your clipboard.";
                return;
            case 9:
                this.field_146297_k.func_147108_a(new GuiTheAltening(this));
                return;
            case 10:
                this.field_146297_k.func_147108_a(new GuiSessionLogin(this));
                return;
            case 12:
                if (LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts().isEmpty()) {
                    this.status = "§cYou do not have any accounts to export.";
                    return;
                }
                File file2 = MiscUtils.saveFileChooser();
                if (file2 == null || file2.isDirectory()) {
                    return;
                }
                try {
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    List<MinecraftAccount> accounts2 = LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts();
                    Intrinsics.checkNotNullExpressionValue(accounts2, "fileManager.accountsConfig.accounts");
                    String accounts3 = CollectionsKt.joinToString$default(accounts2, "\n", null, null, 0, null, GuiAltManager$actionPerformed$accounts$1.INSTANCE, 30, null);
                    FilesKt.writeText$default(file2, accounts3, null, 2, null);
                    this.status = "§aExported successfully!";
                    return;
                } catch (Exception e) {
                    this.status = Intrinsics.stringPlus("§cUnable to export due to error: ", e.getMessage());
                    return;
                }
            case 88:
                this.field_146297_k.func_147108_a(new GuiChangeName(this));
                return;
            case 99:
                GuiButton guiButton7 = this.loginButton;
                if (guiButton7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                    guiButton7 = null;
                }
                guiButton7.field_146124_l = false;
                GuiButton guiButton8 = this.randomButton;
                if (guiButton8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("randomButton");
                    guiButton8 = null;
                }
                guiButton8.field_146124_l = false;
                GuiButton guiButton9 = this.randomCracked;
                if (guiButton9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("randomCracked");
                    guiButton9 = null;
                }
                guiButton9.field_146124_l = false;
                CrackedAccount rand = new CrackedAccount();
                String randomString = RandomUtils.randomString(RandomUtils.nextInt(5, 16));
                Intrinsics.checkNotNullExpressionValue(randomString, "randomString(RandomUtils.nextInt(5, 16))");
                rand.setName(randomString);
                this.status = "§aGenerating...";
                Companion.login(rand, new GuiAltManager$actionPerformed$3(this), new GuiAltManager$actionPerformed$4(this), new GuiAltManager$actionPerformed$5(this));
                return;
            default:
                return;
        }
    }

    public void func_73869_a(char typedChar, int keyCode) {
        GuiTextField guiTextField = this.searchField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField = null;
        }
        if (guiTextField.func_146206_l()) {
            GuiTextField guiTextField2 = this.searchField;
            if (guiTextField2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("searchField");
                guiTextField2 = null;
            }
            guiTextField2.func_146201_a(typedChar, keyCode);
        }
        switch (keyCode) {
            case 1:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 28:
                GuiList guiList = this.altsList;
                if (guiList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                GuiList guiList2 = this.altsList;
                if (guiList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList2 = null;
                }
                guiList.func_148144_a(guiList2.getSelectedSlot(), true, 0, 0);
                break;
            case 200:
                GuiList guiList3 = this.altsList;
                if (guiList3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList3 = null;
                }
                int i = guiList3.getSelectedSlot() - 1;
                if (i < 0) {
                    i = 0;
                }
                GuiList guiList4 = this.altsList;
                if (guiList4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList4 = null;
                }
                guiList4.func_148144_a(i, false, 0, 0);
                break;
            case Opcode.JSR_W /* 201 */:
                GuiList guiList5 = this.altsList;
                if (guiList5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList5 = null;
                }
                guiList5.func_148145_f((-this.field_146295_m) + 100);
                return;
            case 208:
                GuiList guiList6 = this.altsList;
                if (guiList6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList6 = null;
                }
                int i2 = guiList6.getSelectedSlot() + 1;
                GuiList guiList7 = this.altsList;
                if (guiList7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList7 = null;
                }
                if (i2 >= guiList7.func_148127_b()) {
                    GuiList guiList8 = this.altsList;
                    if (guiList8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        guiList8 = null;
                    }
                    i2 = guiList8.func_148127_b() - 1;
                }
                GuiList guiList9 = this.altsList;
                if (guiList9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList9 = null;
                }
                guiList9.func_148144_a(i2, false, 0, 0);
                break;
            case 209:
                GuiList guiList10 = this.altsList;
                if (guiList10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList10 = null;
                }
                guiList10.func_148145_f(this.field_146295_m - 100);
                break;
        }
        super.func_73869_a(typedChar, keyCode);
    }

    public void func_146274_d() {
        super.func_146274_d();
        GuiList guiList = this.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList.func_178039_p();
    }

    public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        GuiTextField guiTextField = this.searchField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField = null;
        }
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.searchField;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchField");
            guiTextField = null;
        }
        guiTextField.func_146178_a();
    }

    /* compiled from: GuiAltManager.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u0014H\u0014J8\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u000eH\u0014J(\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000eH\u0016J\b\u0010!\u001a\u00020\u000eH\u0016J\u0010\u0010\"\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u000eH\u0014R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u00020\u000e8FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006#"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "prevGui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;Lnet/minecraft/client/gui/GuiScreen;)V", "accounts", "", "Lme/liuli/elixir/account/MinecraftAccount;", "getAccounts", "()Ljava/util/List;", "selectedAccount", "getSelectedAccount", "()Lme/liuli/elixir/account/MinecraftAccount;", "selectedSlot", "", "getSelectedSlot", "()I", "setSelectedSlot", "(I)V", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "clickedElement", "doubleClick", "", "var3", "getSize", "isSelected", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$GuiList */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList.class */
    public final class GuiList extends GuiSlot {
        private int selectedSlot;
        final /* synthetic */ GuiAltManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GuiList(@NotNull GuiAltManager this$0, GuiScreen prevGui) {
            super(this$0.field_146297_k, prevGui.field_146294_l, prevGui.field_146295_m, 40, prevGui.field_146295_m - 40, 30);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(prevGui, "prevGui");
            this.this$0 = this$0;
        }

        @NotNull
        public final List<MinecraftAccount> getAccounts() {
            GuiTextField guiTextField = this.this$0.searchField;
            if (guiTextField == null) {
                Intrinsics.throwUninitializedPropertyAccessException("searchField");
                guiTextField = null;
            }
            String func_146179_b = guiTextField.func_146179_b();
            if (func_146179_b != null) {
                if (!(func_146179_b.length() == 0)) {
                    Locale locale = Locale.getDefault();
                    Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
                    Object search = func_146179_b.toLowerCase(locale);
                    Intrinsics.checkNotNullExpressionValue(search, "this as java.lang.String).toLowerCase(locale)");
                    Iterable accounts = LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts();
                    Intrinsics.checkNotNullExpressionValue(accounts, "fileManager.accountsConfig.accounts");
                    Iterable $this$filter$iv = accounts;
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$filter$iv) {
                        MinecraftAccount it = (MinecraftAccount) element$iv$iv;
                        if (StringsKt.contains((CharSequence) it.getName(), (CharSequence) ((CharSequence) search), true) || ((it instanceof MojangAccount) && StringsKt.contains((CharSequence) ((MojangAccount) it).getEmail(), (CharSequence) ((CharSequence) search), true))) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }
                    return (List) destination$iv$iv;
                }
            }
            List<MinecraftAccount> accounts2 = LiquidBounce.INSTANCE.getFileManager().accountsConfig.getAccounts();
            Intrinsics.checkNotNullExpressionValue(accounts2, "fileManager.accountsConfig.accounts");
            return accounts2;
        }

        public final void setSelectedSlot(int i) {
            this.selectedSlot = i;
        }

        public final int getSelectedSlot() {
            if (this.selectedSlot > getAccounts().size()) {
                return -1;
            }
            return this.selectedSlot;
        }

        @Nullable
        public final MinecraftAccount getSelectedAccount() {
            if (getSelectedSlot() >= 0 && getSelectedSlot() < getAccounts().size()) {
                return getAccounts().get(getSelectedSlot());
            }
            return null;
        }

        protected boolean func_148131_a(int id) {
            return getSelectedSlot() == id;
        }

        public int func_148127_b() {
            return getAccounts().size();
        }

        public void func_148144_a(int clickedElement, boolean doubleClick, int var3, int var4) {
            String str;
            this.selectedSlot = clickedElement;
            if (doubleClick) {
                GuiAltManager guiAltManager = this.this$0;
                GuiList guiList = this.this$0.altsList;
                if (guiList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                MinecraftAccount it = guiList.getSelectedAccount();
                if (it == null) {
                    str = "§cSelect an account.";
                } else {
                    GuiAltManager guiAltManager2 = this.this$0;
                    GuiButton guiButton = guiAltManager2.loginButton;
                    if (guiButton == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loginButton");
                        guiButton = null;
                    }
                    guiButton.field_146124_l = false;
                    GuiButton guiButton2 = guiAltManager2.randomButton;
                    if (guiButton2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomButton");
                        guiButton2 = null;
                    }
                    guiButton2.field_146124_l = false;
                    GuiButton guiButton3 = guiAltManager2.randomCracked;
                    if (guiButton3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("randomCracked");
                        guiButton3 = null;
                    }
                    guiButton3.field_146124_l = false;
                    GuiAltManager.Companion.login(it, new GuiAltManager$GuiList$elementClicked$1$1(guiAltManager2, this), new GuiAltManager$GuiList$elementClicked$1$2(guiAltManager2), new GuiAltManager$GuiList$elementClicked$1$3(guiAltManager2));
                    guiAltManager = guiAltManager;
                    str = "§aLogging in...";
                }
                guiAltManager.setStatus(str);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0070  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00a4  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00ad  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void func_180791_a(int r11, int r12, int r13, int r14, int r15, int r16) {
            /*
                r10 = this;
                r0 = r10
                java.util.List r0 = r0.getAccounts()
                r1 = r11
                java.lang.Object r0 = r0.get(r1)
                me.liuli.elixir.account.MinecraftAccount r0 = (me.liuli.elixir.account.MinecraftAccount) r0
                r17 = r0
                r0 = r17
                boolean r0 = r0 instanceof me.liuli.elixir.account.MojangAccount
                if (r0 == 0) goto L3d
                r0 = r17
                me.liuli.elixir.account.MojangAccount r0 = (me.liuli.elixir.account.MojangAccount) r0
                java.lang.String r0 = r0.getName()
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                int r0 = r0.length()
                if (r0 != 0) goto L2e
                r0 = 1
                goto L2f
            L2e:
                r0 = 0
            L2f:
                if (r0 == 0) goto L3d
                r0 = r17
                me.liuli.elixir.account.MojangAccount r0 = (me.liuli.elixir.account.MojangAccount) r0
                java.lang.String r0 = r0.getEmail()
                goto L42
            L3d:
                r0 = r17
                java.lang.String r0 = r0.getName()
            L42:
                r18 = r0
                net.ccbluex.liquidbounce.ui.font.GameFontRenderer r0 = net.ccbluex.liquidbounce.p004ui.font.Fonts.font40
                r1 = r18
                r2 = r10
                int r2 = r2.field_148155_a
                float r2 = (float) r2
                r3 = 1073741824(0x40000000, float:2.0)
                float r2 = r2 / r3
                r3 = r13
                float r3 = (float) r3
                r4 = 1073741824(0x40000000, float:2.0)
                float r3 = r3 + r4
                java.awt.Color r4 = java.awt.Color.WHITE
                int r4 = r4.getRGB()
                r5 = 1
                int r0 = r0.drawCenteredString(r1, r2, r3, r4, r5)
                net.ccbluex.liquidbounce.ui.font.GameFontRenderer r0 = net.ccbluex.liquidbounce.p004ui.font.Fonts.font40
                r1 = r17
                boolean r1 = r1 instanceof me.liuli.elixir.account.CrackedAccount
                if (r1 == 0) goto L70
                java.lang.String r1 = "Cracked"
                goto L8f
            L70:
                r1 = r17
                boolean r1 = r1 instanceof me.liuli.elixir.account.MicrosoftAccount
                if (r1 == 0) goto L7e
                java.lang.String r1 = "Microsoft"
                goto L8f
            L7e:
                r1 = r17
                boolean r1 = r1 instanceof me.liuli.elixir.account.MojangAccount
                if (r1 == 0) goto L8c
                java.lang.String r1 = "Mojang"
                goto L8f
            L8c:
                java.lang.String r1 = "Something else"
            L8f:
                r2 = r10
                int r2 = r2.field_148155_a
                float r2 = (float) r2
                r3 = 1073741824(0x40000000, float:2.0)
                float r2 = r2 / r3
                r3 = r13
                float r3 = (float) r3
                r4 = 1097859072(0x41700000, float:15.0)
                float r3 = r3 + r4
                r4 = r17
                boolean r4 = r4 instanceof me.liuli.elixir.account.CrackedAccount
                if (r4 == 0) goto Lad
                java.awt.Color r4 = java.awt.Color.GRAY
                int r4 = r4.getRGB()
                goto Lbe
            Lad:
                java.awt.Color r4 = new java.awt.Color
                r5 = r4
                r6 = 118(0x76, float:1.65E-43)
                r7 = 255(0xff, float:3.57E-43)
                r8 = 95
                r5.<init>(r6, r7, r8)
                int r4 = r4.getRGB()
            Lbe:
                r5 = 1
                int r0 = r0.drawCenteredString(r1, r2, r3, r4, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager.GuiList.func_180791_a(int, int, int, int, int, int):void");
        }

        protected void func_148123_a() {
        }
    }

    /* compiled from: GuiAltManager.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��J\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJB\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0016\u0010\u0013\u001a\u0012\u0012\b\u0012\u00060\u0015j\u0002`\u0016\u0012\u0004\u0012\u00020\f0\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0\u0012R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$Companion;", "", "()V", "activeGenerators", "", "", "", "altService", "Lcom/thealtening/AltService;", "getAltService", "()Lcom/thealtening/AltService;", "loadActiveGenerators", "", "login", "Ljava/lang/Thread;", "minecraftAccount", "Lme/liuli/elixir/account/MinecraftAccount;", "success", "Lkotlin/Function0;", "error", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "done", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final AltService getAltService() {
            return GuiAltManager.altService;
        }

        public final void loadActiveGenerators() {
            try {
                JsonElement jsonElement = new JsonParser().parse(HttpUtils.get("https://wysi-foundation.github.io/LiquidCloud/LiquidBounce/generators.json"));
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    jsonObject.entrySet().forEach(Companion::m2833loadActiveGenerators$lambda0);
                }
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("Failed to load enabled generators.", throwable);
            }
        }

        /* renamed from: loadActiveGenerators$lambda-0 */
        private static final void m2833loadActiveGenerators$lambda0(Map.Entry dstr$key$value) {
            Intrinsics.checkNotNullParameter(dstr$key$value, "$dstr$key$value");
            String key = (String) dstr$key$value.getKey();
            JsonElement value = (JsonElement) dstr$key$value.getValue();
            GuiAltManager.activeGenerators.put(key, Boolean.valueOf(value.getAsBoolean()));
        }

        @NotNull
        public final Thread login(@NotNull MinecraftAccount minecraftAccount, @NotNull Functions<Unit> success, @NotNull Function1<? super Exception, Unit> error, @NotNull Functions<Unit> done) {
            Intrinsics.checkNotNullParameter(minecraftAccount, "minecraftAccount");
            Intrinsics.checkNotNullParameter(success, "success");
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(done, "done");
            return Thread.thread$default(false, false, null, "LoginTask", 0, new GuiAltManager$Companion$login$1(error, minecraftAccount, success, done), 23, null);
        }
    }
}
