package com.viaversion.viaversion.platform;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/platform/WrappedChannelInitializer.class */
public interface WrappedChannelInitializer {
    ChannelInitializer<Channel> original();
}
