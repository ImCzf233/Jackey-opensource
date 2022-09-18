package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/PointersImpl.class */
public final class PointersImpl implements Pointers {
    static final Pointers EMPTY = new Pointers() { // from class: com.viaversion.viaversion.libs.kyori.adventure.pointer.PointersImpl.1
        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers
        @NotNull
        public <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
            return Optional.empty();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers
        public <T> boolean supports(@NotNull final Pointer<T> pointer) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
        public Pointers.Builder toBuilder() {
            return new BuilderImpl();
        }

        public String toString() {
            return "EmptyPointers";
        }
    };
    private final Map<Pointer<?>, Supplier<?>> pointers;

    PointersImpl(@NotNull final BuilderImpl builder) {
        this.pointers = new HashMap(builder.pointers);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers
    @NotNull
    public <T> Optional<T> get(@NotNull final Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        Supplier<?> supplier = this.pointers.get(pointer);
        if (supplier == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(supplier.get());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers
    public <T> boolean supports(@NotNull final Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        return this.pointers.containsKey(pointer);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public Pointers.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/pointer/PointersImpl$BuilderImpl.class */
    public static final class BuilderImpl implements Pointers.Builder {
        private final Map<Pointer<?>, Supplier<?>> pointers;

        public BuilderImpl() {
            this.pointers = new HashMap();
        }

        BuilderImpl(@NotNull final PointersImpl pointers) {
            this.pointers = new HashMap(pointers.pointers);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers.Builder
        @NotNull
        public <T> Pointers.Builder withDynamic(@NotNull final Pointer<T> pointer, @NotNull final Supplier<T> value) {
            this.pointers.put((Pointer) Objects.requireNonNull(pointer, "pointer"), (Supplier) Objects.requireNonNull(value, "value"));
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public Pointers build() {
            return new PointersImpl(this);
        }
    }
}
