package com.viaversion.viaversion.libs.kyori.examination;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/AbstractExaminer.class */
public abstract class AbstractExaminer<R> implements Examiner<R> {
    @NotNull
    protected abstract <E> R array(final E[] array, @NotNull final Stream<R> elements);

    @NotNull
    protected abstract <E> R collection(@NotNull final Collection<E> collection, @NotNull final Stream<R> elements);

    @NotNull
    protected abstract R examinable(@NotNull final String name, @NotNull final Stream<Map.Entry<String, R>> properties);

    @NotNull
    protected abstract <K, V> R map(@NotNull final Map<K, V> map, @NotNull final Stream<Map.Entry<R, R>> entries);

    @NotNull
    protected abstract R nil();

    @NotNull
    protected abstract R scalar(@NotNull final Object value);

    @NotNull
    protected abstract <T> R stream(@NotNull final Stream<T> stream);

    @NotNull
    protected abstract R stream(@NotNull final DoubleStream stream);

    @NotNull
    protected abstract R stream(@NotNull final IntStream stream);

    @NotNull
    protected abstract R stream(@NotNull final LongStream stream);

    @NotNull
    protected abstract R array(final int length, final IntFunction<R> value);

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(@Nullable final Object value) {
        if (value == null) {
            return nil();
        }
        if (value instanceof String) {
            return examine((String) value);
        }
        if (value instanceof Examinable) {
            return examine((Examinable) value);
        }
        if (value instanceof Collection) {
            return collection((Collection) value);
        }
        if (value instanceof Map) {
            return map((Map) value);
        }
        if (value.getClass().isArray()) {
            Class<?> type = value.getClass().getComponentType();
            if (type.isPrimitive()) {
                if (type == Boolean.TYPE) {
                    return examine((boolean[]) value);
                }
                if (type == Byte.TYPE) {
                    return examine((byte[]) value);
                }
                if (type == Character.TYPE) {
                    return examine((char[]) value);
                }
                if (type == Double.TYPE) {
                    return examine((double[]) value);
                }
                if (type == Float.TYPE) {
                    return examine((float[]) value);
                }
                if (type == Integer.TYPE) {
                    return examine((int[]) value);
                }
                if (type == Long.TYPE) {
                    return examine((long[]) value);
                }
                if (type == Short.TYPE) {
                    return examine((short[]) value);
                }
            }
            return array((Object[]) value);
        } else if (value instanceof Boolean) {
            return examine(((Boolean) value).booleanValue());
        } else {
            if (value instanceof Character) {
                return examine(((Character) value).charValue());
            }
            if (value instanceof Number) {
                if (value instanceof Byte) {
                    return examine(((Byte) value).byteValue());
                }
                if (value instanceof Double) {
                    return examine(((Double) value).doubleValue());
                }
                if (value instanceof Float) {
                    return examine(((Float) value).floatValue());
                }
                if (value instanceof Integer) {
                    return examine(((Integer) value).intValue());
                }
                if (value instanceof Long) {
                    return examine(((Long) value).longValue());
                }
                if (value instanceof Short) {
                    return examine(((Short) value).shortValue());
                }
            } else if (value instanceof BaseStream) {
                if (value instanceof Stream) {
                    return stream((Stream) value);
                }
                if (value instanceof DoubleStream) {
                    return stream((DoubleStream) value);
                }
                if (value instanceof IntStream) {
                    return stream((IntStream) value);
                }
                if (value instanceof LongStream) {
                    return stream((LongStream) value);
                }
            }
            return scalar(value);
        }
    }

    @NotNull
    private <E> R array(final E[] array) {
        return array(array, Arrays.stream(array).map(this::examine));
    }

    @NotNull
    private <E> R collection(@NotNull final Collection<E> collection) {
        return collection(collection, collection.stream().map(this::examine));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(@NotNull final String name, @NotNull final Stream<? extends ExaminableProperty> properties) {
        return examinable(name, properties.map(property -> {
            return new AbstractMap.SimpleImmutableEntry(property.name(), property.examine(this));
        }));
    }

    @NotNull
    private <K, V> R map(@NotNull final Map<K, V> map) {
        return map(map, map.entrySet().stream().map(entry -> {
            return new AbstractMap.SimpleImmutableEntry(examine(entry.getKey()), examine(entry.getValue()));
        }));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final boolean[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final byte[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final char[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final double[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final float[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final int[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final long[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examiner
    @NotNull
    public R examine(final short[] values) {
        return values == null ? nil() : array(values.length, index -> {
            return examine(values[values]);
        });
    }
}
