package me.liuli.elixir.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bJ@\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\b\b\u0002\u0010\r\u001a\u00020\u0004J,\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\bJ@\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b2\b\b\u0002\u0010\r\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��¨\u0006\u0010"}, m53d2 = {"Lme/liuli/elixir/utils/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", PropertyDescriptor.GET, "url", "header", "", "make", "Ljava/net/HttpURLConnection;", "method", "data", "agent", "post", "request", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/utils/HttpUtils.class */
public final class HttpUtils {
    @NotNull
    public static final HttpUtils INSTANCE = new HttpUtils();
    @NotNull
    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

    private HttpUtils() {
    }

    public static /* synthetic */ HttpURLConnection make$default(HttpUtils httpUtils, String str, String str2, String str3, Map map, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = "";
        }
        if ((i & 8) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 16) != 0) {
            str4 = DEFAULT_AGENT;
        }
        return httpUtils.make(str, str2, str3, map, str4);
    }

    @NotNull
    public final HttpURLConnection make(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull Map<String, String> header, @NotNull String agent) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(agent, "agent");
        URLConnection openConnection = new URL(url).openConnection();
        if (openConnection == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
        }
        HttpURLConnection httpConnection = (HttpURLConnection) openConnection;
        httpConnection.setRequestMethod(method);
        httpConnection.setConnectTimeout(2000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestProperty("User-Agent", agent);
        for (Map.Entry element$iv : header.entrySet()) {
            String key = element$iv.getKey();
            String value = element$iv.getValue();
            httpConnection.setRequestProperty(key, value);
        }
        httpConnection.setInstanceFollowRedirects(true);
        httpConnection.setDoOutput(true);
        if (data.length() > 0) {
            DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
        }
        httpConnection.connect();
        return httpConnection;
    }

    public static /* synthetic */ String request$default(HttpUtils httpUtils, String str, String str2, String str3, Map map, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = "";
        }
        if ((i & 8) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 16) != 0) {
            str4 = DEFAULT_AGENT;
        }
        return httpUtils.request(str, str2, str3, map, str4);
    }

    @NotNull
    public final String request(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull Map<String, String> header, @NotNull String agent) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(agent, "agent");
        HttpURLConnection connection = make(url, method, data, header, agent);
        InputStream inputStream = connection.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "connection.inputStream");
        return TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8));
    }

    @NotNull
    public final String get(@NotNull String url, @NotNull Map<String, String> header) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(header, "header");
        return request$default(this, url, "GET", null, header, null, 20, null);
    }

    public static /* synthetic */ String get$default(HttpUtils httpUtils, String str, Map map, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        return httpUtils.get(str, map);
    }

    @NotNull
    public final String post(@NotNull String url, @NotNull String data, @NotNull Map<String, String> header) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(header, "header");
        return request$default(this, url, "POST", data, header, null, 16, null);
    }

    public static /* synthetic */ String post$default(HttpUtils httpUtils, String str, String str2, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        return httpUtils.post(str, str2, map);
    }
}
