package me.liuli.elixir.manage;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MicrosoftAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import me.liuli.elixir.utils.GsonExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: AccountSerializer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004¨\u0006\r"}, m53d2 = {"Lme/liuli/elixir/manage/AccountSerializer;", "", "()V", "accountInstance", "Lme/liuli/elixir/account/MinecraftAccount;", "name", "", "password", "fromJson", "json", "Lcom/google/gson/JsonObject;", "toJson", "account", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/manage/AccountSerializer.class */
public final class AccountSerializer {
    @NotNull
    public static final AccountSerializer INSTANCE = new AccountSerializer();

    private AccountSerializer() {
    }

    @NotNull
    public final JsonObject toJson(@NotNull MinecraftAccount account) {
        Intrinsics.checkNotNullParameter(account, "account");
        JsonObject json = new JsonObject();
        account.toRawJson(json);
        String canonicalName = account.getClass().getCanonicalName();
        Intrinsics.checkNotNullExpressionValue(canonicalName, "account.javaClass.canonicalName");
        GsonExtension.set(json, "type", canonicalName);
        return json;
    }

    @NotNull
    public final MinecraftAccount fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        String string = GsonExtension.string(json, "type");
        Intrinsics.checkNotNull(string);
        Object newInstance = Class.forName(string).newInstance();
        if (newInstance == null) {
            throw new NullPointerException("null cannot be cast to non-null type me.liuli.elixir.account.MinecraftAccount");
        }
        MinecraftAccount account = (MinecraftAccount) newInstance;
        account.fromRawJson(json);
        return account;
    }

    @NotNull
    public final MinecraftAccount accountInstance(@NotNull String name, @NotNull String password) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(password, "password");
        if (StringsKt.startsWith$default(name, "ms@", false, 2, (Object) null)) {
            String realName = name.substring(3);
            Intrinsics.checkNotNullExpressionValue(realName, "this as java.lang.String).substring(startIndex)");
            return password.length() == 0 ? MicrosoftAccount.Companion.buildFromAuthCode(realName, MicrosoftAccount.AuthMethod.Companion.getMICROSOFT()) : MicrosoftAccount.Companion.buildFromPassword$default(MicrosoftAccount.Companion, realName, password, null, 4, null);
        }
        if (password.length() == 0) {
            CrackedAccount it = new CrackedAccount();
            it.setName(name);
            return it;
        }
        MojangAccount it2 = new MojangAccount();
        it2.setName(name);
        it2.setPassword(password);
        return it2;
    }
}
