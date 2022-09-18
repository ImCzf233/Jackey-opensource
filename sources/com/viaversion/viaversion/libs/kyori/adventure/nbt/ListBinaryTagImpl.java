package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "\"ListBinaryTag[type=\" + this.type.toString() + \"]\"", childrenArray = "this.tags.toArray()", hasChildren = "!this.tags.isEmpty()")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ListBinaryTagImpl.class */
public final class ListBinaryTagImpl extends AbstractBinaryTag implements ListBinaryTag {
    static final ListBinaryTag EMPTY = new ListBinaryTagImpl(BinaryTagTypes.END, Collections.emptyList());
    private final List<BinaryTag> tags;
    private final BinaryTagType<? extends BinaryTag> elementType;
    private final int hashCode;

    public ListBinaryTagImpl(final BinaryTagType<? extends BinaryTag> elementType, final List<BinaryTag> tags) {
        this.tags = Collections.unmodifiableList(tags);
        this.elementType = elementType;
        this.hashCode = tags.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    @NotNull
    public BinaryTagType<? extends BinaryTag> elementType() {
        return this.elementType;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    public int size() {
        return this.tags.size();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    @NotNull
    public BinaryTag get(final int index) {
        return this.tags.get(index);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    @NotNull
    public ListBinaryTag set(final int index, @NotNull final BinaryTag newTag, @Nullable final Consumer<? super BinaryTag> removed) {
        return edit(tags -> {
            BinaryTag oldTag = (BinaryTag) tags.set(index, newTag);
            if (removed != null) {
                removed.accept(oldTag);
            }
        }, newTag.type());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    @NotNull
    public ListBinaryTag remove(final int index, @Nullable final Consumer<? super BinaryTag> removed) {
        return edit(tags -> {
            BinaryTag oldTag = (BinaryTag) tags.remove(index);
            if (removed != null) {
                removed.accept(oldTag);
            }
        }, null);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagSetter
    @NotNull
    public ListBinaryTag add(final BinaryTag tag) {
        noAddEnd(tag);
        if (this.elementType != BinaryTagTypes.END) {
            mustBeSameType(tag, this.elementType);
        }
        return edit(tags -> {
            tags.add(tag);
        }, tag.type());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagSetter
    @NotNull
    public ListBinaryTag add(final Iterable<? extends BinaryTag> tagsToAdd) {
        if ((tagsToAdd instanceof Collection) && ((Collection) tagsToAdd).isEmpty()) {
            return this;
        }
        BinaryTagType<?> type = mustBeSameType(tagsToAdd);
        return edit(tags -> {
            Iterator it = tagsToAdd.iterator();
            while (it.hasNext()) {
                BinaryTag tag = (BinaryTag) it.next();
                tags.add(tag);
            }
        }, type);
    }

    public static void noAddEnd(final BinaryTag tag) {
        if (tag.type() == BinaryTagTypes.END) {
            throw new IllegalArgumentException(String.format("Cannot add a %s to a %s", BinaryTagTypes.END, BinaryTagTypes.LIST));
        }
    }

    static BinaryTagType<?> mustBeSameType(final Iterable<? extends BinaryTag> tags) {
        BinaryTagType<?> type = null;
        for (BinaryTag tag : tags) {
            if (type == null) {
                type = tag.type();
            } else {
                mustBeSameType(tag, type);
            }
        }
        return type;
    }

    public static void mustBeSameType(final BinaryTag tag, final BinaryTagType<? extends BinaryTag> type) {
        if (tag.type() != type) {
            throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", tag.type(), type));
        }
    }

    private ListBinaryTag edit(final Consumer<List<BinaryTag>> consumer, @Nullable final BinaryTagType<? extends BinaryTag> maybeElementType) {
        List<BinaryTag> tags = new ArrayList<>(this.tags);
        consumer.accept(tags);
        BinaryTagType<? extends BinaryTag> elementType = this.elementType;
        if (maybeElementType != null && elementType == BinaryTagTypes.END) {
            elementType = maybeElementType;
        }
        return new ListBinaryTagImpl(elementType, tags);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag
    @NotNull
    public Stream<BinaryTag> stream() {
        return this.tags.stream();
    }

    @Override // java.lang.Iterable
    public Iterator<BinaryTag> iterator() {
        final Iterator<BinaryTag> iterator = this.tags.iterator();
        return new Iterator<BinaryTag>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTagImpl.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override // java.util.Iterator
            public BinaryTag next() {
                return (BinaryTag) iterator.next();
            }

            @Override // java.util.Iterator
            public void forEachRemaining(final Consumer<? super BinaryTag> action) {
                iterator.forEachRemaining(action);
            }
        };
    }

    @Override // java.lang.Iterable
    public void forEach(final Consumer<? super BinaryTag> action) {
        this.tags.forEach(action);
    }

    @Override // java.lang.Iterable
    public Spliterator<BinaryTag> spliterator() {
        return Spliterators.spliterator(this.tags, 1040);
    }

    public boolean equals(final Object that) {
        return this == that || ((that instanceof ListBinaryTagImpl) && this.tags.equals(((ListBinaryTagImpl) that).tags));
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("tags", this.tags), ExaminableProperty.m91of("type", this.elementType)});
    }
}
