package me.liuli.elixir.account;

import com.google.gson.JsonObject;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import me.liuli.elixir.compat.Session;
import me.liuli.elixir.utils.GsonExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: CrackedAccount.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, m53d2 = {"Lme/liuli/elixir/account/CrackedAccount;", "Lme/liuli/elixir/account/MinecraftAccount;", "()V", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "session", "Lme/liuli/elixir/compat/Session;", "getSession", "()Lme/liuli/elixir/compat/Session;", "fromRawJson", "", "json", "Lcom/google/gson/JsonObject;", "toRawJson", "update", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/CrackedAccount.class */
public final class CrackedAccount extends MinecraftAccount {
    @NotNull
    private String name = "";

    public CrackedAccount() {
        super("Cracked");
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

    @Override // me.liuli.elixir.account.MinecraftAccount
    @NotNull
    public Session getSession() {
        String name = getName();
        byte[] bytes = getName().getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        String uuid = UUID.nameUUIDFromBytes(bytes).toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "nameUUIDFromBytes(name.t…arsets.UTF_8)).toString()");
        return new Session(name, uuid, "-", "legacy");
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void update() {
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void toRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        GsonExtension.set(json, "name", getName());
    }

    @Override // me.liuli.elixir.account.MinecraftAccount
    public void fromRawJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        String string = GsonExtension.string(json, "name");
        Intrinsics.checkNotNull(string);
        setName(string);
    }
}
