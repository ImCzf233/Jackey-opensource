package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.Closeable;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UserUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/login/UserUtils;", "", "()V", "getUUID", "", "username", "getUsername", "uuid", "isValidToken", "", "token", "isValidTokenOffline", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/login/UserUtils.class */
public final class UserUtils {
    @NotNull
    public static final UserUtils INSTANCE = new UserUtils();

    private UserUtils() {
    }

    public final boolean isValidTokenOffline(@NotNull String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        return token.length() >= 32;
    }

    public final boolean isValidToken(@NotNull String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        CloseableHttpClient client = HttpClients.createDefault();
        Header[] headerArr = {new BasicHeader("Content-Type", "application/json")};
        HttpUriRequest httpPost = new HttpPost("https://authserver.mojang.com/validate");
        httpPost.setHeaders(headerArr);
        JsonElement jsonObject = new JsonObject();
        jsonObject.addProperty("accessToken", token);
        httpPost.setEntity(new StringEntity(new Gson().toJson(jsonObject)));
        CloseableHttpResponse response = client.execute(httpPost);
        return response.getStatusLine().getStatusCode() == 204;
    }

    @Nullable
    public final String getUsername(@NotNull String uuid) {
        Intrinsics.checkNotNullParameter(uuid, "uuid");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(new HttpGet("https://api.mojang.com/user/profiles/" + uuid + "/names"));
        if (response.getStatusLine().getStatusCode() != 200) {
            return null;
        }
        JsonArray names = new JsonParser().parse(EntityUtils.toString(response.getEntity())).getAsJsonArray();
        return names.get(names.size() - 1).getAsJsonObject().get("name").getAsString();
    }

    @NotNull
    public final String getUUID(@NotNull String username) {
        Intrinsics.checkNotNullParameter(username, "username");
        try {
            URLConnection openConnection = new URL(Intrinsics.stringPlus("https://api.mojang.com/users/profiles/minecraft/", username)).openConnection();
            if (openConnection == null) {
                throw new NullPointerException("null cannot be cast to non-null type javax.net.ssl.HttpsURLConnection");
            }
            HttpsURLConnection httpConnection = (HttpsURLConnection) openConnection;
            httpConnection.setConnectTimeout(2000);
            httpConnection.setReadTimeout(2000);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            HttpURLConnection.setFollowRedirects(true);
            httpConnection.setDoOutput(true);
            if (httpConnection.getResponseCode() != 200) {
                return "";
            }
            InputStreamReader inputStreamReader = new InputStreamReader(httpConnection.getInputStream());
            InputStreamReader it = inputStreamReader;
            JsonElement jsonElement = new JsonParser().parse(it);
            if (jsonElement.isJsonObject()) {
                String asString = jsonElement.getAsJsonObject().get("id").getAsString();
                Intrinsics.checkNotNullExpressionValue(asString, "jsonElement.asJsonObject.get(\"id\").asString");
                Closeable.closeFinally(inputStreamReader, null);
                return asString;
            }
            Unit unit = Unit.INSTANCE;
            Closeable.closeFinally(inputStreamReader, null);
            return "";
        } catch (Throwable th) {
            return "";
        }
    }
}
