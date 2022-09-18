package me.liuli.elixir.utils;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��F\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0010\u0004\n\u0002\b\u0004\u001a\u0014\u0010��\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010��\u001a\u0004\u0018\u00010\u0001*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0019\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\t\u001a\u0019\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\n\u001a\u0019\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\r\u001a\u0019\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u000e\u001a\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u0003*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010\u001a\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0011\u001a\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0014\u001a\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0015\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0004*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001d\u0010\u0017\u001a\u00020\u0018*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001aH\u0086\u0002\u001a\u001d\u0010\u0017\u001a\u00020\u0018*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0086\u0002\u001a\u001d\u0010\u0017\u001a\u00020\u0018*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001bH\u0086\u0002\u001a\u001d\u0010\u0017\u001a\u00020\u0018*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001cH\u0086\u0002\u001a\u001d\u0010\u0017\u001a\u00020\u0018*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006H\u0086\u0002\u001a\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u0006*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u0006*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0014\u0010\u001e\u001a\u00020\u0006*\u00020\u001a2\b\b\u0002\u0010\u001f\u001a\u00020\b¨\u0006 "}, m53d2 = {"array", "Lcom/google/gson/JsonArray;", "index", "", "Lcom/google/gson/JsonObject;", LocalCacheFactory.KEY, "", "boolean", "", "(Lcom/google/gson/JsonArray;I)Ljava/lang/Boolean;", "(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/Boolean;", "double", "", "(Lcom/google/gson/JsonArray;I)Ljava/lang/Double;", "(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/Double;", "int", "(Lcom/google/gson/JsonArray;I)Ljava/lang/Integer;", "(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/Integer;", "long", "", "(Lcom/google/gson/JsonArray;I)Ljava/lang/Long;", "(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/Long;", "obj", PropertyDescriptor.SET, "", "value", "Lcom/google/gson/JsonElement;", "", "", "string", "toJsonString", "prettyPrint", "Elixir"})
/* renamed from: me.liuli.elixir.utils.GsonExtensionKt */
/* loaded from: Jackey Client b2.jar:me/liuli/elixir/utils/GsonExtensionKt.class */
public final class GsonExtension {
    public static final void set(@NotNull JsonObject $this$set, @NotNull String key, @NotNull JsonElement value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.add(key, value);
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String key, char value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        $this$set.addProperty(key, Character.valueOf(value));
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String key, @NotNull Number value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.addProperty(key, value);
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String key, @NotNull String value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.addProperty(key, value);
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String key, boolean value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        $this$set.addProperty(key, Boolean.valueOf(value));
    }

    public static /* synthetic */ String toJsonString$default(JsonElement jsonElement, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toJsonString(jsonElement, z);
    }

    @NotNull
    public static final String toJsonString(@NotNull JsonElement $this$toJsonString, boolean prettyPrint) {
        Gson gson;
        Intrinsics.checkNotNullParameter($this$toJsonString, "<this>");
        if (prettyPrint) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        } else {
            gson = new GsonBuilder().create();
        }
        Gson gson2 = gson;
        String json = gson2.toJson($this$toJsonString);
        Intrinsics.checkNotNullExpressionValue(json, "gson.toJson(this)");
        return json;
    }

    @Nullable
    public static final String string(@NotNull JsonObject $this$string, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$string, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$string.has(key)) {
            return $this$string.get(key).getAsString();
        }
        return null;
    }

    @Nullable
    /* renamed from: int */
    public static final Integer m2748int(@NotNull JsonObject $this$int, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$int, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$int.has(key)) {
            return Integer.valueOf($this$int.get(key).getAsInt());
        }
        return null;
    }

    @Nullable
    /* renamed from: long */
    public static final Long m2749long(@NotNull JsonObject $this$long, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$long, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$long.has(key)) {
            return Long.valueOf($this$long.get(key).getAsLong());
        }
        return null;
    }

    @Nullable
    /* renamed from: double */
    public static final Double m2750double(@NotNull JsonObject $this$double, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$double, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$double.has(key)) {
            return Double.valueOf($this$double.get(key).getAsDouble());
        }
        return null;
    }

    @Nullable
    /* renamed from: boolean */
    public static final Boolean m2751boolean(@NotNull JsonObject $this$boolean, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$boolean, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$boolean.has(key)) {
            return Boolean.valueOf($this$boolean.get(key).getAsBoolean());
        }
        return null;
    }

    @Nullable
    public static final JsonObject obj(@NotNull JsonObject $this$obj, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$obj, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$obj.has(key)) {
            return $this$obj.get(key).getAsJsonObject();
        }
        return null;
    }

    @Nullable
    public static final JsonArray array(@NotNull JsonObject $this$array, @NotNull String key) {
        Intrinsics.checkNotNullParameter($this$array, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if ($this$array.has(key)) {
            return $this$array.get(key).getAsJsonArray();
        }
        return null;
    }

    @Nullable
    public static final String string(@NotNull JsonArray $this$string, int index) {
        Intrinsics.checkNotNullParameter($this$string, "<this>");
        if ($this$string.size() > index) {
            return $this$string.get(index).getAsString();
        }
        return null;
    }

    @Nullable
    /* renamed from: int */
    public static final Integer m2752int(@NotNull JsonArray $this$int, int index) {
        Intrinsics.checkNotNullParameter($this$int, "<this>");
        if ($this$int.size() > index) {
            return Integer.valueOf($this$int.get(index).getAsInt());
        }
        return null;
    }

    @Nullable
    /* renamed from: long */
    public static final Long m2753long(@NotNull JsonArray $this$long, int index) {
        Intrinsics.checkNotNullParameter($this$long, "<this>");
        if ($this$long.size() > index) {
            return Long.valueOf($this$long.get(index).getAsLong());
        }
        return null;
    }

    @Nullable
    /* renamed from: double */
    public static final Double m2754double(@NotNull JsonArray $this$double, int index) {
        Intrinsics.checkNotNullParameter($this$double, "<this>");
        if ($this$double.size() > index) {
            return Double.valueOf($this$double.get(index).getAsDouble());
        }
        return null;
    }

    @Nullable
    /* renamed from: boolean */
    public static final Boolean m2755boolean(@NotNull JsonArray $this$boolean, int index) {
        Intrinsics.checkNotNullParameter($this$boolean, "<this>");
        if ($this$boolean.size() > index) {
            return Boolean.valueOf($this$boolean.get(index).getAsBoolean());
        }
        return null;
    }

    @Nullable
    public static final JsonObject obj(@NotNull JsonArray $this$obj, int index) {
        Intrinsics.checkNotNullParameter($this$obj, "<this>");
        if ($this$obj.size() > index) {
            return $this$obj.get(index).getAsJsonObject();
        }
        return null;
    }

    @Nullable
    public static final JsonArray array(@NotNull JsonArray $this$array, int index) {
        Intrinsics.checkNotNullParameter($this$array, "<this>");
        if ($this$array.size() > index) {
            return $this$array.get(index).getAsJsonArray();
        }
        return null;
    }
}
