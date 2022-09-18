package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n��\n\u0002\u0010��\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "movedEntity", "Lnet/minecraft/entity/Entity;", "(Lnet/minecraft/entity/Entity;)V", "getMovedEntity", "()Lnet/minecraft/entity/Entity;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/EntityMovementEvent.class */
public final class EntityMovementEvent extends Event {
    @NotNull
    private final Entity movedEntity;

    @NotNull
    public final Entity component1() {
        return this.movedEntity;
    }

    @NotNull
    public final EntityMovementEvent copy(@NotNull Entity movedEntity) {
        Intrinsics.checkNotNullParameter(movedEntity, "movedEntity");
        return new EntityMovementEvent(movedEntity);
    }

    public static /* synthetic */ EntityMovementEvent copy$default(EntityMovementEvent entityMovementEvent, Entity entity, int i, Object obj) {
        if ((i & 1) != 0) {
            entity = entityMovementEvent.movedEntity;
        }
        return entityMovementEvent.copy(entity);
    }

    @NotNull
    public String toString() {
        return "EntityMovementEvent(movedEntity=" + this.movedEntity + ')';
    }

    public int hashCode() {
        return this.movedEntity.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof EntityMovementEvent) && Intrinsics.areEqual(this.movedEntity, ((EntityMovementEvent) other).movedEntity);
    }

    public EntityMovementEvent(@NotNull Entity movedEntity) {
        Intrinsics.checkNotNullParameter(movedEntity, "movedEntity");
        this.movedEntity = movedEntity;
    }

    @NotNull
    public final Entity getMovedEntity() {
        return this.movedEntity;
    }
}
