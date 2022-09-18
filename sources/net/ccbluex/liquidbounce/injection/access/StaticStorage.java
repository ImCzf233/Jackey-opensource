package net.ccbluex.liquidbounce.injection.access;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/access/StaticStorage.class */
public class StaticStorage {
    private static final EnumFacing[] facings = EnumFacing.values();
    private static final EnumChatFormatting[] chatFormatting = EnumChatFormatting.values();
    private static final EnumParticleTypes[] particleTypes = EnumParticleTypes.values();
    private static final EnumWorldBlockLayer[] worldBlockLayers = EnumWorldBlockLayer.values();

    public static EnumFacing[] facings() {
        return facings;
    }

    public static EnumChatFormatting[] chatFormatting() {
        return chatFormatting;
    }

    public static EnumParticleTypes[] particleTypes() {
        return particleTypes;
    }

    public static EnumWorldBlockLayer[] worldBlockLayers() {
        return worldBlockLayers;
    }
}
