package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/AttackEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "targetEntity", "Lnet/minecraft/entity/Entity;", "(Lnet/minecraft/entity/Entity;)V", "getTargetEntity", "()Lnet/minecraft/entity/Entity;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/AttackEvent.class */
public final class AttackEvent extends Event {
    @Nullable
    private final Entity targetEntity;

    public AttackEvent(@Nullable Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    @Nullable
    public final Entity getTargetEntity() {
        return this.targetEntity;
    }
}
