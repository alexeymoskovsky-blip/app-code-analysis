package io.grpc.okhttp;

import com.arialyy.aria.core.inf.IOptionConstant;
import com.facebook.appevents.integrity.IntegrityManager;
import com.facebook.internal.AnalyticsEvents;
import com.github.sunnysuperman.commons.utils.HttpClient;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.qiyukf.module.zip4j.util.InternalZipConstants;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer;
import io.grpc.Grpc;
import io.grpc.HttpConnectProxiedSocketAddress;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.Http2Ping;
import io.grpc.internal.InUseStateAggregator;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.SerializingExecutor;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.okhttp.ExceptionHandlingFrameWriter;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.okhttp.OkHttpClientStream;
import io.grpc.okhttp.OkHttpFrameLogger;
import io.grpc.okhttp.OutboundFlowController;
import io.grpc.okhttp.internal.ConnectionSpec;
import io.grpc.okhttp.internal.Credentials;
import io.grpc.okhttp.internal.StatusLine;
import io.grpc.okhttp.internal.framed.ErrorCode;
import io.grpc.okhttp.internal.framed.FrameReader;
import io.grpc.okhttp.internal.framed.FrameWriter;
import io.grpc.okhttp.internal.framed.Header;
import io.grpc.okhttp.internal.framed.HeadersMode;
import io.grpc.okhttp.internal.framed.Http2;
import io.grpc.okhttp.internal.framed.Settings;
import io.grpc.okhttp.internal.framed.Variant;
import io.grpc.okhttp.internal.proxy.HttpUrl;
import io.grpc.okhttp.internal.proxy.Request;
import io.perfmark.PerfMark;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import okio.Timeout;

/* JADX INFO: loaded from: classes7.dex */
public class OkHttpClientTransport implements ConnectionClientTransport, ExceptionHandlingFrameWriter.TransportExceptionHandler, OutboundFlowController.Transport {
    public static final Map<ErrorCode, Status> ERROR_CODE_TO_STATUS = buildErrorCodeToStatusMap();
    public static final Logger log = Logger.getLogger(OkHttpClientTransport.class.getName());
    public final InetSocketAddress address;
    public Attributes attributes;
    public ClientFrameHandler clientFrameHandler;
    public SettableFuture<Void> connectedFuture;
    public Runnable connectingCallback;
    public final ConnectionSpec connectionSpec;
    public int connectionUnacknowledgedBytesRead;
    public final String defaultAuthority;
    public boolean enableKeepAlive;
    public final Executor executor;

    @GuardedBy("lock")
    public ExceptionHandlingFrameWriter frameWriter;

    @GuardedBy("lock")
    public boolean goAwaySent;

    @GuardedBy("lock")
    public Status goAwayStatus;

    @GuardedBy("lock")
    public boolean hasStream;
    public HostnameVerifier hostnameVerifier;

    @GuardedBy("lock")
    public final InUseStateAggregator<OkHttpClientStream> inUseState;
    public final int initialWindowSize;
    public KeepAliveManager keepAliveManager;
    public long keepAliveTimeNanos;
    public long keepAliveTimeoutNanos;
    public boolean keepAliveWithoutCalls;
    public ManagedClientTransport.Listener listener;
    public final Object lock;
    public final InternalLogId logId;

    @GuardedBy("lock")
    public int maxConcurrentStreams;
    public final int maxInboundMetadataSize;
    public final int maxMessageSize;

    @GuardedBy("lock")
    public int nextStreamId;
    public OutboundFlowController outboundFlow;

    @GuardedBy("lock")
    public final Deque<OkHttpClientStream> pendingStreams;

    @GuardedBy("lock")
    public Http2Ping ping;

    @VisibleForTesting
    @Nullable
    public final HttpConnectProxiedSocketAddress proxiedAddr;

    @VisibleForTesting
    public int proxySocketTimeout;
    public final Random random;
    public final ScheduledExecutorService scheduler;

    @GuardedBy("lock")
    public InternalChannelz.Security securityInfo;
    public final SerializingExecutor serializingExecutor;
    public Socket socket;
    public final SocketFactory socketFactory;
    public SSLSocketFactory sslSocketFactory;

    @GuardedBy("lock")
    public boolean stopped;
    public final Supplier<Stopwatch> stopwatchFactory;

    @GuardedBy("lock")
    public final Map<Integer, OkHttpClientStream> streams;
    public final Runnable tooManyPingsRunnable;

    @GuardedBy("lock")
    public final TransportTracer transportTracer;
    public final boolean useGetForSafeMethods;
    public final String userAgent;
    public final Variant variant;

    public static /* synthetic */ int access$2412(OkHttpClientTransport okHttpClientTransport, int i) {
        int i2 = okHttpClientTransport.connectionUnacknowledgedBytesRead + i;
        okHttpClientTransport.connectionUnacknowledgedBytesRead = i2;
        return i2;
    }

    @Override // io.grpc.internal.ClientTransport
    public /* bridge */ /* synthetic */ ClientStream newStream(MethodDescriptor methodDescriptor, Metadata metadata, CallOptions callOptions, ClientStreamTracer[] clientStreamTracerArr) {
        return newStream((MethodDescriptor<?, ?>) methodDescriptor, metadata, callOptions, clientStreamTracerArr);
    }

    public static Map<ErrorCode, Status> buildErrorCodeToStatusMap() {
        EnumMap enumMap = new EnumMap(ErrorCode.class);
        ErrorCode errorCode = ErrorCode.NO_ERROR;
        Status status = Status.INTERNAL;
        enumMap.put(errorCode, status.withDescription("No error: A GRPC status of OK should have been sent"));
        enumMap.put(ErrorCode.PROTOCOL_ERROR, status.withDescription("Protocol error"));
        enumMap.put(ErrorCode.INTERNAL_ERROR, status.withDescription("Internal error"));
        enumMap.put(ErrorCode.FLOW_CONTROL_ERROR, status.withDescription("Flow control error"));
        enumMap.put(ErrorCode.STREAM_CLOSED, status.withDescription("Stream closed"));
        enumMap.put(ErrorCode.FRAME_TOO_LARGE, status.withDescription("Frame too large"));
        enumMap.put(ErrorCode.REFUSED_STREAM, Status.UNAVAILABLE.withDescription("Refused stream"));
        enumMap.put(ErrorCode.CANCEL, Status.CANCELLED.withDescription(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_CANCELLED));
        enumMap.put(ErrorCode.COMPRESSION_ERROR, status.withDescription("Compression error"));
        enumMap.put(ErrorCode.CONNECT_ERROR, status.withDescription("Connect error"));
        enumMap.put(ErrorCode.ENHANCE_YOUR_CALM, Status.RESOURCE_EXHAUSTED.withDescription("Enhance your calm"));
        enumMap.put(ErrorCode.INADEQUATE_SECURITY, Status.PERMISSION_DENIED.withDescription("Inadequate security"));
        return Collections.unmodifiableMap(enumMap);
    }

    /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$1 */
    public class AnonymousClass1 extends InUseStateAggregator<OkHttpClientStream> {
        public AnonymousClass1() {
        }

        @Override // io.grpc.internal.InUseStateAggregator
        public void handleInUse() {
            OkHttpClientTransport.this.listener.transportInUse(true);
        }

        @Override // io.grpc.internal.InUseStateAggregator
        public void handleNotInUse() {
            OkHttpClientTransport.this.listener.transportInUse(false);
        }
    }

    public OkHttpClientTransport(OkHttpChannelBuilder.OkHttpTransportFactory okHttpTransportFactory, InetSocketAddress inetSocketAddress, String str, @Nullable String str2, Attributes attributes, @Nullable HttpConnectProxiedSocketAddress httpConnectProxiedSocketAddress, Runnable runnable) {
        this(okHttpTransportFactory, inetSocketAddress, str, str2, attributes, GrpcUtil.STOPWATCH_SUPPLIER, new Http2(), httpConnectProxiedSocketAddress, runnable);
    }

