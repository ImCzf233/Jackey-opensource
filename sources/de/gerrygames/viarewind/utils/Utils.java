package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/Utils.class */
public class Utils {
    public static UUID getUUID(UserConnection user) {
        return user.getProtocolInfo().getUuid();
    }
}
