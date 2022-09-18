package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, m53d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;"})
@DebugMetadata(m44f = "_Sequences.kt", m42l = {2693}, m43i = {0, 0, 0}, m39s = {"L$0", "L$1", "L$2"}, m40n = {"$this$result", "iterator", "next"}, m41m = "invokeSuspend", m45c = "kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2")
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$zipWithNext$2.class */
public final class SequencesKt___SequencesKt$zipWithNext$2 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    Object L$1;
    Object L$2;
    int label;
    private /* synthetic */ Object L$0;
    final /* synthetic */ Sequence<T> $this_zipWithNext;
    final /* synthetic */ Function2<T, T, R> $transform;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$zipWithNext$2(Sequence<? extends T> sequence, Function2<? super T, ? super T, ? extends R> function2, Continuation<? super SequencesKt___SequencesKt$zipWithNext$2> continuation) {
        super(2, continuation);
        this.$this_zipWithNext = sequence;
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> continuation) {
        SequencesKt___SequencesKt$zipWithNext$2 sequencesKt___SequencesKt$zipWithNext$2 = new SequencesKt___SequencesKt$zipWithNext$2(this.$this_zipWithNext, this.$transform, continuation);
        sequencesKt___SequencesKt$zipWithNext$2.L$0 = value;
        return sequencesKt___SequencesKt$zipWithNext$2;
    }

    @Nullable
    public final Object invoke(@NotNull SequenceScope<? super R> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
        return ((SequencesKt___SequencesKt$zipWithNext$2) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b2  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x00aa -> B:9:0x004b). Please submit an issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r12 = r0
            r0 = r6
            int r0 = r0.label
            switch(r0) {
                case 0: goto L20;
                case 1: goto L8f;
                default: goto Lb6;
            }
        L20:
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r6
            java.lang.Object r0 = r0.L$0
            kotlin.sequences.SequenceScope r0 = (kotlin.sequences.SequenceScope) r0
            r8 = r0
            r0 = r6
            kotlin.sequences.Sequence<T> r0 = r0.$this_zipWithNext
            java.util.Iterator r0 = r0.iterator()
            r9 = r0
            r0 = r9
            boolean r0 = r0.hasNext()
            if (r0 != 0) goto L43
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L43:
            r0 = r9
            java.lang.Object r0 = r0.next()
            r10 = r0
        L4b:
            r0 = r9
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto Lb2
            r0 = r9
            java.lang.Object r0 = r0.next()
            r11 = r0
            r0 = r8
            r1 = r6
            kotlin.jvm.functions.Function2<T, T, R> r1 = r1.$transform
            r2 = r10
            r3 = r11
            java.lang.Object r1 = r1.invoke(r2, r3)
            r2 = r6
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3 = r6
            r4 = r8
            r3.L$0 = r4
            r3 = r6
            r4 = r9
            r3.L$1 = r4
            r3 = r6
            r4 = r11
            r3.L$2 = r4
            r3 = r6
            r4 = 1
            r3.label = r4
            java.lang.Object r0 = r0.yield(r1, r2)
            r1 = r0
            r2 = r12
            if (r1 != r2) goto Laa
            r1 = r12
            return r1
        L8f:
            r0 = r6
            java.lang.Object r0 = r0.L$2
            r11 = r0
            r0 = r6
            java.lang.Object r0 = r0.L$1
            java.util.Iterator r0 = (java.util.Iterator) r0
            r9 = r0
            r0 = r6
            java.lang.Object r0 = r0.L$0
            kotlin.sequences.SequenceScope r0 = (kotlin.sequences.SequenceScope) r0
            r8 = r0
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r7
        Laa:
            r0 = r11
            r10 = r0
            goto L4b
        Lb2:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        Lb6:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r1 = r0
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
