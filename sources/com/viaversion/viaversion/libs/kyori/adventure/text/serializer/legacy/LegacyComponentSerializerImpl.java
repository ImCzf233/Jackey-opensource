package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl.class */
public final class LegacyComponentSerializerImpl implements LegacyComponentSerializer {
    static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
    static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");
    private static final TextDecoration[] DECORATIONS = TextDecoration.values();
    private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';
    private static final List<TextFormat> FORMATS;
    private static final String LEGACY_CHARS;
    private static final Optional<LegacyComponentSerializer.Provider> SERVICE;
    static final Consumer<LegacyComponentSerializer.Builder> BUILDER;
    private final char character;
    private final char hexCharacter;
    @Nullable
    private final TextReplacementConfig urlReplacementConfig;
    private final boolean hexColours;
    private final boolean useTerriblyStupidHexFormat;
    private final ComponentFlattener flattener;

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$FormatCodeType.class */
    public enum FormatCodeType {
        MOJANG_LEGACY,
        KYORI_HEX,
        BUNGEECORD_UNUSUAL_HEX
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$Reset.class */
    public enum Reset implements TextFormat {
        INSTANCE
    }

    static {
        Map<TextFormat, String> formats = new LinkedHashMap<>(22);
        formats.put(NamedTextColor.BLACK, "0");
        formats.put(NamedTextColor.DARK_BLUE, "1");
        formats.put(NamedTextColor.DARK_GREEN, "2");
        formats.put(NamedTextColor.DARK_AQUA, "3");
        formats.put(NamedTextColor.DARK_RED, "4");
        formats.put(NamedTextColor.DARK_PURPLE, "5");
        formats.put(NamedTextColor.GOLD, "6");
        formats.put(NamedTextColor.GRAY, "7");
        formats.put(NamedTextColor.DARK_GRAY, "8");
        formats.put(NamedTextColor.BLUE, "9");
        formats.put(NamedTextColor.GREEN, "a");
        formats.put(NamedTextColor.AQUA, "b");
        formats.put(NamedTextColor.RED, "c");
        formats.put(NamedTextColor.LIGHT_PURPLE, "d");
        formats.put(NamedTextColor.YELLOW, "e");
        formats.put(NamedTextColor.WHITE, "f");
        formats.put(TextDecoration.OBFUSCATED, "k");
        formats.put(TextDecoration.BOLD, "l");
        formats.put(TextDecoration.STRIKETHROUGH, "m");
        formats.put(TextDecoration.UNDERLINED, "n");
        formats.put(TextDecoration.ITALIC, "o");
        formats.put(Reset.INSTANCE, "r");
        FORMATS = Collections.unmodifiableList(new ArrayList(formats.keySet()));
        LEGACY_CHARS = String.join("", formats.values());
        if (FORMATS.size() != LEGACY_CHARS.length()) {
            throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
        }
        SERVICE = Services.service(LegacyComponentSerializer.Provider.class);
        BUILDER = (Consumer) SERVICE.map((v0) -> {
            return v0.legacy();
        }).orElseGet(() -> {
            return builder -> {
            };
        });
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$Instances.class */
    public static final class Instances {
        static final LegacyComponentSerializer SECTION = (LegacyComponentSerializer) LegacyComponentSerializerImpl.SERVICE.map((v0) -> {
            return v0.legacySection();
        }).orElseGet(() -> {
            return new LegacyComponentSerializerImpl((char) 167, '#', null, false, false, ComponentFlattener.basic());
        });
        static final LegacyComponentSerializer AMPERSAND = (LegacyComponentSerializer) LegacyComponentSerializerImpl.SERVICE.map((v0) -> {
            return v0.legacyAmpersand();
        }).orElseGet(() -> {
            return new LegacyComponentSerializerImpl('&', '#', null, false, false, ComponentFlattener.basic());
        });

        Instances() {
        }
    }

    LegacyComponentSerializerImpl(final char character, final char hexCharacter, @Nullable final TextReplacementConfig urlReplacementConfig, final boolean hexColours, final boolean useTerriblyStupidHexFormat, final ComponentFlattener flattener) {
        this.character = character;
        this.hexCharacter = hexCharacter;
        this.urlReplacementConfig = urlReplacementConfig;
        this.hexColours = hexColours;
        this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
        this.flattener = flattener;
    }

    @Nullable
    private FormatCodeType determineFormatType(final char legacy, final String input, final int pos) {
        if (pos >= 14) {
            int expectedCharacterPosition = pos - 14;
            int expectedIndicatorPosition = pos - 13;
            if (input.charAt(expectedCharacterPosition) == this.character && input.charAt(expectedIndicatorPosition) == 'x') {
                return FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
            }
        }
        if (legacy == this.hexCharacter && input.length() - pos >= 6) {
            return FormatCodeType.KYORI_HEX;
        }
        if (LEGACY_CHARS.indexOf(legacy) != -1) {
            return FormatCodeType.MOJANG_LEGACY;
        }
        return null;
    }

    @Nullable
    public static LegacyFormat legacyFormat(final char character) {
        int index = LEGACY_CHARS.indexOf(character);
        if (index != -1) {
            TextFormat format = FORMATS.get(index);
            if (format instanceof NamedTextColor) {
                return new LegacyFormat((NamedTextColor) format);
            }
            if (format instanceof TextDecoration) {
                return new LegacyFormat((TextDecoration) format);
            }
            if (format instanceof Reset) {
                return LegacyFormat.RESET;
            }
            return null;
        }
        return null;
    }

    @Nullable
    private DecodedFormat decodeTextFormat(final char legacy, final String input, final int pos) {
        FormatCodeType foundFormat = determineFormatType(legacy, input, pos);
        if (foundFormat == null) {
            return null;
        }
        if (foundFormat == FormatCodeType.KYORI_HEX) {
            TextColor parsed = tryParseHexColor(input.substring(pos, pos + 6));
            if (parsed != null) {
                return new DecodedFormat(foundFormat, parsed);
            }
            return null;
        } else if (foundFormat == FormatCodeType.MOJANG_LEGACY) {
            return new DecodedFormat(foundFormat, FORMATS.get(LEGACY_CHARS.indexOf(legacy)));
        } else {
            if (foundFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                StringBuilder foundHex = new StringBuilder(6);
                for (int i = pos - 1; i >= pos - 11; i -= 2) {
                    foundHex.append(input.charAt(i));
                }
                TextColor parsed2 = tryParseHexColor(foundHex.reverse().toString());
                if (parsed2 != null) {
                    return new DecodedFormat(foundFormat, parsed2);
                }
                return null;
            }
            return null;
        }
    }

    @Nullable
    private static TextColor tryParseHexColor(final String hexDigits) {
        try {
            int color = Integer.parseInt(hexDigits, 16);
            return TextColor.color(color);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isHexTextColor(final TextFormat format) {
        return (format instanceof TextColor) && !(format instanceof NamedTextColor);
    }

    public String toLegacyCode(TextFormat format) {
        char[] charArray;
        if (isHexTextColor(format)) {
            TextColor color = (TextColor) format;
            if (this.hexColours) {
                String hex = String.format("%06x", Integer.valueOf(color.value()));
                if (this.useTerriblyStupidHexFormat) {
                    StringBuilder legacy = new StringBuilder(String.valueOf('x'));
                    for (char character : hex.toCharArray()) {
                        legacy.append(this.character).append(character);
                    }
                    return legacy.toString();
                }
                return this.hexCharacter + hex;
            }
            format = NamedTextColor.nearestTo(color);
        }
        int index = FORMATS.indexOf(format);
        return Character.toString(LEGACY_CHARS.charAt(index));
    }

    private TextComponent extractUrl(final TextComponent component) {
        if (this.urlReplacementConfig == null) {
            return component;
        }
        Component newComponent = component.replaceText(this.urlReplacementConfig);
        return newComponent instanceof TextComponent ? (TextComponent) newComponent : Component.text().append(newComponent).build();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
    @NotNull
    public TextComponent deserialize(@NotNull final String input) {
        int next = input.lastIndexOf(this.character, input.length() - 2);
        if (next == -1) {
            return extractUrl(Component.text(input));
        }
        List<TextComponent> parts = new ArrayList<>();
        TextComponent.Builder current = null;
        boolean reset = false;
        int pos = input.length();
        do {
            DecodedFormat decoded = decodeTextFormat(input.charAt(next + 1), input, next + 2);
            if (decoded != null) {
                int from = next + (decoded.encodedFormat == FormatCodeType.KYORI_HEX ? 8 : 2);
                if (from != pos) {
                    if (current != null) {
                        if (reset) {
                            parts.add(current.build());
                            reset = false;
                            current = Component.text();
                        } else {
                            current = Component.text().append((Component) current.build());
                        }
                    } else {
                        current = Component.text();
                    }
                    current.content(input.substring(from, pos));
                } else if (current == null) {
                    current = Component.text();
                }
                if (!reset) {
                    reset = applyFormat(current, decoded.format);
                }
                if (decoded.encodedFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                    next -= 12;
                }
                pos = next;
            }
            next = input.lastIndexOf(this.character, next - 1);
        } while (next != -1);
        if (current != null) {
            parts.add((TextComponent) current.build());
        }
        String remaining = pos > 0 ? input.substring(0, pos) : "";
        if (parts.size() == 1 && remaining.isEmpty()) {
            return extractUrl(parts.get(0));
        }
        Collections.reverse(parts);
        return extractUrl(Component.text().content(remaining).append(parts).build());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer, com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer
    @NotNull
    public String serialize(@NotNull final Component component) {
        Cereal state = new Cereal();
        this.flattener.flatten(component, state);
        return state.toString();
    }

    private static boolean applyFormat(final TextComponent.Builder builder, @NotNull final TextFormat format) {
        if (format instanceof TextColor) {
            builder.colorIfAbsent((TextColor) format);
            return true;
        } else if (format instanceof TextDecoration) {
            builder.decoration((TextDecoration) format, TextDecoration.State.TRUE);
            return false;
        } else if (format instanceof Reset) {
            return true;
        } else {
            throw new IllegalArgumentException(String.format("unknown format '%s'", format.getClass()));
        }
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public LegacyComponentSerializer.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$Cereal.class */
    public final class Cereal implements FlattenerListener {

        /* renamed from: sb */
        private final StringBuilder f186sb;
        private final StyleState style;
        @Nullable
        private TextFormat lastWritten;
        private StyleState[] styles;
        private int head;

        private Cereal() {
            LegacyComponentSerializerImpl.this = this$0;
            this.f186sb = new StringBuilder();
            this.style = new StyleState();
            this.styles = new StyleState[8];
            this.head = -1;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener
        public void pushStyle(@NotNull final Style pushed) {
            int idx = this.head + 1;
            this.head = idx;
            if (idx >= this.styles.length) {
                this.styles = (StyleState[]) Arrays.copyOf(this.styles, this.styles.length * 2);
            }
            StyleState state = this.styles[idx];
            if (state == null) {
                StyleState[] styleStateArr = this.styles;
                StyleState styleState = new StyleState();
                state = styleState;
                styleStateArr[idx] = styleState;
            }
            if (idx > 0) {
                state.set(this.styles[idx - 1]);
            } else {
                state.clear();
            }
            state.apply(pushed);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener
        public void component(@NotNull final String text) {
            if (!text.isEmpty()) {
                if (this.head < 0) {
                    throw new IllegalStateException("No style has been pushed!");
                }
                this.styles[this.head].applyFormat();
                this.f186sb.append(text);
            }
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener
        public void popStyle(@NotNull final Style style) {
            int i = this.head;
            this.head = i - 1;
            if (i < 0) {
                throw new IllegalStateException("Tried to pop beyond what was pushed!");
            }
        }

        void append(@NotNull final TextFormat format) {
            if (this.lastWritten != format) {
                this.f186sb.append(LegacyComponentSerializerImpl.this.character).append(LegacyComponentSerializerImpl.this.toLegacyCode(format));
            }
            this.lastWritten = format;
        }

        public String toString() {
            return this.f186sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$Cereal$StyleState.class */
        public final class StyleState {
            @Nullable
            private TextColor color;
            private final Set<TextDecoration> decorations = EnumSet.noneOf(TextDecoration.class);
            private boolean needsReset;

            StyleState() {
                Cereal.this = this$1;
            }

            void set(@NotNull final StyleState that) {
                this.color = that.color;
                this.decorations.clear();
                this.decorations.addAll(that.decorations);
            }

            public void clear() {
                this.color = null;
                this.decorations.clear();
            }

            void apply(@NotNull final Style component) {
                TextColor color = component.color();
                if (color != null) {
                    this.color = color;
                }
                int length = LegacyComponentSerializerImpl.DECORATIONS.length;
                for (int i = 0; i < length; i++) {
                    TextDecoration decoration = LegacyComponentSerializerImpl.DECORATIONS[i];
                    switch (component.decoration(decoration)) {
                        case TRUE:
                            this.decorations.add(decoration);
                            break;
                        case FALSE:
                            if (this.decorations.remove(decoration)) {
                                this.needsReset = true;
                                break;
                            } else {
                                break;
                            }
                    }
                }
            }

            void applyFormat() {
                boolean colorChanged = this.color != Cereal.this.style.color;
                if (this.needsReset) {
                    if (!colorChanged) {
                        Cereal.this.append(Reset.INSTANCE);
                    }
                    this.needsReset = false;
                }
                if (!colorChanged && Cereal.this.lastWritten != Reset.INSTANCE) {
                    if (!this.decorations.containsAll(Cereal.this.style.decorations)) {
                        applyFullFormat();
                        return;
                    }
                    for (TextDecoration decoration : this.decorations) {
                        if (Cereal.this.style.decorations.add(decoration)) {
                            Cereal.this.append(decoration);
                        }
                    }
                    return;
                }
                applyFullFormat();
            }

            private void applyFullFormat() {
                if (this.color != null) {
                    Cereal.this.append(this.color);
                } else {
                    Cereal.this.append(Reset.INSTANCE);
                }
                Cereal.this.style.color = this.color;
                for (TextDecoration decoration : this.decorations) {
                    Cereal.this.append(decoration);
                }
                Cereal.this.style.decorations.clear();
                Cereal.this.style.decorations.addAll(this.decorations);
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$BuilderImpl.class */
    public static final class BuilderImpl implements LegacyComponentSerializer.Builder {
        private char character;
        private char hexCharacter;
        private TextReplacementConfig urlReplacementConfig;
        private boolean hexColours;
        private boolean useTerriblyStupidHexFormat;
        private ComponentFlattener flattener;

        public BuilderImpl() {
            this.character = (char) 167;
            this.hexCharacter = '#';
            this.urlReplacementConfig = null;
            this.hexColours = false;
            this.useTerriblyStupidHexFormat = false;
            this.flattener = ComponentFlattener.basic();
            LegacyComponentSerializerImpl.BUILDER.accept(this);
        }

        BuilderImpl(@NotNull final LegacyComponentSerializerImpl serializer) {
            this();
            this.character = serializer.character;
            this.hexCharacter = serializer.hexCharacter;
            this.urlReplacementConfig = serializer.urlReplacementConfig;
            this.hexColours = serializer.hexColours;
            this.useTerriblyStupidHexFormat = serializer.useTerriblyStupidHexFormat;
            this.flattener = serializer.flattener;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder character(final char legacyCharacter) {
            this.character = legacyCharacter;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder hexCharacter(final char legacyHexCharacter) {
            this.hexCharacter = legacyHexCharacter;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls() {
            return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, null);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@NotNull final Pattern pattern) {
            return extractUrls(pattern, null);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@Nullable final Style style) {
            return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder extractUrls(@NotNull final Pattern pattern, @Nullable final Style style) {
            Objects.requireNonNull(pattern, "pattern");
            this.urlReplacementConfig = TextReplacementConfig.builder().match(pattern).replacement(url -> {
                String clickUrl = url.content();
                if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN.matcher(clickUrl).find()) {
                    clickUrl = "http://" + clickUrl;
                }
                return (style == null ? url : url.style(style)).clickEvent(ClickEvent.openUrl(clickUrl));
            }).build();
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder hexColors() {
            this.hexColours = true;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
            this.useTerriblyStupidHexFormat = true;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder
        @NotNull
        public LegacyComponentSerializer.Builder flattener(@NotNull final ComponentFlattener flattener) {
            this.flattener = (ComponentFlattener) Objects.requireNonNull(flattener, "flattener");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.Builder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public LegacyComponentSerializer build() {
            return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyComponentSerializerImpl$DecodedFormat.class */
    public static final class DecodedFormat {
        final FormatCodeType encodedFormat;
        final TextFormat format;

        private DecodedFormat(final FormatCodeType encodedFormat, final TextFormat format) {
            if (format == null) {
                throw new IllegalStateException("No format found");
            }
            this.encodedFormat = encodedFormat;
            this.format = format;
        }
    }
}
