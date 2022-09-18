package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.jetbrains.annotations.NotNull;

/* compiled from: TNTESP.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/TNTESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"})
@ModuleInfo(name = "TNTESP", spacedName = "TNT ESP", description = "Allows you to see ignited TNT blocks through walls.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/TNTESP.class */
public final class TNTESP extends Module {
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable iterable = MinecraftInstance.f362mc.field_71441_e.field_72996_f;
        Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.loadedEntityList");
        Iterable $this$filterIsInstance$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (element$iv$iv instanceof EntityTNTPrimed) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$forEach$iv) {
            RenderUtils.drawEntityBox((EntityTNTPrimed) element$iv, Color.RED, false);
        }
    }
}
