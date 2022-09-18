package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/MutablePropertyReference2Impl.class */
public class MutablePropertyReference2Impl extends MutablePropertyReference2 {
    public MutablePropertyReference2Impl(KDeclarationContainer owner, String name, String signature) {
        super(((ClassBasedDeclarationContainer) owner).getJClass(), name, signature, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version = "1.4")
    public MutablePropertyReference2Impl(Class owner, String name, String signature, int flags) {
        super(owner, name, signature, flags);
    }

    @Override // kotlin.reflect.KProperty2
    public Object get(Object receiver1, Object receiver2) {
        return getGetter().call(receiver1, receiver2);
    }

    @Override // kotlin.reflect.KMutableProperty2
    public void set(Object receiver1, Object receiver2, Object value) {
        getSetter().call(receiver1, receiver2, value);
    }
}
