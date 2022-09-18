package de.gerrygames.viarewind.protocol.protocol1_8to1_9.util;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Vector;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import kotlin.jvm.internal.ShortCompanionObject;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/util/RelativeMoveUtil.class */
public class RelativeMoveUtil {
    public static Vector[] calculateRelativeMoves(UserConnection user, int entityId, int relX, int relY, int relZ) {
        Vector[] moves;
        int sentRelZ;
        int sentRelY;
        int sentRelX;
        EntityTracker tracker = (EntityTracker) user.get(EntityTracker.class);
        Vector offset = tracker.getEntityOffset(entityId);
        int relX2 = relX + offset.getBlockX();
        int relY2 = relY + offset.getBlockY();
        int relZ2 = relZ + offset.getBlockZ();
        if (relX2 > 32767) {
            offset.setBlockX(relX2 - ShortCompanionObject.MAX_VALUE);
            relX2 = 32767;
        } else if (relX2 < -32768) {
            offset.setBlockX(relX2 - ShortCompanionObject.MIN_VALUE);
            relX2 = -32768;
        } else {
            offset.setBlockX(0);
        }
        if (relY2 > 32767) {
            offset.setBlockY(relY2 - ShortCompanionObject.MAX_VALUE);
            relY2 = 32767;
        } else if (relY2 < -32768) {
            offset.setBlockY(relY2 - ShortCompanionObject.MIN_VALUE);
            relY2 = -32768;
        } else {
            offset.setBlockY(0);
        }
        if (relZ2 > 32767) {
            offset.setBlockZ(relZ2 - ShortCompanionObject.MAX_VALUE);
            relZ2 = 32767;
        } else if (relZ2 < -32768) {
            offset.setBlockZ(relZ2 - ShortCompanionObject.MIN_VALUE);
            relZ2 = -32768;
        } else {
            offset.setBlockZ(0);
        }
        if (relX2 > 16256 || relX2 < -16384 || relY2 > 16256 || relY2 < -16384 || relZ2 > 16256 || relZ2 < -16384) {
            byte relX1 = (byte) (relX2 / 256);
            byte relX22 = (byte) Math.round((relX2 - (relX1 * 128)) / 128.0f);
            byte relY1 = (byte) (relY2 / 256);
            byte relY22 = (byte) Math.round((relY2 - (relY1 * 128)) / 128.0f);
            byte relZ1 = (byte) (relZ2 / 256);
            byte relZ22 = (byte) Math.round((relZ2 - (relZ1 * 128)) / 128.0f);
            sentRelX = relX1 + relX22;
            sentRelY = relY1 + relY22;
            sentRelZ = relZ1 + relZ22;
            moves = new Vector[]{new Vector(relX1, relY1, relZ1), new Vector(relX22, relY22, relZ22)};
        } else {
            sentRelX = Math.round(relX2 / 128.0f);
            sentRelY = Math.round(relY2 / 128.0f);
            sentRelZ = Math.round(relZ2 / 128.0f);
            moves = new Vector[]{new Vector(sentRelX, sentRelY, sentRelZ)};
        }
        offset.setBlockX((offset.getBlockX() + relX2) - (sentRelX * 128));
        offset.setBlockY((offset.getBlockY() + relY2) - (sentRelY * 128));
        offset.setBlockZ((offset.getBlockZ() + relZ2) - (sentRelZ * 128));
        tracker.setEntityOffset(entityId, offset);
        return moves;
    }
}
