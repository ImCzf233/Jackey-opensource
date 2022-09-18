package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBar.class */
public interface BossBar extends Examinable {
    public static final float MIN_PROGRESS = 0.0f;
    public static final float MAX_PROGRESS = 1.0f;
    @ApiStatus.ScheduledForRemoval
    @Deprecated
    public static final float MIN_PERCENT = 0.0f;
    @ApiStatus.ScheduledForRemoval
    @Deprecated
    public static final float MAX_PERCENT = 1.0f;

    @NotNull
    Component name();

    @Contract("_ -> this")
    @NotNull
    BossBar name(@NotNull final Component name);

    float progress();

    @Contract("_ -> this")
    @NotNull
    BossBar progress(final float progress);

    @NotNull
    Color color();

    @Contract("_ -> this")
    @NotNull
    BossBar color(@NotNull final Color color);

    @NotNull
    Overlay overlay();

    @Contract("_ -> this")
    @NotNull
    BossBar overlay(@NotNull final Overlay overlay);

    @NotNull
    Set<Flag> flags();

    @Contract("_ -> this")
    @NotNull
    BossBar flags(@NotNull final Set<Flag> flags);

    boolean hasFlag(@NotNull final Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlag(@NotNull final Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlag(@NotNull final Flag flag);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull final Flag... flags);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull final Flag... flags);

    @Contract("_ -> this")
    @NotNull
    BossBar addFlags(@NotNull final Iterable<Flag> flags);

    @Contract("_ -> this")
    @NotNull
    BossBar removeFlags(@NotNull final Iterable<Flag> flags);

    @Contract("_ -> this")
    @NotNull
    BossBar addListener(@NotNull final Listener listener);

    @Contract("_ -> this")
    @NotNull
    BossBar removeListener(@NotNull final Listener listener);

    @NotNull
    static BossBar bossBar(@NotNull final ComponentLike name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay) {
        BossBarImpl.checkProgress(progress);
        return bossBar(name.asComponent(), progress, color, overlay);
    }

    @NotNull
    static BossBar bossBar(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay) {
        BossBarImpl.checkProgress(progress);
        return new BossBarImpl(name, progress, color, overlay);
    }

    @NotNull
    static BossBar bossBar(@NotNull final ComponentLike name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay, @NotNull final Set<Flag> flags) {
        BossBarImpl.checkProgress(progress);
        return bossBar(name.asComponent(), progress, color, overlay, flags);
    }

    @NotNull
    static BossBar bossBar(@NotNull final Component name, final float progress, @NotNull final Color color, @NotNull final Overlay overlay, @NotNull final Set<Flag> flags) {
        BossBarImpl.checkProgress(progress);
        return new BossBarImpl(name, progress, color, overlay, flags);
    }

    @Contract("_ -> this")
    @NotNull
    default BossBar name(@NotNull final ComponentLike name) {
        return name(name.asComponent());
    }

    @ApiStatus.ScheduledForRemoval
    @Deprecated
    default float percent() {
        return progress();
    }

    @Contract("_ -> this")
    @Deprecated
    @NotNull
    @ApiStatus.ScheduledForRemoval
    default BossBar percent(final float progress) {
        return progress(progress);
    }

    @ApiStatus.OverrideOnly
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBar$Listener.class */
    public interface Listener {
        default void bossBarNameChanged(@NotNull final BossBar bar, @NotNull final Component oldName, @NotNull final Component newName) {
        }

        default void bossBarProgressChanged(@NotNull final BossBar bar, final float oldProgress, final float newProgress) {
            bossBarPercentChanged(bar, oldProgress, newProgress);
        }

        @ApiStatus.ScheduledForRemoval
        @Deprecated
        default void bossBarPercentChanged(@NotNull final BossBar bar, final float oldProgress, final float newProgress) {
        }

        default void bossBarColorChanged(@NotNull final BossBar bar, @NotNull final Color oldColor, @NotNull final Color newColor) {
        }

        default void bossBarOverlayChanged(@NotNull final BossBar bar, @NotNull final Overlay oldOverlay, @NotNull final Overlay newOverlay) {
        }

        default void bossBarFlagsChanged(@NotNull final BossBar bar, @NotNull final Set<Flag> flagsAdded, @NotNull final Set<Flag> flagsRemoved) {
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBar$Color.class */
    public enum Color {
        PINK("pink"),
        BLUE("blue"),
        RED("red"),
        GREEN("green"),
        YELLOW("yellow"),
        PURPLE("purple"),
        WHITE("white");
        
        public static final Index<String, Color> NAMES = Index.create(Color.class, color -> {
            return color.name;
        });
        private final String name;

        Color(final String name) {
            this.name = name;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBar$Flag.class */
    public enum Flag {
        DARKEN_SCREEN("darken_screen"),
        PLAY_BOSS_MUSIC("play_boss_music"),
        CREATE_WORLD_FOG("create_world_fog");
        
        public static final Index<String, Flag> NAMES = Index.create(Flag.class, flag -> {
            return flag.name;
        });
        private final String name;

        Flag(final String name) {
            this.name = name;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBar$Overlay.class */
    public enum Overlay {
        PROGRESS("progress"),
        NOTCHED_6("notched_6"),
        NOTCHED_10("notched_10"),
        NOTCHED_12("notched_12"),
        NOTCHED_20("notched_20");
        
        public static final Index<String, Overlay> NAMES = Index.create(Overlay.class, overlay -> {
            return overlay.name;
        });
        private final String name;

        Overlay(final String name) {
            this.name = name;
        }
    }
}
