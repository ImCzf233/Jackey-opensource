package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets;
import com.viaversion.viaversion.libs.gson.JsonObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/platform/ViaInjector.class */
public interface ViaInjector {
    void inject() throws Exception;

    void uninject() throws Exception;

    int getServerProtocolVersion() throws Exception;

    JsonObject getDump();

    default boolean lateProtocolVersionSetting() {
        return false;
    }

    default IntSortedSet getServerProtocolVersions() throws Exception {
        return IntSortedSets.singleton(getServerProtocolVersion());
    }

    default String getEncoderName() {
        return "via-encoder";
    }

    default String getDecoderName() {
        return "via-decoder";
    }
}
