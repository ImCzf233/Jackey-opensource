package net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers;

import kotlin.Metadata;
import org.spongepowered.asm.util.Constants;

/* compiled from: AbstractJavaLinkerHandler.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler;", "", "()V", "addMember", "", "clazz", Constants.CLASS, "name", "accessibleObject", "Ljava/lang/reflect/AccessibleObject;", "setPropertyGetter", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler.class */
public final class AbstractJavaLinkerHandler {
    public static final AbstractJavaLinkerHandler INSTANCE = new AbstractJavaLinkerHandler();

    private AbstractJavaLinkerHandler() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0069, code lost:
        return r8;
     */
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String addMember(@org.jetbrains.annotations.NotNull java.lang.Class<?> r7, @org.jetbrains.annotations.NotNull java.lang.String r8, @org.jetbrains.annotations.NotNull java.lang.reflect.AccessibleObject r9) {
        /*
            r0 = r7
            java.lang.String r1 = "clazz"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r8
            java.lang.String r1 = "name"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r9
            java.lang.String r1 = "accessibleObject"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r9
            boolean r0 = r0 instanceof java.lang.reflect.Method
            if (r0 != 0) goto L1b
            r0 = r8
            return r0
        L1b:
            r0 = r7
            r10 = r0
        L1d:
            r0 = r10
            java.lang.String r0 = r0.getName()
            java.lang.String r1 = "java.lang.Object"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L68
            net.ccbluex.liquidbounce.script.remapper.Remapper r0 = net.ccbluex.liquidbounce.script.remapper.Remapper.INSTANCE
            r1 = r10
            r2 = r8
            r3 = r9
            java.lang.reflect.Method r3 = (java.lang.reflect.Method) r3
            java.lang.String r3 = org.objectweb.asm.Type.getMethodDescriptor(r3)
            r4 = r3
            java.lang.String r5 = "Type.getMethodDescriptor(accessibleObject)"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r4, r5)
            java.lang.String r0 = r0.remapMethod(r1, r2, r3)
            r11 = r0
            r0 = r11
            r1 = r8
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L50
            r0 = r11
            return r0
        L50:
            r0 = r10
            java.lang.Class r0 = r0.getSuperclass()
            if (r0 != 0) goto L5a
            goto L68
        L5a:
            r0 = r10
            java.lang.Class r0 = r0.getSuperclass()
            r1 = r0
            java.lang.String r2 = "currentClass.superclass"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r2)
            r10 = r0
            goto L1d
        L68:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers.AbstractJavaLinkerHandler.addMember(java.lang.Class, java.lang.String, java.lang.reflect.AccessibleObject):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004a, code lost:
        return r5;
     */
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String addMember(@org.jetbrains.annotations.NotNull java.lang.Class<?> r4, @org.jetbrains.annotations.NotNull java.lang.String r5) {
        /*
            r0 = r4
            java.lang.String r1 = "clazz"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r5
            java.lang.String r1 = "name"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r4
            r6 = r0
        Le:
            r0 = r6
            java.lang.String r0 = r0.getName()
            java.lang.String r1 = "java.lang.Object"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L49
            net.ccbluex.liquidbounce.script.remapper.Remapper r0 = net.ccbluex.liquidbounce.script.remapper.Remapper.INSTANCE
            r1 = r6
            r2 = r5
            java.lang.String r0 = r0.remapField(r1, r2)
            r7 = r0
            r0 = r7
            r1 = r5
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L31
            r0 = r7
            return r0
        L31:
            r0 = r6
            java.lang.Class r0 = r0.getSuperclass()
            if (r0 != 0) goto L3b
            goto L49
        L3b:
            r0 = r6
            java.lang.Class r0 = r0.getSuperclass()
            r1 = r0
            java.lang.String r2 = "currentClass.superclass"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r2)
            r6 = r0
            goto Le
        L49:
            r0 = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers.AbstractJavaLinkerHandler.addMember(java.lang.Class, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004a, code lost:
        return r5;
     */
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.String setPropertyGetter(@org.jetbrains.annotations.NotNull java.lang.Class<?> r4, @org.jetbrains.annotations.NotNull java.lang.String r5) {
        /*
            r0 = r4
            java.lang.String r1 = "clazz"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r5
            java.lang.String r1 = "name"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r4
            r6 = r0
        Le:
            r0 = r6
            java.lang.String r0 = r0.getName()
            java.lang.String r1 = "java.lang.Object"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L49
            net.ccbluex.liquidbounce.script.remapper.Remapper r0 = net.ccbluex.liquidbounce.script.remapper.Remapper.INSTANCE
            r1 = r6
            r2 = r5
            java.lang.String r0 = r0.remapField(r1, r2)
            r7 = r0
            r0 = r7
            r1 = r5
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L31
            r0 = r7
            return r0
        L31:
            r0 = r6
            java.lang.Class r0 = r0.getSuperclass()
            if (r0 != 0) goto L3b
            goto L49
        L3b:
            r0 = r6
            java.lang.Class r0 = r0.getSuperclass()
            r1 = r0
            java.lang.String r2 = "currentClass.superclass"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r2)
            r6 = r0
            goto Le
        L49:
            r0 = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers.AbstractJavaLinkerHandler.setPropertyGetter(java.lang.Class, java.lang.String):java.lang.String");
    }
}
