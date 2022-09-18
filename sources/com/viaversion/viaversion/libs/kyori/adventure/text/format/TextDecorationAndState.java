package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/TextDecorationAndState.class */
public interface TextDecorationAndState extends Examinable, StyleBuilderApplicable {
    @NotNull
    TextDecoration decoration();

    TextDecoration.State state();

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable
    default void styleApply(final Style.Builder style) {
        style.decoration(decoration(), state());
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("decoration", decoration()), ExaminableProperty.m91of("state", state())});
    }
}
