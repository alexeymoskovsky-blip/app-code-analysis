package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.auth.AuthProtocolState;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.NonRepeatableRequestException;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.ConnectionRequest;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.BasicRouteDirector;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRouteDirector;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.auth.HttpAuthenticator;
import cz.msebera.android.httpclient.impl.conn.ConnectionShutdownException;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes7.dex */
@Immutable
public class MainClientExec implements ClientExecChain {
    public final HttpAuthenticator authenticator;
    public final HttpClientConnectionManager connManager;
    public final ConnectionKeepAliveStrategy keepAliveStrategy;
    public HttpClientAndroidLog log;
    public final AuthenticationStrategy proxyAuthStrategy;
    public final HttpProcessor proxyHttpProcessor;
    public final HttpRequestExecutor requestExecutor;
    public final ConnectionReuseStrategy reuseStrategy;
    public final HttpRouteDirector routeDirector;
    public final AuthenticationStrategy targetAuthStrategy;
    public final UserTokenHandler userTokenHandler;

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpProcessor, "Proxy HTTP processor");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.proxyHttpProcessor = httpProcessor;
        this.targetAuthStrategy = authenticationStrategy;
        this.proxyAuthStrategy = authenticationStrategy2;
        this.userTokenHandler = userTokenHandler;
    }

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, new ImmutableHttpProcessor(new RequestTargetHost()), authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        RuntimeException runtimeException;
        ConnectionHolder connectionHolder;
        IOException iOException;
        HttpException httpException;
        ConnectionHolder connectionHolder2;
        HttpClientAndroidLog httpClientAndroidLog;
        int i;
        StringBuilder sb;
        HttpClientConnection httpClientConnection;
        AuthState authState;
        String str;
        Object obj;
        HttpResponse response;
        Object userToken;
        AuthState authState2;
        HttpResponse httpResponse;
        String str2;
        HttpRoute httpRoute2 = httpRoute;
        HttpExecutionAware httpExecutionAware2 = httpExecutionAware;
        Args.notNull(httpRoute2, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        AuthState targetAuthState = httpClientContext.getTargetAuthState();
        if (targetAuthState == null) {
            targetAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.target-scope", targetAuthState);
        }
        AuthState authState3 = targetAuthState;
        AuthState proxyAuthState = httpClientContext.getProxyAuthState();
        if (proxyAuthState == null) {
            proxyAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        }
        AuthState authState4 = proxyAuthState;
        if (httpRequestWrapper instanceof HttpEntityEnclosingRequest) {
            RequestEntityProxy.enhance((HttpEntityEnclosingRequest) httpRequestWrapper);
        }
        Object userToken2 = httpClientContext.getUserToken();
        ConnectionRequest connectionRequestRequestConnection = this.connManager.requestConnection(httpRoute2, userToken2);
        String str3 = "Request aborted";
        if (httpExecutionAware2 != null) {
            if (httpExecutionAware.isAborted()) {
                connectionRequestRequestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware2.setCancellable(connectionRequestRequestConnection);
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        try {
            int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
            HttpClientConnection httpClientConnection2 = connectionRequestRequestConnection.get(connectionRequestTimeout > 0 ? connectionRequestTimeout : 0L, TimeUnit.MILLISECONDS);
            httpClientContext.setAttribute("http.connection", httpClientConnection2);
            if (requestConfig.isStaleConnectionCheckEnabled() && httpClientConnection2.isOpen()) {
                this.log.debug("Stale connection check");
                if (httpClientConnection2.isStale()) {
                    this.log.debug("Stale connection detected");
                    httpClientConnection2.close();
                }
            }
            ConnectionHolder connectionHolder3 = new ConnectionHolder(this.log, this.connManager, httpClientConnection2);
            if (httpExecutionAware2 != null) {
                try {
                    try {
                        httpExecutionAware2.setCancellable(connectionHolder3);
                    } catch (HttpException e) {
                        httpException = e;
                        connectionHolder = connectionHolder3;
                        connectionHolder.abortConnection();
                        throw httpException;
                    } catch (IOException e2) {
                        iOException = e2;
                        connectionHolder = connectionHolder3;
                        connectionHolder.abortConnection();
                        throw iOException;
                    } catch (RuntimeException e3) {
                        runtimeException = e3;
                        connectionHolder = connectionHolder3;
                        connectionHolder.abortConnection();
                        throw runtimeException;
                    }
                } catch (ConnectionShutdownException e4) {
                    InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
                    interruptedIOException.initCause(e4);
                    throw interruptedIOException;
                }
            }
            int i2 = 1;
            int i3 = 1;
            while (true) {
                if (i3 > i2 && !RequestEntityProxy.isRepeatable(httpRequestWrapper)) {
                    throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
                }
                if (httpExecutionAware2 != null && httpExecutionAware.isAborted()) {
                    throw new RequestAbortedException(str3);
                }
                try {
                    if (httpClientConnection2.isOpen()) {
                        i = i3;
                        connectionHolder2 = connectionHolder3;
                        httpClientConnection = httpClientConnection2;
                        obj = userToken2;
                        authState = authState4;
                        str = str3;
                    } else {
                        try {
                            httpClientAndroidLog = this.log;
                            i = i3;
                            sb = new StringBuilder();
                            connectionHolder2 = connectionHolder3;
                        } catch (HttpException e5) {
                            e = e5;
                            connectionHolder2 = connectionHolder3;
                        } catch (IOException e6) {
                            e = e6;
                            connectionHolder2 = connectionHolder3;
                        } catch (RuntimeException e7) {
                            e = e7;
                            connectionHolder2 = connectionHolder3;
                        }
                        try {
                            sb.append("Opening connection ");
                            sb.append(httpRoute2);
                            httpClientAndroidLog.debug(sb.toString());
                            AuthState authState5 = authState4;
                            httpClientConnection = httpClientConnection2;
                            authState = authState4;
                            str = str3;
                            obj = userToken2;
                            try {
                                establishRoute(authState5, httpClientConnection2, httpRoute, httpRequestWrapper, httpClientContext);
                            } catch (TunnelRefusedException e8) {
                                if (this.log.isDebugEnabled()) {
                                    this.log.debug(e8.getMessage());
                                }
                                response = e8.getResponse();
                                connectionHolder = connectionHolder2;
                                if (obj == null) {
                                    userToken = this.userTokenHandler.getUserToken(httpClientContext);
                                    httpClientContext.setAttribute("http.user-token", userToken);
                                } else {
                                    userToken = obj;
                                }
                                if (userToken != null) {
                                    connectionHolder.setState(userToken);
                                }
                                HttpEntity entity = response.getEntity();
                                if (entity != null && entity.isStreaming()) {
                                    return new HttpResponseProxy(response, connectionHolder);
                                }
                                connectionHolder.releaseConnection();
                                return new HttpResponseProxy(response, null);
                            }
                        } catch (HttpException e9) {
                            e = e9;
                            httpException = e;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw httpException;
                        } catch (IOException e10) {
                            e = e10;
                            iOException = e;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw iOException;
                        } catch (RuntimeException e11) {
                            e = e11;
                            runtimeException = e;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw runtimeException;
                        }
                    }
                    try {
                        int socketTimeout = requestConfig.getSocketTimeout();
                        if (socketTimeout >= 0) {
                            httpClientConnection.setSocketTimeout(socketTimeout);
                        }
                        if (httpExecutionAware2 != null && httpExecutionAware.isAborted()) {
                            throw new RequestAbortedException(str);
                        }
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Executing request " + httpRequestWrapper.getRequestLine());
                        }
                        if (!httpRequestWrapper.containsHeader("Authorization")) {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Target auth state: " + authState3.getState());
                            }
                            this.authenticator.generateAuthResponse(httpRequestWrapper, authState3, httpClientContext);
                        }
                        if (httpRequestWrapper.containsHeader("Proxy-Authorization") || httpRoute.isTunnelled()) {
                            authState2 = authState;
                        } else {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Proxy auth state: " + authState.getState());
                            }
                            authState2 = authState;
                            this.authenticator.generateAuthResponse(httpRequestWrapper, authState2, httpClientContext);
                        }
                        HttpResponse httpResponseExecute = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
                        if (this.reuseStrategy.keepAlive(httpResponseExecute, httpClientContext)) {
                            long keepAliveDuration = this.keepAliveStrategy.getKeepAliveDuration(httpResponseExecute, httpClientContext);
                            if (this.log.isDebugEnabled()) {
                                if (keepAliveDuration > 0) {
                                    str2 = "for " + keepAliveDuration + " " + TimeUnit.MILLISECONDS;
                                } else {
                                    str2 = "indefinitely";
                                }
                                httpResponse = httpResponseExecute;
                                this.log.debug("Connection can be kept alive " + str2);
                            } else {
                                httpResponse = httpResponseExecute;
                            }
                            connectionHolder = connectionHolder2;
                            try {
                                connectionHolder.setValidFor(keepAliveDuration, TimeUnit.MILLISECONDS);
                                connectionHolder.markReusable();
                            } catch (HttpException e12) {
                                e = e12;
                                httpException = e;
                                connectionHolder.abortConnection();
                                throw httpException;
                            } catch (IOException e13) {
                                e = e13;
                                iOException = e;
                                connectionHolder.abortConnection();
                                throw iOException;
                            } catch (RuntimeException e14) {
                                e = e14;
                                runtimeException = e;
                                connectionHolder.abortConnection();
                                throw runtimeException;
                            }
                        } else {
                            httpResponse = httpResponseExecute;
                            connectionHolder = connectionHolder2;
                            connectionHolder.markNonReusable();
                        }
                        HttpResponse httpResponse2 = httpResponse;
                        AuthState authState6 = authState2;
                        if (!needAuthentication(authState3, authState2, httpRoute, httpResponse2, httpClientContext)) {
                            response = httpResponse2;
                            break;
                        }
                        HttpEntity entity2 = httpResponse2.getEntity();
                        if (connectionHolder.isReusable()) {
                            EntityUtils.consume(entity2);
                        } else {
                            httpClientConnection.close();
                            AuthProtocolState state = authState6.getState();
                            AuthProtocolState authProtocolState = AuthProtocolState.SUCCESS;
                            if (state == authProtocolState && authState6.getAuthScheme() != null && authState6.getAuthScheme().isConnectionBased()) {
                                this.log.debug("Resetting proxy auth state");
                                authState6.reset();
                            }
                            if (authState3.getState() == authProtocolState && authState3.getAuthScheme() != null && authState3.getAuthScheme().isConnectionBased()) {
                                this.log.debug("Resetting target auth state");
                                authState3.reset();
                            }
                        }
                        HttpRequest original = httpRequestWrapper.getOriginal();
                        if (!original.containsHeader("Authorization")) {
                            httpRequestWrapper.removeHeaders("Authorization");
                        }
                        if (!original.containsHeader("Proxy-Authorization")) {
                            httpRequestWrapper.removeHeaders("Proxy-Authorization");
                        }
                        i3 = i + 1;
                        httpClientConnection2 = httpClientConnection;
                        connectionHolder3 = connectionHolder;
                        str3 = str;
                        authState4 = authState6;
                        userToken2 = obj;
                        i2 = 1;
                        httpRoute2 = httpRoute;
                        httpExecutionAware2 = httpExecutionAware;
                    } catch (HttpException e15) {
                        e = e15;
                        connectionHolder = connectionHolder2;
                    } catch (IOException e16) {
                        e = e16;
                        connectionHolder = connectionHolder2;
                    } catch (RuntimeException e17) {
                        e = e17;
                        connectionHolder = connectionHolder2;
                    }
                } catch (HttpException e18) {
                    e = e18;
                    connectionHolder = connectionHolder3;
                } catch (IOException e19) {
                    e = e19;
                    connectionHolder = connectionHolder3;
                } catch (RuntimeException e20) {
                    e = e20;
                    connectionHolder = connectionHolder3;
                }
            }
        } catch (InterruptedException e21) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", e21);
        } catch (ExecutionException e22) {
            Throwable th = e22;
            Throwable cause = th.getCause();
            if (cause != null) {
                th = cause;
            }
            throw new RequestAbortedException("Request execution failed", th);
        }
    }

    public void establishRoute(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) throws IOException, HttpException {
        int iNextStep;
        int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        RouteTracker routeTracker = new RouteTracker(httpRoute);
        do {
            HttpRoute route = routeTracker.toRoute();
            iNextStep = this.routeDirector.nextStep(httpRoute, route);
            switch (iNextStep) {
                case -1:
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + route);
                case 0:
                    this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    break;
                case 1:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    break;
                case 2:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectProxy(httpRoute.getProxyHost(), false);
                    break;
                case 3:
                    boolean zCreateTunnelToTarget = createTunnelToTarget(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(zCreateTunnelToTarget);
                    break;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean zCreateTunnelToProxy = createTunnelToProxy(httpRoute, hopCount, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(hopCount), zCreateTunnelToProxy);
                    break;
                case 5:
                    this.connManager.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    break;
                default:
                    throw new IllegalStateException("Unknown step indicator " + iNextStep + " from RouteDirector.");
            }
        } while (iNextStep > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0093, code lost:
    
        if (r16.reuseStrategy.keepAlive(r7, r21) == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0095, code lost:
    
        r16.log.debug("Connection kept alive");
        cz.msebera.android.httpclient.util.EntityUtils.consume(r7.getEntity());
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a4, code lost:
    
        r18.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean createTunnelToTarget(cz.msebera.android.httpclient.auth.AuthState r17, cz.msebera.android.httpclient.HttpClientConnection r18, cz.msebera.android.httpclient.conn.routing.HttpRoute r19, cz.msebera.android.httpclient.HttpRequest r20, cz.msebera.android.httpclient.client.protocol.HttpClientContext r21) throws cz.msebera.android.httpclient.HttpException, java.io.IOException {
        /*
            Method dump skipped, instruction units count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.MainClientExec.createTunnelToTarget(cz.msebera.android.httpclient.auth.AuthState, cz.msebera.android.httpclient.HttpClientConnection, cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.HttpRequest, cz.msebera.android.httpclient.client.protocol.HttpClientContext):boolean");
    }

    public final boolean createTunnelToProxy(HttpRoute httpRoute, int i, HttpClientContext httpClientContext) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }

    public final boolean needAuthentication(AuthState authState, AuthState authState2, HttpRoute httpRoute, HttpResponse httpResponse, HttpClientContext httpClientContext) {
        if (!httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            return false;
        }
        HttpHost targetHost = httpClientContext.getTargetHost();
        if (targetHost == null) {
            targetHost = httpRoute.getTargetHost();
        }
        if (targetHost.getPort() < 0) {
            targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
        }
        boolean zIsAuthenticationRequested = this.authenticator.isAuthenticationRequested(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        HttpHost proxyHost = httpRoute.getProxyHost();
        if (proxyHost == null) {
            proxyHost = httpRoute.getTargetHost();
        }
        boolean zIsAuthenticationRequested2 = this.authenticator.isAuthenticationRequested(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
        if (zIsAuthenticationRequested) {
            return this.authenticator.handleAuthChallenge(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        }
        if (!zIsAuthenticationRequested2) {
            return false;
        }
        return this.authenticator.handleAuthChallenge(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
    }
}
