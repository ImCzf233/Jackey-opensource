package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonParser.class */
public final class JsonParser {
    public static JsonElement parseString(String json) throws JsonSyntaxException {
        return parseReader(new StringReader(json));
    }

    public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = parseReader(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return element;
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        } catch (NumberFormatException e3) {
            throw new JsonSyntaxException(e3);
        }
    }

    public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
        boolean lenient = reader.isLenient();
        reader.setLenient(true);
        try {
            try {
                JsonElement parse = Streams.parse(reader);
                reader.setLenient(lenient);
                return parse;
            } catch (OutOfMemoryError e) {
                throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
            } catch (StackOverflowError e2) {
                throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e2);
            }
        } catch (Throwable th) {
            reader.setLenient(lenient);
            throw th;
        }
    }

    @Deprecated
    public JsonElement parse(String json) throws JsonSyntaxException {
        return parseString(json);
    }

    @Deprecated
    public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
        return parseReader(json);
    }

    @Deprecated
    public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
        return parseReader(json);
    }
}
