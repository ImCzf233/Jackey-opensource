package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* compiled from: Cape.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "styleValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getStyleValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "getCapeLocation", "Lnet/minecraft/util/ResourceLocation;", "value", "CapeStyle", "LiquidBounce"})
@ModuleInfo(name = "Cape", description = "LiquidBounce+ capes.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Cape.class */
public final class Cape extends Module {
    @NotNull
    private final ListValue styleValue = new ListValue("Style", new String[]{"Dark", "Light", "Special1", "Special2"}, "Dark");

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final ResourceLocation getCapeLocation(@NotNull String value) {
        ResourceLocation resourceLocation;
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            String upperCase = value.toUpperCase();
            Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
            resourceLocation = CapeStyle.valueOf(upperCase).getLocation();
        } catch (IllegalArgumentException e) {
            resourceLocation = CapeStyle.DARK.getLocation();
        }
        return resourceLocation;
    }

    /* compiled from: Cape.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape$CapeStyle;", "", "location", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILnet/minecraft/util/ResourceLocation;)V", "getLocation", "()Lnet/minecraft/util/ResourceLocation;", "DARK", "LIGHT", "SPECIAL1", "SPECIAL2", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Cape$CapeStyle.class */
    public enum CapeStyle {
        DARK(new ResourceLocation("liquidbounce+/cape/dark.png")),
        LIGHT(new ResourceLocation("liquidbounce+/cape/light.png")),
        SPECIAL1(new ResourceLocation("liquidbounce+/cape/special1.png")),
        SPECIAL2(new ResourceLocation("liquidbounce+/cape/special2.png"));
        
        @NotNull
        private final ResourceLocation location;

        CapeStyle(ResourceLocation location) {
            this.location = location;
        }

        @NotNull
        public final ResourceLocation getLocation() {
            return this.location;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.styleValue.get();
    }
}
