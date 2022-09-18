package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/TagStringWriter.class */
public final class TagStringWriter implements AutoCloseable {
    private final Appendable out;
    private final String indent;
    private int level;
    private boolean needsSeparator;
    private boolean legacy;

    public TagStringWriter(final Appendable out, final String indent) {
        this.out = out;
        this.indent = indent;
    }

    public TagStringWriter legacy(final boolean legacy) {
        this.legacy = legacy;
        return this;
    }

    public TagStringWriter writeTag(final BinaryTag tag) throws IOException {
        BinaryTagType<?> type = tag.type();
        if (type == BinaryTagTypes.COMPOUND) {
            return writeCompound((CompoundBinaryTag) tag);
        }
        if (type == BinaryTagTypes.LIST) {
            return writeList((ListBinaryTag) tag);
        }
        if (type == BinaryTagTypes.BYTE_ARRAY) {
            return writeByteArray((ByteArrayBinaryTag) tag);
        }
        if (type == BinaryTagTypes.INT_ARRAY) {
            return writeIntArray((IntArrayBinaryTag) tag);
        }
        if (type == BinaryTagTypes.LONG_ARRAY) {
            return writeLongArray((LongArrayBinaryTag) tag);
        }
        if (type == BinaryTagTypes.STRING) {
            return value(((StringBinaryTag) tag).value(), (char) 0);
        }
        if (type == BinaryTagTypes.BYTE) {
            return value(Byte.toString(((ByteBinaryTag) tag).value()), 'b');
        }
        if (type == BinaryTagTypes.SHORT) {
            return value(Short.toString(((ShortBinaryTag) tag).value()), 's');
        }
        if (type == BinaryTagTypes.INT) {
            return value(Integer.toString(((IntBinaryTag) tag).value()), 'i');
        }
        if (type == BinaryTagTypes.LONG) {
            return value(Long.toString(((LongBinaryTag) tag).value()), Character.toUpperCase('l'));
        }
        if (type == BinaryTagTypes.FLOAT) {
            return value(Float.toString(((FloatBinaryTag) tag).value()), 'f');
        }
        if (type == BinaryTagTypes.DOUBLE) {
            return value(Double.toString(((DoubleBinaryTag) tag).value()), 'd');
        }
        throw new IOException("Unknown tag type: " + type);
    }

    private TagStringWriter writeCompound(final CompoundBinaryTag tag) throws IOException {
        beginCompound();
        for (Map.Entry<String, ? extends BinaryTag> entry : tag) {
            key(entry.getKey());
            writeTag(entry.getValue());
        }
        endCompound();
        return this;
    }

    private TagStringWriter writeList(final ListBinaryTag tag) throws IOException {
        beginList();
        int idx = 0;
        boolean lineBreaks = prettyPrinting() && breakListElement(tag.elementType());
        for (BinaryTag el : tag) {
            printAndResetSeparator(!lineBreaks);
            if (lineBreaks) {
                newlineIndent();
            }
            if (this.legacy) {
                int i = idx;
                idx++;
                this.out.append(String.valueOf(i));
                appendSeparator(':');
            }
            writeTag(el);
        }
        endList(lineBreaks);
        return this;
    }

    private TagStringWriter writeByteArray(final ByteArrayBinaryTag tag) throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        beginArray('b');
        char byteArrayType = Character.toUpperCase('b');
        byte[] value = ByteArrayBinaryTagImpl.value(tag);
        for (byte b : value) {
            printAndResetSeparator(true);
            value(Byte.toString(b), byteArrayType);
        }
        endArray();
        return this;
    }

    private TagStringWriter writeIntArray(final IntArrayBinaryTag tag) throws IOException {
        if (this.legacy) {
            beginList();
        } else {
            beginArray('i');
        }
        int[] value = IntArrayBinaryTagImpl.value(tag);
        for (int i : value) {
            printAndResetSeparator(true);
            value(Integer.toString(i), 'i');
        }
        endArray();
        return this;
    }

    private TagStringWriter writeLongArray(final LongArrayBinaryTag tag) throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        beginArray('l');
        long[] value = LongArrayBinaryTagImpl.value(tag);
        for (long j : value) {
            printAndResetSeparator(true);
            value(Long.toString(j), 'l');
        }
        endArray();
        return this;
    }

    public TagStringWriter beginCompound() throws IOException {
        printAndResetSeparator(false);
        this.level++;
        this.out.append('{');
        return this;
    }

    public TagStringWriter endCompound() throws IOException {
        this.level--;
        newlineIndent();
        this.out.append('}');
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter key(final String key) throws IOException {
        printAndResetSeparator(false);
        newlineIndent();
        writeMaybeQuoted(key, false);
        appendSeparator(':');
        return this;
    }

    public TagStringWriter value(final String value, final char valueType) throws IOException {
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
        printAndResetSeparator(false);
        this.level++;
        this.out.append('[');
        return this;
    }

    public TagStringWriter endList(final boolean lineBreak) throws IOException {
        this.level--;
        if (lineBreak) {
            newlineIndent();
        }
        this.out.append(']');
        this.needsSeparator = true;
        return this;
    }

    private TagStringWriter beginArray(final char type) throws IOException {
        beginList().out.append(Character.toUpperCase(type)).append(';');
        if (prettyPrinting()) {
            this.out.append(' ');
        }
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return endList(false);
    }

    private void writeMaybeQuoted(final String content, boolean requireQuotes) throws IOException {
        if (!requireQuotes) {
            int i = 0;
            while (true) {
                if (i >= content.length()) {
                    break;
                } else if (Tokens.m126id(content.charAt(i))) {
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

    private static String escape(final String content, final char quoteChar) {
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

    private void printAndResetSeparator(final boolean pad) throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            if (pad && prettyPrinting()) {
                this.out.append(' ');
            }
            this.needsSeparator = false;
        }
    }

    private boolean breakListElement(final BinaryTagType<?> type) {
        return type == BinaryTagTypes.COMPOUND || type == BinaryTagTypes.LIST || type == BinaryTagTypes.BYTE_ARRAY || type == BinaryTagTypes.INT_ARRAY || type == BinaryTagTypes.LONG_ARRAY;
    }

    private boolean prettyPrinting() {
        return this.indent.length() > 0;
    }

    private void newlineIndent() throws IOException {
        if (prettyPrinting()) {
            this.out.append(Tokens.NEWLINE);
            for (int i = 0; i < this.level; i++) {
                this.out.append(this.indent);
            }
        }
    }

    private Appendable appendSeparator(final char separatorChar) throws IOException {
        this.out.append(separatorChar);
        if (prettyPrinting()) {
            this.out.append(' ');
        }
        return this.out;
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
