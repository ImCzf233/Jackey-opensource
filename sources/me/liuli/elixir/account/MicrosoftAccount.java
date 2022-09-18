package me.liuli.elixir.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import me.liuli.elixir.compat.OAuthServer;
import me.liuli.elixir.exception.LoginException;
import me.liuli.elixir.utils.GsonExtension;
import me.liuli.elixir.utils.HttpUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: MicrosoftAccount.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018�� \u00192\u00020\u0001:\u0003\u0018\u0019\u001aB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\u0007\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n��\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001b"}, m53d2 = {"Lme/liuli/elixir/account/MicrosoftAccount;", "Lme/liuli/elixir/account/MinecraftAccount;", "()V", "accessToken", "", "authMethod", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "refreshToken", "session", "Lme/liuli/elixir/compat/Session;", "getSession", "()Lme/liuli/elixir/compat/Session;", "uuid", "fromRawJson", "", "json", "Lcom/google/gson/JsonObject;", "toRawJson", "update", "AuthMethod", "Companion", "OAuthHandler", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MicrosoftAccount.class */
public final class MicrosoftAccount extends MinecraftAccount {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String name = "";
    @NotNull
    private String uuid = "";
    @NotNull
    private String accessToken = "";
    @NotNull
    private String refreshToken = "";
    @NotNull
    private AuthMethod authMethod = AuthMethod.Companion.getMICROSOFT();
    @NotNull
    public static final String XBOX_PRE_AUTH_URL = "https://login.live.com/oauth20_authorize.srf?client_id=<client_id>&redirect_uri=<redirect_uri>&response_type=code&display=touch&scope=<scope>";
    @NotNull
    public static final String XBOX_AUTH_URL = "https://login.live.com/oauth20_token.srf";
    @NotNull
    public static final String XBOX_XBL_URL = "https://user.auth.xboxlive.com/user/authenticate";
    @NotNull
    public static final String XBOX_XSTS_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";
    @NotNull
    public static final String MC_AUTH_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";
    @NotNull
    public static final String MC_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";
    @NotNull
    public static final String XBOX_AUTH_DATA = "client_id=<client_id>&redirect_uri=<redirect_uri>&grant_type=authorization_code&code=";
    @NotNull
    public static final String XBOX_REFRESH_DATA = "client_id=<client_id>&scope=<scope>&grant_type=refresh_token&redirect_uri=<redirect_uri>&refresh_token=";
    @NotNull
    public static final String XBOX_XBL_DATA = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"<rps_ticket>\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";
    @NotNull
    public static final String XBOX_XSTS_DATA = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"<xbl_token>\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";
    @NotNull
    public static final String MC_AUTH_DATA = "{\"identityToken\":\"XBL3.0 x=<userhash>;<xsts_token>\"}";

    /* compiled from: MicrosoftAccount.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018��2\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0005H&¨\u0006\u000b"}, m53d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "", "authError", "", "error", "", "authResult", "account", "Lme/liuli/elixir/account/MicrosoftAccount;", "openUrl", "url", "Elixir"})
    /* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MicrosoftAccount$OAuthHandler.class */
    public interface OAuthHandler {
        void openUrl(@NotNull String str);

        void authResult(@NotNull MicrosoftAccount microsoftAccount);

        void authError(@NotNull String str);
    }

