package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

/* compiled from: AutoPlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay$onPacket$7.class */
final class AutoPlay$onPacket$7 extends Lambda implements Functions<Unit> {
    final /* synthetic */ AutoPlay this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoPlay$onPacket$7(AutoPlay $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Minecraft minecraft;
        ListValue listValue;
        minecraft = MinecraftInstance.f362mc;
        EntityPlayerSP entityPlayerSP = minecraft.field_71439_g;
        listValue = this.this$0.bwModeValue;
        entityPlayerSP.func_71165_d(Intrinsics.stringPlus("/bw join ", listValue.get()));
    }
}
