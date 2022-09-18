package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import java.util.List;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ServerList.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinServerList.class */
public abstract class MixinServerList {
    private static final Logger patcher$logger = LogManager.getLogger("Patcher/ServerList");
    @Shadow
    @Final
    private List<ServerData> field_78858_b;

    @Shadow
    public abstract void func_78855_b();

    @Overwrite
    public ServerData func_78850_a(int index) {
        try {
            return this.field_78858_b.get(index);
        } catch (Exception e) {
            patcher$logger.error("Failed to get server data.", e);
            return null;
        }
    }

    @Overwrite
    public void func_78851_b(int index) {
        try {
            this.field_78858_b.remove(index);
        } catch (Exception e) {
            patcher$logger.error("Failed to remove server data.", e);
        }
    }

    @Overwrite
    public void func_78849_a(ServerData server) {
        try {
            this.field_78858_b.add(server);
        } catch (Exception e) {
            patcher$logger.error("Failed to add server data.", e);
        }
    }

    @Overwrite
    public void func_78857_a(int p_78857_1_, int p_78857_2_) {
        try {
            ServerData serverdata = func_78850_a(p_78857_1_);
            this.field_78858_b.set(p_78857_1_, func_78850_a(p_78857_2_));
            this.field_78858_b.set(p_78857_2_, serverdata);
            func_78855_b();
        } catch (Exception e) {
            patcher$logger.error("Failed to swap servers.", e);
        }
    }

    @Overwrite
    public void func_147413_a(int index, ServerData server) {
        try {
            this.field_78858_b.set(index, server);
        } catch (Exception e) {
            patcher$logger.error("Failed to set server data.", e);
        }
    }
}
