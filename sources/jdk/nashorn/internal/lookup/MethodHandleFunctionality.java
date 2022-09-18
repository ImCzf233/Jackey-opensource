package jdk.nashorn.internal.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/lookup/MethodHandleFunctionality.class */
public interface MethodHandleFunctionality {
    MethodHandle filterArguments(MethodHandle methodHandle, int i, MethodHandle... methodHandleArr);

    MethodHandle filterReturnValue(MethodHandle methodHandle, MethodHandle methodHandle2);

    MethodHandle guardWithTest(MethodHandle methodHandle, MethodHandle methodHandle2, MethodHandle methodHandle3);

    MethodHandle insertArguments(MethodHandle methodHandle, int i, Object... objArr);

    MethodHandle dropArguments(MethodHandle methodHandle, int i, Class<?>... clsArr);

    MethodHandle dropArguments(MethodHandle methodHandle, int i, List<Class<?>> list);

    MethodHandle foldArguments(MethodHandle methodHandle, MethodHandle methodHandle2);

    MethodHandle explicitCastArguments(MethodHandle methodHandle, MethodType methodType);

    MethodHandle arrayElementGetter(Class<?> cls);

    MethodHandle arrayElementSetter(Class<?> cls);

    MethodHandle throwException(Class<?> cls, Class<? extends Throwable> cls2);

    MethodHandle catchException(MethodHandle methodHandle, Class<? extends Throwable> cls, MethodHandle methodHandle2);

    MethodHandle constant(Class<?> cls, Object obj);

    MethodHandle identity(Class<?> cls);

    MethodHandle asType(MethodHandle methodHandle, MethodType methodType);

    MethodHandle asCollector(MethodHandle methodHandle, Class<?> cls, int i);

    MethodHandle asSpreader(MethodHandle methodHandle, Class<?> cls, int i);

    MethodHandle bindTo(MethodHandle methodHandle, Object obj);

    MethodHandle getter(MethodHandles.Lookup lookup, Class<?> cls, String str, Class<?> cls2);

    MethodHandle staticGetter(MethodHandles.Lookup lookup, Class<?> cls, String str, Class<?> cls2);

    MethodHandle setter(MethodHandles.Lookup lookup, Class<?> cls, String str, Class<?> cls2);

    MethodHandle staticSetter(MethodHandles.Lookup lookup, Class<?> cls, String str, Class<?> cls2);

    MethodHandle find(Method method);

    MethodHandle findStatic(MethodHandles.Lookup lookup, Class<?> cls, String str, MethodType methodType);

    MethodHandle findVirtual(MethodHandles.Lookup lookup, Class<?> cls, String str, MethodType methodType);

    MethodHandle findSpecial(MethodHandles.Lookup lookup, Class<?> cls, String str, MethodType methodType, Class<?> cls2);

    SwitchPoint createSwitchPoint();

    MethodHandle guardWithTest(SwitchPoint switchPoint, MethodHandle methodHandle, MethodHandle methodHandle2);

    MethodType type(Class<?> cls, Class<?>... clsArr);
}