    public MicrosoftAccount() {
        super("Microsoft");
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002b, code lost:
        if ((r7.accessToken.length() == 0) != false) goto L12;
     */
    @Override // me.liuli.elixir.account.MinecraftAccount
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public me.liuli.elixir.compat.Session getSession() {
        /*
            r7 = this;
            r0 = r7
            java.lang.String r0 = r0.uuid
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L13
            r0 = 1
            goto L14
        L13:
            r0 = 0
        L14:
            if (r0 != 0) goto L2e
            r0 = r7
            java.lang.String r0 = r0.accessToken
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L2a
            r0 = 1
            goto L2b
        L2a:
            r0 = 0
        L2b:
            if (r0 == 0) goto L32
        L2e:
            r0 = r7
            r0.update()
        L32:
            me.liuli.elixir.compat.Session r0 = new me.liuli.elixir.compat.Session
            r1 = r0
            r2 = r7
            java.lang.String r2 = r2.getName()
            r3 = r7
            java.lang.String r3 = r3.uuid
            r4 = r7
            java.lang.String r4 = r4.accessToken
            java.lang.String r5 = "mojang"
            r1.<init>(r2, r3, r4, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: me.liuli.elixir.account.MicrosoftAccount.getSession():me.liuli.elixir.compat.Session");
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void update() {
        String str;
        Map jsonPostHeader = MapsKt.mapOf(TuplesKt.m46to("Content-Type", "application/json"), TuplesKt.m46to("Accept", "application/json"));
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = HttpUtils.make$default(HttpUtils.INSTANCE, XBOX_AUTH_URL, "POST", Intrinsics.stringPlus(Companion.replaceKeys(this.authMethod, XBOX_REFRESH_DATA), this.refreshToken), MapsKt.mapOf(TuplesKt.m46to("Content-Type", "application/x-www-form-urlencoded")), null, 16, null).getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "HttpUtils.make(\n        …urlencoded\")).inputStream");
        JsonObject msRefreshJson = jsonParser.parse(new InputStreamReader(inputStream, Charsets.UTF_8)).getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(msRefreshJson, "msRefreshJson");
        String msAccessToken = GsonExtension.string(msRefreshJson, "access_token");
        if (msAccessToken == null) {
            throw new LoginException("Microsoft access token is null");
        }
        String string = GsonExtension.string(msRefreshJson, "refresh_token");
        if (string == null) {
            throw new LoginException("Microsoft new refresh token is null");
        }
        this.refreshToken = string;
        JsonParser jsonParser2 = new JsonParser();
        InputStream inputStream2 = HttpUtils.make$default(HttpUtils.INSTANCE, XBOX_XBL_URL, "POST", StringsKt.replace$default(XBOX_XBL_DATA, "<rps_ticket>", StringsKt.replace$default(this.authMethod.getRpsTicketRule(), "<access_token>", msAccessToken, false, 4, (Object) null), false, 4, (Object) null), jsonPostHeader, null, 16, null).getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream2, "HttpUtils.make(XBOX_XBL_…onPostHeader).inputStream");
        JsonObject xblJson = jsonParser2.parse(new InputStreamReader(inputStream2, Charsets.UTF_8)).getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(xblJson, "xblJson");
        String xblToken = GsonExtension.string(xblJson, "Token");
        if (xblToken == null) {
            throw new LoginException("Microsoft XBL token is null");
        }
        JsonObject obj = GsonExtension.obj(xblJson, "DisplayClaims");
        if (obj == null) {
            str = null;
        } else {
            JsonArray array = GsonExtension.array(obj, "xui");
            if (array == null) {
                str = null;
            } else {
                JsonElement jsonElement = array.get(0);
                if (jsonElement == null) {
                    str = null;
                } else {
                    JsonObject asJsonObject = jsonElement.getAsJsonObject();
                    str = asJsonObject == null ? null : GsonExtension.string(asJsonObject, "uhs");
                }
            }
        }
        if (str == null) {
            throw new LoginException("Microsoft XBL userhash is null");
        }
        String userhash = str;
        JsonParser jsonParser3 = new JsonParser();
        InputStream inputStream3 = HttpUtils.make$default(HttpUtils.INSTANCE, XBOX_XSTS_URL, "POST", StringsKt.replace$default(XBOX_XSTS_DATA, "<xbl_token>", xblToken, false, 4, (Object) null), jsonPostHeader, null, 16, null).getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream3, "HttpUtils.make(XBOX_XSTS…onPostHeader).inputStream");
        JsonObject xstsJson = jsonParser3.parse(new InputStreamReader(inputStream3, Charsets.UTF_8)).getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(xstsJson, "xstsJson");
        String xstsToken = GsonExtension.string(xstsJson, "Token");
        if (xstsToken == null) {
            throw new LoginException("Microsoft XSTS token is null");
        }
        JsonParser jsonParser4 = new JsonParser();
        InputStream inputStream4 = HttpUtils.make$default(HttpUtils.INSTANCE, MC_AUTH_URL, "POST", StringsKt.replace$default(StringsKt.replace$default(MC_AUTH_DATA, "<userhash>", userhash, false, 4, (Object) null), "<xsts_token>", xstsToken, false, 4, (Object) null), jsonPostHeader, null, 16, null).getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream4, "HttpUtils.make(MC_AUTH_U…onPostHeader).inputStream");
        JsonObject mcJson = jsonParser4.parse(new InputStreamReader(inputStream4, Charsets.UTF_8)).getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(mcJson, "mcJson");
        String string2 = GsonExtension.string(mcJson, "access_token");
        if (string2 == null) {
            throw new LoginException("Minecraft access token is null");
        }
        this.accessToken = string2;
        JsonParser jsonParser5 = new JsonParser();
        InputStream inputStream5 = HttpUtils.make$default(HttpUtils.INSTANCE, MC_PROFILE_URL, "GET", "", MapsKt.mapOf(TuplesKt.m46to("Authorization", Intrinsics.stringPlus("Bearer ", this.accessToken))), null, 16, null).getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream5, "HttpUtils.make(MC_PROFIL…ccessToken\")).inputStream");
        JsonObject mcProfileJson = jsonParser5.parse(new InputStreamReader(inputStream5, Charsets.UTF_8)).getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(mcProfileJson, "mcProfileJson");
        String string3 = GsonExtension.string(mcProfileJson, "name");
        if (string3 == null) {
            throw new LoginException("Minecraft account name is null");
        }
        setName(string3);
        String string4 = GsonExtension.string(mcProfileJson, "id");
        if (string4 == null) {
            throw new LoginException("Minecraft account uuid is null");
        }
        this.uuid = string4;
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void toRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        GsonExtension.set(json, "name", getName());
        GsonExtension.set(json, "refreshToken", this.refreshToken);
        Map $this$filterValues$iv = AuthMethod.Companion.getRegistry();
        LinkedHashMap result$iv = new LinkedHashMap();
        for (Map.Entry entry$iv : $this$filterValues$iv.entrySet()) {
            AuthMethod it = entry$iv.getValue();
            if (Intrinsics.areEqual(it, this.authMethod)) {
                result$iv.put(entry$iv.getKey(), entry$iv.getValue());
            }
        }
        String str = (String) CollectionsKt.firstOrNull(result$iv.keySet());
        if (str == null) {
            throw new LoginException("Unregistered auth method");
        }
        GsonExtension.set(json, "authMethod", str);
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void fromRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        String string = GsonExtension.string(json, "name");
        Intrinsics.checkNotNull(string);
        setName(string);
        String string2 = GsonExtension.string(json, "refreshToken");
        Intrinsics.checkNotNull(string2);
        this.refreshToken = string2;
        Map<String, AuthMethod> registry = AuthMethod.Companion.getRegistry();
        String string3 = GsonExtension.string(json, "authMethod");
        Intrinsics.checkNotNull(string3);
        AuthMethod authMethod = registry.get(string3);
        if (authMethod == null) {
            throw new LoginException("Unregistered auth method");
        }
        this.authMethod = authMethod;
    }

    /* compiled from: MicrosoftAccount.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0013J \u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\b\b\u0002\u0010\u0018\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n��¨\u0006\u001e"}, m53d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$Companion;", "", "()V", "MC_AUTH_DATA", "", "MC_AUTH_URL", "MC_PROFILE_URL", "XBOX_AUTH_DATA", "XBOX_AUTH_URL", "XBOX_PRE_AUTH_URL", "XBOX_REFRESH_DATA", "XBOX_XBL_DATA", "XBOX_XBL_URL", "XBOX_XSTS_DATA", "XBOX_XSTS_URL", "buildFromAuthCode", "Lme/liuli/elixir/account/MicrosoftAccount;", "code", "method", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "buildFromOpenBrowser", "Lme/liuli/elixir/compat/OAuthServer;", "handler", "Lme/liuli/elixir/account/MicrosoftAccount$OAuthHandler;", "authMethod", "buildFromPassword", "username", "password", "replaceKeys", "string", "Elixir"})
    /* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MicrosoftAccount$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final MicrosoftAccount buildFromAuthCode(@NotNull String code, @NotNull AuthMethod method) {
            Intrinsics.checkNotNullParameter(code, "code");
            Intrinsics.checkNotNullParameter(method, "method");
            JsonParser jsonParser = new JsonParser();
            InputStream inputStream = HttpUtils.make$default(HttpUtils.INSTANCE, MicrosoftAccount.XBOX_AUTH_URL, "POST", Intrinsics.stringPlus(replaceKeys(method, MicrosoftAccount.XBOX_AUTH_DATA), code), MapsKt.mapOf(TuplesKt.m46to("Content-Type", "application/x-www-form-urlencoded")), null, 16, null).getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "HttpUtils.make(XBOX_AUTH…urlencoded\")).inputStream");
            JsonObject data = jsonParser.parse(new InputStreamReader(inputStream, Charsets.UTF_8)).getAsJsonObject();
            if (data.has("refresh_token")) {
                MicrosoftAccount it = new MicrosoftAccount();
                Intrinsics.checkNotNullExpressionValue(data, "data");
                String string = GsonExtension.string(data, "refresh_token");
                Intrinsics.checkNotNull(string);
                it.refreshToken = string;
                it.authMethod = method;
                it.update();
                return it;
            }
            throw new LoginException("Failed to get refresh token");
        }

        public static /* synthetic */ MicrosoftAccount buildFromPassword$default(Companion companion, String str, String str2, AuthMethod authMethod, int i, Object obj) {
            if ((i & 4) != 0) {
                authMethod = AuthMethod.Companion.getMICROSOFT();
            }
            return companion.buildFromPassword(str, str2, authMethod);
        }

        private static final String buildFromPassword$findArgs(String resp, String arg) {
            if (StringsKt.contains$default((CharSequence) resp, (CharSequence) arg, false, 2, (Object) null)) {
                String it = resp.substring(StringsKt.indexOf$default((CharSequence) resp, Intrinsics.stringPlus(arg, ":'"), 0, false, 6, (Object) null) + arg.length() + 2);
                Intrinsics.checkNotNullExpressionValue(it, "this as java.lang.String).substring(startIndex)");
                String substring = it.substring(0, StringsKt.indexOf$default((CharSequence) it, "',", 0, false, 6, (Object) null));
                Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                return substring;
            }
            throw new LoginException(Intrinsics.stringPlus("Failed to find argument in response ", arg));
        }

        @NotNull
        public final MicrosoftAccount buildFromPassword(@NotNull String username, @NotNull String password, @NotNull AuthMethod authMethod) {
            Intrinsics.checkNotNullParameter(username, "username");
            Intrinsics.checkNotNullParameter(password, "password");
            Intrinsics.checkNotNullParameter(authMethod, "authMethod");
            HttpURLConnection preAuthConnection = HttpUtils.make$default(HttpUtils.INSTANCE, replaceKeys(authMethod, MicrosoftAccount.XBOX_PRE_AUTH_URL), "GET", null, null, null, 28, null);
            InputStream inputStream = preAuthConnection.getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "preAuthConnection.inputStream");
            String html = TextStreamsKt.readText(new InputStreamReader(inputStream, Charsets.UTF_8));
            List<String> list = preAuthConnection.getHeaderFields().get("Set-Cookie");
            if (list == null) {
                list = CollectionsKt.emptyList();
            }
            String cookies = CollectionsKt.joinToString$default(list, ";", null, null, 0, null, null, 62, null);
            String urlPost = buildFromPassword$findArgs(html, "urlPost");
            String it = buildFromPassword$findArgs(html, "sFTTag");
            String ppft = it.substring(StringsKt.indexOf$default((CharSequence) it, "value=\"", 0, false, 6, (Object) null) + 7, it.length() - 3);
            Intrinsics.checkNotNullExpressionValue(ppft, "this as java.lang.String…ing(startIndex, endIndex)");
            preAuthConnection.disconnect();
            HttpURLConnection authConnection = HttpUtils.make$default(HttpUtils.INSTANCE, urlPost, "POST", "login=" + username + "&loginfmt=" + username + "&passwd=" + password + "&PPFT=" + ppft, MapsKt.mapOf(TuplesKt.m46to("Cookie", cookies), TuplesKt.m46to("Content-Type", "application/x-www-form-urlencoded")), null, 16, null);
            InputStream inputStream2 = authConnection.getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream2, "authConnection.inputStream");
            TextStreamsKt.readText(new InputStreamReader(inputStream2, Charsets.UTF_8));
            String it2 = authConnection.getURL().toString();
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            if (!StringsKt.contains$default((CharSequence) it2, (CharSequence) "code=", false, 2, (Object) null)) {
                throw new LoginException("Failed to get auth code from response");
            }
            String pre = it2.substring(StringsKt.indexOf$default((CharSequence) it2, "code=", 0, false, 6, (Object) null) + 5);
            Intrinsics.checkNotNullExpressionValue(pre, "this as java.lang.String).substring(startIndex)");
            String code = pre.substring(0, StringsKt.indexOf$default((CharSequence) pre, "&", 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(code, "this as java.lang.String…ing(startIndex, endIndex)");
            authConnection.disconnect();
            return buildFromAuthCode(code, authMethod);
        }

        public static /* synthetic */ OAuthServer buildFromOpenBrowser$default(Companion companion, OAuthHandler oAuthHandler, AuthMethod authMethod, int i, Object obj) {
            if ((i & 2) != 0) {
                authMethod = AuthMethod.Companion.getAZURE_APP();
            }
            return companion.buildFromOpenBrowser(oAuthHandler, authMethod);
        }

        @NotNull
        public final OAuthServer buildFromOpenBrowser(@NotNull OAuthHandler handler, @NotNull AuthMethod authMethod) {
            Intrinsics.checkNotNullParameter(handler, "handler");
            Intrinsics.checkNotNullParameter(authMethod, "authMethod");
            OAuthServer it = new OAuthServer(handler, authMethod, null, null, 12, null);
            it.start();
            return it;
        }

        @NotNull
        public final String replaceKeys(@NotNull AuthMethod method, @NotNull String string) {
            Intrinsics.checkNotNullParameter(method, "method");
            Intrinsics.checkNotNullParameter(string, "string");
            return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(string, "<client_id>", method.getClientId(), false, 4, (Object) null), "<redirect_uri>", method.getRedirectUri(), false, 4, (Object) null), "<scope>", method.getScope(), false, 4, (Object) null);
        }
    }

    /* compiled from: MicrosoftAccount.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018�� \r2\u00020\u0001:\u0001\rB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\f\u0010\t¨\u0006\u000e"}, m53d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "", "clientId", "", "redirectUri", "scope", "rpsTicketRule", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getClientId", "()Ljava/lang/String;", "getRedirectUri", "getRpsTicketRule", "getScope", "Companion", "Elixir"})
    /* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MicrosoftAccount$AuthMethod.class */
    public static final class AuthMethod {
        @NotNull
        private final String clientId;
        @NotNull
        private final String redirectUri;
        @NotNull
        private final String scope;
        @NotNull
        private final String rpsTicketRule;
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private static final Map<String, AuthMethod> registry = new LinkedHashMap();
        @NotNull
        private static final AuthMethod MICROSOFT = new AuthMethod("00000000441cc96b", "https://login.live.com/oauth20_desktop.srf", "service::user.auth.xboxlive.com::MBI_SSL", "<access_token>");
        @NotNull
        private static final AuthMethod AZURE_APP = new AuthMethod("0add8caf-2cc6-4546-b798-c3d171217dd9", "http://localhost:1919/login", "XboxLive.signin%20offline_access", "d=<access_token>");

        /* compiled from: MicrosoftAccount.kt */
        @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\n¢\u0006\b\n��\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, m53d2 = {"Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod$Companion;", "", "()V", "AZURE_APP", "Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "getAZURE_APP", "()Lme/liuli/elixir/account/MicrosoftAccount$AuthMethod;", "MICROSOFT", "getMICROSOFT", "registry", "", "", "getRegistry", "()Ljava/util/Map;", "Elixir"})
        /* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MicrosoftAccount$AuthMethod$Companion.class */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            private Companion() {
            }

            @NotNull
            public final Map<String, AuthMethod> getRegistry() {
                return AuthMethod.registry;
            }

            @NotNull
            public final AuthMethod getMICROSOFT() {
                return AuthMethod.MICROSOFT;
            }

            @NotNull
            public final AuthMethod getAZURE_APP() {
                return AuthMethod.AZURE_APP;
            }
        }

        public AuthMethod(@NotNull String clientId, @NotNull String redirectUri, @NotNull String scope, @NotNull String rpsTicketRule) {
            Intrinsics.checkNotNullParameter(clientId, "clientId");
            Intrinsics.checkNotNullParameter(redirectUri, "redirectUri");
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(rpsTicketRule, "rpsTicketRule");
            this.clientId = clientId;
            this.redirectUri = redirectUri;
            this.scope = scope;
            this.rpsTicketRule = rpsTicketRule;
        }

        @NotNull
        public final String getClientId() {
            return this.clientId;
        }

        @NotNull
        public final String getRedirectUri() {
            return this.redirectUri;
        }

        @NotNull
        public final String getScope() {
            return this.scope;
        }

        @NotNull
        public final String getRpsTicketRule() {
            return this.rpsTicketRule;
        }

        static {
            registry.put("MICROSOFT", MICROSOFT);
            registry.put("AZURE_APP", AZURE_APP);
        }
    }
}