    public OkHttpClientTransport(OkHttpChannelBuilder.OkHttpTransportFactory okHttpTransportFactory, InetSocketAddress inetSocketAddress, String str, @Nullable String str2, Attributes attributes, Supplier<Stopwatch> supplier, Variant variant, @Nullable HttpConnectProxiedSocketAddress httpConnectProxiedSocketAddress, Runnable runnable) {
        this.random = new Random();
        this.lock = new Object();
        this.streams = new HashMap();
        this.maxConcurrentStreams = 0;
        this.pendingStreams = new LinkedList();
        this.inUseState = new InUseStateAggregator<OkHttpClientStream>() { // from class: io.grpc.okhttp.OkHttpClientTransport.1
            public AnonymousClass1() {
            }

            @Override // io.grpc.internal.InUseStateAggregator
            public void handleInUse() {
                OkHttpClientTransport.this.listener.transportInUse(true);
            }

            @Override // io.grpc.internal.InUseStateAggregator
            public void handleNotInUse() {
                OkHttpClientTransport.this.listener.transportInUse(false);
            }
        };
        this.proxySocketTimeout = 30000;
        this.address = (InetSocketAddress) Preconditions.checkNotNull(inetSocketAddress, IntegrityManager.INTEGRITY_TYPE_ADDRESS);
        this.defaultAuthority = str;
        this.maxMessageSize = okHttpTransportFactory.maxMessageSize;
        this.initialWindowSize = okHttpTransportFactory.flowControlWindow;
        this.executor = (Executor) Preconditions.checkNotNull(okHttpTransportFactory.executor, "executor");
        this.serializingExecutor = new SerializingExecutor(okHttpTransportFactory.executor);
        this.scheduler = (ScheduledExecutorService) Preconditions.checkNotNull(okHttpTransportFactory.scheduledExecutorService, "scheduledExecutorService");
        this.nextStreamId = 3;
        SocketFactory socketFactory = okHttpTransportFactory.socketFactory;
        this.socketFactory = socketFactory == null ? SocketFactory.getDefault() : socketFactory;
        this.sslSocketFactory = okHttpTransportFactory.sslSocketFactory;
        this.hostnameVerifier = okHttpTransportFactory.hostnameVerifier;
        this.connectionSpec = (ConnectionSpec) Preconditions.checkNotNull(okHttpTransportFactory.connectionSpec, "connectionSpec");
        this.stopwatchFactory = (Supplier) Preconditions.checkNotNull(supplier, "stopwatchFactory");
        this.variant = (Variant) Preconditions.checkNotNull(variant, "variant");
        this.userAgent = GrpcUtil.getGrpcUserAgent("okhttp", str2);
        this.proxiedAddr = httpConnectProxiedSocketAddress;
        this.tooManyPingsRunnable = (Runnable) Preconditions.checkNotNull(runnable, "tooManyPingsRunnable");
        this.maxInboundMetadataSize = okHttpTransportFactory.maxInboundMetadataSize;
        this.transportTracer = okHttpTransportFactory.transportTracerFactory.create();
        this.logId = InternalLogId.allocate(getClass(), inetSocketAddress.toString());
        this.attributes = Attributes.newBuilder().set(GrpcAttributes.ATTR_CLIENT_EAG_ATTRS, attributes).build();
        this.useGetForSafeMethods = okHttpTransportFactory.useGetForSafeMethods;
        initTransportTracer();
    }

    @VisibleForTesting
    public OkHttpClientTransport(OkHttpChannelBuilder.OkHttpTransportFactory okHttpTransportFactory, String str, Supplier<Stopwatch> supplier, Variant variant, @Nullable Runnable runnable, SettableFuture<Void> settableFuture, Runnable runnable2) {
        this(okHttpTransportFactory, new InetSocketAddress("127.0.0.1", 80), "notarealauthority:80", str, Attributes.EMPTY, supplier, variant, null, runnable2);
        this.connectingCallback = runnable;
        this.connectedFuture = (SettableFuture) Preconditions.checkNotNull(settableFuture, "connectedFuture");
    }

    public boolean isUsingPlaintext() {
        return this.sslSocketFactory == null;
    }

    /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$2 */
    public class AnonymousClass2 implements TransportTracer.FlowControlReader {
        public AnonymousClass2() {
        }

        @Override // io.grpc.internal.TransportTracer.FlowControlReader
        public TransportTracer.FlowControlWindows read() {
            TransportTracer.FlowControlWindows flowControlWindows;
            synchronized (OkHttpClientTransport.this.lock) {
                flowControlWindows = new TransportTracer.FlowControlWindows(OkHttpClientTransport.this.outboundFlow == null ? -1L : OkHttpClientTransport.this.outboundFlow.windowUpdate(null, 0), (long) (OkHttpClientTransport.this.initialWindowSize * 0.5f));
            }
            return flowControlWindows;
        }
    }

    public final void initTransportTracer() {
        synchronized (this.lock) {
            this.transportTracer.setFlowControlWindowReader(new TransportTracer.FlowControlReader() { // from class: io.grpc.okhttp.OkHttpClientTransport.2
                public AnonymousClass2() {
                }

                @Override // io.grpc.internal.TransportTracer.FlowControlReader
                public TransportTracer.FlowControlWindows read() {
                    TransportTracer.FlowControlWindows flowControlWindows;
                    synchronized (OkHttpClientTransport.this.lock) {
                        flowControlWindows = new TransportTracer.FlowControlWindows(OkHttpClientTransport.this.outboundFlow == null ? -1L : OkHttpClientTransport.this.outboundFlow.windowUpdate(null, 0), (long) (OkHttpClientTransport.this.initialWindowSize * 0.5f));
                    }
                    return flowControlWindows;
                }
            });
        }
    }

    public void enableKeepAlive(boolean z, long j, long j2, boolean z2) {
        this.enableKeepAlive = z;
        this.keepAliveTimeNanos = j;
        this.keepAliveTimeoutNanos = j2;
        this.keepAliveWithoutCalls = z2;
    }

