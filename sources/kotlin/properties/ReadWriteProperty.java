package kotlin.properties;

import kotlin.Metadata;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

/* compiled from: Interfaces.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018��*\u0006\b��\u0010\u0001 ��*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003J\"\u0010\u0004\u001a\u00028\u00012\u0006\u0010\u0005\u001a\u00028��2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H¦\u0002¢\u0006\u0002\u0010\bJ*\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00028��2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\u000b\u001a\u00028\u0001H¦\u0002¢\u0006\u0002\u0010\f¨\u0006\r"}, m53d2 = {"Lkotlin/properties/ReadWriteProperty;", "T", "V", "Lkotlin/properties/ReadOnlyProperty;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/properties/ReadWriteProperty.class */
public interface ReadWriteProperty<T, V> extends ReadOnlyProperty<T, V> {
    @Override // kotlin.properties.ReadOnlyProperty
    V getValue(T t, @NotNull KProperty<?> kProperty);

    void setValue(T t, @NotNull KProperty<?> kProperty, V v);
}
