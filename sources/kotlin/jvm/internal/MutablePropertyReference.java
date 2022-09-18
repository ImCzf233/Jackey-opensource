package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KMutableProperty;

/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/MutablePropertyReference.class */
public abstract class MutablePropertyReference extends PropertyReference implements KMutableProperty {
    public MutablePropertyReference() {
    }

    @SinceKotlin(version = "1.1")
    public MutablePropertyReference(Object receiver) {
        super(receiver);
    }

    @SinceKotlin(version = "1.4")
    public MutablePropertyReference(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }
}
