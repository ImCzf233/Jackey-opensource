package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

/* compiled from: AutoPlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay$onPacket$3.class */
final class AutoPlay$onPacket$3 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ItemStack $item;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoPlay$onPacket$3(ItemStack $item) {
        super(0);
        this.$item = $item;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Minecraft minecraft;
        Minecraft minecraft2;
        minecraft = MinecraftInstance.f362mc;
        minecraft.func_147114_u().func_147297_a(new C09PacketHeldItemChange(7));
        ItemStack itemStack = this.$item;
        int i = 0;
        while (i < 2) {
            i++;
            minecraft2 = MinecraftInstance.f362mc;
            minecraft2.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(itemStack));
        }
    }
}
