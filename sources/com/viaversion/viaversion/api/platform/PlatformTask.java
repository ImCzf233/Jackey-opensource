package com.viaversion.viaversion.api.platform;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/platform/PlatformTask.class */
public interface PlatformTask<T> {
    T getObject();

    void cancel();
}
