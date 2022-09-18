package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/chat/TagSerializer.class */
public class TagSerializer {
    private static final Pattern PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");

    public static String toString(JsonObject object) {
        StringBuilder builder = new StringBuilder("{");
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Preconditions.checkArgument(entry.getValue().isJsonPrimitive());
            if (builder.length() != 1) {
                builder.append(',');
            }
            String escapedText = escape(entry.getValue().getAsString());
            builder.append(entry.getKey()).append(':').append(escapedText);
        }
        return builder.append('}').toString();
    }

    public static JsonObject toJson(CompoundTag tag) {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, Tag> entry : tag.entrySet()) {
            object.add(entry.getKey(), toJson(entry.getValue()));
        }
        return object;
    }

    private static JsonElement toJson(Tag tag) {
        if (tag instanceof CompoundTag) {
            return toJson((CompoundTag) tag);
        }
        if (tag instanceof ListTag) {
            ListTag list = (ListTag) tag;
            JsonArray array = new JsonArray();
            Iterator<Tag> it = list.iterator();
            while (it.hasNext()) {
                Tag listEntry = it.next();
                array.add(toJson(listEntry));
            }
            return array;
        }
        return new JsonPrimitive(tag.getValue().toString());
    }

    public static String escape(String s) {
        if (PLAIN_TEXT.matcher(s).matches()) {
            return s;
        }
        StringBuilder builder = new StringBuilder(" ");
        char currentQuote = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                builder.append('\\');
            } else if (c == '\"' || c == '\'') {
                if (currentQuote == 0) {
                    currentQuote = c == '\"' ? '\'' : '\"';
                }
                if (currentQuote == c) {
                    builder.append('\\');
                }
            }
            builder.append(c);
        }
        if (currentQuote == 0) {
            currentQuote = '\"';
        }
        builder.setCharAt(0, currentQuote);
        builder.append(currentQuote);
        return builder.toString();
    }
}
