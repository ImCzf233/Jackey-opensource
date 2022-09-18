package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectObjectImmutableSortedPair.class */
public class ObjectObjectImmutableSortedPair<K extends Comparable<K>> extends ObjectObjectImmutablePair<K, K> implements SortedPair<K>, Serializable {
    private static final long serialVersionUID = 0;

    private ObjectObjectImmutableSortedPair(K left, K right) {
        super(left, right);
    }

    /* renamed from: of */
    public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> m160of(K left, K right) {
        if (left.compareTo(right) <= 0) {
            return new ObjectObjectImmutableSortedPair<>(left, right);
        }
        return new ObjectObjectImmutableSortedPair<>(right, left);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair
    public boolean equals(Object other) {
        return other != null && (other instanceof SortedPair) && Objects.equals(this.left, ((SortedPair) other).left()) && Objects.equals(this.right, ((SortedPair) other).right());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair
    public String toString() {
        return "{" + left() + "," + right() + "}";
    }
}
