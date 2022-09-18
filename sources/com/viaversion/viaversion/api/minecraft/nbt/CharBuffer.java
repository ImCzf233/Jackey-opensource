package com.viaversion.viaversion.api.minecraft.nbt;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/nbt/CharBuffer.class */
final class CharBuffer {
    private final CharSequence sequence;
    private int index;

    public CharBuffer(CharSequence sequence) {
        this.sequence = sequence;
    }

    public char peek() {
        return this.sequence.charAt(this.index);
    }

    public char peek(int offset) {
        return this.sequence.charAt(this.index + offset);
    }

    public char take() {
        CharSequence charSequence = this.sequence;
        int i = this.index;
        this.index = i + 1;
        return charSequence.charAt(i);
    }

    public boolean advance() {
        this.index++;
        return hasMore();
    }

    public boolean hasMore() {
        return this.index < this.sequence.length();
    }

    public boolean hasMore(int offset) {
        return this.index + offset < this.sequence.length();
    }

    public CharSequence takeUntil(char until) throws StringTagParseException {
        char until2 = Character.toLowerCase(until);
        int endIdx = -1;
        int idx = this.index;
        while (true) {
            if (idx >= this.sequence.length()) {
                break;
            }
            if (this.sequence.charAt(idx) == '\\') {
                idx++;
            } else if (Character.toLowerCase(this.sequence.charAt(idx)) == until2) {
                endIdx = idx;
                break;
            }
            idx++;
        }
        if (endIdx == -1) {
            throw makeError("No occurrence of " + until2 + " was found");
        }
        CharSequence result = this.sequence.subSequence(this.index, endIdx);
        this.index = endIdx + 1;
        return result;
    }

    public CharBuffer expect(char expectedChar) throws StringTagParseException {
        skipWhitespace();
        if (!hasMore()) {
            throw makeError("Expected character '" + expectedChar + "' but got EOF");
        }
        if (peek() != expectedChar) {
            throw makeError("Expected character '" + expectedChar + "' but got '" + peek() + "'");
        }
        take();
        return this;
    }

    public boolean takeIf(char token) {
        skipWhitespace();
        if (hasMore() && peek() == token) {
            advance();
            return true;
        }
        return false;
    }

    public CharBuffer skipWhitespace() {
        while (hasMore() && Character.isWhitespace(peek())) {
            advance();
        }
        return this;
    }

    public StringTagParseException makeError(String message) {
        return new StringTagParseException(message, this.sequence, this.index);
    }
}
