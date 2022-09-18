package kotlin.sequences;

import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, m53d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;"})
@DebugMetadata(m44f = "Sequences.kt", m42l = {145}, m43i = {0, 0}, m39s = {"L$0", "L$1"}, m40n = {"$this$sequence", "buffer"}, m41m = "invokeSuspend", m45c = "kotlin.sequences.SequencesKt__SequencesKt$shuffled$1")
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt__SequencesKt$shuffled$1.class */
public final class SequencesKt__SequencesKt$shuffled$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
    Object L$1;
    int label;
    private /* synthetic */ Object L$0;
    final /* synthetic */ Sequence<T> $this_shuffled;
    final /* synthetic */ Random $random;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt__SequencesKt$shuffled$1(Sequence<? extends T> sequence, Random $random, Continuation<? super SequencesKt__SequencesKt$shuffled$1> continuation) {
        super(2, continuation);
        this.$this_shuffled = sequence;
        this.$random = $random;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> continuation) {
        SequencesKt__SequencesKt$shuffled$1 sequencesKt__SequencesKt$shuffled$1 = new SequencesKt__SequencesKt$shuffled$1(this.$this_shuffled, this.$random, continuation);
        sequencesKt__SequencesKt$shuffled$1.L$0 = value;
        return sequencesKt__SequencesKt$shuffled$1;
    }

    @Nullable
    public final Object invoke(@NotNull SequenceScope<? super T> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
        return ((SequencesKt__SequencesKt$shuffled$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        List buffer;
        SequenceScope $this$sequence;
        Object value;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                $this$sequence = (SequenceScope) this.L$0;
                buffer = SequencesKt.toMutableList(this.$this_shuffled);
                break;
            case 1:
                buffer = (List) this.L$1;
                $this$sequence = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        do {
            if (!buffer.isEmpty()) {
                int j = this.$random.nextInt(buffer.size());
                Object last = CollectionsKt.removeLast(buffer);
                value = j < buffer.size() ? buffer.set(j, last) : last;
                this.L$0 = $this$sequence;
                this.L$1 = buffer;
                this.label = 1;
            } else {
                return Unit.INSTANCE;
            }
        } while ($this$sequence.yield(value, this) != coroutine_suspended);
        return coroutine_suspended;
    }
}
