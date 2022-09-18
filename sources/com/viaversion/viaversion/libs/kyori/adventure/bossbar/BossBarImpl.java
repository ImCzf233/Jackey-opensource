package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/bossbar/BossBarImpl.class */
public final class BossBarImpl extends HackyBossBarPlatformBridge implements BossBar {
    private static final BiConsumer<BossBarImpl, Set<BossBar.Flag>> FLAGS_ADDED = bar, flagsAdded -> {
        bar.forEachListener(listener -> {
            listener.bossBarFlagsChanged(bar, flagsAdded, Collections.emptySet());
        });
    };
    private static final BiConsumer<BossBarImpl, Set<BossBar.Flag>> FLAGS_REMOVED = bar, flagsRemoved -> {
        bar.forEachListener(listener -> {
            listener.bossBarFlagsChanged(bar, Collections.emptySet(), flagsRemoved);
        });
    };
    private final List<BossBar.Listener> listeners;
    private Component name;
    private float progress;
    private BossBar.Color color;
    private BossBar.Overlay overlay;
    private final Set<BossBar.Flag> flags;

    public BossBarImpl(@NotNull final Component name, final float progress, @NotNull final BossBar.Color color, @NotNull final BossBar.Overlay overlay) {
        this.listeners = new CopyOnWriteArrayList();
        this.flags = EnumSet.noneOf(BossBar.Flag.class);
        this.name = (Component) Objects.requireNonNull(name, "name");
        this.progress = progress;
        this.color = (BossBar.Color) Objects.requireNonNull(color, "color");
        this.overlay = (BossBar.Overlay) Objects.requireNonNull(overlay, "overlay");
    }

