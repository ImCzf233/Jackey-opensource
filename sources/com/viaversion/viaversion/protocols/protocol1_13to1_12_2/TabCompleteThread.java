package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/TabCompleteThread.class */
public class TabCompleteThread implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
            if (info.getProtocolInfo() != null && info.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) && info.getChannel().isOpen()) {
                ((TabCompleteTracker) info.get(TabCompleteTracker.class)).sendPacketToServer(info);
            }
        }
    }
}
