package de.gerrygames.viarewind.api;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/api/ViaRewindConfig.class */
public interface ViaRewindConfig {

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/api/ViaRewindConfig$CooldownIndicator.class */
    public enum CooldownIndicator {
        TITLE,
        ACTION_BAR,
        BOSS_BAR,
        DISABLED
    }

    CooldownIndicator getCooldownIndicator();

    boolean isReplaceAdventureMode();

    boolean isReplaceParticles();

    int getMaxBookPages();

    int getMaxBookPageSize();
}
