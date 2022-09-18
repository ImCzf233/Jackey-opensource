package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty2;

/* loaded from: Jackey Client b2.jar:kotlin/jvm/internal/PropertyReference2.class */
public abstract class PropertyReference2 extends PropertyReference implements KProperty2 {
    public PropertyReference2() {
    }

    @SinceKotlin(version = "1.4")
    public PropertyReference2(Class owner, String name, String signature, int flags) {
        super(NO_RECEIVER, owner, name, signature, flags);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.property2(this);
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object receiver1, Object receiver2) {
        return get(receiver1, receiver2);
    }

    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    public KProperty2.Getter getGetter() {
        return ((KProperty2) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty2
    @SinceKotlin(version = "1.1")
    public Object getDelegate(Object receiver1, Object receiver2) {
        return ((KProperty2) getReflected()).getDelegate(receiver1, receiver2);
    }
}
