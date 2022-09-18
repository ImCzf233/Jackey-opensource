package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/internal/bind/JsonTreeReader.class */
public final class JsonTreeReader extends JsonReader {
    private static final Reader UNREADABLE_READER = new Reader() { // from class: com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader.1
        @Override // java.io.Reader
        public int read(char[] buffer, int offset, int count) throws IOException {
            throw new AssertionError();
        }

        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final Object SENTINEL_CLOSED = new Object();
    private Object[] stack = new Object[32];
    private int stackSize = 0;
    private String[] pathNames = new String[32];
    private int[] pathIndices = new int[32];

    public JsonTreeReader(JsonElement element) {
        super(UNREADABLE_READER);
        push(element);
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void beginArray() throws IOException {
        expect(JsonToken.BEGIN_ARRAY);
        JsonArray array = (JsonArray) peekStack();
        push(array.iterator());
        this.pathIndices[this.stackSize - 1] = 0;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void endArray() throws IOException {
        expect(JsonToken.END_ARRAY);
        popStack();
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void beginObject() throws IOException {
        expect(JsonToken.BEGIN_OBJECT);
        JsonObject object = (JsonObject) peekStack();
        push(object.entrySet().iterator());
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void endObject() throws IOException {
        expect(JsonToken.END_OBJECT);
        popStack();
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public boolean hasNext() throws IOException {
        JsonToken token = peek();
        return (token == JsonToken.END_OBJECT || token == JsonToken.END_ARRAY) ? false : true;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public JsonToken peek() throws IOException {
        if (this.stackSize == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object o = peekStack();
        if (o instanceof Iterator) {
            boolean isObject = this.stack[this.stackSize - 2] instanceof JsonObject;
            Iterator<?> iterator = (Iterator) o;
            if (!iterator.hasNext()) {
                return isObject ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            } else if (isObject) {
                return JsonToken.NAME;
            } else {
                push(iterator.next());
                return peek();
            }
        } else if (o instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        } else {
            if (o instanceof JsonArray) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (o instanceof JsonPrimitive) {
                JsonPrimitive primitive = (JsonPrimitive) o;
                if (primitive.isString()) {
                    return JsonToken.STRING;
                }
                if (primitive.isBoolean()) {
                    return JsonToken.BOOLEAN;
                }
                if (primitive.isNumber()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            } else if (o instanceof JsonNull) {
                return JsonToken.NULL;
            } else {
                if (o == SENTINEL_CLOSED) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    private Object peekStack() {
        return this.stack[this.stackSize - 1];
    }

    private Object popStack() {
        Object[] objArr = this.stack;
        int i = this.stackSize - 1;
        this.stackSize = i;
        Object result = objArr[i];
        this.stack[this.stackSize] = null;
        return result;
    }

    private void expect(JsonToken expected) throws IOException {
        if (peek() != expected) {
            throw new IllegalStateException("Expected " + expected + " but was " + peek() + locationString());
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public String nextName() throws IOException {
        expect(JsonToken.NAME);
        Iterator<?> i = (Iterator) peekStack();
        Map.Entry<?, ?> entry = (Map.Entry) i.next();
        String result = (String) entry.getKey();
        this.pathNames[this.stackSize - 1] = result;
        push(entry.getValue());
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public String nextString() throws IOException {
        JsonToken token = peek();
        if (token != JsonToken.STRING && token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + token + locationString());
        }
        String result = ((JsonPrimitive) popStack()).getAsString();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public boolean nextBoolean() throws IOException {
        expect(JsonToken.BOOLEAN);
        boolean result = ((JsonPrimitive) popStack()).getAsBoolean();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void nextNull() throws IOException {
        expect(JsonToken.NULL);
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public double nextDouble() throws IOException {
        JsonToken token = peek();
        if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + locationString());
        }
        double result = ((JsonPrimitive) peekStack()).getAsDouble();
        if (!isLenient() && (Double.isNaN(result) || Double.isInfinite(result))) {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + result);
        }
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public long nextLong() throws IOException {
        JsonToken token = peek();
        if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + locationString());
        }
        long result = ((JsonPrimitive) peekStack()).getAsLong();
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public int nextInt() throws IOException {
        JsonToken token = peek();
        if (token != JsonToken.NUMBER && token != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + token + locationString());
        }
        int result = ((JsonPrimitive) peekStack()).getAsInt();
        popStack();
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
        return result;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.stack = new Object[]{SENTINEL_CLOSED};
        this.stackSize = 1;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public void skipValue() throws IOException {
        if (peek() == JsonToken.NAME) {
            nextName();
            this.pathNames[this.stackSize - 2] = Configurator.NULL;
        } else {
            popStack();
            if (this.stackSize > 0) {
                this.pathNames[this.stackSize - 1] = Configurator.NULL;
            }
        }
        if (this.stackSize > 0) {
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public String toString() {
        return getClass().getSimpleName();
    }

    public void promoteNameToValue() throws IOException {
        expect(JsonToken.NAME);
        Iterator<?> i = (Iterator) peekStack();
        Map.Entry<?, ?> entry = (Map.Entry) i.next();
        push(entry.getValue());
        push(new JsonPrimitive((String) entry.getKey()));
    }

    private void push(Object newTop) {
        if (this.stackSize == this.stack.length) {
            int newLength = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, newLength);
            this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, newLength);
        }
        Object[] objArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        objArr[i] = newTop;
    }

    @Override // com.viaversion.viaversion.libs.gson.stream.JsonReader
    public String getPath() {
        StringBuilder result = new StringBuilder().append('$');
        int i = 0;
        while (i < this.stackSize) {
            if (this.stack[i] instanceof JsonArray) {
                i++;
                if (this.stack[i] instanceof Iterator) {
                    result.append('[').append(this.pathIndices[i]).append(']');
                }
            } else if (this.stack[i] instanceof JsonObject) {
                i++;
                if (this.stack[i] instanceof Iterator) {
                    result.append('.');
                    if (this.pathNames[i] != null) {
                        result.append(this.pathNames[i]);
                    }
                }
            }
            i++;
        }
        return result.toString();
    }

    private String locationString() {
        return " at path " + getPath();
    }
}
