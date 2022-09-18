package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/pipe/Pipe.class */
public abstract class Pipe {
    private static final int VERSION = 1;
    PipeStatus status = PipeStatus.CONNECTING;
    IPCListener listener;
    private DiscordBuild build;
    final IPCClient ipcClient;
    private final HashMap<String, Callback> callbacks;
    private static final Logger LOGGER = LoggerFactory.getLogger(Pipe.class);
    private static final String[] unixPaths = {"XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP"};

    public abstract Packet read() throws IOException, JSONException;

    public abstract void write(byte[] bArr) throws IOException;

    public abstract void close() throws IOException;

    public Pipe(IPCClient ipcClient, HashMap<String, Callback> callbacks) {
        this.ipcClient = ipcClient;
        this.callbacks = callbacks;
    }

    public static Pipe openPipe(IPCClient ipcClient, long clientId, HashMap<String, Callback> callbacks, DiscordBuild... preferredOrder) throws NoDiscordClientException {
        if (preferredOrder == null || preferredOrder.length == 0) {
            preferredOrder = new DiscordBuild[]{DiscordBuild.ANY};
        }
        Pipe pipe = null;
        Pipe[] open = new Pipe[DiscordBuild.values().length];
        for (int i = 0; i < 10; i++) {
            try {
                String location = getPipeLocation(i);
                LOGGER.debug(String.format("Searching for IPC: %s", location));
                pipe = createPipe(ipcClient, callbacks, location);
                pipe.send(Packet.OpCode.HANDSHAKE, new JSONObject().put("v", 1).put("client_id", Long.toString(clientId)), null);
                Packet p = pipe.read();
                pipe.build = DiscordBuild.from(p.getJson().getJSONObject("data").getJSONObject("config").getString("api_endpoint"));
                LOGGER.debug(String.format("Found a valid client (%s) with packet: %s", pipe.build.name(), p.toString()));
            } catch (IOException | JSONException e) {
                pipe = null;
            }
            if (pipe.build == preferredOrder[0] || DiscordBuild.ANY == preferredOrder[0]) {
                LOGGER.info(String.format("Found preferred client: %s", pipe.build.name()));
                break;
            }
            open[pipe.build.ordinal()] = pipe;
            open[DiscordBuild.ANY.ordinal()] = pipe;
            pipe.build = null;
            pipe = null;
        }
        if (pipe == null) {
            int i2 = 1;
            while (true) {
                if (i2 >= preferredOrder.length) {
                    break;
                }
                DiscordBuild cb = preferredOrder[i2];
                LOGGER.debug(String.format("Looking for client build: %s", cb.name()));
                if (open[cb.ordinal()] == null) {
                    i2++;
                } else {
                    pipe = open[cb.ordinal()];
                    open[cb.ordinal()] = null;
                    if (cb == DiscordBuild.ANY) {
                        for (int k = 0; k < open.length; k++) {
                            if (open[k] == pipe) {
                                pipe.build = DiscordBuild.values()[k];
                                open[k] = null;
                            }
                        }
                    } else {
                        pipe.build = cb;
                    }
                    LOGGER.info(String.format("Found preferred client: %s", pipe.build.name()));
                }
            }
            if (pipe == null) {
                throw new NoDiscordClientException();
            }
        }
        for (int i3 = 0; i3 < open.length; i3++) {
            if (i3 != DiscordBuild.ANY.ordinal() && open[i3] != null) {
                try {
                    open[i3].close();
                } catch (IOException ex) {
                    LOGGER.debug("Failed to close an open IPC pipe!", (Throwable) ex);
                }
            }
        }
        pipe.status = PipeStatus.CONNECTED;
        return pipe;
    }

    private static Pipe createPipe(IPCClient ipcClient, HashMap<String, Callback> callbacks, String location) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return new WindowsPipe(ipcClient, callbacks, location);
        }
        if (osName.contains("linux") || osName.contains("mac")) {
            try {
                return new UnixPipe(ipcClient, callbacks, location);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Unsupported OS: " + osName);
    }

    public void send(Packet.OpCode op, JSONObject data, Callback callback) {
        try {
            String nonce = generateNonce();
            Packet p = new Packet(op, data.put("nonce", nonce));
            if (callback != null && !callback.isEmpty()) {
                this.callbacks.put(nonce, callback);
            }
            write(p.toBytes());
            LOGGER.debug(String.format("Sent packet: %s", p.toString()));
            if (this.listener != null) {
                this.listener.onPacketSent(this.ipcClient, p);
            }
        } catch (IOException e) {
            LOGGER.error("Encountered an IOException while sending a packet and disconnected!");
            this.status = PipeStatus.DISCONNECTED;
        }
    }

    private static String generateNonce() {
        return UUID.randomUUID().toString();
    }

    public PipeStatus getStatus() {
        return this.status;
    }

    public void setStatus(PipeStatus status) {
        this.status = status;
    }

    public void setListener(IPCListener listener) {
        this.listener = listener;
    }

    public DiscordBuild getDiscordBuild() {
        return this.build;
    }

    private static String getPipeLocation(int i) {
        String[] strArr;
        if (System.getProperty("os.name").contains("Win")) {
            return "\\\\?\\pipe\\discord-ipc-" + i;
        }
        String tmppath = null;
        for (String str : unixPaths) {
            tmppath = System.getenv(str);
            if (tmppath != null) {
                break;
            }
        }
        if (tmppath == null) {
            tmppath = "/tmp";
        }
        return tmppath + "/discord-ipc-" + i;
    }
}
