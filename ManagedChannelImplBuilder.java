package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.errorprone.annotations.DoNotCall;
import io.grpc.Attributes;
import io.grpc.BinaryLog;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalGlobalInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import io.grpc.NameResolverRegistry;
import io.grpc.ProxyDetector;
import io.grpc.internal.ExponentialBackoffPolicy;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes7.dex */
public final class ManagedChannelImplBuilder extends ManagedChannelBuilder<ManagedChannelImplBuilder> {
    public static final long DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES = 1048576;
    public static final long DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES = 16777216;
    public static final String DIRECT_ADDRESS_SCHEME = "directaddress";

    @VisibleForTesting
    public static final long IDLE_MODE_MAX_TIMEOUT_DAYS = 30;
    public boolean authorityCheckerDisabled;

    @Nullable
    public String authorityOverride;

    @Nullable
    public BinaryLog binlog;

    @Nullable
    public final CallCredentials callCredentials;
    public final ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider;

    @Nullable
    public final ChannelCredentials channelCredentials;
    public InternalChannelz channelz;
    public final ClientTransportFactoryBuilder clientTransportFactoryBuilder;
    public CompressorRegistry compressorRegistry;
    public DecompressorRegistry decompressorRegistry;
    public String defaultLbPolicy;

    @Nullable
    public Map<String, ?> defaultServiceConfig;

    @Nullable
    public final SocketAddress directServerAddress;
    public ObjectPool<? extends Executor> executorPool;
    public boolean fullStreamDecompression;
    public long idleTimeoutMillis;
    public final List<ClientInterceptor> interceptors;
    public boolean lookUpServiceConfig;
    public int maxHedgedAttempts;
    public int maxRetryAttempts;
    public int maxTraceEvents;
    public NameResolver.Factory nameResolverFactory;
    public final NameResolverRegistry nameResolverRegistry;
    public ObjectPool<? extends Executor> offloadExecutorPool;
    public long perRpcBufferLimit;

    @Nullable
    public ProxyDetector proxyDetector;
    public boolean recordFinishedRpcs;
    public boolean recordRealTimeMetrics;
    public boolean recordRetryMetrics;
    public boolean recordStartedRpcs;
    public long retryBufferSize;
    public boolean retryEnabled;
    public boolean statsEnabled;
    public final String target;
    public boolean tracingEnabled;

    @Nullable
    public String userAgent;
    public static final Logger log = Logger.getLogger(ManagedChannelImplBuilder.class.getName());

    @VisibleForTesting
    public static final long IDLE_MODE_DEFAULT_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(30);
    public static final long IDLE_MODE_MIN_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(1);
    public static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    public static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    public static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();

    public interface ChannelBuilderDefaultPortProvider {
        int getDefaultPort();
    }

    public interface ClientTransportFactoryBuilder {
        ClientTransportFactory buildClientTransportFactory();
    }

    @Override // io.grpc.ManagedChannelBuilder
    public /* bridge */ /* synthetic */ ManagedChannelBuilder defaultServiceConfig(@Nullable Map map) {
        return defaultServiceConfig((Map<String, ?>) map);
    }

    @Override // io.grpc.ManagedChannelBuilder
    public /* bridge */ /* synthetic */ ManagedChannelBuilder intercept(List list) {
        return intercept((List<ClientInterceptor>) list);
    }

    @DoNotCall("ClientTransportFactoryBuilder is required, use a constructor")
    public static ManagedChannelBuilder<?> forAddress(String str, int i) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    @DoNotCall("ClientTransportFactoryBuilder is required, use a constructor")
    public static ManagedChannelBuilder<?> forTarget(String str) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    public static class UnsupportedClientTransportFactoryBuilder implements ClientTransportFactoryBuilder {
        @Override // io.grpc.internal.ManagedChannelImplBuilder.ClientTransportFactoryBuilder
        public ClientTransportFactory buildClientTransportFactory() {
            throw new UnsupportedOperationException();
        }
    }

    public static final class FixedPortProvider implements ChannelBuilderDefaultPortProvider {
        public final int port;

        public FixedPortProvider(int i) {
            this.port = i;
        }

        @Override // io.grpc.internal.ManagedChannelImplBuilder.ChannelBuilderDefaultPortProvider
        public int getDefaultPort() {
            return this.port;
        }
    }

    public static final class ManagedChannelDefaultPortProvider implements ChannelBuilderDefaultPortProvider {
        @Override // io.grpc.internal.ManagedChannelImplBuilder.ChannelBuilderDefaultPortProvider
        public int getDefaultPort() {
            return GrpcUtil.DEFAULT_PORT_SSL;
        }

        public ManagedChannelDefaultPortProvider() {
        }

