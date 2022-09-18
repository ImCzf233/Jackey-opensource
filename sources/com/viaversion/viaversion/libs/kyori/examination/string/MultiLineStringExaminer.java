package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/string/MultiLineStringExaminer.class */
public class MultiLineStringExaminer extends AbstractExaminer<Stream<String>> {
    private static final String INDENT_2 = "  ";
    private final StringExaminer examiner;

    @NotNull
    public static MultiLineStringExaminer simpleEscaping() {
        return Instances.SIMPLE_ESCAPING;
    }

    public MultiLineStringExaminer(@NotNull final StringExaminer examiner) {
        this.examiner = examiner;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <E> Stream<String> array(final E[] array, @NotNull final Stream<Stream<String>> elements) {
        return arrayLike(elements);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <E> Stream<String> collection(@NotNull final Collection<E> collection, @NotNull final Stream<Stream<String>> elements) {
        return arrayLike(elements);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> examinable(@NotNull final String name, @NotNull final Stream<Map.Entry<String, Stream<String>>> properties) {
        Stream<String> flattened = flatten(",", properties.map(entry -> {
            return association(examine((String) entry.getKey()), " = ", (Stream) entry.getValue());
        }));
        Stream<String> indented = indent(flattened);
        return enclose(indented, name + "{", "}");
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <K, V> Stream<String> map(@NotNull final Map<K, V> map, @NotNull final Stream<Map.Entry<Stream<String>, Stream<String>>> entries) {
        Stream<String> flattened = flatten(",", entries.map(entry -> {
            return association((Stream) entry.getKey(), " = ", (Stream) entry.getValue());
        }));
        Stream<String> indented = indent(flattened);
        return enclose(indented, "{", "}");
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> nil() {
        return Stream.of(this.examiner.nil());
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> scalar(@NotNull final Object value) {
        return Stream.of(this.examiner.scalar(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final boolean value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final byte value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final char value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final double value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final float value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final int value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final long value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(final short value) {
        return Stream.of(this.examiner.examine(value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> array(final int length, final IntFunction<Stream<String>> value) {
        Stream<Stream<String>> stream;
        if (length == 0) {
            stream = Stream.empty();
        } else {
            stream = IntStream.range(0, length).mapToObj(value);
        }
        return arrayLike(stream);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public <T> Stream<String> stream(@NotNull final Stream<T> stream) {
        return arrayLike(stream.map(this::examine));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> stream(@NotNull final DoubleStream stream) {
        return arrayLike(stream.mapToObj(this::examine));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> stream(@NotNull final IntStream stream) {
        return arrayLike(stream.mapToObj(this::examine));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer
    @NotNull
    public Stream<String> stream(@NotNull final LongStream stream) {
        return arrayLike(stream.mapToObj(this::examine));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public Stream<String> examine(@Nullable final String value) {
        return Stream.of(this.examiner.examine(value));
    }

    private Stream<String> arrayLike(final Stream<Stream<String>> streams) {
        Stream<String> flattened = flatten(",", streams);
        Stream<String> indented = indent(flattened);
        return enclose(indented, "[", "]");
    }

    private static Stream<String> enclose(final Stream<String> lines, final String open, final String close) {
        return enclose((List) lines.collect(Collectors.toList()), open, close);
    }

    private static Stream<String> enclose(final List<String> lines, final String open, final String close) {
        if (lines.isEmpty()) {
            return Stream.of(open + close);
        }
        return (Stream) Stream.of((Object[]) new Stream[]{Stream.of(open), indent(lines.stream()), Stream.of(close)}).reduce(Stream.empty(), Stream::concat);
    }

    private static Stream<String> flatten(final String delimiter, final Stream<Stream<String>> bumpy) {
        List<String> flat = new ArrayList<>();
        bumpy.forEachOrdered(lines -> {
            if (!flat.isEmpty()) {
                int last = flat.size() - 1;
                flat.set(last, ((String) flat.get(last)) + delimiter);
            }
            Objects.requireNonNull(flat);
            lines.forEachOrdered((v1) -> {
                r1.add(v1);
            });
        });
        return flat.stream();
    }

    public static Stream<String> association(final Stream<String> left, final String middle, final Stream<String> right) {
        return association((List) left.collect(Collectors.toList()), middle, (List) right.collect(Collectors.toList()));
    }

    private static Stream<String> association(final List<String> left, final String middle, final List<String> right) {
        int lefts = left.size();
        int rights = right.size();
        int height = Math.max(lefts, rights);
        int leftWidth = Strings.maxLength(left.stream());
        String leftPad = lefts < 2 ? "" : Strings.repeat(" ", leftWidth);
        String middlePad = lefts < 2 ? "" : Strings.repeat(" ", middle.length());
        List<String> result = new ArrayList<>(height);
        int i = 0;
        while (i < height) {
            String l = i < lefts ? Strings.padEnd(left.get(i), leftWidth, ' ') : leftPad;
            String m = i == 0 ? middle : middlePad;
            String r = i < rights ? right.get(i) : "";
            result.add(l + m + r);
            i++;
        }
        return result.stream();
    }

    private static Stream<String> indent(final Stream<String> lines) {
        return lines.map(line -> {
            return INDENT_2 + line;
        });
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/string/MultiLineStringExaminer$Instances.class */
    private static final class Instances {
        static final MultiLineStringExaminer SIMPLE_ESCAPING = new MultiLineStringExaminer(StringExaminer.simpleEscaping());

        private Instances() {
        }
    }
}
