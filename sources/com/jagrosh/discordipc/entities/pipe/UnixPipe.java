package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/pipe/UnixPipe.class */
public class UnixPipe extends Pipe {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnixPipe.class);
    private final AFUNIXSocket socket = AFUNIXSocket.newInstance();

    public UnixPipe(IPCClient ipcClient, HashMap<String, Callback> callbacks, String location) throws IOException {
        super(ipcClient, callbacks);
        this.socket.connect(new AFUNIXSocketAddress(new File(location)));
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public Packet read() throws IOException, JSONException {
        InputStream is = this.socket.getInputStream();
        while (is.available() == 0 && this.status == PipeStatus.CONNECTED) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
            }
        }
        if (this.status == PipeStatus.DISCONNECTED) {
            throw new IOException("Disconnected!");
        }
        if (this.status == PipeStatus.CLOSED) {
            return new Packet(Packet.OpCode.CLOSE, null);
        }
        byte[] d = new byte[8];
        is.read(d);
        ByteBuffer bb = ByteBuffer.wrap(d);
        Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(bb.getInt())];
        byte[] d2 = new byte[Integer.reverseBytes(bb.getInt())];
        is.read(d2);
        Packet p = new Packet(op, new JSONObject(new String(d2)));
        LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public void write(byte[] b) throws IOException {
        this.socket.getOutputStream().write(b);
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public void close() throws IOException {
        LOGGER.debug("Closing IPC pipe...");
        send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.socket.close();
    }
}
