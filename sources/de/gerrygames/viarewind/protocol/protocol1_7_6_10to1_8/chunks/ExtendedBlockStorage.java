package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/chunks/ExtendedBlockStorage.class */
public class ExtendedBlockStorage {
    private int yBase;
    private NibbleArray blockMSBArray;
    private NibbleArray skylightArray;
    private byte[] blockLSBArray = new byte[4096];
    private NibbleArray blockMetadataArray = new NibbleArray(this.blockLSBArray.length);
    private NibbleArray blocklightArray = new NibbleArray(this.blockLSBArray.length);

    public ExtendedBlockStorage(int paramInt, boolean paramBoolean) {
        this.yBase = paramInt;
        if (paramBoolean) {
            this.skylightArray = new NibbleArray(this.blockLSBArray.length);
        }
    }

    public int getExtBlockMetadata(int paramInt1, int paramInt2, int paramInt3) {
        return this.blockMetadataArray.get(paramInt1, paramInt2, paramInt3);
    }

    public void setExtBlockMetadata(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.blockMetadataArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public int getYLocation() {
        return this.yBase;
    }

    public void setExtSkylightValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.skylightArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public int getExtSkylightValue(int paramInt1, int paramInt2, int paramInt3) {
        return this.skylightArray.get(paramInt1, paramInt2, paramInt3);
    }

    public void setExtBlocklightValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.blocklightArray.set(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public int getExtBlocklightValue(int paramInt1, int paramInt2, int paramInt3) {
        return this.blocklightArray.get(paramInt1, paramInt2, paramInt3);
    }

    public byte[] getBlockLSBArray() {
        return this.blockLSBArray;
    }

    public boolean isEmpty() {
        return this.blockMSBArray == null;
    }

    public void clearMSBArray() {
        this.blockMSBArray = null;
    }

    public NibbleArray getBlockMSBArray() {
        return this.blockMSBArray;
    }

    public NibbleArray getMetadataArray() {
        return this.blockMetadataArray;
    }

    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }

    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }

    public void setBlockLSBArray(byte[] paramArrayOfByte) {
        this.blockLSBArray = paramArrayOfByte;
    }

    public void setBlockMSBArray(NibbleArray paramNibbleArray) {
        this.blockMSBArray = paramNibbleArray;
    }

    public void setBlockMetadataArray(NibbleArray paramNibbleArray) {
        this.blockMetadataArray = paramNibbleArray;
    }

    public void setBlocklightArray(NibbleArray paramNibbleArray) {
        this.blocklightArray = paramNibbleArray;
    }

    public void setSkylightArray(NibbleArray paramNibbleArray) {
        this.skylightArray = paramNibbleArray;
    }

    public NibbleArray createBlockMSBArray() {
        this.blockMSBArray = new NibbleArray(this.blockLSBArray.length);
        return this.blockMSBArray;
    }
}
