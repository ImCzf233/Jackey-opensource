package org.apache.log4j.pattern;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/FormattingInfo.class */
public final class FormattingInfo {
    private static final char[] SPACES = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE);
    private final int minLength;
    private final int maxLength;
    private final boolean leftAlign;

    public FormattingInfo(boolean leftAlign, int minLength, int maxLength) {
        this.leftAlign = leftAlign;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public static FormattingInfo getDefault() {
        return DEFAULT;
    }

    public boolean isLeftAligned() {
        return this.leftAlign;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void format(int fieldStart, StringBuffer buffer) {
        int rawLength = buffer.length() - fieldStart;
        if (rawLength > this.maxLength) {
            buffer.delete(fieldStart, buffer.length() - this.maxLength);
        } else if (rawLength < this.minLength) {
            if (this.leftAlign) {
                int fieldEnd = buffer.length();
                buffer.setLength(fieldStart + this.minLength);
                for (int i = fieldEnd; i < buffer.length(); i++) {
                    buffer.setCharAt(i, ' ');
                }
                return;
            }
            int padLength = this.minLength - rawLength;
            while (padLength > 8) {
                buffer.insert(fieldStart, SPACES);
                padLength -= 8;
            }
            buffer.insert(fieldStart, SPACES, 0, padLength);
        }
    }
}
