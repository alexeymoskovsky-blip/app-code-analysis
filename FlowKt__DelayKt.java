package kotlinx.coroutines.flow;

import androidx.lifecycle.SavedStateHandle;
import com.petkit.android.api.ErrorCode;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Delay.kt */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class FlowKt__DelayKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2 */
    /* JADX INFO: compiled from: Delay.kt */
    public static final class AnonymousClass2<T> extends Lambda implements Function1<T, Long> {
        public final /* synthetic */ long $timeoutMillis;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(long j) {
            super(1);
            j = j;
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        public final Long invoke(T t) {
            return Long.valueOf(j);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @FlowPreview
    @NotNull
    public static final <T> Flow<T> debounce(@NotNull Flow<? extends T> flow, long j) {
        if (j >= 0) {
            return j == 0 ? flow : debounceInternal$FlowKt__DelayKt(flow, new Function1<T, Long>() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt.debounce.2
                public final /* synthetic */ long $timeoutMillis;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(long j2) {
                    super(1);
                    j = j2;
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Long invoke(T t) {
                    return Long.valueOf(j);
                }
            });
        }
        throw new IllegalArgumentException("Debounce timeout should not be negative");
    }

    @FlowPreview
    @OverloadResolutionByLambdaReturnType
    @NotNull
    public static final <T> Flow<T> debounce(@NotNull Flow<? extends T> flow, @NotNull Function1<? super T, Long> function1) {
        return debounceInternal$FlowKt__DelayKt(flow, function1);
    }

    @FlowPreview
    @NotNull
    /* JADX INFO: renamed from: debounce-HG0u8IE */
    public static final <T> Flow<T> m3228debounceHG0u8IE(@NotNull Flow<? extends T> flow, long j) {
        return FlowKt.debounce(flow, DelayKt.m3186toDelayMillisLRDsOJo(j));
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$3 */
    /* JADX INFO: compiled from: Delay.kt */
    public static final class AnonymousClass3<T> extends Lambda implements Function1<T, Long> {
        public final /* synthetic */ Function1<T, Duration> $timeout;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass3(Function1<? super T, Duration> function1) {
            super(1);
            function1 = function1;
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        public final Long invoke(T t) {
            return Long.valueOf(DelayKt.m3186toDelayMillisLRDsOJo(function1.invoke(t).m3076unboximpl()));
        }
    }

    @FlowPreview
    @JvmName(name = "debounceDuration")
    @NotNull
    @OverloadResolutionByLambdaReturnType
    public static final <T> Flow<T> debounceDuration(@NotNull Flow<? extends T> flow, @NotNull Function1<? super T, Duration> function1) {
        return debounceInternal$FlowKt__DelayKt(flow, new Function1<T, Long>() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt.debounce.3
            public final /* synthetic */ Function1<T, Duration> $timeout;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(Function1<? super T, Duration> function12) {
                super(1);
                function1 = function12;
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Long invoke(T t) {
                return Long.valueOf(DelayKt.m3186toDelayMillisLRDsOJo(function1.invoke(t).m3076unboximpl()));
            }
        });
    }

    public static final <T> Flow<T> debounceInternal$FlowKt__DelayKt(Flow<? extends T> flow, Function1<? super T, Long> function1) {
        return FlowCoroutineKt.scopedFlow(new FlowKt__DelayKt$debounceInternal$1(function1, flow, null));
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2 */
    /* JADX INFO: compiled from: Delay.kt */
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2", f = "Delay.kt", i = {0, 0, 0, 0}, l = {352}, m = "invokeSuspend", n = {"downstream", SavedStateHandle.VALUES, "lastValue", "ticker"}, s = {"L$0", "L$1", "L$2", "L$3"})
    public static final class C03472<T> extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ long $periodMillis;
        public final /* synthetic */ Flow<T> $this_sample;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public Object L$2;
        public Object L$3;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C03472(long j, Flow<? extends T> flow, Continuation<? super C03472> continuation) {
            super(3, continuation);
            this.$periodMillis = j;
            this.$this_sample = flow;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull FlowCollector<? super T> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            C03472 c03472 = new C03472(this.$periodMillis, this.$this_sample, continuation);
            c03472.L$0 = coroutineScope;
            c03472.L$1 = flowCollector;
            return c03472.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            FlowCollector flowCollector;
            ReceiveChannel receiveChannel;
            Ref.ObjectRef objectRef;
            ReceiveChannel receiveChannelFixedPeriodTicker$default;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                FlowCollector flowCollector2 = (FlowCollector) this.L$1;
                ReceiveChannel receiveChannelProduce$default = ProduceKt.produce$default(coroutineScope, null, -1, new FlowKt__DelayKt$sample$2$values$1(this.$this_sample, null), 1, null);
                Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                flowCollector = flowCollector2;
                receiveChannel = receiveChannelProduce$default;
                objectRef = objectRef2;
                receiveChannelFixedPeriodTicker$default = FlowKt__DelayKt.fixedPeriodTicker$default(coroutineScope, this.$periodMillis, 0L, 2, null);
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                receiveChannelFixedPeriodTicker$default = (ReceiveChannel) this.L$3;
                objectRef = (Ref.ObjectRef) this.L$2;
                receiveChannel = (ReceiveChannel) this.L$1;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (objectRef.element != NullSurrogateKt.DONE) {
                this.L$0 = flowCollector;
                this.L$1 = receiveChannel;
                this.L$2 = objectRef;
                this.L$3 = receiveChannelFixedPeriodTicker$default;
                this.label = 1;
                SelectBuilderImpl selectBuilderImpl = new SelectBuilderImpl(this);
                try {
                    selectBuilderImpl.invoke(receiveChannel.getOnReceiveCatching(), new FlowKt__DelayKt$sample$2$1$1(objectRef, receiveChannelFixedPeriodTicker$default, null));
                    selectBuilderImpl.invoke(receiveChannelFixedPeriodTicker$default.getOnReceive(), new FlowKt__DelayKt$sample$2$1$2(objectRef, flowCollector, null));
                } catch (Throwable th) {
                    selectBuilderImpl.handleBuilderException(th);
                }
                Object result = selectBuilderImpl.getResult();
                if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(this);
                }
                if (result == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    @FlowPreview
    @NotNull
    public static final <T> Flow<T> sample(@NotNull Flow<? extends T> flow, long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("Sample period should be positive");
        }
        return FlowCoroutineKt.scopedFlow(new C03472(j, flow, null));
    }

    public static /* synthetic */ ReceiveChannel fixedPeriodTicker$default(CoroutineScope coroutineScope, long j, long j2, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        return FlowKt.fixedPeriodTicker(coroutineScope, j, j2);
    }

    @NotNull
    public static final ReceiveChannel<Unit> fixedPeriodTicker(@NotNull CoroutineScope coroutineScope, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
        }
        if (j2 < 0) {
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        return ProduceKt.produce$default(coroutineScope, null, 0, new C03463(j2, j, null), 1, null);
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3 */
    /* JADX INFO: compiled from: Delay.kt */
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3", f = "Delay.kt", i = {0, 1, 2}, l = {ErrorCode.ERR_POST_TOPIC_NOT_EXISTS, ErrorCode.ERR_POST_BLOCK_SELF, ErrorCode.ERR_POST_BLOCK_EXCEEED_MAX_NUMBER}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$0"})
    public static final class C03463 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
        public final /* synthetic */ long $delayMillis;
        public final /* synthetic */ long $initialDelayMillis;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03463(long j, long j2, Continuation<? super C03463> continuation) {
            super(2, continuation);
            this.$initialDelayMillis = j;
            this.$delayMillis = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C03463 c03463 = new C03463(this.$initialDelayMillis, this.$delayMillis, continuation);
            c03463.L$0 = obj;
            return c03463;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ProducerScope<? super Unit> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C03463) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x004f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x005c A[RETURN] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x005a -> B:36:0x003f). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 3
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L2a
                if (r1 == r4) goto L22
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                goto L22
            L12:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1a:
                java.lang.Object r1 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                kotlin.ResultKt.throwOnFailure(r8)
                goto L50
            L22:
                java.lang.Object r1 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3f
            L2a:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                r1 = r8
                kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
                long r5 = r7.$initialDelayMillis
                r7.L$0 = r1
                r7.label = r4
                java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r5, r7)
                if (r8 != r0) goto L3f
                return r0
            L3f:
                kotlinx.coroutines.channels.SendChannel r8 = r1.getChannel()
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                r7.L$0 = r1
                r7.label = r3
                java.lang.Object r8 = r8.send(r4, r7)
                if (r8 != r0) goto L50
                return r0
            L50:
                long r4 = r7.$delayMillis
                r7.L$0 = r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r4, r7)
                if (r8 != r0) goto L3f
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt.C03463.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @FlowPreview
    @NotNull
    /* JADX INFO: renamed from: sample-HG0u8IE */
    public static final <T> Flow<T> m3229sampleHG0u8IE(@NotNull Flow<? extends T> flow, long j) {
        return FlowKt.sample(flow, DelayKt.m3186toDelayMillisLRDsOJo(j));
    }
}
