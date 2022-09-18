package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/Examinable.class */
public interface Examinable {
    @NotNull
    default String examinableName() {
        return getClass().getSimpleName();
    }

    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.empty();
    }

    @NotNull
    default <R> R examine(@NotNull final Examiner<R> examiner) {
        return examiner.examine(this);
    }
}
