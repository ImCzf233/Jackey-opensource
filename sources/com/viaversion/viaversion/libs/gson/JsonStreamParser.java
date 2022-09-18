package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonStreamParser.class */
public final class JsonStreamParser implements Iterator<JsonElement> {
    private final JsonReader parser;
    private final Object lock;

    public JsonStreamParser(String json) {
        this(new StringReader(json));
    }

    public JsonStreamParser(Reader reader) {
        this.parser = new JsonReader(reader);
        this.parser.setLenient(true);
        this.lock = new Object();
    }

    @Override // java.util.Iterator
    public JsonElement next() throws JsonParseException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return Streams.parse(this.parser);
        } catch (JsonParseException e) {
            if (!(e.getCause() instanceof EOFException)) {
                throw e;
            }
            throw new NoSuchElementException();
        } catch (OutOfMemoryError e2) {
            throw new JsonParseException("Failed parsing JSON source to Json", e2);
        } catch (StackOverflowError e3) {
            throw new JsonParseException("Failed parsing JSON source to Json", e3);
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        boolean z;
        synchronized (this.lock) {
            try {
                try {
                    z = this.parser.peek() != JsonToken.END_DOCUMENT;
                } catch (IOException e) {
                    throw new JsonIOException(e);
                }
            } catch (MalformedJsonException e2) {
                throw new JsonSyntaxException(e2);
            }
        }
        return z;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
