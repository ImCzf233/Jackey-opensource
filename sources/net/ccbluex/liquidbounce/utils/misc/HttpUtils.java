package net.ccbluex.liquidbounce.utils.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004H\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004J\"\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "download", "", "url", "file", "Ljava/io/File;", PropertyDescriptor.GET, "make", "Ljava/net/HttpURLConnection;", "method", "agent", "request", "requestStream", "Ljava/io/InputStream;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/HttpUtils.class */
public final class HttpUtils {
    @NotNull
    public static final HttpUtils INSTANCE = new HttpUtils();
    @NotNull
    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private HttpUtils() {
    }

    static {
        HttpURLConnection.setFollowRedirects(true);
    }

    static /* synthetic */ HttpURLConnection make$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.make(str, str2, str3);
    }

    private final HttpURLConnection make(String url, String method, String agent) {
        URLConnection openConnection = new URL(url).openConnection();
        if (openConnection == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
        }
        HttpURLConnection httpConnection = (HttpURLConnection) openConnection;
        httpConnection.setRequestMethod(method);
        httpConnection.setConnectTimeout(10000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestProperty("User-Agent", agent);
        httpConnection.setInstanceFollowRedirects(true);
        httpConnection.setDoOutput(true);
        return httpConnection;
    }

    public static /* synthetic */ String request$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.request(str, str2, str3);
    }

    @NotNull
    public final String request(@NotNull String url, @NotNull String method, @NotNull String agent) throws IOException {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(agent, "agent");
        HttpURLConnection connection = make(url, method, agent);
        InputStream inputStream = connection.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "connection.inputStream");
        return TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8));
    }

    public static /* synthetic */ InputStream requestStream$default(HttpUtils httpUtils, String str, String str2, String str3, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            str3 = DEFAULT_AGENT;
        }
        return httpUtils.requestStream(str, str2, str3);
    }

    @Nullable
    public final InputStream requestStream(@NotNull String url, @NotNull String method, @NotNull String agent) throws IOException {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(agent, "agent");
        HttpURLConnection connection = make(url, method, agent);
        return connection.getInputStream();
    }

    @JvmStatic
    @NotNull
    public static final String get(@NotNull String url) throws IOException {
        Intrinsics.checkNotNullParameter(url, "url");
        return request$default(INSTANCE, url, "GET", null, 4, null);
    }

    @JvmStatic
    public static final void download(@NotNull String url, @NotNull File file) throws IOException {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(file, "file");
        FileUtils.copyInputStreamToFile(make$default(INSTANCE, url, "GET", null, 4, null).getInputStream(), file);
    }
}
