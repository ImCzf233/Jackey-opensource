package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.platform.providers.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/provider/TitleRenderProvider.class */
public abstract class TitleRenderProvider implements Provider {
    protected Map<UUID, Integer> fadeIn = new HashMap();
    protected Map<UUID, Integer> stay = new HashMap();
    protected Map<UUID, Integer> fadeOut = new HashMap();
    protected Map<UUID, String> titles = new HashMap();
    protected Map<UUID, String> subTitles = new HashMap();
    protected Map<UUID, AtomicInteger> times = new HashMap();

    public void setTimings(UUID uuid, int fadeIn, int stay, int fadeOut) {
        setFadeIn(uuid, fadeIn);
        setStay(uuid, stay);
        setFadeOut(uuid, fadeOut);
        AtomicInteger time = getTime(uuid);
        if (time.get() > 0) {
            time.set(getFadeIn(uuid) + getStay(uuid) + getFadeOut(uuid));
        }
    }

    public void reset(UUID uuid) {
        this.titles.remove(uuid);
        this.subTitles.remove(uuid);
        getTime(uuid).set(0);
        this.fadeIn.remove(uuid);
        this.stay.remove(uuid);
        this.fadeOut.remove(uuid);
    }

    public void setTitle(UUID uuid, String title) {
        this.titles.put(uuid, title);
        getTime(uuid).set(getFadeIn(uuid) + getStay(uuid) + getFadeOut(uuid));
    }

    public void setSubTitle(UUID uuid, String subTitle) {
        this.subTitles.put(uuid, subTitle);
    }

    public void clear(UUID uuid) {
        this.titles.remove(uuid);
        this.subTitles.remove(uuid);
        getTime(uuid).set(0);
    }

    public AtomicInteger getTime(UUID uuid) {
        return this.times.computeIfAbsent(uuid, key -> {
            return new AtomicInteger(0);
        });
    }

    public int getFadeIn(UUID uuid) {
        return this.fadeIn.getOrDefault(uuid, 10).intValue();
    }

    public int getStay(UUID uuid) {
        return this.stay.getOrDefault(uuid, 70).intValue();
    }

    public int getFadeOut(UUID uuid) {
        return this.fadeOut.getOrDefault(uuid, 20).intValue();
    }

    public void setFadeIn(UUID uuid, int fadeIn) {
        if (fadeIn < 0) {
            this.fadeIn.remove(uuid);
        } else {
            this.fadeIn.put(uuid, Integer.valueOf(fadeIn));
        }
    }

    public void setStay(UUID uuid, int stay) {
        if (stay < 0) {
            this.stay.remove(uuid);
        } else {
            this.stay.put(uuid, Integer.valueOf(stay));
        }
    }

    public void setFadeOut(UUID uuid, int fadeOut) {
        if (fadeOut < 0) {
            this.fadeOut.remove(uuid);
        } else {
            this.fadeOut.put(uuid, Integer.valueOf(fadeOut));
        }
    }
}
