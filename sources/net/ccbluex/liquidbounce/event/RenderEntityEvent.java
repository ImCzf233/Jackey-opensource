package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\u0018��2\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0012¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/RenderEntityEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "entity", "Lnet/minecraft/entity/Entity;", "x", "", "y", "z", "entityYaw", "", "partialTicks", "(Lnet/minecraft/entity/Entity;DDDFF)V", "getEntity", "()Lnet/minecraft/entity/Entity;", "getEntityYaw", "()F", "getPartialTicks", "getX", "()D", "getY", "getZ", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/RenderEntityEvent.class */
public final class RenderEntityEvent extends Event {
    @NotNull
    private final Entity entity;

    /* renamed from: x */
    private final double f331x;

    /* renamed from: y */
    private final double f332y;

    /* renamed from: z */
    private final double f333z;
    private final float entityYaw;
    private final float partialTicks;

    @NotNull
    public final Entity getEntity() {
        return this.entity;
    }

    public final double getX() {
        return this.f331x;
    }

    public final double getY() {
        return this.f332y;
    }

    public final double getZ() {
        return this.f333z;
    }

    public final float getEntityYaw() {
        return this.entityYaw;
    }

    public RenderEntityEvent(@NotNull Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        this.entity = entity;
        this.f331x = x;
        this.f332y = y;
        this.f333z = z;
        this.entityYaw = entityYaw;
        this.partialTicks = partialTicks;
    }

    public final float getPartialTicks() {
        return this.partialTicks;
    }
}
