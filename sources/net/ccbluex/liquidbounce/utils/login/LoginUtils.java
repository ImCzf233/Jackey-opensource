package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;

/* compiled from: LoginUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "loginSessionId", "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "sessionId", "", "LoginResult", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/login/LoginUtils.class */
public final class LoginUtils extends MinecraftInstance {
    @NotNull
    public static final LoginUtils INSTANCE = new LoginUtils();

    /* compiled from: LoginUtils.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "", "(Ljava/lang/String;I)V", "INVALID_ACCOUNT_DATA", "LOGGED", "FAILED_PARSE_TOKEN", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult.class */
    public enum LoginResult {
        INVALID_ACCOUNT_DATA,
        LOGGED,
        FAILED_PARSE_TOKEN
    }

    private LoginUtils() {
    }

    @JvmStatic
    @NotNull
    public static final LoginResult loginSessionId(@NotNull String sessionId) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        try {
            byte[] decode = Base64.getDecoder().decode((String) StringsKt.split$default((CharSequence) sessionId, new String[]{"."}, false, 0, 6, (Object) null).get(1));
            Intrinsics.checkNotNullExpressionValue(decode, "getDecoder().decode(sessionId.split(\".\")[1])");
            String decodedSessionData = new String(decode, Charsets.UTF_8);
            try {
                JsonObject sessionObject = new JsonParser().parse(decodedSessionData).getAsJsonObject();
                String uuid = sessionObject.get("spr").getAsString();
                String accessToken = sessionObject.get("yggt").getAsString();
                UserUtils userUtils = UserUtils.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
                if (!userUtils.isValidToken(accessToken)) {
                    return LoginResult.INVALID_ACCOUNT_DATA;
                }
                UserUtils userUtils2 = UserUtils.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(uuid, "uuid");
                String username = userUtils2.getUsername(uuid);
                if (username == null) {
                    return LoginResult.INVALID_ACCOUNT_DATA;
                }
                MinecraftInstance.f362mc.field_71449_j = new Session(username, uuid, accessToken, "mojang");
                LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                return LoginResult.LOGGED;
            } catch (Exception e) {
                return LoginResult.FAILED_PARSE_TOKEN;
            }
        } catch (Exception e2) {
            return LoginResult.FAILED_PARSE_TOKEN;
        }
    }
}
