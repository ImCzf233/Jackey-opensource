package net.ccbluex.liquidbounce.features.module.modules.color;

import net.ccbluex.liquidbounce.value.IntegerValue;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/color/ColorElement.class */
public class ColorElement extends IntegerValue {
    public ColorElement(int counter, Material m, IntegerValue basis) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255, () -> {
            return Boolean.valueOf(basis.get().intValue() >= counter);
        });
    }

    public ColorElement(int counter, Material m) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255);
    }

    public void onChanged(Integer oldValue, Integer newValue) {
        ColorMixer.regenerateColors(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/color/ColorElement$Material.class */
    public enum Material {
        RED("Red"),
        GREEN("Green"),
        BLUE("Blue");
        
        private final String colName;

        Material(String name) {
            this.colName = name;
        }

        public String getColorName() {
            return this.colName;
        }
    }
}
