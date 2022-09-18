package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemPhysics.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/ItemPhysics;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "itemWeight", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getItemWeight", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "LiquidBounce"})
@ModuleInfo(name = "ItemPhysics", spacedName = "Item Physics", description = "newton hits", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/ItemPhysics.class */
public final class ItemPhysics extends Module {
    @NotNull
    private final FloatValue itemWeight = new FloatValue("Weight", 0.5f, 0.0f, 1.0f, "x");

    @NotNull
    public final FloatValue getItemWeight() {
        return this.itemWeight;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return String.valueOf(this.itemWeight.get().floatValue());
    }
}
