package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformImplementations.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u001e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010��\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b��\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010��\u001a\u00020\u00018��X\u0081\u0004¢\u0006\u0002\n��¨\u0006\u000e"}, m53d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/internal/PlatformImplementationsKt.class */
public final class PlatformImplementationsKt {
    @JvmPlatformAnnotations
    @NotNull
    public static final PlatformImplementations IMPLEMENTATIONS;

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:4:0x000c
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    static {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementationsKt.m2431clinit():void");
    }

    @InlineOnly
    private static final /* synthetic */ <T> T castToBaseType(Object instance) {
        try {
            Intrinsics.reifiedOperationMarker(1, "T");
            return (T) instance;
        } catch (ClassCastException e) {
            ClassLoader instanceCL = instance.getClass().getClassLoader();
            Intrinsics.reifiedOperationMarker(4, "T");
            ClassLoader baseTypeCL = Object.class.getClassLoader();
            Throwable initCause = new ClassCastException("Instance classloader: " + instanceCL + ", base type classloader: " + baseTypeCL).initCause(e);
            Intrinsics.checkNotNullExpressionValue(initCause, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
            throw initCause;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:8:0x0024
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    private static final int getJavaVersion() {
        /*
            r0 = 65542(0x10006, float:9.1844E-41)
            r7 = r0
            java.lang.String r0 = "java.specification.version"
            java.lang.String r0 = java.lang.System.getProperty(r0)
            r9 = r0
            r0 = r9
            if (r0 != 0) goto Lf
            r0 = r7
            return r0
        Lf:
            r0 = r9
            r8 = r0
            r0 = r8
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            r1 = 46
            r2 = 0
            r3 = 0
            r4 = 6
            r5 = 0
            int r0 = kotlin.text.StringsKt.indexOf$default(r0, r1, r2, r3, r4, r5)
            r9 = r0
            r0 = r9
            if (r0 >= 0) goto L36
        L25:
            r0 = r8
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L30
            r1 = 65536(0x10000, float:9.18355E-41)
            int r0 = r0 * r1
            r10 = r0
            goto L34
        L30:
            r11 = move-exception
            r0 = r7
            r10 = r0
        L34:
            r0 = r10
            return r0
        L36:
            r0 = r8
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            r1 = 46
            r2 = r9
            r3 = 1
            int r2 = r2 + r3
            r3 = 0
            r4 = 4
            r5 = 0
            int r0 = kotlin.text.StringsKt.indexOf$default(r0, r1, r2, r3, r4, r5)
            r10 = r0
            r0 = r10
            if (r0 >= 0) goto L4f
            r0 = r8
            int r0 = r0.length()
            r10 = r0
        L4f:
            r0 = r8
            r12 = r0
            r0 = 0
            r13 = r0
            r0 = r12
            r1 = r13
            r2 = r9
            java.lang.String r0 = r0.substring(r1, r2)
            r14 = r0
            r0 = r14
            java.lang.String r1 = "this as java.lang.String…ing(startIndex, endIndex)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r0 = r14
            r11 = r0
            r0 = r8
            r13 = r0
            r0 = r9
            r1 = 1
            int r0 = r0 + r1
            r14 = r0
            r0 = r13
            r1 = r14
            r2 = r10
            java.lang.String r0 = r0.substring(r1, r2)
            r15 = r0
            r0 = r15
            java.lang.String r1 = "this as java.lang.String…ing(startIndex, endIndex)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r0 = r15
            r12 = r0
            r0 = r11
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L9b
            r1 = 65536(0x10000, float:9.18355E-41)
            int r0 = r0 * r1
            r1 = r12
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.NumberFormatException -> L9b
            int r0 = r0 + r1
            r13 = r0
            goto La0
        L9b:
            r14 = move-exception
            r0 = r7
            r13 = r0
        La0:
            r0 = r13
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementationsKt.getJavaVersion():int");
    }

    @SinceKotlin(version = "1.2")
    @PublishedApi
    public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
        return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
    }
}
