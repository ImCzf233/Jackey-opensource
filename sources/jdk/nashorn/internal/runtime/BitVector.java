package jdk.nashorn.internal.runtime;

import java.util.Arrays;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/BitVector.class */
public final class BitVector implements Cloneable {
    private static final int BITSPERSLOT = 64;
    private static final int SLOTSQUANTA = 4;
    private static final int BITSHIFT = 6;
    private static final int BITMASK = 63;
    private long[] bits;

    public BitVector() {
        this.bits = new long[4];
    }

    public BitVector(long length) {
        int need = (int) growthNeeded(length);
        this.bits = new long[need];
    }

    public BitVector(long[] bits) {
        this.bits = (long[]) bits.clone();
    }

    public void copy(BitVector other) {
        this.bits = (long[]) other.bits.clone();
    }

    private static long slotsNeeded(long length) {
        return (length + 63) >> 6;
    }

    private static long growthNeeded(long length) {
        return (((slotsNeeded(length) + 4) - 1) / 4) * 4;
    }

    private long slot(int index) {
        if (0 > index || index >= this.bits.length) {
            return 0L;
        }
        return this.bits[index];
    }

    public void resize(long length) {
        int need = (int) growthNeeded(length);
        if (this.bits.length != need) {
            this.bits = Arrays.copyOf(this.bits, need);
        }
        int shift = (int) (length & 63);
        int slot = (int) (length >> 6);
        if (shift != 0) {
            long[] jArr = this.bits;
            jArr[slot] = jArr[slot] & ((1 << shift) - 1);
            slot++;
        }
        while (slot < this.bits.length) {
            this.bits[slot] = 0;
            slot++;
        }
    }

    public void set(long bit) {
        long[] jArr = this.bits;
        int i = (int) (bit >> 6);
        jArr[i] = jArr[i] | (1 << ((int) (bit & 63)));
    }

    public void clear(long bit) {
        long[] jArr = this.bits;
        int i = (int) (bit >> 6);
        jArr[i] = jArr[i] & ((1 << ((int) (bit & 63))) ^ (-1));
    }

    public void toggle(long bit) {
        long[] jArr = this.bits;
        int i = (int) (bit >> 6);
        jArr[i] = jArr[i] ^ (1 << ((int) (bit & 63)));
    }

    public void setTo(long length) {
        if (0 < length) {
            int lastWord = (int) (length >> 6);
            long lastBits = (1 << ((int) (length & 63))) - 1;
            Arrays.fill(this.bits, 0, lastWord, -1L);
            if (lastBits != 0) {
                long[] jArr = this.bits;
                jArr[lastWord] = jArr[lastWord] | lastBits;
            }
        }
    }

    public void clearAll() {
        Arrays.fill(this.bits, 0L);
    }

    public boolean isSet(long bit) {
        return (this.bits[(int) (bit >> 6)] & (1 << ((int) (bit & 63)))) != 0;
    }

    public boolean isClear(long bit) {
        return (this.bits[(int) (bit >> 6)] & (1 << ((int) (bit & 63)))) == 0;
    }

    public void shiftLeft(long shift, long length) {
        if (shift != 0) {
            int leftShift = (int) (shift & 63);
            int rightShift = 64 - leftShift;
            int slotShift = (int) (shift >> 6);
            int slotCount = this.bits.length - slotShift;
            if (leftShift == 0) {
                int slot = 0;
                int from = slotShift;
                while (slot < slotCount) {
                    this.bits[slot] = slot(from);
                    slot++;
                    from++;
                }
            } else {
                int from2 = slotShift;
                for (int slot2 = 0; slot2 < slotCount; slot2++) {
                    from2++;
                    this.bits[slot2] = (slot(from2) >>> leftShift) | (slot(from2) << rightShift);
                }
            }
        }
        resize(length);
    }

    public void shiftRight(long shift, long length) {
        resize(length);
        if (shift != 0) {
            int rightShift = (int) (shift & 63);
            int leftShift = 64 - rightShift;
            int slotShift = (int) (shift >> 6);
            if (leftShift == 0) {
                int slot = this.bits.length;
                int from = slot - slotShift;
                while (slot >= slotShift) {
                    slot--;
                    from--;
                    this.bits[slot] = slot(from);
                }
            } else {
                int slot2 = this.bits.length;
                int from2 = slot2 - slotShift;
                while (slot2 > 0) {
                    slot2--;
                    from2--;
                    this.bits[slot2] = (slot(from2 - 1) >>> leftShift) | (slot(from2) << rightShift);
                }
            }
        }
        resize(length);
    }

    public void setRange(long fromIndex, long toIndex) {
        if (fromIndex < toIndex) {
            int firstWord = (int) (fromIndex >> 6);
            int lastWord = (int) ((toIndex - 1) >> 6);
            long firstBits = (-1) << ((int) fromIndex);
            long lastBits = (-1) >>> ((int) (-toIndex));
            if (firstWord == lastWord) {
                long[] jArr = this.bits;
                jArr[firstWord] = jArr[firstWord] | (firstBits & lastBits);
                return;
            }
            long[] jArr2 = this.bits;
            jArr2[firstWord] = jArr2[firstWord] | firstBits;
            Arrays.fill(this.bits, firstWord + 1, lastWord, -1L);
            long[] jArr3 = this.bits;
            jArr3[lastWord] = jArr3[lastWord] | lastBits;
        }
    }
}
