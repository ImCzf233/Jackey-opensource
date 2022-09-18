package me.liuli.elixir.compat;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import me.liuli.elixir.account.MicrosoftAccount;
import org.jetbrains.annotations.NotNull;

/* compiled from: OAuthServer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018��2\u00020\u0001:\u0001\u0014B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0015"}, m53d2 = {"Lme/liuli/elixir/compat/OAuthServer;", "", "handler", "Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "httpServer", "Lcom/sun/net/httpserver/HttpServer;", "context", "", "(Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;Lcom/sun/net/httpserver/HttpServer;Ljava/lang/String;)V", "getHandler", "()Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "threadPoolExecutor", "Ljava/util/concurrent/ThreadPoolExecutor;", "start", "", "stop", "isInterrupt", "", "OAuthHttpHandler", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/compat/OAuthServer.class */
public final class OAuthServer {
    @NotNull
    private final MicrosoftAccount.OAuthHandler handler;
    @NotNull
    private final MicrosoftAccount.AuthMethod authMethod;
    @NotNull
    private final HttpServer httpServer;
    @NotNull
    private final String context;
    @NotNull
    private final ThreadPoolExecutor threadPoolExecutor;

    public OAuthServer(@NotNull MicrosoftAccount.OAuthHandler handler, @NotNull MicrosoftAccount.AuthMethod authMethod, @NotNull HttpServer httpServer, @NotNull String context) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(authMethod, "authMethod");
        Intrinsics.checkNotNullParameter(httpServer, "httpServer");
        Intrinsics.checkNotNullParameter(context, "context");
        this.handler = handler;
        this.authMethod = authMethod;
        this.httpServer = httpServer;
        this.context = context;
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        if (newFixedThreadPool == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.util.concurrent.ThreadPoolExecutor");
        }
        this.threadPoolExecutor = (ThreadPoolExecutor) newFixedThreadPool;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ OAuthServer(me.liuli.elixir.account.MicrosoftAccount.OAuthHandler r7, me.liuli.elixir.account.MicrosoftAccount.AuthMethod r8, com.sun.net.httpserver.HttpServer r9, java.lang.String r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r0 = r11
            r1 = 2
            r0 = r0 & r1
            if (r0 == 0) goto Le
            me.liuli.elixir.account.MicrosoftAccount$AuthMethod$Companion r0 = me.liuli.elixir.account.MicrosoftAccount.AuthMethod.Companion
            me.liuli.elixir.account.MicrosoftAccount$AuthMethod r0 = r0.getAZURE_APP()
            r8 = r0
        Le:
            r0 = r11
            r1 = 4
            r0 = r0 & r1
            if (r0 == 0) goto L31
            java.net.InetSocketAddress r0 = new java.net.InetSocketAddress
            r1 = r0
            java.lang.String r2 = "localhost"
            r3 = 1919(0x77f, float:2.689E-42)
            r1.<init>(r2, r3)
            r1 = 0
            com.sun.net.httpserver.HttpServer r0 = com.sun.net.httpserver.HttpServer.create(r0, r1)
            r13 = r0
            r0 = r13
            java.lang.String r1 = "create(InetSocketAddress(\"localhost\", 1919), 0)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r0 = r13
            r9 = r0
        L31:
            r0 = r11
            r1 = 8
            r0 = r0 & r1
            if (r0 == 0) goto L3d
            java.lang.String r0 = "/login"
            r10 = r0
        L3d:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: me.liuli.elixir.compat.OAuthServer.<init>(me.liuli.elixir.account.MicrosoftAccount$OAuthHandler, me.liuli.elixir.account.MicrosoftAccount$AuthMethod, com.sun.net.httpserver.HttpServer, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final MicrosoftAccount.OAuthHandler getHandler() {
        return this.handler;
    }

    public final void start() {
        this.httpServer.createContext(this.context, new OAuthHttpHandler(this, this.authMethod));
        this.httpServer.setExecutor(this.threadPoolExecutor);
        this.httpServer.start();
        this.handler.openUrl(MicrosoftAccount.Companion.replaceKeys(this.authMethod, MicrosoftAccount.XBOX_PRE_AUTH_URL));
    }

    public static /* synthetic */ void stop$default(OAuthServer oAuthServer, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        oAuthServer.stop(z);
    }

    public final void stop(boolean isInterrupt) {
        this.httpServer.stop(0);
        this.threadPoolExecutor.shutdown();
        if (isInterrupt) {
            this.handler.authError("Has been interrupted");
        }
    }

    /* compiled from: OAuthServer.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n��\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0010"}, m53d2 = {"Lme/liuli/elixir/compat/OAuthServer$OAuthHttpHandler;", "Lcom/sun/net/httpserver/HttpHandler;", "server", "Lme/liuli/elixir/compat/OAuthServer;", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "(Lme/liuli/elixir/compat/OAuthServer;Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;)V", "handle", "", "exchange", "Lcom/sun/net/httpserver/HttpExchange;", "response", "message", "", "code", "", "Elixir"})
    /* loaded from: Jackey Client b2.jar:me/liuli/elixir/compat/OAuthServer$OAuthHttpHandler.class */
    public static final class OAuthHttpHandler implements HttpHandler {
        @NotNull
        private final OAuthServer server;
        @NotNull
        private final MicrosoftAccount.AuthMethod authMethod;

        public OAuthHttpHandler(@NotNull OAuthServer server, @NotNull MicrosoftAccount.AuthMethod authMethod) {
            Intrinsics.checkNotNullParameter(server, "server");
            Intrinsics.checkNotNullParameter(authMethod, "authMethod");
            this.server = server;
            this.authMethod = authMethod;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
            jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:12:0x0130
            	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
            	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
            */
        public void handle(@org.jetbrains.annotations.NotNull com.sun.net.httpserver.HttpExchange r8) {
            /*
                Method dump skipped, instructions count: 422
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: me.liuli.elixir.compat.OAuthServer.OAuthHttpHandler.handle(com.sun.net.httpserver.HttpExchange):void");
        }

        private final void response(HttpExchange exchange, String message, int code) {
            byte[] bytes = message.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            exchange.sendResponseHeaders(code, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }
    }
}
