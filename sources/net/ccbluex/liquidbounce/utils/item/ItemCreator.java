package net.ccbluex.liquidbounce.utils.item;

import java.util.Objects;
import java.util.regex.Pattern;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ItemCreator.class */
public final class ItemCreator {
    public static ItemStack createItem(String itemArguments) {
        try {
            String itemArguments2 = itemArguments.replace('&', (char) 167);
            Item item = new Item();
            String[] args = null;
            int i = 1;
            int j = 0;
            for (int mode = 0; mode <= Math.min(12, itemArguments2.length() - 2); mode++) {
                args = itemArguments2.substring(mode).split(Pattern.quote(" "));
                ResourceLocation resourcelocation = new ResourceLocation(args[0]);
                item = (Item) Item.field_150901_e.func_82594_a(resourcelocation);
                if (item != null) {
                    break;
                }
            }
            if (item == null) {
                return null;
            }
            if (((String[]) Objects.requireNonNull(args)).length >= 2 && args[1].matches("\\d+")) {
                i = Integer.parseInt(args[1]);
            }
            if (args.length >= 3 && args[2].matches("\\d+")) {
                j = Integer.parseInt(args[2]);
            }
            ItemStack itemstack = new ItemStack(item, i, j);
            if (args.length >= 4) {
                StringBuilder NBT = new StringBuilder();
                for (int nbtcount = 3; nbtcount < args.length; nbtcount++) {
                    NBT.append(" ").append(args[nbtcount]);
                }
                itemstack.func_77982_d(JsonToNBT.func_180713_a(NBT.toString()));
            }
            return itemstack;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
