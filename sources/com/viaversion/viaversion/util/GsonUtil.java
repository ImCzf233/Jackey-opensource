package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/GsonUtil.class */
public final class GsonUtil {
    private static final Gson GSON = new GsonBuilder().create();

    public static Gson getGson() {
        return GSON;
    }
}
