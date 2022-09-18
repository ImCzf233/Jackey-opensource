package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/nbt/TagStringWriter.class */
final class TagStringWriter implements AutoCloseable {
    private final Appendable out;
    private int level;
    private boolean needsSeparator;

    public TagStringWriter(Appendable out) {
        this.out = out;
    }

    public TagStringWriter writeTag(Tag tag) throws IOException {
        if (tag instanceof CompoundTag) {
            return writeCompound((CompoundTag) tag);
        }
        if (tag instanceof ListTag) {
            return writeList((ListTag) tag);
        }
        if (tag instanceof ByteArrayTag) {
            return writeByteArray((ByteArrayTag) tag);
        }
        if (tag instanceof IntArrayTag) {
            return writeIntArray((IntArrayTag) tag);
        }
        if (tag instanceof LongArrayTag) {
            return writeLongArray((LongArrayTag) tag);
        }
        if (tag instanceof StringTag) {
            return value(((StringTag) tag).getValue(), (char) 0);
        }
        if (tag instanceof ByteTag) {
            return value(Byte.toString(((NumberTag) tag).asByte()), 'b');
        }
        if (tag instanceof ShortTag) {
            return value(Short.toString(((NumberTag) tag).asShort()), 's');
        }
        if (tag instanceof IntTag) {
            return value(Integer.toString(((NumberTag) tag).asInt()), 'i');
        }
        if (tag instanceof LongTag) {
            return value(Long.toString(((NumberTag) tag).asLong()), Character.toUpperCase('l'));
        }
        if (tag instanceof FloatTag) {
            return value(Float.toString(((NumberTag) tag).asFloat()), 'f');
        }
        if (tag instanceof DoubleTag) {
            return value(Double.toString(((NumberTag) tag).asDouble()), 'd');
        }
        throw new IOException("Unknown tag type: " + tag.getClass().getSimpleName());
    }

    private TagStringWriter writeCompound(CompoundTag tag) throws IOException {
        beginCompound();
        for (Map.Entry<String, Tag> entry : tag.entrySet()) {
            key(entry.getKey());
            writeTag(entry.getValue());
        }
        endCompound();
        return this;
    }

    private TagStringWriter writeList(ListTag tag) throws IOException {
        beginList();
        Iterator<Tag> it = tag.iterator();
        while (it.hasNext()) {
            Tag el = it.next();
            printAndResetSeparator();
            writeTag(el);
        }
        endList();
        return this;
    }

    private TagStringWriter writeByteArray(ByteArrayTag tag) throws IOException {
        beginArray('b');
        byte[] value = tag.getValue();
        for (byte b : value) {
            printAndResetSeparator();
            value(Byte.toString(b), 'b');
        }
        endArray();
        return this;
    }

    private TagStringWriter writeIntArray(IntArrayTag tag) throws IOException {
        beginArray('i');
        int[] value = tag.getValue();
        for (int i : value) {
            printAndResetSeparator();
            value(Integer.toString(i), 'i');
        }
        endArray();
        return this;
    }

    private TagStringWriter writeLongArray(LongArrayTag tag) throws IOException {
        beginArray('l');
        long[] value = tag.getValue();
        for (long j : value) {
            printAndResetSeparator();
            value(Long.toString(j), 'l');
        }
        endArray();
        return this;
    }

    public TagStringWriter beginCompound() throws IOException {
        printAndResetSeparator();
        this.level++;
        this.out.append('{');
        return this;
    }

    public TagStringWriter endCompound() throws IOException {
        this.out.append('}');
        this.level--;
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter key(String key) throws IOException {
        printAndResetSeparator();
        writeMaybeQuoted(key, false);
        this.out.append(':');
        return this;
    }

    public TagStringWriter value(String value, char valueType) throws IOException {
        if (valueType == 0) {
            writeMaybeQuoted(value, true);
        } else {
            this.out.append(value);
            if (valueType != 'i') {
                this.out.append(valueType);
            }
        }
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter beginList() throws IOException {
        printAndResetSeparator();
        this.level++;
        this.out.append('[');
        return this;
    }

    public TagStringWriter endList() throws IOException {
        this.out.append(']');
        this.level--;
        this.needsSeparator = true;
        return this;
    }

    private TagStringWriter beginArray(char type) throws IOException {
        beginList().out.append(type).append(';');
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return endList();
    }

    private void writeMaybeQuoted(String content, boolean requireQuotes) throws IOException {
        if (!requireQuotes) {
            int i = 0;
            while (true) {
                if (i >= content.length()) {
                    break;
                } else if (Tokens.m221id(content.charAt(i))) {
                    i++;
                } else {
                    requireQuotes = true;
                    break;
                }
            }
        }
        if (requireQuotes) {
            this.out.append('\"');
            this.out.append(escape(content, '\"'));
            this.out.append('\"');
            return;
        }
        this.out.append(content);
    }

    private static String escape(String content, char quoteChar) {
        StringBuilder output = new StringBuilder(content.length());
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == quoteChar || c == '\\') {
                output.append('\\');
            }
            output.append(c);
        }
        return output.toString();
    }

    private void printAndResetSeparator() throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            this.needsSeparator = false;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.level != 0) {
            throw new IllegalStateException("Document finished with unbalanced start and end objects");
        }
        if (this.out instanceof Writer) {
            ((Writer) this.out).flush();
        }
    }
}
