package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/pipe/WindowsPipe.class */
public class WindowsPipe extends Pipe {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowsPipe.class);
    private final RandomAccessFile file;

    public WindowsPipe(IPCClient ipcClient, HashMap<String, Callback> callbacks, String location) {
        super(ipcClient, callbacks);
        try {
            this.file = new RandomAccessFile(location, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public void write(byte[] b) throws IOException {
        this.file.write(b);
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public Packet read() throws IOException, JSONException {
        while (this.file.length() == 0 && this.status == PipeStatus.CONNECTED) {
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
        Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
        int len = Integer.reverseBytes(this.file.readInt());
        byte[] d = new byte[len];
        this.file.readFully(d);
        Packet p = new Packet(op, new JSONObject(new String(d)));
        LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }

    @Override // com.jagrosh.discordipc.entities.pipe.Pipe
    public void close() throws IOException {
        LOGGER.debug("Closing IPC pipe...");
        send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.file.close();
    }
}