    @Override // io.grpc.internal.ClientTransport
    public void ping(ClientTransport.PingCallback pingCallback, Executor executor) {
        long jNextLong;
        synchronized (this.lock) {
            try {
                boolean z = true;
                Preconditions.checkState(this.frameWriter != null);
                if (this.stopped) {
                    Http2Ping.notifyFailed(pingCallback, executor, getPingFailure());
                    return;
                }
                Http2Ping http2Ping = this.ping;
                if (http2Ping != null) {
                    jNextLong = 0;
                    z = false;
                } else {
                    jNextLong = this.random.nextLong();
                    Stopwatch stopwatch = this.stopwatchFactory.get();
                    stopwatch.start();
                    Http2Ping http2Ping2 = new Http2Ping(jNextLong, stopwatch);
                    this.ping = http2Ping2;
                    this.transportTracer.reportKeepAliveSent();
                    http2Ping = http2Ping2;
                }
                if (z) {
                    this.frameWriter.ping(false, (int) (jNextLong >>> 32), (int) jNextLong);
                }
                http2Ping.addCallback(pingCallback, executor);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // io.grpc.internal.ClientTransport
    public OkHttpClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions, ClientStreamTracer[] clientStreamTracerArr) throws Throwable {
        Preconditions.checkNotNull(methodDescriptor, "method");
        Preconditions.checkNotNull(metadata, IOptionConstant.headers);
        StatsTraceContext statsTraceContextNewClientContext = StatsTraceContext.newClientContext(clientStreamTracerArr, getAttributes(), metadata);
        synchronized (this.lock) {
            try {
                try {
                    return new OkHttpClientStream(methodDescriptor, metadata, this.frameWriter, this, this.outboundFlow, this.lock, this.maxMessageSize, this.initialWindowSize, this.defaultAuthority, this.userAgent, statsTraceContextNewClientContext, this.transportTracer, callOptions, this.useGetForSafeMethods);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    @GuardedBy("lock")
    public void streamReadyToStart(OkHttpClientStream okHttpClientStream) {
        if (this.goAwayStatus != null) {
            okHttpClientStream.transportState().transportReportStatus(this.goAwayStatus, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
        } else if (this.streams.size() >= this.maxConcurrentStreams) {
            this.pendingStreams.add(okHttpClientStream);
            setInUse(okHttpClientStream);
        } else {
            startStream(okHttpClientStream);
        }
    }

    @GuardedBy("lock")
    public final void startStream(OkHttpClientStream okHttpClientStream) {
        Preconditions.checkState(okHttpClientStream.transportState().id() == -1, "StreamId already assigned");
        this.streams.put(Integer.valueOf(this.nextStreamId), okHttpClientStream);
        setInUse(okHttpClientStream);
        okHttpClientStream.transportState().start(this.nextStreamId);
        if ((okHttpClientStream.getType() != MethodDescriptor.MethodType.UNARY && okHttpClientStream.getType() != MethodDescriptor.MethodType.SERVER_STREAMING) || okHttpClientStream.useGet()) {
            this.frameWriter.flush();
        }
        int i = this.nextStreamId;
        if (i >= 2147483645) {
            this.nextStreamId = Integer.MAX_VALUE;
            startGoAway(Integer.MAX_VALUE, ErrorCode.NO_ERROR, Status.UNAVAILABLE.withDescription("Stream ids exhausted"));
        } else {
            this.nextStreamId = i + 2;
        }
    }

    @GuardedBy("lock")
    public final boolean startPendingStreams() {
        boolean z = false;
        while (!this.pendingStreams.isEmpty() && this.streams.size() < this.maxConcurrentStreams) {
            startStream(this.pendingStreams.poll());
            z = true;
        }
        return z;
    }

    @GuardedBy("lock")
    public void removePendingStream(OkHttpClientStream okHttpClientStream) {
        this.pendingStreams.remove(okHttpClientStream);
        maybeClearInUse(okHttpClientStream);
    }

    @Override // io.grpc.internal.ManagedClientTransport
    public Runnable start(ManagedClientTransport.Listener listener) {
        this.listener = (ManagedClientTransport.Listener) Preconditions.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        if (this.enableKeepAlive) {
            KeepAliveManager keepAliveManager = new KeepAliveManager(new KeepAliveManager.ClientKeepAlivePinger(this), this.scheduler, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls);
            this.keepAliveManager = keepAliveManager;
            keepAliveManager.onTransportStarted();
        }
        AsyncSink asyncSinkSink = AsyncSink.sink(this.serializingExecutor, this, 10000);
        FrameWriter frameWriterLimitControlFramesWriter = asyncSinkSink.limitControlFramesWriter(this.variant.newWriter(Okio.buffer(asyncSinkSink), true));
        synchronized (this.lock) {
            ExceptionHandlingFrameWriter exceptionHandlingFrameWriter = new ExceptionHandlingFrameWriter(this, frameWriterLimitControlFramesWriter);
            this.frameWriter = exceptionHandlingFrameWriter;
            this.outboundFlow = new OutboundFlowController(this, exceptionHandlingFrameWriter);
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.serializingExecutor.execute(new Runnable() { // from class: io.grpc.okhttp.OkHttpClientTransport.3
            public final /* synthetic */ AsyncSink val$asyncSink;
            public final /* synthetic */ CountDownLatch val$latch;

            public AnonymousClass3(CountDownLatch countDownLatch2, AsyncSink asyncSinkSink2) {
                countDownLatch = countDownLatch2;
                asyncSink = asyncSinkSink2;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // java.lang.Runnable
            public void run() {
                OkHttpClientTransport okHttpClientTransport;
                ClientFrameHandler clientFrameHandler;
                Socket socketCreateHttpProxySocket;
                SSLSession session;
                Socket socket;
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
                BufferedSource bufferedSourceBuffer = Okio.buffer(new Source() { // from class: io.grpc.okhttp.OkHttpClientTransport.3.1
                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
                    public void close() {
                    }

                    @Override // okio.Source
                    public long read(Buffer buffer, long j) {
                        return -1L;
                    }

                    public AnonymousClass1() {
                    }

                    @Override // okio.Source
                    public Timeout timeout() {
                        return Timeout.NONE;
                    }
                });
                try {
                    try {
                        OkHttpClientTransport okHttpClientTransport2 = OkHttpClientTransport.this;
                        HttpConnectProxiedSocketAddress httpConnectProxiedSocketAddress = okHttpClientTransport2.proxiedAddr;
                        if (httpConnectProxiedSocketAddress == null) {
                            socketCreateHttpProxySocket = okHttpClientTransport2.socketFactory.createSocket(OkHttpClientTransport.this.address.getAddress(), OkHttpClientTransport.this.address.getPort());
                        } else if (httpConnectProxiedSocketAddress.getProxyAddress() instanceof InetSocketAddress) {
                            OkHttpClientTransport okHttpClientTransport3 = OkHttpClientTransport.this;
                            socketCreateHttpProxySocket = okHttpClientTransport3.createHttpProxySocket(okHttpClientTransport3.proxiedAddr.getTargetAddress(), (InetSocketAddress) OkHttpClientTransport.this.proxiedAddr.getProxyAddress(), OkHttpClientTransport.this.proxiedAddr.getUsername(), OkHttpClientTransport.this.proxiedAddr.getPassword());
                        } else {
                            throw Status.INTERNAL.withDescription("Unsupported SocketAddress implementation " + OkHttpClientTransport.this.proxiedAddr.getProxyAddress().getClass()).asException();
                        }
                        Socket socket2 = socketCreateHttpProxySocket;
                        if (OkHttpClientTransport.this.sslSocketFactory != null) {
                            SSLSocket sSLSocketUpgrade = OkHttpTlsUpgrader.upgrade(OkHttpClientTransport.this.sslSocketFactory, OkHttpClientTransport.this.hostnameVerifier, socket2, OkHttpClientTransport.this.getOverridenHost(), OkHttpClientTransport.this.getOverridenPort(), OkHttpClientTransport.this.connectionSpec);
                            session = sSLSocketUpgrade.getSession();
                            socket = sSLSocketUpgrade;
                        } else {
                            session = null;
                            socket = socket2;
                        }
                        socket.setTcpNoDelay(true);
                        BufferedSource bufferedSourceBuffer2 = Okio.buffer(Okio.source(socket));
                        asyncSink.becomeConnected(Okio.sink(socket), socket);
                        OkHttpClientTransport okHttpClientTransport4 = OkHttpClientTransport.this;
                        okHttpClientTransport4.attributes = okHttpClientTransport4.attributes.toBuilder().set(Grpc.TRANSPORT_ATTR_REMOTE_ADDR, socket.getRemoteSocketAddress()).set(Grpc.TRANSPORT_ATTR_LOCAL_ADDR, socket.getLocalSocketAddress()).set(Grpc.TRANSPORT_ATTR_SSL_SESSION, session).set(GrpcAttributes.ATTR_SECURITY_LEVEL, session == null ? SecurityLevel.NONE : SecurityLevel.PRIVACY_AND_INTEGRITY).build();
                        OkHttpClientTransport okHttpClientTransport5 = OkHttpClientTransport.this;
                        okHttpClientTransport5.clientFrameHandler = okHttpClientTransport5.new ClientFrameHandler(okHttpClientTransport5.variant.newReader(bufferedSourceBuffer2, true));
                        synchronized (OkHttpClientTransport.this.lock) {
                            try {
                                OkHttpClientTransport.this.socket = (Socket) Preconditions.checkNotNull(socket, "socket");
                                if (session != null) {
                                    OkHttpClientTransport.this.securityInfo = new InternalChannelz.Security(new InternalChannelz.Tls(session));
                                }
                            } finally {
                            }
                        }
                    } catch (StatusException e) {
                        OkHttpClientTransport.this.startGoAway(0, ErrorCode.INTERNAL_ERROR, e.getStatus());
                        okHttpClientTransport = OkHttpClientTransport.this;
                        clientFrameHandler = okHttpClientTransport.new ClientFrameHandler(okHttpClientTransport.variant.newReader(bufferedSourceBuffer, true));
                        okHttpClientTransport.clientFrameHandler = clientFrameHandler;
                    } catch (Exception e2) {
                        OkHttpClientTransport.this.onException(e2);
                        okHttpClientTransport = OkHttpClientTransport.this;
                        clientFrameHandler = okHttpClientTransport.new ClientFrameHandler(okHttpClientTransport.variant.newReader(bufferedSourceBuffer, true));
                        okHttpClientTransport.clientFrameHandler = clientFrameHandler;
                    }
                } catch (Throwable th) {
                    OkHttpClientTransport okHttpClientTransport6 = OkHttpClientTransport.this;
                    okHttpClientTransport6.clientFrameHandler = okHttpClientTransport6.new ClientFrameHandler(okHttpClientTransport6.variant.newReader(bufferedSourceBuffer, true));
                    throw th;
                }
            }

            /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$3$1 */
            public class AnonymousClass1 implements Source {
                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                }

                @Override // okio.Source
                public long read(Buffer buffer, long j) {
                    return -1L;
                }

                public AnonymousClass1() {
                }

                @Override // okio.Source
                public Timeout timeout() {
                    return Timeout.NONE;
                }
            }
        });
        try {
            sendConnectionPrefaceAndSettings();
            countDownLatch2.countDown();
            this.serializingExecutor.execute(new Runnable() { // from class: io.grpc.okhttp.OkHttpClientTransport.4
                public AnonymousClass4() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    Runnable runnable = OkHttpClientTransport.this.connectingCallback;
                    if (runnable != null) {
                        runnable.run();
                    }
                    OkHttpClientTransport.this.executor.execute(OkHttpClientTransport.this.clientFrameHandler);
                    synchronized (OkHttpClientTransport.this.lock) {
                        OkHttpClientTransport.this.maxConcurrentStreams = Integer.MAX_VALUE;
                        OkHttpClientTransport.this.startPendingStreams();
                    }
                    SettableFuture<Void> settableFuture = OkHttpClientTransport.this.connectedFuture;
                    if (settableFuture != null) {
                        settableFuture.set(null);
                    }
                }
            });
            return null;
        } catch (Throwable th) {
            countDownLatch2.countDown();
            throw th;
        }
    }

    /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$3 */
    public class AnonymousClass3 implements Runnable {
        public final /* synthetic */ AsyncSink val$asyncSink;
        public final /* synthetic */ CountDownLatch val$latch;

        public AnonymousClass3(CountDownLatch countDownLatch2, AsyncSink asyncSinkSink2) {
            countDownLatch = countDownLatch2;
            asyncSink = asyncSinkSink2;
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // java.lang.Runnable
        public void run() {
            OkHttpClientTransport okHttpClientTransport;
            ClientFrameHandler clientFrameHandler;
            Socket socketCreateHttpProxySocket;
            SSLSession session;
            Socket socket;
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            BufferedSource bufferedSourceBuffer = Okio.buffer(new Source() { // from class: io.grpc.okhttp.OkHttpClientTransport.3.1
                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                }

                @Override // okio.Source
                public long read(Buffer buffer, long j) {
                    return -1L;
                }

                public AnonymousClass1() {
                }

                @Override // okio.Source
                public Timeout timeout() {
                    return Timeout.NONE;
                }
            });
            try {
                try {
                    OkHttpClientTransport okHttpClientTransport2 = OkHttpClientTransport.this;
                    HttpConnectProxiedSocketAddress httpConnectProxiedSocketAddress = okHttpClientTransport2.proxiedAddr;
                    if (httpConnectProxiedSocketAddress == null) {
                        socketCreateHttpProxySocket = okHttpClientTransport2.socketFactory.createSocket(OkHttpClientTransport.this.address.getAddress(), OkHttpClientTransport.this.address.getPort());
                    } else if (httpConnectProxiedSocketAddress.getProxyAddress() instanceof InetSocketAddress) {
                        OkHttpClientTransport okHttpClientTransport3 = OkHttpClientTransport.this;
                        socketCreateHttpProxySocket = okHttpClientTransport3.createHttpProxySocket(okHttpClientTransport3.proxiedAddr.getTargetAddress(), (InetSocketAddress) OkHttpClientTransport.this.proxiedAddr.getProxyAddress(), OkHttpClientTransport.this.proxiedAddr.getUsername(), OkHttpClientTransport.this.proxiedAddr.getPassword());
                    } else {
                        throw Status.INTERNAL.withDescription("Unsupported SocketAddress implementation " + OkHttpClientTransport.this.proxiedAddr.getProxyAddress().getClass()).asException();
                    }
                    Socket socket2 = socketCreateHttpProxySocket;
                    if (OkHttpClientTransport.this.sslSocketFactory != null) {
                        SSLSocket sSLSocketUpgrade = OkHttpTlsUpgrader.upgrade(OkHttpClientTransport.this.sslSocketFactory, OkHttpClientTransport.this.hostnameVerifier, socket2, OkHttpClientTransport.this.getOverridenHost(), OkHttpClientTransport.this.getOverridenPort(), OkHttpClientTransport.this.connectionSpec);
                        session = sSLSocketUpgrade.getSession();
                        socket = sSLSocketUpgrade;
                    } else {
                        session = null;
                        socket = socket2;
                    }
                    socket.setTcpNoDelay(true);
                    BufferedSource bufferedSourceBuffer2 = Okio.buffer(Okio.source(socket));
                    asyncSink.becomeConnected(Okio.sink(socket), socket);
                    OkHttpClientTransport okHttpClientTransport4 = OkHttpClientTransport.this;
                    okHttpClientTransport4.attributes = okHttpClientTransport4.attributes.toBuilder().set(Grpc.TRANSPORT_ATTR_REMOTE_ADDR, socket.getRemoteSocketAddress()).set(Grpc.TRANSPORT_ATTR_LOCAL_ADDR, socket.getLocalSocketAddress()).set(Grpc.TRANSPORT_ATTR_SSL_SESSION, session).set(GrpcAttributes.ATTR_SECURITY_LEVEL, session == null ? SecurityLevel.NONE : SecurityLevel.PRIVACY_AND_INTEGRITY).build();
                    OkHttpClientTransport okHttpClientTransport5 = OkHttpClientTransport.this;
                    okHttpClientTransport5.clientFrameHandler = okHttpClientTransport5.new ClientFrameHandler(okHttpClientTransport5.variant.newReader(bufferedSourceBuffer2, true));
                    synchronized (OkHttpClientTransport.this.lock) {
                        try {
                            OkHttpClientTransport.this.socket = (Socket) Preconditions.checkNotNull(socket, "socket");
                            if (session != null) {
                                OkHttpClientTransport.this.securityInfo = new InternalChannelz.Security(new InternalChannelz.Tls(session));
                            }
                        } finally {
                        }
                    }
                } catch (StatusException e) {
                    OkHttpClientTransport.this.startGoAway(0, ErrorCode.INTERNAL_ERROR, e.getStatus());
                    okHttpClientTransport = OkHttpClientTransport.this;
                    clientFrameHandler = okHttpClientTransport.new ClientFrameHandler(okHttpClientTransport.variant.newReader(bufferedSourceBuffer, true));
                    okHttpClientTransport.clientFrameHandler = clientFrameHandler;
                } catch (Exception e2) {
                    OkHttpClientTransport.this.onException(e2);
                    okHttpClientTransport = OkHttpClientTransport.this;
                    clientFrameHandler = okHttpClientTransport.new ClientFrameHandler(okHttpClientTransport.variant.newReader(bufferedSourceBuffer, true));
                    okHttpClientTransport.clientFrameHandler = clientFrameHandler;
                }
            } catch (Throwable th) {
                OkHttpClientTransport okHttpClientTransport6 = OkHttpClientTransport.this;
                okHttpClientTransport6.clientFrameHandler = okHttpClientTransport6.new ClientFrameHandler(okHttpClientTransport6.variant.newReader(bufferedSourceBuffer, true));
                throw th;
            }
        }

        /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$3$1 */
        public class AnonymousClass1 implements Source {
            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // okio.Source
            public long read(Buffer buffer, long j) {
                return -1L;
            }

            public AnonymousClass1() {
            }

            @Override // okio.Source
            public Timeout timeout() {
                return Timeout.NONE;
            }
        }
    }

    /* JADX INFO: renamed from: io.grpc.okhttp.OkHttpClientTransport$4 */
    public class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = OkHttpClientTransport.this.connectingCallback;
            if (runnable != null) {
                runnable.run();
            }
            OkHttpClientTransport.this.executor.execute(OkHttpClientTransport.this.clientFrameHandler);
            synchronized (OkHttpClientTransport.this.lock) {
                OkHttpClientTransport.this.maxConcurrentStreams = Integer.MAX_VALUE;
                OkHttpClientTransport.this.startPendingStreams();
            }
            SettableFuture<Void> settableFuture = OkHttpClientTransport.this.connectedFuture;
            if (settableFuture != null) {
                settableFuture.set(null);
            }
        }
    }

    public final void sendConnectionPrefaceAndSettings() {
        synchronized (this.lock) {
            try {
                this.frameWriter.connectionPreface();
                Settings settings = new Settings();
                OkHttpSettingsUtil.set(settings, 7, this.initialWindowSize);
                this.frameWriter.settings(settings);
                if (this.initialWindowSize > 65535) {
                    this.frameWriter.windowUpdate(0, r1 - 65535);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Socket createHttpProxySocket(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, String str, String str2) throws StatusException {
        Socket socketCreateSocket;
        Socket socket = null;
        try {
            if (inetSocketAddress2.getAddress() != null) {
                socketCreateSocket = this.socketFactory.createSocket(inetSocketAddress2.getAddress(), inetSocketAddress2.getPort());
            } else {
                socketCreateSocket = this.socketFactory.createSocket(inetSocketAddress2.getHostName(), inetSocketAddress2.getPort());
            }
            socket = socketCreateSocket;
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(this.proxySocketTimeout);
            Source source = Okio.source(socket);
            BufferedSink bufferedSinkBuffer = Okio.buffer(Okio.sink(socket));
            Request requestCreateHttpProxyRequest = createHttpProxyRequest(inetSocketAddress, str, str2);
            HttpUrl httpUrl = requestCreateHttpProxyRequest.httpUrl();
            bufferedSinkBuffer.writeUtf8(String.format(Locale.US, "CONNECT %s:%d HTTP/1.1", httpUrl.host(), Integer.valueOf(httpUrl.port()))).writeUtf8(HttpClient.MULTIPART_NEWLINE);
            int size = requestCreateHttpProxyRequest.headers().size();
            for (int i = 0; i < size; i++) {
                bufferedSinkBuffer.writeUtf8(requestCreateHttpProxyRequest.headers().name(i)).writeUtf8(": ").writeUtf8(requestCreateHttpProxyRequest.headers().value(i)).writeUtf8(HttpClient.MULTIPART_NEWLINE);
            }
            bufferedSinkBuffer.writeUtf8(HttpClient.MULTIPART_NEWLINE);
            bufferedSinkBuffer.flush();
            StatusLine statusLine = StatusLine.parse(readUtf8LineStrictUnbuffered(source));
            while (!readUtf8LineStrictUnbuffered(source).equals("")) {
            }
            int i2 = statusLine.code;
            if (i2 < 200 || i2 >= 300) {
                Buffer buffer = new Buffer();
                try {
                    socket.shutdownOutput();
                    source.read(buffer, 1024L);
                } catch (IOException e) {
                    buffer.writeUtf8("Unable to read body: " + e.toString());
                }
                try {
                    socket.close();
                } catch (IOException unused) {
                }
                throw Status.UNAVAILABLE.withDescription(String.format(Locale.US, "Response returned from proxy was not successful (expected 2xx, got %d %s). Response body:\n%s", Integer.valueOf(statusLine.code), statusLine.message, buffer.readUtf8())).asException();
            }
            socket.setSoTimeout(0);
            return socket;
        } catch (IOException e2) {
            if (socket != null) {
                GrpcUtil.closeQuietly(socket);
            }
            throw Status.UNAVAILABLE.withDescription("Failed trying to connect with proxy").withCause(e2).asException();
        }
    }

    public final Request createHttpProxyRequest(InetSocketAddress inetSocketAddress, String str, String str2) {
        HttpUrl httpUrlBuild = new HttpUrl.Builder().scheme("https").host(inetSocketAddress.getHostName()).port(inetSocketAddress.getPort()).build();
        Request.Builder builderHeader = new Request.Builder().url(httpUrlBuild).header("Host", httpUrlBuild.host() + ":" + httpUrlBuild.port()).header("User-Agent", this.userAgent);
        if (str != null && str2 != null) {
            builderHeader.header("Proxy-Authorization", Credentials.basic(str, str2));
        }
        return builderHeader.build();
    }

    public static String readUtf8LineStrictUnbuffered(Source source) throws IOException {
        Buffer buffer = new Buffer();
        while (source.read(buffer, 1L) != -1) {
            if (buffer.getByte(buffer.size() - 1) == 10) {
                return buffer.readUtf8LineStrict();
            }
        }
        throw new EOFException("\\n not found: " + buffer.readByteString().hex());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add(IntegrityManager.INTEGRITY_TYPE_ADDRESS, this.address).toString();
    }

    @Override // io.grpc.InternalWithLogId
    public InternalLogId getLogId() {
        return this.logId;
    }

    @VisibleForTesting
    public String getOverridenHost() {
        URI uriAuthorityToUri = GrpcUtil.authorityToUri(this.defaultAuthority);
        if (uriAuthorityToUri.getHost() != null) {
            return uriAuthorityToUri.getHost();
        }
        return this.defaultAuthority;
    }

    @VisibleForTesting
    public int getOverridenPort() {
        URI uriAuthorityToUri = GrpcUtil.authorityToUri(this.defaultAuthority);
        if (uriAuthorityToUri.getPort() != -1) {
            return uriAuthorityToUri.getPort();
        }
        return this.address.getPort();
    }

    @Override // io.grpc.internal.ManagedClientTransport
    public void shutdown(Status status) {
        synchronized (this.lock) {
            try {
                if (this.goAwayStatus != null) {
                    return;
                }
                this.goAwayStatus = status;
                this.listener.transportShutdown(status);
                stopIfNecessary();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // io.grpc.internal.ManagedClientTransport
    public void shutdownNow(Status status) {
        shutdown(status);
        synchronized (this.lock) {
            try {
                Iterator<Map.Entry<Integer, OkHttpClientStream>> it = this.streams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, OkHttpClientStream> next = it.next();
                    it.remove();
                    next.getValue().transportState().transportReportStatus(status, false, new Metadata());
                    maybeClearInUse(next.getValue());
                }
                for (OkHttpClientStream okHttpClientStream : this.pendingStreams) {
                    okHttpClientStream.transportState().transportReportStatus(status, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                    maybeClearInUse(okHttpClientStream);
                }
                this.pendingStreams.clear();
                stopIfNecessary();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // io.grpc.internal.ConnectionClientTransport
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override // io.grpc.okhttp.OutboundFlowController.Transport
    public OutboundFlowController.StreamState[] getActiveStreams() {
        OutboundFlowController.StreamState[] streamStateArr;
        synchronized (this.lock) {
            try {
                streamStateArr = new OutboundFlowController.StreamState[this.streams.size()];
                Iterator<OkHttpClientStream> it = this.streams.values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    streamStateArr[i] = it.next().transportState().getOutboundFlowState();
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return streamStateArr;
    }

    @VisibleForTesting
    public ClientFrameHandler getHandler() {
        return this.clientFrameHandler;
    }

    @VisibleForTesting
    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    @VisibleForTesting
    public int getPendingStreamSize() {
        int size;
        synchronized (this.lock) {
            size = this.pendingStreams.size();
        }
        return size;
    }

    @VisibleForTesting
    public void setNextStreamId(int i) {
        synchronized (this.lock) {
            this.nextStreamId = i;
        }
    }

    @Override // io.grpc.okhttp.ExceptionHandlingFrameWriter.TransportExceptionHandler
    public void onException(Throwable th) {
        Preconditions.checkNotNull(th, "failureCause");
        startGoAway(0, ErrorCode.INTERNAL_ERROR, Status.UNAVAILABLE.withCause(th));
    }

    public final void onError(ErrorCode errorCode, String str) {
        startGoAway(0, errorCode, toGrpcStatus(errorCode).augmentDescription(str));
    }

    public final void startGoAway(int i, ErrorCode errorCode, Status status) {
        synchronized (this.lock) {
            try {
                if (this.goAwayStatus == null) {
                    this.goAwayStatus = status;
                    this.listener.transportShutdown(status);
                }
                if (errorCode != null && !this.goAwaySent) {
                    this.goAwaySent = true;
                    this.frameWriter.goAway(0, errorCode, new byte[0]);
                }
                Iterator<Map.Entry<Integer, OkHttpClientStream>> it = this.streams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, OkHttpClientStream> next = it.next();
                    if (next.getKey().intValue() > i) {
                        it.remove();
                        next.getValue().transportState().transportReportStatus(status, ClientStreamListener.RpcProgress.REFUSED, false, new Metadata());
                        maybeClearInUse(next.getValue());
                    }
                }
                for (OkHttpClientStream okHttpClientStream : this.pendingStreams) {
                    okHttpClientStream.transportState().transportReportStatus(status, ClientStreamListener.RpcProgress.MISCARRIED, true, new Metadata());
                    maybeClearInUse(okHttpClientStream);
                }
                this.pendingStreams.clear();
                stopIfNecessary();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void finishStream(int i, @Nullable Status status, ClientStreamListener.RpcProgress rpcProgress, boolean z, @Nullable ErrorCode errorCode, @Nullable Metadata metadata) {
        synchronized (this.lock) {
            try {
                OkHttpClientStream okHttpClientStreamRemove = this.streams.remove(Integer.valueOf(i));
                if (okHttpClientStreamRemove != null) {
                    if (errorCode != null) {
                        this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                    }
                    if (status != null) {
                        OkHttpClientStream.TransportState transportState = okHttpClientStreamRemove.transportState();
                        if (metadata == null) {
                            metadata = new Metadata();
                        }
                        transportState.transportReportStatus(status, rpcProgress, z, metadata);
                    }
                    if (!startPendingStreams()) {
                        stopIfNecessary();
                        maybeClearInUse(okHttpClientStreamRemove);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @GuardedBy("lock")
    public final void stopIfNecessary() {
        if (this.goAwayStatus == null || !this.streams.isEmpty() || !this.pendingStreams.isEmpty() || this.stopped) {
            return;
        }
        this.stopped = true;
        KeepAliveManager keepAliveManager = this.keepAliveManager;
        if (keepAliveManager != null) {
            keepAliveManager.onTransportTermination();
        }
        Http2Ping http2Ping = this.ping;
        if (http2Ping != null) {
            http2Ping.failed(getPingFailure());
            this.ping = null;
        }
        if (!this.goAwaySent) {
            this.goAwaySent = true;
            this.frameWriter.goAway(0, ErrorCode.NO_ERROR, new byte[0]);
        }
        this.frameWriter.close();
    }

    @GuardedBy("lock")
    public final void maybeClearInUse(OkHttpClientStream okHttpClientStream) {
        if (this.hasStream && this.pendingStreams.isEmpty() && this.streams.isEmpty()) {
            this.hasStream = false;
            KeepAliveManager keepAliveManager = this.keepAliveManager;
            if (keepAliveManager != null) {
                keepAliveManager.onTransportIdle();
            }
        }
        if (okHttpClientStream.shouldBeCountedForInUse()) {
            this.inUseState.updateObjectInUse(okHttpClientStream, false);
        }
    }

    @GuardedBy("lock")
    public final void setInUse(OkHttpClientStream okHttpClientStream) {
        if (!this.hasStream) {
            this.hasStream = true;
            KeepAliveManager keepAliveManager = this.keepAliveManager;
            if (keepAliveManager != null) {
                keepAliveManager.onTransportActive();
            }
        }
        if (okHttpClientStream.shouldBeCountedForInUse()) {
            this.inUseState.updateObjectInUse(okHttpClientStream, true);
        }
    }

    public final Throwable getPingFailure() {
        synchronized (this.lock) {
            try {
                Status status = this.goAwayStatus;
                if (status != null) {
                    return status.asException();
                }
                return Status.UNAVAILABLE.withDescription("Connection closed").asException();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean mayHaveCreatedStream(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.lock
            monitor-enter(r0)
            int r1 = r2.nextStreamId     // Catch: java.lang.Throwable -> Lf
            if (r3 >= r1) goto Lc
            r1 = 1
            r3 = r3 & r1
            if (r3 != r1) goto Lc
            goto Ld
        Lc:
            r1 = 0
        Ld:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            return r1
        Lf:
            r3 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpClientTransport.mayHaveCreatedStream(int):boolean");
    }

    public OkHttpClientStream getStream(int i) {
        OkHttpClientStream okHttpClientStream;
        synchronized (this.lock) {
            okHttpClientStream = this.streams.get(Integer.valueOf(i));
        }
        return okHttpClientStream;
    }

    @VisibleForTesting
    public static Status toGrpcStatus(ErrorCode errorCode) {
        Status status = ERROR_CODE_TO_STATUS.get(errorCode);
        if (status != null) {
            return status;
        }
        return Status.UNKNOWN.withDescription("Unknown http2 error code: " + errorCode.httpCode);
    }

    @Override // io.grpc.InternalInstrumented
    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        SettableFuture settableFutureCreate = SettableFuture.create();
        synchronized (this.lock) {
            try {
                if (this.socket == null) {
                    settableFutureCreate.set(new InternalChannelz.SocketStats(this.transportTracer.getStats(), null, null, new InternalChannelz.SocketOptions.Builder().build(), null));
                } else {
                    settableFutureCreate.set(new InternalChannelz.SocketStats(this.transportTracer.getStats(), this.socket.getLocalSocketAddress(), this.socket.getRemoteSocketAddress(), Utils.getSocketOptions(this.socket), this.securityInfo));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return settableFutureCreate;
    }

    public class ClientFrameHandler implements FrameReader.Handler, Runnable {
        public FrameReader frameReader;
        public final OkHttpFrameLogger logger = new OkHttpFrameLogger(Level.FINE, (Class<?>) OkHttpClientTransport.class);
        public boolean firstSettings = true;

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void ackSettings() {
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void priority(int i, int i2, int i3, boolean z) {
        }

        public ClientFrameHandler(FrameReader frameReader) {
            this.frameReader = frameReader;
        }

        @Override // java.lang.Runnable
        public void run() {
            Status statusWithDescription;
            String name = Thread.currentThread().getName();
            Thread.currentThread().setName("OkHttpClientTransport");
            while (this.frameReader.nextFrame(this)) {
                try {
                    if (OkHttpClientTransport.this.keepAliveManager != null) {
                        OkHttpClientTransport.this.keepAliveManager.onDataReceived();
                    }
                } catch (Throwable th) {
                    try {
                        OkHttpClientTransport.this.startGoAway(0, ErrorCode.PROTOCOL_ERROR, Status.INTERNAL.withDescription("error in frame handler").withCause(th));
                        try {
                            this.frameReader.close();
                        } catch (IOException e) {
                            e = e;
                            OkHttpClientTransport.log.log(Level.INFO, "Exception closing frame reader", (Throwable) e);
                        }
                    } catch (Throwable th2) {
                        try {
                            this.frameReader.close();
                        } catch (IOException e2) {
                            OkHttpClientTransport.log.log(Level.INFO, "Exception closing frame reader", (Throwable) e2);
                        }
                        OkHttpClientTransport.this.listener.transportTerminated();
                        Thread.currentThread().setName(name);
                        throw th2;
                    }
                }
            }
            synchronized (OkHttpClientTransport.this.lock) {
                statusWithDescription = OkHttpClientTransport.this.goAwayStatus;
            }
            if (statusWithDescription == null) {
                statusWithDescription = Status.UNAVAILABLE.withDescription("End of stream or IOException");
            }
            OkHttpClientTransport.this.startGoAway(0, ErrorCode.INTERNAL_ERROR, statusWithDescription);
            try {
                this.frameReader.close();
            } catch (IOException e3) {
                e = e3;
                OkHttpClientTransport.log.log(Level.INFO, "Exception closing frame reader", (Throwable) e);
            }
            OkHttpClientTransport.this.listener.transportTerminated();
            Thread.currentThread().setName(name);
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void data(boolean z, int i, BufferedSource bufferedSource, int i2) throws IOException {
            this.logger.logData(OkHttpFrameLogger.Direction.INBOUND, i, bufferedSource.getBuffer(), i2, z);
            OkHttpClientStream stream = OkHttpClientTransport.this.getStream(i);
            if (stream == null) {
                if (OkHttpClientTransport.this.mayHaveCreatedStream(i)) {
                    synchronized (OkHttpClientTransport.this.lock) {
                        OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.STREAM_CLOSED);
                    }
                    bufferedSource.skip(i2);
                } else {
                    OkHttpClientTransport.this.onError(ErrorCode.PROTOCOL_ERROR, "Received data for unknown stream: " + i);
                    return;
                }
            } else {
                long j = i2;
                bufferedSource.require(j);
                Buffer buffer = new Buffer();
                buffer.write(bufferedSource.getBuffer(), j);
                PerfMark.event("OkHttpClientTransport$ClientFrameHandler.data", stream.transportState().tag());
                synchronized (OkHttpClientTransport.this.lock) {
                    stream.transportState().transportDataReceived(buffer, z);
                }
            }
            OkHttpClientTransport.access$2412(OkHttpClientTransport.this, i2);
            if (OkHttpClientTransport.this.connectionUnacknowledgedBytesRead >= OkHttpClientTransport.this.initialWindowSize * 0.5f) {
                synchronized (OkHttpClientTransport.this.lock) {
                    OkHttpClientTransport.this.frameWriter.windowUpdate(0, OkHttpClientTransport.this.connectionUnacknowledgedBytesRead);
                }
                OkHttpClientTransport.this.connectionUnacknowledgedBytesRead = 0;
            }
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void headers(boolean z, boolean z2, int i, int i2, List<Header> list, HeadersMode headersMode) {
            Status statusWithDescription;
            int iHeaderBlockSize;
            boolean z3 = true;
            this.logger.logHeaders(OkHttpFrameLogger.Direction.INBOUND, i, list, z2);
            if (OkHttpClientTransport.this.maxInboundMetadataSize == Integer.MAX_VALUE || (iHeaderBlockSize = headerBlockSize(list)) <= OkHttpClientTransport.this.maxInboundMetadataSize) {
                statusWithDescription = null;
            } else {
                statusWithDescription = Status.RESOURCE_EXHAUSTED.withDescription(String.format(Locale.US, "Response %s metadata larger than %d: %d", z2 ? "trailer" : "header", Integer.valueOf(OkHttpClientTransport.this.maxInboundMetadataSize), Integer.valueOf(iHeaderBlockSize)));
            }
            synchronized (OkHttpClientTransport.this.lock) {
                try {
                    OkHttpClientStream okHttpClientStream = (OkHttpClientStream) OkHttpClientTransport.this.streams.get(Integer.valueOf(i));
                    if (okHttpClientStream == null) {
                        if (OkHttpClientTransport.this.mayHaveCreatedStream(i)) {
                            OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.STREAM_CLOSED);
                        }
                    } else if (statusWithDescription == null) {
                        PerfMark.event("OkHttpClientTransport$ClientFrameHandler.headers", okHttpClientStream.transportState().tag());
                        okHttpClientStream.transportState().transportHeadersReceived(list, z2);
                    } else {
                        if (!z2) {
                            OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                        }
                        okHttpClientStream.transportState().transportReportStatus(statusWithDescription, false, new Metadata());
                    }
                    z3 = false;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z3) {
                OkHttpClientTransport.this.onError(ErrorCode.PROTOCOL_ERROR, "Received header for unknown stream: " + i);
            }
        }

        public final int headerBlockSize(List<Header> list) {
            long size = 0;
            for (int i = 0; i < list.size(); i++) {
                Header header = list.get(i);
                size += (long) (header.name.size() + 32 + header.value.size());
            }
            return (int) Math.min(size, 2147483647L);
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void rstStream(int i, ErrorCode errorCode) {
            this.logger.logRstStream(OkHttpFrameLogger.Direction.INBOUND, i, errorCode);
            Status statusAugmentDescription = OkHttpClientTransport.toGrpcStatus(errorCode).augmentDescription("Rst Stream");
            boolean z = statusAugmentDescription.getCode() == Status.Code.CANCELLED || statusAugmentDescription.getCode() == Status.Code.DEADLINE_EXCEEDED;
            synchronized (OkHttpClientTransport.this.lock) {
                try {
                    OkHttpClientStream okHttpClientStream = (OkHttpClientStream) OkHttpClientTransport.this.streams.get(Integer.valueOf(i));
                    if (okHttpClientStream != null) {
                        PerfMark.event("OkHttpClientTransport$ClientFrameHandler.rstStream", okHttpClientStream.transportState().tag());
                        OkHttpClientTransport.this.finishStream(i, statusAugmentDescription, errorCode == ErrorCode.REFUSED_STREAM ? ClientStreamListener.RpcProgress.REFUSED : ClientStreamListener.RpcProgress.PROCESSED, z, null, null);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void settings(boolean z, Settings settings) {
            boolean zInitialOutboundWindowSize;
            this.logger.logSettings(OkHttpFrameLogger.Direction.INBOUND, settings);
            synchronized (OkHttpClientTransport.this.lock) {
                try {
                    if (OkHttpSettingsUtil.isSet(settings, 4)) {
                        OkHttpClientTransport.this.maxConcurrentStreams = OkHttpSettingsUtil.get(settings, 4);
                    }
                    if (OkHttpSettingsUtil.isSet(settings, 7)) {
                        zInitialOutboundWindowSize = OkHttpClientTransport.this.outboundFlow.initialOutboundWindowSize(OkHttpSettingsUtil.get(settings, 7));
                    } else {
                        zInitialOutboundWindowSize = false;
                    }
                    if (this.firstSettings) {
                        OkHttpClientTransport.this.listener.transportReady();
                        this.firstSettings = false;
                    }
                    OkHttpClientTransport.this.frameWriter.ackSettings(settings);
                    if (zInitialOutboundWindowSize) {
                        OkHttpClientTransport.this.outboundFlow.writeStreams();
                    }
                    OkHttpClientTransport.this.startPendingStreams();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void ping(boolean z, int i, int i2) {
            Http2Ping http2Ping;
            long j = (((long) i) << 32) | (((long) i2) & InternalZipConstants.ZIP_64_SIZE_LIMIT);
            this.logger.logPing(OkHttpFrameLogger.Direction.INBOUND, j);
            if (!z) {
                synchronized (OkHttpClientTransport.this.lock) {
                    OkHttpClientTransport.this.frameWriter.ping(true, i, i2);
                }
                return;
            }
            synchronized (OkHttpClientTransport.this.lock) {
                try {
                    http2Ping = null;
                    if (OkHttpClientTransport.this.ping == null) {
                        OkHttpClientTransport.log.warning("Received unexpected ping ack. No ping outstanding");
                    } else if (OkHttpClientTransport.this.ping.payload() == j) {
                        Http2Ping http2Ping2 = OkHttpClientTransport.this.ping;
                        OkHttpClientTransport.this.ping = null;
                        http2Ping = http2Ping2;
                    } else {
                        OkHttpClientTransport.log.log(Level.WARNING, String.format(Locale.US, "Received unexpected ping ack. Expecting %d, got %d", Long.valueOf(OkHttpClientTransport.this.ping.payload()), Long.valueOf(j)));
                    }
                } finally {
                }
            }
            if (http2Ping != null) {
                http2Ping.complete();
            }
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
            this.logger.logGoAway(OkHttpFrameLogger.Direction.INBOUND, i, errorCode, byteString);
            if (errorCode == ErrorCode.ENHANCE_YOUR_CALM) {
                String strUtf8 = byteString.utf8();
                OkHttpClientTransport.log.log(Level.WARNING, String.format("%s: Received GOAWAY with ENHANCE_YOUR_CALM. Debug data: %s", this, strUtf8));
                if ("too_many_pings".equals(strUtf8)) {
                    OkHttpClientTransport.this.tooManyPingsRunnable.run();
                }
            }
            Status statusAugmentDescription = GrpcUtil.Http2Error.statusForCode(errorCode.httpCode).augmentDescription("Received Goaway");
            if (byteString.size() > 0) {
                statusAugmentDescription = statusAugmentDescription.augmentDescription(byteString.utf8());
            }
            OkHttpClientTransport.this.startGoAway(i, null, statusAugmentDescription);
        }

        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        public void pushPromise(int i, int i2, List<Header> list) throws IOException {
            this.logger.logPushPromise(OkHttpFrameLogger.Direction.INBOUND, i, i2, list);
            synchronized (OkHttpClientTransport.this.lock) {
                OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.PROTOCOL_ERROR);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:59:0x0077  */
        /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
        @Override // io.grpc.okhttp.internal.framed.FrameReader.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void windowUpdate(int r8, long r9) {
            /*
                r7 = this;
                io.grpc.okhttp.OkHttpFrameLogger r0 = r7.logger
                io.grpc.okhttp.OkHttpFrameLogger$Direction r1 = io.grpc.okhttp.OkHttpFrameLogger.Direction.INBOUND
                r0.logWindowsUpdate(r1, r8, r9)
                r0 = 0
                int r2 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r2 != 0) goto L2c
                java.lang.String r9 = "Received 0 flow control window increment."
                if (r8 != 0) goto L19
                io.grpc.okhttp.OkHttpClientTransport r8 = io.grpc.okhttp.OkHttpClientTransport.this
                io.grpc.okhttp.internal.framed.ErrorCode r10 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                io.grpc.okhttp.OkHttpClientTransport.access$2300(r8, r10, r9)
                goto L2b
            L19:
                io.grpc.okhttp.OkHttpClientTransport r0 = io.grpc.okhttp.OkHttpClientTransport.this
                io.grpc.Status r10 = io.grpc.Status.INTERNAL
                io.grpc.Status r2 = r10.withDescription(r9)
                io.grpc.internal.ClientStreamListener$RpcProgress r3 = io.grpc.internal.ClientStreamListener.RpcProgress.PROCESSED
                io.grpc.okhttp.internal.framed.ErrorCode r5 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                r6 = 0
                r4 = 0
                r1 = r8
                r0.finishStream(r1, r2, r3, r4, r5, r6)
            L2b:
                return
            L2c:
                io.grpc.okhttp.OkHttpClientTransport r0 = io.grpc.okhttp.OkHttpClientTransport.this
                java.lang.Object r0 = io.grpc.okhttp.OkHttpClientTransport.access$100(r0)
                monitor-enter(r0)
                if (r8 != 0) goto L44
                io.grpc.okhttp.OkHttpClientTransport r8 = io.grpc.okhttp.OkHttpClientTransport.this     // Catch: java.lang.Throwable -> L42
                io.grpc.okhttp.OutboundFlowController r8 = io.grpc.okhttp.OkHttpClientTransport.access$200(r8)     // Catch: java.lang.Throwable -> L42
                r1 = 0
                int r10 = (int) r9     // Catch: java.lang.Throwable -> L42
                r8.windowUpdate(r1, r10)     // Catch: java.lang.Throwable -> L42
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L42
                return
            L42:
                r8 = move-exception
                goto L90
            L44:
                io.grpc.okhttp.OkHttpClientTransport r1 = io.grpc.okhttp.OkHttpClientTransport.this     // Catch: java.lang.Throwable -> L42
                java.util.Map r1 = io.grpc.okhttp.OkHttpClientTransport.access$2600(r1)     // Catch: java.lang.Throwable -> L42
                java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L42
                java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> L42
                io.grpc.okhttp.OkHttpClientStream r1 = (io.grpc.okhttp.OkHttpClientStream) r1     // Catch: java.lang.Throwable -> L42
                if (r1 == 0) goto L69
                io.grpc.okhttp.OkHttpClientTransport r2 = io.grpc.okhttp.OkHttpClientTransport.this     // Catch: java.lang.Throwable -> L42
                io.grpc.okhttp.OutboundFlowController r2 = io.grpc.okhttp.OkHttpClientTransport.access$200(r2)     // Catch: java.lang.Throwable -> L42
                io.grpc.okhttp.OkHttpClientStream$TransportState r1 = r1.transportState()     // Catch: java.lang.Throwable -> L42
                io.grpc.okhttp.OutboundFlowController$StreamState r1 = r1.getOutboundFlowState()     // Catch: java.lang.Throwable -> L42
                int r10 = (int) r9     // Catch: java.lang.Throwable -> L42
                r2.windowUpdate(r1, r10)     // Catch: java.lang.Throwable -> L42
                goto L73
            L69:
                io.grpc.okhttp.OkHttpClientTransport r9 = io.grpc.okhttp.OkHttpClientTransport.this     // Catch: java.lang.Throwable -> L42
                boolean r9 = r9.mayHaveCreatedStream(r8)     // Catch: java.lang.Throwable -> L42
                if (r9 != 0) goto L73
                r9 = 1
                goto L74
            L73:
                r9 = 0
            L74:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L42
                if (r9 == 0) goto L8f
                io.grpc.okhttp.OkHttpClientTransport r9 = io.grpc.okhttp.OkHttpClientTransport.this
                io.grpc.okhttp.internal.framed.ErrorCode r10 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Received window_update for unknown stream: "
                r0.append(r1)
                r0.append(r8)
                java.lang.String r8 = r0.toString()
                io.grpc.okhttp.OkHttpClientTransport.access$2300(r9, r10, r8)
            L8f:
                return
            L90:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L42
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpClientTransport.ClientFrameHandler.windowUpdate(int, long):void");
        }
    }
}
