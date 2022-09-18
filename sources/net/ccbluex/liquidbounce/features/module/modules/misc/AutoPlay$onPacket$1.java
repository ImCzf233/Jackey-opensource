package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

/* compiled from: AutoPlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay$onPacket$1.class */
final class AutoPlay$onPacket$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ItemStack $item;
    final /* synthetic */ AutoPlay this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoPlay$onPacket$1(ItemStack $item, AutoPlay $receiver) {
        super(0);
        this.$item = $item;
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Minecraft minecraft;
        Minecraft minecraft2;
        Minecraft minecraft3;
        Minecraft minecraft4;
        minecraft = MinecraftInstance.f362mc;
        minecraft.func_147114_u().func_147297_a(new C09PacketHeldItemChange(6));
        minecraft2 = MinecraftInstance.f362mc;
        minecraft2.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(this.$item));
        minecraft3 = MinecraftInstance.f362mc;
        NetHandlerPlayClient func_147114_u = minecraft3.func_147114_u();
        minecraft4 = MinecraftInstance.f362mc;
        func_147114_u.func_147297_a(new C09PacketHeldItemChange(minecraft4.field_71439_g.field_71071_by.field_70461_c));
        this.this$0.clickState = 2;
    }
}
