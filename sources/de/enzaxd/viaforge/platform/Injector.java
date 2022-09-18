package de.enzaxd.viaforge.platform;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.gson.JsonObject;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/platform/Injector.class */
public class Injector implements ViaInjector {
    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void inject() {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public void uninject() {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public int getServerProtocolVersion() {
        return 47;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public String getEncoderName() {
        return "via-encoder";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public String getDecoderName() {
        return "via-decoder";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public JsonObject getDump() {
        JsonObject obj = new JsonObject();
        return obj;
    }
}
