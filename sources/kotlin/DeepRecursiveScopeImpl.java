package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbes;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DeepRecursive.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��B\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018��*\u0004\b��\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004BK\u00129\u0010\u0005\u001a5\b\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028��\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0004\u0012\u00028��\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b\u0012\u0006\u0010\t\u001a\u00028��ø\u0001��¢\u0006\u0002\u0010\nJ\u0019\u0010\u0015\u001a\u00028\u00012\u0006\u0010\t\u001a\u00028��H\u0096@ø\u0001��¢\u0006\u0002\u0010\u0016Jc\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000429\u0010\u0018\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b2\u000e\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\u0002ø\u0001��¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\u0013H\u0016ø\u0001��¢\u0006\u0002\u0010\u001cJ\u000b\u0010\u001d\u001a\u00028\u0001¢\u0006\u0002\u0010\u001eJ5\u0010\u0015\u001a\u0002H\u001f\"\u0004\b\u0002\u0010 \"\u0004\b\u0003\u0010\u001f*\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H\u001f0!2\u0006\u0010\t\u001a\u0002H H\u0096@ø\u0001��¢\u0006\u0002\u0010\"R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fRF\u0010\u0010\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\bX\u0082\u000eø\u0001��¢\u0006\u0004\n\u0002\u0010\u0011R\u001e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0013X\u0082\u000eø\u0001��ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0014R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006#"}, m53d2 = {"Lkotlin/DeepRecursiveScopeImpl;", "T", "R", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)V", "cont", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "function", "Lkotlin/jvm/functions/Function3;", "result", "Lkotlin/Result;", Constants.OBJECT, "callRecursive", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crossFunctionCompletion", "currentFunction", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resumeWith", "", "(Ljava/lang/Object;)V", "runCallLoop", "()Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"})
@ExperimentalStdlibApi
/* loaded from: Jackey Client b2.jar:kotlin/DeepRecursiveScopeImpl.class */
public final class DeepRecursiveScopeImpl<T, R> extends DeepRecursiveScope<T, R> implements Continuation<R> {
    @NotNull
    private Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> function;
    @Nullable
    private Object value;
    @Nullable
    private Continuation<Object> cont = this;
    @NotNull
    private Object result;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public DeepRecursiveScopeImpl(@NotNull Function3<? super DeepRecursiveScope<T, R>, ? super T, ? super Continuation<? super R>, ? extends Object> block, T t) {
        super(null);
        Object obj;
        Intrinsics.checkNotNullParameter(block, "block");
        this.function = block;
        this.value = t;
        obj = DeepRecursiveKt.UNDEFINED_RESULT;
        this.result = obj;
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object result) {
        this.cont = null;
        this.result = result;
    }

    @Override // kotlin.DeepRecursiveScope
    @Nullable
    public Object callRecursive(T t, @NotNull Continuation<? super R> continuation) {
        this.cont = continuation;
        this.value = t;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbes.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended;
    }

    @Override // kotlin.DeepRecursiveScope
    @Nullable
    public <U, S> Object callRecursive(@NotNull DeepRecursive<U, S> deepRecursive, U u, @NotNull Continuation<? super S> continuation) {
        Function3 function = deepRecursive.getBlock$kotlin_stdlib();
        DeepRecursiveScopeImpl<T, R> $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1 = this;
        Function3 currentFunction = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function;
        if (function != currentFunction) {
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function = function;
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.crossFunctionCompletion(currentFunction, continuation);
        } else {
            $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = continuation;
        }
        $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.value = u;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbes.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended;
    }

    private final Continuation<Object> crossFunctionCompletion(final Function3<? super DeepRecursiveScope<?, ?>, Object, ? super Continuation<Object>, ? extends Object> function3, final Continuation<Object> continuation) {
        final EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        return new Continuation<Object>() { // from class: kotlin.DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1
            @Override // kotlin.coroutines.Continuation
            @NotNull
            public CoroutineContext getContext() {
                return CoroutineContext.this;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(@NotNull Object result) {
                this.function = function3;
                this.cont = continuation;
                this.result = result;
            }
        };
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:8:0x0026
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    public final R runCallLoop() {
        /*
            r5 = this;
        L0:
            r0 = r5
            java.lang.Object r0 = r0.result
            r6 = r0
            r0 = r5
            kotlin.coroutines.Continuation<java.lang.Object> r0 = r0.cont
            r8 = r0
            r0 = r8
            if (r0 != 0) goto L1a
            r0 = r6
            r9 = r0
            r0 = r9
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r9
            return r0
        L1a:
            r0 = r8
            r7 = r0
            java.lang.Object r0 = kotlin.DeepRecursiveKt.access$getUNDEFINED_RESULT$p()
            r1 = r6
            boolean r0 = kotlin.Result.m1217equalsimpl0(r0, r1)
            if (r0 == 0) goto L86
        L27:
            r0 = r5
            kotlin.jvm.functions.Function3<? super kotlin.DeepRecursiveScope<?, ?>, java.lang.Object, ? super kotlin.coroutines.Continuation<java.lang.Object>, ? extends java.lang.Object> r0 = r0.function     // Catch: java.lang.Throwable -> L4a
            r9 = r0
            r0 = r5
            java.lang.Object r0 = r0.value     // Catch: java.lang.Throwable -> L4a
            r10 = r0
            r0 = r9
            r1 = 3
            java.lang.Object r0 = kotlin.jvm.internal.TypeIntrinsics.beforeCheckcastToFunctionOfArity(r0, r1)     // Catch: java.lang.Throwable -> L4a
            kotlin.jvm.functions.Function3 r0 = (kotlin.jvm.functions.Function3) r0     // Catch: java.lang.Throwable -> L4a
            r1 = r5
            r2 = r10
            r3 = r7
            java.lang.Object r0 = r0.invoke(r1, r2, r3)     // Catch: java.lang.Throwable -> L4a
            r9 = r0
            goto L66
        L4a:
            r10 = move-exception
            r0 = r7
            r11 = r0
            r0 = r11
            kotlin.Result$Companion r1 = kotlin.Result.Companion
            r12 = r1
            r1 = r10
            java.lang.Object r1 = kotlin.ResultKt.createFailure(r1)
            java.lang.Object r1 = kotlin.Result.m1214constructorimpl(r1)
            r0.resumeWith(r1)
            goto L0
        L66:
            r0 = r9
            r8 = r0
            r0 = r8
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 == r1) goto L0
            r0 = r7
            r9 = r0
            r0 = r9
            kotlin.Result$Companion r1 = kotlin.Result.Companion
            r10 = r1
            r1 = r8
            java.lang.Object r1 = kotlin.Result.m1214constructorimpl(r1)
            r0.resumeWith(r1)
            goto L0
        L86:
            r0 = r5
            java.lang.Object r1 = kotlin.DeepRecursiveKt.access$getUNDEFINED_RESULT$p()
            r0.result = r1
            r0 = r7
            r1 = r6
            r0.resumeWith(r1)
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.DeepRecursiveScopeImpl.runCallLoop():java.lang.Object");
    }
}
