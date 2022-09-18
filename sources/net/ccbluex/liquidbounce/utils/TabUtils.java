package net.ccbluex.liquidbounce.utils;

import net.minecraft.client.gui.GuiTextField;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/TabUtils.class */
public final class TabUtils {
    public static void tab(GuiTextField... textFields) {
        for (int i = 0; i < textFields.length; i++) {
            GuiTextField textField = textFields[i];
            if (textField.func_146206_l()) {
                textField.func_146195_b(false);
                int i2 = i + 1;
                if (i2 >= textFields.length) {
                    i2 = 0;
                }
                textFields[i2].func_146195_b(true);
                return;
            }
        }
    }
}
