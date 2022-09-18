package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;

/* compiled from: AutoPlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay$onPacket$6$1.class */
final class AutoPlay$onPacket$6$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ClickEvent $clickEvent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoPlay$onPacket$6$1(ClickEvent $clickEvent) {
        super(0);
        this.$clickEvent = $clickEvent;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Minecraft minecraft;
        minecraft = MinecraftInstance.f362mc;
        minecraft.field_71439_g.func_71165_d(this.$clickEvent.func_150668_b());
    }
}
