package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/providers/BukkitBlockConnectionProvider.class */
public class BukkitBlockConnectionProvider extends BlockConnectionProvider {
    private Chunk lastChunk;

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public int getWorldBlockData(UserConnection user, int bx, int by, int bz) {
        UUID uuid = user.getProtocolInfo().getUuid();
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            World world = player.getWorld();
            int x = bx >> 4;
            int z = bz >> 4;
            if (world.isChunkLoaded(x, z)) {
                Chunk c = getChunk(world, x, z);
                Block b = c.getBlock(bx, by, bz);
                return (b.getTypeId() << 4) | b.getData();
            }
            return 0;
        }
        return 0;
    }

    public Chunk getChunk(World world, int x, int z) {
        if (this.lastChunk != null && this.lastChunk.getX() == x && this.lastChunk.getZ() == z) {
            return this.lastChunk;
        }
        Chunk chunkAt = world.getChunkAt(x, z);
        this.lastChunk = chunkAt;
        return chunkAt;
    }
}
