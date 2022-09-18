package net.ccbluex.liquidbounce.utils;

import com.google.gson.JsonObject;
import io.netty.util.concurrent.GenericFutureListener;
import java.lang.reflect.Field;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/ClientUtils.class */
public final class ClientUtils extends MinecraftInstance {
    private static final Logger logger = LogManager.getLogger("LiquidBounce");
    private static Field fastRenderField;

    static {
        try {
            fastRenderField = GameSettings.class.getDeclaredField("ofFastRender");
            if (!fastRenderField.isAccessible()) {
                fastRenderField.setAccessible(true);
            }
        } catch (NoSuchFieldException e) {
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void disableFastRender() {
        try {
            if (fastRenderField != null) {
                if (!fastRenderField.isAccessible()) {
                    fastRenderField.setAccessible(true);
                }
                fastRenderField.setBoolean(f362mc.field_71474_y, false);
            }
        } catch (IllegalAccessException e) {
        }
    }

    public static void sendEncryption(NetworkManager networkManager, SecretKey secretKey, PublicKey publicKey, S01PacketEncryptionRequest encryptionRequest) {
        networkManager.func_179288_a(new C01PacketEncryptionResponse(secretKey, publicKey, encryptionRequest.func_149607_e()), p_operationComplete_1_ -> {
            networkManager.func_150727_a(secretKey);
        }, new GenericFutureListener[0]);
    }

    public static void displayChatMessage(String message) {
        if (f362mc.field_71439_g == null) {
            getLogger().info("(MCChat)" + message);
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        f362mc.field_71439_g.func_145747_a(IChatComponent.Serializer.func_150699_a(jsonObject.toString()));
    }
}
