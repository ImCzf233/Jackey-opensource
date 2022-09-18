package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/PropertyReference2Impl.class */
public class PropertyReference2Impl extends PropertyReference2 {
    public PropertyReference2Impl(KDeclarationContainer owner, String name, String signature) {
        super(((ClassBasedDeclarationContainer) owner).getJClass(), name, signature, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version = "1.4")
    public PropertyReference2Impl(Class owner, String name, String signature, int flags) {
        super(owner, name, signature, flags);
    }

    @Override // kotlin.reflect.KProperty2
    public Object get(Object receiver1, Object receiver2) {
        return getGetter().call(receiver1, receiver2);
    }
}
