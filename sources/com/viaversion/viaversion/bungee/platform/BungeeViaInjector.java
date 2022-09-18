package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.bungee.handlers.BungeeChannelInitializer;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.util.SetWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ProxyServer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/platform/BungeeViaInjector.class */
public class BungeeViaInjector implements ViaInjector {
    private static final Field LISTENERS_FIELD;
    private final List<Channel> injectedChannels = new ArrayList();

    static {
        try {
            LISTENERS_FIELD = ProxyServer.getInstance().getClass().getDeclaredField("listeners");
            LISTENERS_FIELD.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to access listeners field.", e);
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void inject() throws ReflectiveOperationException {
        Set<Channel> listeners = (Set) LISTENERS_FIELD.get(ProxyServer.getInstance());
        LISTENERS_FIELD.set(ProxyServer.getInstance(), new SetWrapper(listeners, channel -> {
            try {
                injectChannel(channel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        for (Channel channel2 : listeners) {
            injectChannel(channel2);
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Bungee without a reboot!");
    }

    private void injectChannel(Channel channel) throws ReflectiveOperationException {
        List<String> names = channel.pipeline().names();
        ChannelHandler bootstrapAcceptor = null;
        for (String name : names) {
            ChannelHandler handler = channel.pipeline().get(name);
            try {
                ReflectionUtil.get(handler, "childHandler", ChannelInitializer.class);
                bootstrapAcceptor = handler;
            } catch (Exception e) {
            }
        }
        if (bootstrapAcceptor == null) {
            bootstrapAcceptor = channel.pipeline().first();
        }
        if (bootstrapAcceptor.getClass().getName().equals("net.md_5.bungee.query.QueryHandler")) {
            return;
        }
        try {
            ChannelInitializer<Channel> oldInit = (ChannelInitializer) ReflectionUtil.get(bootstrapAcceptor, "childHandler", ChannelInitializer.class);
            ChannelInitializer<Channel> newInit = new BungeeChannelInitializer(oldInit);
            ReflectionUtil.set(bootstrapAcceptor, "childHandler", newInit);
            this.injectedChannels.add(channel);
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public int getServerProtocolVersion() throws Exception {
        return getBungeeSupportedVersions().get(0).intValue();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public IntSortedSet getServerProtocolVersions() throws Exception {
        return new IntLinkedOpenHashSet(getBungeeSupportedVersions());
    }

    private List<Integer> getBungeeSupportedVersions() throws Exception {
        return (List) ReflectionUtil.getStatic(Class.forName("net.md_5.bungee.protocol.ProtocolConstants"), "SUPPORTED_VERSION_IDS", List.class);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public JsonObject getDump() {
        JsonObject data = new JsonObject();
        JsonArray injectedChannelInitializers = new JsonArray();
        for (Channel channel : this.injectedChannels) {
            JsonObject channelInfo = new JsonObject();
            channelInfo.addProperty("channelClass", channel.getClass().getName());
            JsonArray pipeline = new JsonArray();
            for (String pipeName : channel.pipeline().names()) {
                JsonObject handlerInfo = new JsonObject();
                handlerInfo.addProperty("name", pipeName);
                ChannelHandler channelHandler = channel.pipeline().get(pipeName);
                if (channelHandler == null) {
                    handlerInfo.addProperty("status", "INVALID");
                } else {
                    handlerInfo.addProperty("class", channelHandler.getClass().getName());
                    try {
                        Object child = ReflectionUtil.get(channelHandler, "childHandler", ChannelInitializer.class);
                        handlerInfo.addProperty("childClass", child.getClass().getName());
                        if (child instanceof BungeeChannelInitializer) {
                            handlerInfo.addProperty("oldInit", ((BungeeChannelInitializer) child).getOriginal().getClass().getName());
                        }
                    } catch (ReflectiveOperationException e) {
                    }
                    pipeline.add(handlerInfo);
                }
            }
            channelInfo.add("pipeline", pipeline);
            injectedChannelInitializers.add(channelInfo);
        }
        data.add("injectedChannelInitializers", injectedChannelInitializers);
        try {
            Object list = LISTENERS_FIELD.get(ProxyServer.getInstance());
            data.addProperty("currentList", list.getClass().getName());
            if (list instanceof SetWrapper) {
                data.addProperty("wrappedList", ((SetWrapper) list).originalSet().getClass().getName());
            }
        } catch (ReflectiveOperationException e2) {
        }
        return data;
    }
}
