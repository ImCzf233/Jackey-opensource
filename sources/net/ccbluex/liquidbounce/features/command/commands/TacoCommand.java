package net.ccbluex.liquidbounce.features.command.commands;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* compiled from: TacoCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u0007\n��\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\u0002\u0018��2\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u001a2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n��R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001c"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TacoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "image", "", "running", "", "tacoTextures", "", "Lnet/minecraft/util/ResourceLocation;", "[Lnet/minecraft/util/ResourceLocation;", "toggle", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/TacoCommand.class */
public final class TacoCommand extends Command implements Listenable {
    private boolean toggle;
    private int image;
    private float running;
    @NotNull
    private final ResourceLocation[] tacoTextures = {new ResourceLocation("liquidbounce+/taco/1.png"), new ResourceLocation("liquidbounce+/taco/2.png"), new ResourceLocation("liquidbounce+/taco/3.png"), new ResourceLocation("liquidbounce+/taco/4.png"), new ResourceLocation("liquidbounce+/taco/5.png"), new ResourceLocation("liquidbounce+/taco/6.png"), new ResourceLocation("liquidbounce+/taco/7.png"), new ResourceLocation("liquidbounce+/taco/8.png"), new ResourceLocation("liquidbounce+/taco/9.png"), new ResourceLocation("liquidbounce+/taco/10.png"), new ResourceLocation("liquidbounce+/taco/11.png"), new ResourceLocation("liquidbounce+/taco/12.png")};

    public TacoCommand() {
        super("taco", new String[0]);
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.toggle = !this.toggle;
        ClientUtils.displayChatMessage(this.toggle ? "§aTACO TACO TACO. :)" : "§cYou made the little taco sad! :(");
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.toggle) {
            return;
        }
        this.running += 0.15f * RenderUtils.deltaTime;
        ScaledResolution scaledResolution = new ScaledResolution(MinecraftInstance.f362mc);
        RenderUtils.drawImage(this.tacoTextures[this.image], (int) this.running, scaledResolution.func_78328_b() - 60, 64, 32);
        if (scaledResolution.func_78326_a() <= this.running) {
            this.running = -64.0f;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.toggle) {
            this.image = 0;
            return;
        }
        this.image++;
        if (this.image >= this.tacoTextures.length) {
            this.image = 0;
        }
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return CollectionsKt.listOf("TACO");
    }
}
