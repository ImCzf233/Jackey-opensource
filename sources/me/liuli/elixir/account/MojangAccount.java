package me.liuli.elixir.account;

import com.google.gson.JsonObject;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import kotlin.Annotations;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.exception.LoginException;
import me.liuli.elixir.utils.GsonExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: MojangAccount.kt */
@Annotations(message = "Mojang removed support for MojangAccount")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u001a\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001b"}, m53d2 = {"Lme/liuli/elixir/account/MojangAccount;", "Lme/liuli/elixir/account/MinecraftAccount;", "()V", "accessToken", "", "email", "getEmail", "()Ljava/lang/String;", "setEmail", "(Ljava/lang/String;)V", "name", "getName", "setName", "password", "getPassword", "setPassword", "session", "Lme/liuli/elixir/compat/Session;", "getSession", "()Lme/liuli/elixir/compat/Session;", "uuid", "fromRawJson", "", "json", "Lcom/google/gson/JsonObject;", "toRawJson", "update", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MojangAccount.class */
public final class MojangAccount extends MinecraftAccount {
    @NotNull
    private String name = "";
    @NotNull
    private String email = "";
    @NotNull
    private String password = "";
    @NotNull
    private String uuid = "";
    @NotNull
    private String accessToken = "";

    public MojangAccount() {
        super("Mojang");
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

    @NotNull
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.email = str;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.password = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
        if ((r7.accessToken.length() == 0) != false) goto L17;
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
            java.lang.String r0 = r0.getName()
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L13
            r0 = 1
            goto L14
        L13:
            r0 = 0
        L14:
            if (r0 != 0) goto L45
            r0 = r7
            java.lang.String r0 = r0.uuid
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L2a
            r0 = 1
            goto L2b
        L2a:
            r0 = 0
        L2b:
            if (r0 != 0) goto L45
            r0 = r7
            java.lang.String r0 = r0.accessToken
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L41
            r0 = 1
            goto L42
        L41:
            r0 = 0
        L42:
            if (r0 == 0) goto L49
        L45:
            r0 = r7
            r0.update()
        L49:
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
        throw new UnsupportedOperationException("Method not decompiled: me.liuli.elixir.account.MojangAccount.getSession():me.liuli.elixir.compat.Session");
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void update() {
        YggdrasilUserAuthentication createUserAuthentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        if (createUserAuthentication == null) {
            throw new NullPointerException("null cannot be cast to non-null type com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
        }
        YggdrasilUserAuthentication userAuthentication = createUserAuthentication;
        userAuthentication.setUsername(this.email);
        userAuthentication.setPassword(this.password);
        try {
            userAuthentication.logIn();
            String name = userAuthentication.getSelectedProfile().getName();
            Intrinsics.checkNotNullExpressionValue(name, "userAuthentication.selectedProfile.name");
            setName(name);
            String uuid = userAuthentication.getSelectedProfile().getId().toString();
            Intrinsics.checkNotNullExpressionValue(uuid, "userAuthentication.selectedProfile.id.toString()");
            this.uuid = uuid;
            String authenticatedToken = userAuthentication.getAuthenticatedToken();
            Intrinsics.checkNotNullExpressionValue(authenticatedToken, "userAuthentication.authenticatedToken");
            this.accessToken = authenticatedToken;
        } catch (AuthenticationUnavailableException e) {
            throw new LoginException("Mojang server is unavailable");
        } catch (AuthenticationException exception) {
            String message = exception.getMessage();
            if (message == null) {
                message = "Unknown error";
            }
            throw new LoginException(message);
        }
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void toRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        GsonExtension.set(json, "name", getName());
        GsonExtension.set(json, "email", this.email);
        GsonExtension.set(json, "password", this.password);
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void fromRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        String string = GsonExtension.string(json, "name");
        Intrinsics.checkNotNull(string);
        setName(string);
        String string2 = GsonExtension.string(json, "email");
        Intrinsics.checkNotNull(string2);
        this.email = string2;
        String string3 = GsonExtension.string(json, "password");
        Intrinsics.checkNotNull(string3);
        this.password = string3;
    }
}
