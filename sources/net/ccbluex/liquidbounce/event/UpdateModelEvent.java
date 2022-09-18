package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/UpdateModelEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "model", "Lnet/minecraft/client/model/ModelPlayer;", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/model/ModelPlayer;)V", "getModel", "()Lnet/minecraft/client/model/ModelPlayer;", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayer;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/UpdateModelEvent.class */
public final class UpdateModelEvent extends Event {
    @NotNull
    private final EntityPlayer player;
    @NotNull
    private final ModelPlayer model;

    public UpdateModelEvent(@NotNull EntityPlayer player, @NotNull ModelPlayer model) {
        Intrinsics.checkNotNullParameter(player, "player");
        Intrinsics.checkNotNullParameter(model, "model");
        this.player = player;
        this.model = model;
    }

    @NotNull
    public final EntityPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final ModelPlayer getModel() {
        return this.model;
    }
}
