package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import java.io.IOException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/JsonReaderInternalAccess.class */
public abstract class JsonReaderInternalAccess {
    public static JsonReaderInternalAccess INSTANCE;

    public abstract void promoteNameToValue(JsonReader jsonReader) throws IOException;
}
