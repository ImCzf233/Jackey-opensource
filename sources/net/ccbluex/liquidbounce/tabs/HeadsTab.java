package net.ccbluex.liquidbounce.tabs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/* compiled from: HeadsTab.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010!\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/tabs/HeadsTab;", "Lnet/minecraft/creativetab/CreativeTabs;", "()V", "heads", "Ljava/util/ArrayList;", "Lnet/minecraft/item/ItemStack;", "displayAllReleventItems", "", "itemList", "", "getTabIconItem", "Lnet/minecraft/item/Item;", "getTranslatedTabLabel", "", "hasSearchBar", "", "loadHeads", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/tabs/HeadsTab.class */
public final class HeadsTab extends CreativeTabs {
    @NotNull
    private final ArrayList<ItemStack> heads = new ArrayList<>();

    public HeadsTab() {
        super("Heads");
        func_78025_a("item_search.png");
        loadHeads();
    }

    private final void loadHeads() {
        try {
            ClientUtils.getLogger().info("Loading heads...");
            JsonElement headsConfiguration = new JsonParser().parse(HttpUtils.get("https://wysi-foundation.github.io/LiquidCloud/LiquidBounce/heads.json"));
            if (!headsConfiguration.isJsonObject()) {
                return;
            }
            JsonObject headsConf = headsConfiguration.getAsJsonObject();
            if (headsConf.get("enabled").getAsBoolean()) {
                String url = headsConf.get("url").getAsString();
                ClientUtils.getLogger().info("Loading heads from " + ((Object) url) + "...");
                JsonParser jsonParser = new JsonParser();
                Intrinsics.checkNotNullExpressionValue(url, "url");
                JsonElement headsElement = jsonParser.parse(HttpUtils.get(url));
                if (!headsElement.isJsonObject()) {
                    ClientUtils.getLogger().error("Something is wrong, the heads json is not a JsonObject!");
                    return;
                }
                JsonObject headsObject = headsElement.getAsJsonObject();
                for (Map.Entry entry : headsObject.entrySet()) {
                    Intrinsics.checkNotNullExpressionValue(entry, "headsObject.entrySet()");
                    JsonElement value = (JsonElement) entry.getValue();
                    JsonObject headElement = value.getAsJsonObject();
                    this.heads.add(ItemUtils.createItem("skull 1 3 {display:{Name:\"" + ((Object) headElement.get("name").getAsString()) + "\"},SkullOwner:{Id:\"" + ((Object) headElement.get("uuid").getAsString()) + "\",Properties:{textures:[{Value:\"" + ((Object) headElement.get("value").getAsString()) + "\"}]}}}"));
                }
                ClientUtils.getLogger().info("Loaded " + this.heads.size() + " heads from HeadDB.");
                return;
            }
            ClientUtils.getLogger().info("Heads are disabled.");
        } catch (Exception e) {
            ClientUtils.getLogger().error("Error while reading heads.", e);
        }
    }

    public void func_78018_a(@NotNull List<ItemStack> itemList) {
        Intrinsics.checkNotNullParameter(itemList, "itemList");
        itemList.addAll(this.heads);
    }

    @NotNull
    public Item func_78016_d() {
        Item skull = Items.field_151144_bL;
        Intrinsics.checkNotNullExpressionValue(skull, "skull");
        return skull;
    }

    @NotNull
    public String func_78024_c() {
        return "Heads";
    }

    public boolean hasSearchBar() {
        return true;
    }
}
