package me.liuli.elixir.account;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.compat.Session;
import org.jetbrains.annotations.NotNull;

/* compiled from: MinecraftAccount.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0012\u001a\u00020\u000eH&R\u0012\u0010\u0005\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\f\u0010\u0007¨\u0006\u0013"}, m53d2 = {"Lme/liuli/elixir/account/MinecraftAccount;", "", "type", "", "(Ljava/lang/String;)V", "name", "getName", "()Ljava/lang/String;", "session", "Lme/liuli/elixir/compat/Session;", "getSession", "()Lme/liuli/elixir/compat/Session;", "getType", "fromRawJson", "", "json", "Lcom/google/gson/JsonObject;", "toRawJson", "update", "Elixir"})
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/account/MinecraftAccount.class */
public abstract class MinecraftAccount {
    @NotNull
    private final String type;

    @NotNull
    public abstract String getName();

    @NotNull
    public abstract Session getSession();

    public abstract void update();

    public abstract void toRawJson(@NotNull JsonObject jsonObject);

    public abstract void fromRawJson(@NotNull JsonObject jsonObject);

    public MinecraftAccount(@NotNull String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }
}
