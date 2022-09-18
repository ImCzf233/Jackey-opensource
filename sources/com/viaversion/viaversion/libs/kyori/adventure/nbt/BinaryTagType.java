package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagType.class */
public abstract class BinaryTagType<T extends BinaryTag> implements Predicate<BinaryTagType<? extends BinaryTag>> {
    private static final List<BinaryTagType<? extends BinaryTag>> TYPES = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagType$Reader.class */
    public interface Reader<T extends BinaryTag> {
        @NotNull
        T read(@NotNull final DataInput input) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagType$Writer.class */
    public interface Writer<T extends BinaryTag> {
        void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException;
    }

    /* renamed from: id */
    public abstract byte mo138id();

    public abstract boolean numeric();

    @NotNull
    public abstract T read(@NotNull final DataInput input) throws IOException;

    public abstract void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException;

    public static <T extends BinaryTag> void write(final BinaryTagType<? extends BinaryTag> type, final T tag, final DataOutput output) throws IOException {
        type.write(tag, output);
    }

    @NotNull
    /* renamed from: of */
    public static BinaryTagType<? extends BinaryTag> m139of(final byte id) {
        for (int i = 0; i < TYPES.size(); i++) {
            BinaryTagType<? extends BinaryTag> type = TYPES.get(i);
            if (type.mo138id() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.valueOf((int) id));
    }

    @NotNull
    public static <T extends BinaryTag> BinaryTagType<T> register(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
        return register(new Impl(type, id, reader, writer));
    }

    @NotNull
    public static <T extends NumberBinaryTag> BinaryTagType<T> registerNumeric(final Class<T> type, final byte id, final Reader<T> reader, final Writer<T> writer) {
        return register(new Impl.Numeric(type, id, reader, writer));
    }

    private static <T extends BinaryTag, Y extends BinaryTagType<T>> Y register(final Y type) {
        TYPES.add(type);
        return type;
    }

    public boolean test(final BinaryTagType<? extends BinaryTag> that) {
        return this == that || (numeric() && that.numeric());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagType$Impl.class */
    public static class Impl<T extends BinaryTag> extends BinaryTagType<T> {
        final Class<T> type;

        /* renamed from: id */
        final byte f178id;
        private final Reader<T> reader;
        @Nullable
        private final Writer<T> writer;

        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType, java.util.function.Predicate
        public /* bridge */ /* synthetic */ boolean test(final BinaryTagType<? extends BinaryTag> that) {
            return super.test(that);
        }

        Impl(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
            this.type = type;
            this.f178id = id;
            this.reader = reader;
            this.writer = writer;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType
        @NotNull
        public final T read(@NotNull final DataInput input) throws IOException {
            return this.reader.read(input);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType
        public final void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException {
            if (this.writer != null) {
                this.writer.write(tag, output);
            }
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType
        /* renamed from: id */
        public final byte mo138id() {
            return this.f178id;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType
        public boolean numeric() {
            return false;
        }

        public String toString() {
            return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + ((int) this.f178id) + "]";
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/BinaryTagType$Impl$Numeric.class */
        public static class Numeric<T extends BinaryTag> extends Impl<T> {
            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType.Impl, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType, java.util.function.Predicate
            public /* bridge */ /* synthetic */ boolean test(final BinaryTagType<? extends BinaryTag> that) {
                return super.test(that);
            }

            Numeric(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
                super(type, id, reader, writer);
            }

            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType.Impl, com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType
            boolean numeric() {
                return true;
            }

            @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType.Impl
            public String toString() {
                return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + ((int) this.f178id) + " (numeric)]";
            }
        }
    }
}
