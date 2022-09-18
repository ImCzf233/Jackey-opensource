package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacySoundRewriter.class */
public abstract class LegacySoundRewriter<T extends BackwardsProtocol> extends RewriterBase<T> {
    protected final Int2ObjectMap<SoundData> soundRewrites = new Int2ObjectOpenHashMap(64);

    public LegacySoundRewriter(T protocol) {
        super(protocol);
    }

    public SoundData added(int id, int replacement) {
        return added(id, replacement, -1.0f);
    }

    public SoundData added(int id, int replacement, float newPitch) {
        SoundData data = new SoundData(replacement, true, newPitch, true);
        this.soundRewrites.put(id, (int) data);
        return data;
    }

    public SoundData removed(int id) {
        SoundData data = new SoundData(-1, false, -1.0f, false);
        this.soundRewrites.put(id, (int) data);
        return data;
    }

    public int handleSounds(int soundId) {
        int newSoundId = soundId;
        SoundData data = this.soundRewrites.get(soundId);
        if (data != null) {
            return data.getReplacementSound();
        }
        ObjectIterator<Int2ObjectMap.Entry<SoundData>> it = this.soundRewrites.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<SoundData> entry = it.next();
            if (soundId > entry.getIntKey()) {
                if (entry.getValue().isAdded()) {
                    newSoundId--;
                } else {
                    newSoundId++;
                }
            }
        }
        return newSoundId;
    }

    public boolean hasPitch(int soundId) {
        SoundData data = this.soundRewrites.get(soundId);
        return data != null && data.isChangePitch();
    }

    public float handlePitch(int soundId) {
        SoundData data = this.soundRewrites.get(soundId);
        if (data != null) {
            return data.getNewPitch();
        }
        return 1.0f;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/LegacySoundRewriter$SoundData.class */
    public static final class SoundData {
        private final int replacementSound;
        private final boolean changePitch;
        private final float newPitch;
        private final boolean added;

        public SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
            this.replacementSound = replacementSound;
            this.changePitch = changePitch;
            this.newPitch = newPitch;
            this.added = added;
        }

        public int getReplacementSound() {
            return this.replacementSound;
        }

        public boolean isChangePitch() {
            return this.changePitch;
        }

        public float getNewPitch() {
            return this.newPitch;
        }

        public boolean isAdded() {
            return this.added;
        }
    }
}
