package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
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
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/nbt/TagStringReader.class */
public final class TagStringReader {
    private static final int MAX_DEPTH = 512;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private final CharBuffer buffer;
    private boolean acceptLegacy = true;
    private int depth;

    public TagStringReader(CharBuffer buffer) {
        this.buffer = buffer;
    }

    public CompoundTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        CompoundTag compoundTag = new CompoundTag();
        if (this.buffer.takeIf('}')) {
            return compoundTag;
        }
        while (this.buffer.hasMore()) {
            compoundTag.put(key(), tag());
            if (separatorOrCompleteWith('}')) {
                return compoundTag;
            }
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }

    public ListTag list() throws StringTagParseException {
        ListTag listTag = new ListTag();
        this.buffer.expect('[');
        boolean prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!prefixedIndex && this.buffer.takeIf(']')) {
            return listTag;
        }
        while (this.buffer.hasMore()) {
            if (prefixedIndex) {
                this.buffer.takeUntil(':');
            }
            Tag next = tag();
            listTag.add(next);
            if (separatorOrCompleteWith(']')) {
                return listTag;
            }
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public Tag array(char elementType) throws StringTagParseException {
        this.buffer.expect('[').expect(elementType).expect(';');
        char elementType2 = Character.toLowerCase(elementType);
        if (elementType2 == 'b') {
            return new ByteArrayTag(byteArray());
        }
        if (elementType2 == 'i') {
            return new IntArrayTag(intArray());
        }
        if (elementType2 == 'l') {
            return new LongArrayTag(longArray());
        }
        throw this.buffer.makeError("Type " + elementType2 + " is not a valid element type in an array!");
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_BYTE_ARRAY;
        }
        IntList bytes = new IntArrayList();
        while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
            try {
                bytes.add(Byte.parseByte(value.toString()));
                if (separatorOrCompleteWith(']')) {
                    byte[] result = new byte[bytes.size()];
                    for (int i = 0; i < bytes.size(); i++) {
                        result[i] = (byte) bytes.getInt(i);
                    }
                    return result;
                }
            } catch (NumberFormatException e) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_INT_ARRAY;
        }
        IntStream.Builder builder = IntStream.builder();
        while (this.buffer.hasMore()) {
            Tag value = tag();
            if (!(value instanceof IntTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((NumberTag) value).asInt());
            if (separatorOrCompleteWith(']')) {
                return builder.build().toArray();
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_LONG_ARRAY;
        }
        LongStream.Builder longs = LongStream.builder();
        while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('l');
            try {
                longs.add(Long.parseLong(value.toString()));
                if (separatorOrCompleteWith(']')) {
                    return longs.build().toArray();
                }
            } catch (NumberFormatException e) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    public String key() throws StringTagParseException {
        this.buffer.skipWhitespace();
        char starChar = this.buffer.peek();
        try {
            if (starChar == '\'' || starChar == '\"') {
                String unescape = unescape(this.buffer.takeUntil(this.buffer.take()).toString());
                this.buffer.expect(':');
                return unescape;
            }
            StringBuilder builder = new StringBuilder();
            while (this.buffer.hasMore()) {
                char peek = this.buffer.peek();
                if (!Tokens.m221id(peek)) {
                    if (this.acceptLegacy) {
                        if (peek == '\\') {
                            this.buffer.take();
                        } else if (peek == ':') {
                            break;
                        } else {
                            builder.append(this.buffer.take());
                        }
                    } else {
                        break;
                    }
                } else {
                    builder.append(this.buffer.take());
                }
            }
            String sb = builder.toString();
            this.buffer.expect(':');
            return sb;
        } catch (Throwable th) {
            this.buffer.expect(':');
            throw th;
        }
    }

    public Tag tag() throws StringTagParseException {
        int i = this.depth;
        this.depth = i + 1;
        if (i > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char startToken = this.buffer.skipWhitespace().peek();
            switch (startToken) {
                case '\"':
                case '\'':
                    this.buffer.advance();
                    return new StringTag(unescape(this.buffer.takeUntil(startToken).toString()));
                case '[':
                    return (!this.buffer.hasMore(2) || this.buffer.peek(2) != ';') ? list() : array(this.buffer.peek(1));
                case '{':
                    return compound();
                default:
                    return scalar();
            }
        } finally {
            this.depth--;
        }
    }

    private Tag scalar() {
        StringBuilder builder = new StringBuilder();
        int noLongerNumericAt = -1;
        while (this.buffer.hasMore()) {
            char current = this.buffer.peek();
            if (current == '\\') {
                this.buffer.advance();
                current = this.buffer.take();
            } else if (!Tokens.m221id(current)) {
                break;
            } else {
                this.buffer.advance();
            }
            builder.append(current);
            if (noLongerNumericAt == -1 && !Tokens.numeric(current)) {
                noLongerNumericAt = builder.length();
            }
        }
        int length = builder.length();
        String built = builder.toString();
        if (noLongerNumericAt == length) {
            char last = built.charAt(length - 1);
            try {
                switch (Character.toLowerCase(last)) {
                    case 'b':
                        return new ByteTag(Byte.parseByte(built.substring(0, length - 1)));
                    case 'd':
                        double doubleValue = Double.parseDouble(built.substring(0, length - 1));
                        if (Double.isFinite(doubleValue)) {
                            return new DoubleTag(doubleValue);
                        }
                        break;
                    case 'f':
                        float floatValue = Float.parseFloat(built.substring(0, length - 1));
                        if (Float.isFinite(floatValue)) {
                            return new FloatTag(floatValue);
                        }
                        break;
                    case 'i':
                        return new IntTag(Integer.parseInt(built.substring(0, length - 1)));
                    case 'l':
                        return new LongTag(Long.parseLong(built.substring(0, length - 1)));
                    case 's':
                        return new ShortTag(Short.parseShort(built.substring(0, length - 1)));
                }
            } catch (NumberFormatException e) {
            }
        } else if (noLongerNumericAt == -1) {
            try {
                return new IntTag(Integer.parseInt(built));
            } catch (NumberFormatException e2) {
                if (built.indexOf(46) != -1) {
                    try {
                        return new DoubleTag(Double.parseDouble(built));
                    } catch (NumberFormatException e3) {
                    }
                }
            }
        }
        if (built.equalsIgnoreCase("true")) {
            return new ByteTag((byte) 1);
        }
        if (built.equalsIgnoreCase("false")) {
            return new ByteTag((byte) 0);
        }
        return new StringTag(built);
    }

    private boolean separatorOrCompleteWith(char endCharacter) throws StringTagParseException {
        if (this.buffer.takeIf(endCharacter)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(endCharacter);
    }

    private static String unescape(String withEscapes) {
        int indexOf;
        int escapeIdx = withEscapes.indexOf(92);
        if (escapeIdx == -1) {
            return withEscapes;
        }
        int lastEscape = 0;
        StringBuilder output = new StringBuilder(withEscapes.length());
        do {
            output.append((CharSequence) withEscapes, lastEscape, escapeIdx);
            lastEscape = escapeIdx + 1;
            indexOf = withEscapes.indexOf(92, lastEscape + 1);
            escapeIdx = indexOf;
        } while (indexOf != -1);
        output.append(withEscapes.substring(lastEscape));
        return output.toString();
    }

    public void legacy(boolean acceptLegacy) {
        this.acceptLegacy = acceptLegacy;
    }
}
