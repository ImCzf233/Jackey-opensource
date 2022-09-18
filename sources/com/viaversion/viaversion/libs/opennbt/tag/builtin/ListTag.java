package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/opennbt/tag/builtin/ListTag.class */
public class ListTag extends Tag implements Iterable<Tag> {

    /* renamed from: ID */
    public static final int f197ID = 9;
    private final List<Tag> value;
    private Class<? extends Tag> type;

    public ListTag() {
        this.value = new ArrayList();
    }

    public ListTag(@Nullable Class<? extends Tag> type) {
        this.type = type;
        this.value = new ArrayList();
    }

    public ListTag(List<Tag> value) throws IllegalArgumentException {
        this.value = new ArrayList(value.size());
        setValue(value);
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public List<Tag> getValue() {
        return this.value;
    }

    public void setValue(List<Tag> value) throws IllegalArgumentException {
        Preconditions.checkNotNull(value);
        this.type = null;
        this.value.clear();
        for (Tag tag : value) {
            add(tag);
        }
    }

    public Class<? extends Tag> getElementType() {
        return this.type;
    }

    public boolean add(Tag tag) throws IllegalArgumentException {
        Preconditions.checkNotNull(tag);
        if (this.type == null) {
            this.type = tag.getClass();
        } else if (tag.getClass() != this.type) {
            throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
        }
        return this.value.add(tag);
    }

    public boolean remove(Tag tag) {
        return this.value.remove(tag);
    }

    public <T extends Tag> T get(int index) {
        return (T) this.value.get(index);
    }

    public int size() {
        return this.value.size();
    }

    @Override // java.lang.Iterable
    public Iterator<Tag> iterator() {
        return this.value.iterator();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void read(DataInput in) throws IOException {
        this.type = null;
        int id = in.readByte();
        if (id != 0) {
            this.type = TagRegistry.getClassFor(id);
            if (this.type == null) {
                throw new IOException("Unknown tag ID in ListTag: " + id);
            }
        }
        int count = in.readInt();
        for (int index = 0; index < count; index++) {
            try {
                Tag tag = TagRegistry.createInstance(id);
                tag.read(in);
                add(tag);
            } catch (TagCreateException e) {
                throw new IOException("Failed to create tag.", e);
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public void write(DataOutput out) throws IOException {
        if (this.type == null) {
            out.writeByte(0);
        } else {
            int id = TagRegistry.getIdFor(this.type);
            if (id == -1) {
                throw new IOException("ListTag contains unregistered tag class.");
            }
            out.writeByte(id);
        }
        out.writeInt(this.value.size());
        for (Tag tag : this.value) {
            tag.write(out);
        }
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public final ListTag clone() {
        List<Tag> newList = new ArrayList<>();
        for (Tag value : this.value) {
            newList.add(value.clone());
        }
        return new ListTag(newList);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListTag tags = (ListTag) o;
        if (Objects.equals(this.type, tags.type)) {
            return this.value.equals(tags.value);
        }
        return false;
    }

    public int hashCode() {
        int result = this.type != null ? this.type.hashCode() : 0;
        return (31 * result) + this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
    public int getTagId() {
        return 9;
    }
}
