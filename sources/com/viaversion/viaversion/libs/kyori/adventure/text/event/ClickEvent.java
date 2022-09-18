package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/ClickEvent.class */
public final class ClickEvent implements Examinable, StyleBuilderApplicable {
    private final Action action;
    private final String value;

    @NotNull
    public static ClickEvent openUrl(@NotNull final String url) {
        return new ClickEvent(Action.OPEN_URL, url);
    }

    @NotNull
    public static ClickEvent openUrl(@NotNull final URL url) {
        return openUrl(url.toExternalForm());
    }

    @NotNull
    public static ClickEvent openFile(@NotNull final String file) {
        return new ClickEvent(Action.OPEN_FILE, file);
    }

    @NotNull
    public static ClickEvent runCommand(@NotNull final String command) {
        return new ClickEvent(Action.RUN_COMMAND, command);
    }

    @NotNull
    public static ClickEvent suggestCommand(@NotNull final String command) {
        return new ClickEvent(Action.SUGGEST_COMMAND, command);
    }

    @NotNull
    public static ClickEvent changePage(@NotNull final String page) {
        return new ClickEvent(Action.CHANGE_PAGE, page);
    }

    @NotNull
    public static ClickEvent changePage(final int page) {
        return changePage(String.valueOf(page));
    }

    @NotNull
    public static ClickEvent copyToClipboard(@NotNull final String text) {
        return new ClickEvent(Action.COPY_TO_CLIPBOARD, text);
    }

    @NotNull
    public static ClickEvent clickEvent(@NotNull final Action action, @NotNull final String value) {
        return new ClickEvent(action, value);
    }

    private ClickEvent(@NotNull final Action action, @NotNull final String value) {
        this.action = (Action) Objects.requireNonNull(action, "action");
        this.value = (String) Objects.requireNonNull(value, "value");
    }

    @NotNull
    public Action action() {
        return this.action;
    }

    @NotNull
    public String value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable
    public void styleApply(final Style.Builder style) {
        style.clickEvent(this);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ClickEvent that = (ClickEvent) other;
        return this.action == that.action && Objects.equals(this.value, that.value);
    }

    public int hashCode() {
        int result = this.action.hashCode();
        return (31 * result) + this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("action", this.action), ExaminableProperty.m90of("value", this.value)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/ClickEvent$Action.class */
    public enum Action {
        OPEN_URL("open_url", true),
        OPEN_FILE("open_file", false),
        RUN_COMMAND("run_command", true),
        SUGGEST_COMMAND("suggest_command", true),
        CHANGE_PAGE("change_page", true),
        COPY_TO_CLIPBOARD("copy_to_clipboard", true);
        
        public static final Index<String, Action> NAMES = Index.create(Action.class, constant -> {
            return constant.name;
        });
        private final String name;
        private final boolean readable;

        Action(@NotNull final String name, final boolean readable) {
            this.name = name;
            this.readable = readable;
        }

        public boolean readable() {
            return this.readable;
        }

        @Override // java.lang.Enum
        @NotNull
        public String toString() {
            return this.name;
        }
    }
}
