package net.ccbluex.liquidbounce.features.module.modules.render;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "Animations", description = "Render items Animations", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Animations.class */
public class Animations extends Module {
    public static final ListValue Sword = new ListValue("Style", new String[]{"Normal", "SlideDown1", "SlideDown2", "Slide", "Minecraft", "Remix", "Avatar", "Tap1", "Tap2", "Poke", "Push1", "Push2", "Up", "Shield", "Akrien", "VisionFX", "Swong", "SigmaOld", "ETB", "Rotate360", "SmoothFloat", "Strange", "Reverse", "Zoom", "Move", "Stab", "Jello"}, "Minecraft");
    public static final FloatValue Scale = new FloatValue("Scale", 0.4f, 0.0f, 4.0f);
    public static final FloatValue itemPosX = new FloatValue("ItemX", 0.0f, -1.0f, 1.0f);
    public static final FloatValue itemPosY = new FloatValue("ItemY", 0.0f, -1.0f, 1.0f);
    public static final FloatValue itemPosZ = new FloatValue("ItemZ", 0.0f, -1.0f, 1.0f);
    public static final FloatValue blockPosX = new FloatValue("BlockingX", 0.0f, -1.0f, 1.0f);
    public static final FloatValue blockPosY = new FloatValue("BlockingY", 0.0f, -1.0f, 1.0f);
    public static final FloatValue blockPosZ = new FloatValue("BlockingZ", 0.0f, -1.0f, 1.0f);
    public static final IntegerValue SpeedSwing = new IntegerValue("Swing-Speed", 4, 0, 20);
    public static final BoolValue RotateItems = new BoolValue("Rotate-Items", false);
    public static final FloatValue SpeedRotate = new FloatValue("Rotate-Speed", 1.0f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(RotateItems.get().booleanValue() || Sword.get().equalsIgnoreCase("smoothfloat") || Sword.get().equalsIgnoreCase("rotate360"));
    });
    public static final ListValue transformFirstPersonRotate = new ListValue("RotateMode", new String[]{"RotateY", "RotateXY", "Custom", "None"}, "RotateY");
    public static final FloatValue customRotate1 = new FloatValue("RotateXAxis", 0.0f, -180.0f, 180.0f, () -> {
        return Boolean.valueOf(RotateItems.get().booleanValue() && transformFirstPersonRotate.get().equalsIgnoreCase("custom"));
    });
    public static final FloatValue customRotate2 = new FloatValue("RotateYAxis", 0.0f, -180.0f, 180.0f, () -> {
        return Boolean.valueOf(RotateItems.get().booleanValue() && transformFirstPersonRotate.get().equalsIgnoreCase("custom"));
    });
    public static final FloatValue customRotate3 = new FloatValue("RotateZAxis", 0.0f, -180.0f, 180.0f, () -> {
        return Boolean.valueOf(RotateItems.get().booleanValue() && transformFirstPersonRotate.get().equalsIgnoreCase("custom"));
    });
    public static final FloatValue mcSwordPos = new FloatValue("MCPosOffset", 0.45f, 0.0f, 0.5f, () -> {
        return Boolean.valueOf(Sword.get().equalsIgnoreCase(Key.MINECRAFT_NAMESPACE));
    });
    public static final BoolValue fakeBlock = new BoolValue("Fake-Block", false);
    public static final BoolValue blockEverything = new BoolValue("Block-Everything", false);
    public static final ListValue guiAnimations = new ListValue("Container-Animation", new String[]{"None", "Zoom", "VSlide", "HSlide", "HVSlide", "Smooth"}, "None");
    public static final ListValue tabAnimations = new ListValue("Tab-Animation", new String[]{"None", "Zoom", "Slide"}, "Zoom");
    public static final BoolValue noBlockParticles = new BoolValue("NoBlockParticles", false);
}
