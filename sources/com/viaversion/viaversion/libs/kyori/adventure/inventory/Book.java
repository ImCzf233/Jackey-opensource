package com.viaversion.viaversion.libs.kyori.adventure.inventory;

import com.viaversion.viaversion.libs.kyori.adventure.inventory.BookImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/inventory/Book.class */
public interface Book extends Buildable<Book, Builder>, Examinable {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/inventory/Book$Builder.class */
    public interface Builder extends Buildable.Builder<Book> {
        @Contract("_ -> this")
        @NotNull
        Builder title(@NotNull final Component title);

        @Contract("_ -> this")
        @NotNull
        Builder author(@NotNull final Component author);

        @Contract("_ -> this")
        @NotNull
        Builder addPage(@NotNull final Component page);

        @Contract("_ -> this")
        @NotNull
        Builder pages(@NotNull final Component... pages);

        @Contract("_ -> this")
        @NotNull
        Builder pages(@NotNull final Collection<Component> pages);

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        Book build();
    }

    @NotNull
    Component title();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book title(@NotNull final Component title);

    @NotNull
    Component author();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book author(@NotNull final Component author);

    @NotNull
    List<Component> pages();

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    Book pages(@NotNull final List<Component> pages);

    @NotNull
    static Book book(@NotNull final Component title, @NotNull final Component author, @NotNull final Collection<Component> pages) {
        return new BookImpl(title, author, new ArrayList(pages));
    }

    @NotNull
    static Book book(@NotNull final Component title, @NotNull final Component author, @NotNull final Component... pages) {
        return book(title, author, Arrays.asList(pages));
    }

    @NotNull
    static Builder builder() {
        return new BookImpl.BuilderImpl();
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    default Book pages(@NotNull final Component... pages) {
        return pages(Arrays.asList(pages));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    default Builder toBuilder() {
        return builder().title(title()).author(author()).pages(pages());
    }
}
