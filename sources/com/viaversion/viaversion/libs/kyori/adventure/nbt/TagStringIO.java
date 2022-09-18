package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/TagStringIO.class */
public final class TagStringIO {
    private static final TagStringIO INSTANCE = new TagStringIO(new Builder());
    private final boolean acceptLegacy;
    private final boolean emitLegacy;
    private final String indent;

    @NotNull
    public static TagStringIO get() {
        return INSTANCE;
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    private TagStringIO(@NotNull final Builder builder) {
        this.acceptLegacy = builder.acceptLegacy;
        this.emitLegacy = builder.emitLegacy;
        this.indent = builder.indent;
    }

    public CompoundBinaryTag asCompound(final String input) throws IOException {
        try {
            CharBuffer buffer = new CharBuffer(input);
            TagStringReader parser = new TagStringReader(buffer);
            parser.legacy(this.acceptLegacy);
            CompoundBinaryTag tag = parser.compound();
            if (buffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            }
            return tag;
        } catch (StringTagParseException ex) {
            throw new IOException(ex);
        }
    }

    public String asString(final CompoundBinaryTag input) throws IOException {
        StringBuilder sb = new StringBuilder();
        TagStringWriter emit = new TagStringWriter(sb, this.indent);
        try {
            emit.legacy(this.emitLegacy);
            emit.writeTag(input);
            emit.close();
            return sb.toString();
        } catch (Throwable th) {
            try {
                emit.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void toWriter(final CompoundBinaryTag input, final Writer dest) throws IOException {
        TagStringWriter emit = new TagStringWriter(dest, this.indent);
        try {
            emit.legacy(this.emitLegacy);
            emit.writeTag(input);
            emit.close();
        } catch (Throwable th) {
            try {
                emit.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/TagStringIO$Builder.class */
    public static class Builder {
        private boolean acceptLegacy = true;
        private boolean emitLegacy = false;
        private String indent = "";

        Builder() {
        }

        @NotNull
        public Builder indent(final int spaces) {
            if (spaces == 0) {
                this.indent = "";
            } else if ((this.indent.length() > 0 && this.indent.charAt(0) != ' ') || spaces != this.indent.length()) {
                char[] indent = new char[spaces];
                Arrays.fill(indent, ' ');
                this.indent = String.copyValueOf(indent);
            }
            return this;
        }

        @NotNull
        public Builder indentTab(final int tabs) {
            if (tabs == 0) {
                this.indent = "";
            } else if ((this.indent.length() > 0 && this.indent.charAt(0) != '\t') || tabs != this.indent.length()) {
                char[] indent = new char[tabs];
                Arrays.fill(indent, '\t');
                this.indent = String.copyValueOf(indent);
            }
            return this;
        }

        @NotNull
        public Builder acceptLegacy(final boolean legacy) {
            this.acceptLegacy = legacy;
            return this;
        }

        @NotNull
        public Builder emitLegacy(final boolean legacy) {
            this.emitLegacy = legacy;
            return this;
        }

        @NotNull
        public TagStringIO build() {
            return new TagStringIO(this);
        }
    }
}
