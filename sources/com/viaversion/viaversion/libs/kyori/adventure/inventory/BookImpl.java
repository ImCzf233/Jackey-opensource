package com.viaversion.viaversion.libs.kyori.adventure.inventory;

import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/inventory/BookImpl.class */
public final class BookImpl implements Book {
    private final Component title;
    private final Component author;
    private final List<Component> pages;

    public BookImpl(@NotNull final Component title, @NotNull final Component author, @NotNull final List<Component> pages) {
        this.title = (Component) Objects.requireNonNull(title, "title");
        this.author = (Component) Objects.requireNonNull(author, "author");
        this.pages = Collections.unmodifiableList((List) Objects.requireNonNull(pages, "pages"));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public Component title() {
        return this.title;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public Book title(@NotNull final Component title) {
        return new BookImpl((Component) Objects.requireNonNull(title, "title"), this.author, this.pages);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public Component author() {
        return this.author;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public Book author(@NotNull final Component author) {
        return new BookImpl(this.title, (Component) Objects.requireNonNull(author, "author"), this.pages);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public List<Component> pages() {
        return this.pages;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book
    @NotNull
    public Book pages(@NotNull final List<Component> pages) {
        return new BookImpl(this.title, this.author, new ArrayList((Collection) Objects.requireNonNull(pages, "pages")));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("title", this.title), ExaminableProperty.m91of("author", this.author), ExaminableProperty.m91of("pages", this.pages)});
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookImpl)) {
            return false;
        }
        BookImpl that = (BookImpl) o;
        return this.title.equals(that.title) && this.author.equals(that.author) && this.pages.equals(that.pages);
    }

    public int hashCode() {
        int result = this.title.hashCode();
        return (31 * ((31 * result) + this.author.hashCode())) + this.pages.hashCode();
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/inventory/BookImpl$BuilderImpl.class */
    public static final class BuilderImpl implements Book.Builder {
        private Component title = Component.empty();
        private Component author = Component.empty();
        private final List<Component> pages = new ArrayList();

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder
        @NotNull
        public Book.Builder title(@NotNull final Component title) {
            this.title = (Component) Objects.requireNonNull(title, "title");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder
        @NotNull
        public Book.Builder author(@NotNull final Component author) {
            this.author = (Component) Objects.requireNonNull(author, "author");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder
        @NotNull
        public Book.Builder addPage(@NotNull final Component page) {
            this.pages.add((Component) Objects.requireNonNull(page, "page"));
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder
        @NotNull
        public Book.Builder pages(@NotNull final Collection<Component> pages) {
            this.pages.addAll((Collection) Objects.requireNonNull(pages, "pages"));
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder
        @NotNull
        public Book.Builder pages(@NotNull final Component... pages) {
            Collections.addAll(this.pages, pages);
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.inventory.Book.Builder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public Book build() {
            return new BookImpl(this.title, this.author, new ArrayList(this.pages));
        }
    }
}