    public BossBarImpl(@NotNull final Component name, final float progress, @NotNull final BossBar.Color color, @NotNull final BossBar.Overlay overlay, @NotNull final Set<BossBar.Flag> flags) {
        this(name, progress, color, overlay);
        this.flags.addAll(flags);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public Component name() {
        return this.name;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar name(@NotNull final Component newName) {
        Objects.requireNonNull(newName, "name");
        Component oldName = this.name;
        if (!Objects.equals(newName, oldName)) {
            this.name = newName;
            forEachListener(listener -> {
                newName.bossBarNameChanged(this, oldName, oldName);
            });
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    public float progress() {
        return this.progress;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar progress(final float newProgress) {
        checkProgress(newProgress);
        float oldProgress = this.progress;
        if (newProgress != oldProgress) {
            this.progress = newProgress;
            forEachListener(listener -> {
                newProgress.bossBarProgressChanged(this, oldProgress, oldProgress);
            });
        }
        return this;
    }

    public static void checkProgress(final float progress) {
        if (progress < 0.0f || progress > 1.0f) {
            throw new IllegalArgumentException("progress must be between 0.0 and 1.0, was " + progress);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar.Color color() {
        return this.color;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar color(@NotNull final BossBar.Color newColor) {
        Objects.requireNonNull(newColor, "color");
        BossBar.Color oldColor = this.color;
        if (newColor != oldColor) {
            this.color = newColor;
            forEachListener(listener -> {
                newColor.bossBarColorChanged(this, oldColor, oldColor);
            });
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar.Overlay overlay() {
        return this.overlay;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar overlay(@NotNull final BossBar.Overlay newOverlay) {
        Objects.requireNonNull(newOverlay, "overlay");
        BossBar.Overlay oldOverlay = this.overlay;
        if (newOverlay != oldOverlay) {
            this.overlay = newOverlay;
            forEachListener(listener -> {
                newOverlay.bossBarOverlayChanged(this, oldOverlay, oldOverlay);
            });
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public Set<BossBar.Flag> flags() {
        return Collections.unmodifiableSet(this.flags);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar flags(@NotNull final Set<BossBar.Flag> newFlags) {
        if (newFlags.isEmpty()) {
            Set<BossBar.Flag> oldFlags = EnumSet.copyOf((Collection) this.flags);
            this.flags.clear();
            forEachListener(listener -> {
                oldFlags.bossBarFlagsChanged(this, Collections.emptySet(), oldFlags);
            });
        } else if (!this.flags.equals(newFlags)) {
            Set<BossBar.Flag> oldFlags2 = EnumSet.copyOf((Collection) this.flags);
            this.flags.clear();
            this.flags.addAll(newFlags);
            Set<BossBar.Flag> added = EnumSet.copyOf((Collection) newFlags);
            Objects.requireNonNull(oldFlags2);
            added.removeIf((v1) -> {
                return r1.contains(v1);
            });
            Set<BossBar.Flag> removed = EnumSet.copyOf((Collection) oldFlags2);
            Set<BossBar.Flag> set = this.flags;
            Objects.requireNonNull(set);
            removed.removeIf((v1) -> {
                return r1.contains(v1);
            });
            forEachListener(listener2 -> {
                removed.bossBarFlagsChanged(this, added, added);
            });
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    public boolean hasFlag(@NotNull final BossBar.Flag flag) {
        return this.flags.contains(flag);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar addFlag(@NotNull final BossBar.Flag flag) {
        return editFlags(flag, (v0, v1) -> {
            return v0.add(v1);
        }, FLAGS_ADDED);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar removeFlag(@NotNull final BossBar.Flag flag) {
        return editFlags(flag, (v0, v1) -> {
            return v0.remove(v1);
        }, FLAGS_REMOVED);
    }

    @NotNull
    private BossBar editFlags(@NotNull final BossBar.Flag flag, @NotNull final BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, final BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
        if (predicate.test(this.flags, flag)) {
            onChange.accept(this, Collections.singleton(flag));
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar addFlags(@NotNull final BossBar.Flag... flags) {
        return editFlags(flags, (v0, v1) -> {
            return v0.add(v1);
        }, FLAGS_ADDED);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar removeFlags(@NotNull final BossBar.Flag... flags) {
        return editFlags(flags, (v0, v1) -> {
            return v0.remove(v1);
        }, FLAGS_REMOVED);
    }

    @NotNull
    private BossBar editFlags(final BossBar.Flag[] flags, final BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, final BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
        if (flags.length == 0) {
            return this;
        }
        Set<BossBar.Flag> changes = null;
        int length = flags.length;
        for (int i = 0; i < length; i++) {
            if (predicate.test(this.flags, flags[i])) {
                if (changes == null) {
                    changes = EnumSet.noneOf(BossBar.Flag.class);
                }
                changes.add(flags[i]);
            }
        }
        if (changes != null) {
            onChange.accept(this, changes);
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar addFlags(@NotNull final Iterable<BossBar.Flag> flags) {
        return editFlags(flags, (v0, v1) -> {
            return v0.add(v1);
        }, FLAGS_ADDED);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar removeFlags(@NotNull final Iterable<BossBar.Flag> flags) {
        return editFlags(flags, (v0, v1) -> {
            return v0.remove(v1);
        }, FLAGS_REMOVED);
    }

    @NotNull
    private BossBar editFlags(final Iterable<BossBar.Flag> flags, final BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, final BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
        Set<BossBar.Flag> changes = null;
        for (BossBar.Flag flag : flags) {
            if (predicate.test(this.flags, flag)) {
                if (changes == null) {
                    changes = EnumSet.noneOf(BossBar.Flag.class);
                }
                changes.add(flag);
            }
        }
        if (changes != null) {
            onChange.accept(this, changes);
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar addListener(@NotNull final BossBar.Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar
    @NotNull
    public BossBar removeListener(@NotNull final BossBar.Listener listener) {
        this.listeners.remove(listener);
        return this;
    }

    public void forEachListener(@NotNull final Consumer<BossBar.Listener> consumer) {
        for (BossBar.Listener listener : this.listeners) {
            consumer.accept(listener);
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("name", this.name), ExaminableProperty.m94of("progress", this.progress), ExaminableProperty.m91of("color", this.color), ExaminableProperty.m91of("overlay", this.overlay), ExaminableProperty.m91of("flags", this.flags)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
