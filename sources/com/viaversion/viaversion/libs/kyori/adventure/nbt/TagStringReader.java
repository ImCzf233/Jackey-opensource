package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/TagStringReader.class */
public final class TagStringReader {
    private static final int MAX_DEPTH = 512;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private final CharBuffer buffer;
    private boolean acceptLegacy;
    private int depth;

    public TagStringReader(final CharBuffer buffer) {
        this.buffer = buffer;
    }

    public CompoundBinaryTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        if (this.buffer.takeIf('}')) {
            return CompoundBinaryTag.empty();
        }
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        while (this.buffer.hasMore()) {
            builder.put(key(), tag());
            if (separatorOrCompleteWith('}')) {
                return builder.build();
            }
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }

    public ListBinaryTag list() throws StringTagParseException {
        ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.builder();
        this.buffer.expect('[');
        boolean prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!prefixedIndex && this.buffer.takeIf(']')) {
            return ListBinaryTag.empty();
        }
        while (this.buffer.hasMore()) {
            if (prefixedIndex) {
                this.buffer.takeUntil(':');
            }
            BinaryTag next = tag();
            builder.add((ListBinaryTag.Builder<BinaryTag>) next);
            if (separatorOrCompleteWith(']')) {
                return builder.build();
            }
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public BinaryTag array(char elementType) throws StringTagParseException {
        this.buffer.expect('[').expect(elementType).expect(';');
        char elementType2 = Character.toLowerCase(elementType);
        if (elementType2 == 'b') {
            return ByteArrayBinaryTag.m137of(byteArray());
        }
        if (elementType2 == 'i') {
            return IntArrayBinaryTag.m133of(intArray());
        }
        if (elementType2 == 'l') {
            return LongArrayBinaryTag.m130of(longArray());
        }
        throw this.buffer.makeError("Type " + elementType2 + " is not a valid element type in an array!");
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_BYTE_ARRAY;
        }
        List<Byte> bytes = new ArrayList<>();
        while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
            try {
                bytes.add(Byte.valueOf(value.toString()));
                if (separatorOrCompleteWith(']')) {
                    byte[] result = new byte[bytes.size()];
                    for (int i = 0; i < bytes.size(); i++) {
                        result[i] = bytes.get(i).byteValue();
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
            BinaryTag value = tag();
            if (!(value instanceof IntBinaryTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((IntBinaryTag) value).intValue());
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
                if (!Tokens.m126id(peek)) {
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

    public BinaryTag tag() throws StringTagParseException {
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
                    return StringBinaryTag.m127of(unescape(this.buffer.takeUntil(startToken).toString()));
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0136 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag scalar() {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringReader.scalar():com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag");
    }

    private boolean separatorOrCompleteWith(final char endCharacter) throws StringTagParseException {
        if (this.buffer.takeIf(endCharacter)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(endCharacter);
    }

    private static String unescape(final String withEscapes) {
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

    public void legacy(final boolean acceptLegacy) {
        this.acceptLegacy = acceptLegacy;
    }
}
