package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJVM.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\b\u0006\n\u0002\u0010\u000b\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018��*\u0006\b��\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u001f\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028��0\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\bH\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n��R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028��\u0018\u00010\u0006X\u0088\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u000b\u001a\u00028��8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, m53d2 = {"Lkotlin/SynchronizedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "lock", "", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)V", "_value", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/SynchronizedLazyImpl.class */
public final class SynchronizedLazyImpl<T> implements Lazy<T>, Serializable {
    @Nullable
    private Functions<? extends T> initializer;
    @Nullable
    private volatile Object _value;
    @NotNull
    private final Object lock;

    public SynchronizedLazyImpl(@NotNull Functions<? extends T> initializer, @Nullable Object lock) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        this.initializer = initializer;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        this.lock = lock == null ? this : lock;
    }

    public /* synthetic */ SynchronizedLazyImpl(Functions functions, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(functions, (i & 2) != 0 ? null : obj);
    }

    @Override // kotlin.Lazy
    public T getValue() {
        T t;
        T t2;
        T t3 = (T) this._value;
        if (t3 != UNINITIALIZED_VALUE.INSTANCE) {
            return t3;
        }
        synchronized (this.lock) {
            Object _v2 = this._value;
            if (_v2 != UNINITIALIZED_VALUE.INSTANCE) {
                t = _v2;
            } else {
                Functions<? extends T> functions = this.initializer;
                Intrinsics.checkNotNull(functions);
                T invoke = functions.invoke();
                this._value = invoke;
                this.initializer = null;
                t = invoke;
            }
            t2 = (T) t;
        }
        return t2;
    }

    @Override // kotlin.Lazy
    public boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }

    @NotNull
    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }
}
