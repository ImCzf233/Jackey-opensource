package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.log4j.spi.Configurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/string/StringExaminer.class */
public class StringExaminer extends AbstractExaminer<String> {
    private static final Function<String, String> DEFAULT_ESCAPER = string -> {
        return string.replace("\"", "\\\"").replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    };
    private static final Collector<CharSequence, ?, String> COMMA_CURLY = Collectors.joining(", ", "{", "}");
    private static final Collector<CharSequence, ?, String> COMMA_SQUARE = Collectors.joining(", ", "[", "]");
    private final Function<String, String> escaper;

    @NotNull
    public static StringExaminer simpleEscaping() {
        return Instances.SIMPLE_ESCAPING;
    }

    public StringExaminer(@NotNull final Function<String, String> escaper) {
        this.escaper = escaper;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <E> String array(final E[] array, @NotNull final Stream<String> elements) {
        return (String) elements.collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <E> String collection(@NotNull final Collection<E> collection, @NotNull final Stream<String> elements) {
        return (String) elements.collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String examinable(@NotNull final String name, @NotNull final Stream<Map.Entry<String, String>> properties) {
        return name + ((String) properties.map(property -> {
            return ((String) property.getKey()) + '=' + ((String) property.getValue());
        }).collect(COMMA_CURLY));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <K, V> String map(@NotNull final Map<K, V> map, @NotNull final Stream<Map.Entry<String, String>> entries) {
        return (String) entries.map(entry -> {
            return ((String) entry.getKey()) + '=' + ((String) entry.getValue());
        }).collect(COMMA_CURLY);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String nil() {
        return Configurator.NULL;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String scalar(@NotNull final Object value) {
        return String.valueOf(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final boolean value) {
        return String.valueOf(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final byte value) {
        return String.valueOf((int) value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final char value) {
        return Strings.wrapIn(this.escaper.apply(String.valueOf(value)), '\'');
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final double value) {
        return Strings.withSuffix(String.valueOf(value), 'd');
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final float value) {
        return Strings.withSuffix(String.valueOf(value), 'f');
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final int value) {
        return String.valueOf(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final long value) {
        return String.valueOf(value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(final short value) {
        return String.valueOf((int) value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <T> String stream(@NotNull final Stream<T> stream) {
        return (String) stream.map(this::examine).collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String stream(@NotNull final DoubleStream stream) {
        return (String) stream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String stream(@NotNull final IntStream stream) {
        return (String) stream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String stream(@NotNull final LongStream stream) {
        return (String) stream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public String examine(@Nullable final String value) {
        return value == null ? nil() : Strings.wrapIn(this.escaper.apply(value), '\"');
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public String array(final int length, final IntFunction<String> value) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < length; i++) {
            sb.append(value.apply(i));
            if (i + 1 < length) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/string/StringExaminer$Instances.class */
    public static final class Instances {
        static final StringExaminer SIMPLE_ESCAPING = new StringExaminer(StringExaminer.DEFAULT_ESCAPER);

        private Instances() {
        }
    }
}