        public /* synthetic */ ManagedChannelDefaultPortProvider(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public ManagedChannelImplBuilder(String str, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this(str, null, null, clientTransportFactoryBuilder, channelBuilderDefaultPortProvider);
    }

    public ManagedChannelImplBuilder(String str, @Nullable ChannelCredentials channelCredentials, @Nullable CallCredentials callCredentials, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        ObjectPool<? extends Executor> objectPool = DEFAULT_EXECUTOR_POOL;
        this.executorPool = objectPool;
        this.offloadExecutorPool = objectPool;
        this.interceptors = new ArrayList();
        NameResolverRegistry defaultRegistry = NameResolverRegistry.getDefaultRegistry();
        this.nameResolverRegistry = defaultRegistry;
        this.nameResolverFactory = defaultRegistry.asFactory();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = 1048576L;
        this.retryEnabled = true;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.recordRetryMetrics = true;
        this.tracingEnabled = true;
        this.target = (String) Preconditions.checkNotNull(str, "target");
        this.channelCredentials = channelCredentials;
        this.callCredentials = callCredentials;
        this.clientTransportFactoryBuilder = (ClientTransportFactoryBuilder) Preconditions.checkNotNull(clientTransportFactoryBuilder, "clientTransportFactoryBuilder");
        this.directServerAddress = null;
        if (channelBuilderDefaultPortProvider != null) {
            this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider;
        } else {
            this.channelBuilderDefaultPortProvider = new ManagedChannelDefaultPortProvider();
        }
    }

    @VisibleForTesting
    public static String makeTargetStringForDirectAddress(SocketAddress socketAddress) {
        try {
            return new URI(DIRECT_ADDRESS_SCHEME, "", "/" + socketAddress, null).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ManagedChannelImplBuilder(SocketAddress socketAddress, String str, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        this(socketAddress, str, null, null, clientTransportFactoryBuilder, channelBuilderDefaultPortProvider);
    }

    public ManagedChannelImplBuilder(SocketAddress socketAddress, String str, @Nullable ChannelCredentials channelCredentials, @Nullable CallCredentials callCredentials, ClientTransportFactoryBuilder clientTransportFactoryBuilder, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider) {
        ObjectPool<? extends Executor> objectPool = DEFAULT_EXECUTOR_POOL;
        this.executorPool = objectPool;
        this.offloadExecutorPool = objectPool;
        this.interceptors = new ArrayList();
        NameResolverRegistry defaultRegistry = NameResolverRegistry.getDefaultRegistry();
        this.nameResolverRegistry = defaultRegistry;
        this.nameResolverFactory = defaultRegistry.asFactory();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = 1048576L;
        this.retryEnabled = true;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.recordRetryMetrics = true;
        this.tracingEnabled = true;
        this.target = makeTargetStringForDirectAddress(socketAddress);
        this.channelCredentials = channelCredentials;
        this.callCredentials = callCredentials;
        this.clientTransportFactoryBuilder = (ClientTransportFactoryBuilder) Preconditions.checkNotNull(clientTransportFactoryBuilder, "clientTransportFactoryBuilder");
        this.directServerAddress = socketAddress;
        this.nameResolverFactory = new DirectAddressNameResolverFactory(socketAddress, str);
        if (channelBuilderDefaultPortProvider != null) {
            this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider;
        } else {
            this.channelBuilderDefaultPortProvider = new ManagedChannelDefaultPortProvider();
        }
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder executor(Executor executor) {
        if (executor != null) {
            this.executorPool = new FixedObjectPool(executor);
        } else {
            this.executorPool = DEFAULT_EXECUTOR_POOL;
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder offloadExecutor(Executor executor) {
        if (executor != null) {
            this.offloadExecutorPool = new FixedObjectPool(executor);
        } else {
            this.offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder intercept(List<ClientInterceptor> list) {
        this.interceptors.addAll(list);
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder intercept(ClientInterceptor... clientInterceptorArr) {
        return intercept(Arrays.asList(clientInterceptorArr));
    }

    @Override // io.grpc.ManagedChannelBuilder
    @Deprecated
    public ManagedChannelImplBuilder nameResolverFactory(NameResolver.Factory factory) {
        SocketAddress socketAddress = this.directServerAddress;
        Preconditions.checkState(socketAddress == null, "directServerAddress is set (%s), which forbids the use of NameResolverFactory", socketAddress);
        if (factory != null) {
            this.nameResolverFactory = factory;
        } else {
            this.nameResolverFactory = this.nameResolverRegistry.asFactory();
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder defaultLoadBalancingPolicy(String str) {
        SocketAddress socketAddress = this.directServerAddress;
        Preconditions.checkState(socketAddress == null, "directServerAddress is set (%s), which forbids the use of load-balancing policy", socketAddress);
        Preconditions.checkArgument(str != null, "policy cannot be null");
        this.defaultLbPolicy = str;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder enableFullStreamDecompression() {
        this.fullStreamDecompression = true;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder decompressorRegistry(DecompressorRegistry decompressorRegistry) {
        if (decompressorRegistry != null) {
            this.decompressorRegistry = decompressorRegistry;
        } else {
            this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder compressorRegistry(CompressorRegistry compressorRegistry) {
        if (compressorRegistry != null) {
            this.compressorRegistry = compressorRegistry;
        } else {
            this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder userAgent(@Nullable String str) {
        this.userAgent = str;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder overrideAuthority(String str) {
        this.authorityOverride = checkAuthority(str);
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder idleTimeout(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "idle timeout is %s, but must be positive", j);
        if (timeUnit.toDays(j) >= 30) {
            this.idleTimeoutMillis = -1L;
        } else {
            this.idleTimeoutMillis = Math.max(timeUnit.toMillis(j), IDLE_MODE_MIN_TIMEOUT_MILLIS);
        }
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder maxRetryAttempts(int i) {
        this.maxRetryAttempts = i;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder maxHedgedAttempts(int i) {
        this.maxHedgedAttempts = i;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder retryBufferSize(long j) {
        Preconditions.checkArgument(j > 0, "retry buffer size must be positive");
        this.retryBufferSize = j;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder perRpcBufferLimit(long j) {
        Preconditions.checkArgument(j > 0, "per RPC buffer limit must be positive");
        this.perRpcBufferLimit = j;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder disableRetry() {
        this.retryEnabled = false;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder enableRetry() {
        this.retryEnabled = true;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder setBinaryLog(BinaryLog binaryLog) {
        this.binlog = binaryLog;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder maxTraceEvents(int i) {
        Preconditions.checkArgument(i >= 0, "maxTraceEvents must be non-negative");
        this.maxTraceEvents = i;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder proxyDetector(@Nullable ProxyDetector proxyDetector) {
        this.proxyDetector = proxyDetector;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder defaultServiceConfig(@Nullable Map<String, ?> map) {
        this.defaultServiceConfig = checkMapEntryTypes(map);
        return this;
    }

    @Nullable
    public static Map<String, ?> checkMapEntryTypes(@Nullable Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Preconditions.checkArgument(entry.getKey() instanceof String, "The key of the entry '%s' is not of String type", entry);
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                linkedHashMap.put(str, null);
            } else if (value instanceof Map) {
                linkedHashMap.put(str, checkMapEntryTypes((Map) value));
            } else if (value instanceof List) {
                linkedHashMap.put(str, checkListEntryTypes((List) value));
            } else if (value instanceof String) {
                linkedHashMap.put(str, value);
            } else if (value instanceof Double) {
                linkedHashMap.put(str, value);
            } else if (value instanceof Boolean) {
                linkedHashMap.put(str, value);
            } else {
                throw new IllegalArgumentException("The value of the map entry '" + entry + "' is of type '" + value.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public static List<?> checkListEntryTypes(List<?> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Object obj : list) {
            if (obj == null) {
                arrayList.add(null);
            } else if (obj instanceof Map) {
                arrayList.add(checkMapEntryTypes((Map) obj));
            } else if (obj instanceof List) {
                arrayList.add(checkListEntryTypes((List) obj));
            } else if (obj instanceof String) {
                arrayList.add(obj);
            } else if (obj instanceof Double) {
                arrayList.add(obj);
            } else if (obj instanceof Boolean) {
                arrayList.add(obj);
            } else {
                throw new IllegalArgumentException("The entry '" + obj + "' is of type '" + obj.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannelImplBuilder disableServiceConfigLookUp() {
        this.lookUpServiceConfig = false;
        return this;
    }

    public void setStatsEnabled(boolean z) {
        this.statsEnabled = z;
    }

    public void setStatsRecordStartedRpcs(boolean z) {
        this.recordStartedRpcs = z;
    }

    public void setStatsRecordFinishedRpcs(boolean z) {
        this.recordFinishedRpcs = z;
    }

    public void setStatsRecordRealTimeMetrics(boolean z) {
        this.recordRealTimeMetrics = z;
    }

    public void setStatsRecordRetryMetrics(boolean z) {
        this.recordRetryMetrics = z;
    }

    public void setTracingEnabled(boolean z) {
        this.tracingEnabled = z;
    }

    @VisibleForTesting
    public String checkAuthority(String str) {
        return this.authorityCheckerDisabled ? str : GrpcUtil.checkAuthority(str);
    }

    public ManagedChannelImplBuilder disableCheckAuthority() {
        this.authorityCheckerDisabled = true;
        return this;
    }

    public ManagedChannelImplBuilder enableCheckAuthority() {
        this.authorityCheckerDisabled = false;
        return this;
    }

    @Override // io.grpc.ManagedChannelBuilder
    public ManagedChannel build() {
        return new ManagedChannelOrphanWrapper(new ManagedChannelImpl(this, this.clientTransportFactoryBuilder.buildClientTransportFactory(), new ExponentialBackoffPolicy.Provider(), SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR), GrpcUtil.STOPWATCH_SUPPLIER, getEffectiveInterceptors(), TimeProvider.SYSTEM_TIME_PROVIDER));
    }

    @VisibleForTesting
    public List<ClientInterceptor> getEffectiveInterceptors() {
        boolean z;
        ClientInterceptor clientInterceptor;
        ArrayList arrayList = new ArrayList(this.interceptors);
        List<ClientInterceptor> clientInterceptors = InternalGlobalInterceptors.getClientInterceptors();
        if (clientInterceptors != null) {
            arrayList.addAll(clientInterceptors);
            z = true;
        } else {
            z = false;
        }
        ClientInterceptor clientInterceptor2 = null;
        if (!z && this.statsEnabled) {
            try {
                Class<?> cls = Class.forName("io.grpc.census.InternalCensusStatsAccessor");
                Class<?> cls2 = Boolean.TYPE;
                clientInterceptor = (ClientInterceptor) cls.getDeclaredMethod("getClientInterceptor", cls2, cls2, cls2, cls2).invoke(null, Boolean.valueOf(this.recordStartedRpcs), Boolean.valueOf(this.recordFinishedRpcs), Boolean.valueOf(this.recordRealTimeMetrics), Boolean.valueOf(this.recordRetryMetrics));
            } catch (ClassNotFoundException e) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e);
                clientInterceptor = null;
            } catch (IllegalAccessException e2) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e2);
                clientInterceptor = null;
            } catch (NoSuchMethodException e3) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e3);
                clientInterceptor = null;
            } catch (InvocationTargetException e4) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e4);
                clientInterceptor = null;
            }
            if (clientInterceptor != null) {
                arrayList.add(0, clientInterceptor);
            }
        }
        if (!z && this.tracingEnabled) {
            try {
                clientInterceptor2 = (ClientInterceptor) Class.forName("io.grpc.census.InternalCensusTracingAccessor").getDeclaredMethod("getClientInterceptor", null).invoke(null, null);
            } catch (ClassNotFoundException e5) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e5);
            } catch (IllegalAccessException e6) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e6);
            } catch (NoSuchMethodException e7) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e7);
            } catch (InvocationTargetException e8) {
                log.log(Level.FINE, "Unable to apply census stats", (Throwable) e8);
            }
            if (clientInterceptor2 != null) {
                arrayList.add(0, clientInterceptor2);
            }
        }
        return arrayList;
    }

    public int getDefaultPort() {
        return this.channelBuilderDefaultPortProvider.getDefaultPort();
    }

    public static class DirectAddressNameResolverFactory extends NameResolver.Factory {
        public final SocketAddress address;
        public final String authority;

        public DirectAddressNameResolverFactory(SocketAddress socketAddress, String str) {
            this.address = socketAddress;
            this.authority = str;
        }

        /* JADX INFO: renamed from: io.grpc.internal.ManagedChannelImplBuilder$DirectAddressNameResolverFactory$1 */
        public class AnonymousClass1 extends NameResolver {
            @Override // io.grpc.NameResolver
            public void shutdown() {
            }

            public AnonymousClass1() {
            }

            @Override // io.grpc.NameResolver
            public String getServiceAuthority() {
                return DirectAddressNameResolverFactory.this.authority;
            }

            @Override // io.grpc.NameResolver
            public void start(NameResolver.Listener2 listener2) {
                listener2.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup(DirectAddressNameResolverFactory.this.address))).setAttributes(Attributes.EMPTY).build());
            }
        }

        @Override // io.grpc.NameResolver.Factory
        public NameResolver newNameResolver(URI uri, NameResolver.Args args) {
            return new NameResolver() { // from class: io.grpc.internal.ManagedChannelImplBuilder.DirectAddressNameResolverFactory.1
                @Override // io.grpc.NameResolver
                public void shutdown() {
                }

                public AnonymousClass1() {
                }

                @Override // io.grpc.NameResolver
                public String getServiceAuthority() {
                    return DirectAddressNameResolverFactory.this.authority;
                }

                @Override // io.grpc.NameResolver
                public void start(NameResolver.Listener2 listener2) {
                    listener2.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup(DirectAddressNameResolverFactory.this.address))).setAttributes(Attributes.EMPTY).build());
                }
            };
        }

        @Override // io.grpc.NameResolver.Factory
        public String getDefaultScheme() {
            return ManagedChannelImplBuilder.DIRECT_ADDRESS_SCHEME;
        }
    }

    public ObjectPool<? extends Executor> getOffloadExecutorPool() {
        return this.offloadExecutorPool;
    }
}
