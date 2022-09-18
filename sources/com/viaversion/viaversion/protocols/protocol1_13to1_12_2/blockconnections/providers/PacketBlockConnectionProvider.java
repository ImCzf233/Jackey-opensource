package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/providers/PacketBlockConnectionProvider.class */
public class PacketBlockConnectionProvider extends BlockConnectionProvider {
    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public void storeBlock(UserConnection connection, int x, int y, int z, int blockState) {
        ((BlockConnectionStorage) connection.get(BlockConnectionStorage.class)).store(x, y, z, blockState);
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public void removeBlock(UserConnection connection, int x, int y, int z) {
        ((BlockConnectionStorage) connection.get(BlockConnectionStorage.class)).remove(x, y, z);
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public int getBlockData(UserConnection connection, int x, int y, int z) {
        return ((BlockConnectionStorage) connection.get(BlockConnectionStorage.class)).get(x, y, z);
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public void clearStorage(UserConnection connection) {
        ((BlockConnectionStorage) connection.get(BlockConnectionStorage.class)).clear();
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public void unloadChunk(UserConnection connection, int x, int z) {
        ((BlockConnectionStorage) connection.get(BlockConnectionStorage.class)).unloadChunk(x, z);
    }

    @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider
    public boolean storesBlocks() {
        return true;
    }
}
