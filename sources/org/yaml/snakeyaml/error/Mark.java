package org.yaml.snakeyaml.error;

import java.io.Serializable;
import org.yaml.snakeyaml.scanner.Constant;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/error/Mark.class */
public final class Mark implements Serializable {
    private String name;
    private int index;
    private int line;
    private int column;
    private int[] buffer;
    private int pointer;

    private static int[] toCodePoints(char[] str) {
        int[] codePoints = new int[Character.codePointCount(str, 0, str.length)];
        int i = 0;
        int c = 0;
        while (i < str.length) {
            int cp = Character.codePointAt(str, i);
            codePoints[c] = cp;
            i += Character.charCount(cp);
            c++;
        }
        return codePoints;
    }

    public Mark(String name, int index, int line, int column, char[] str, int pointer) {
        this(name, index, line, column, toCodePoints(str), pointer);
    }

    @Deprecated
    public Mark(String name, int index, int line, int column, String buffer, int pointer) {
        this(name, index, line, column, buffer.toCharArray(), pointer);
    }

    public Mark(String name, int index, int line, int column, int[] buffer, int pointer) {
        this.name = name;
        this.index = index;
        this.line = line;
        this.column = column;
        this.buffer = buffer;
        this.pointer = pointer;
    }

    private boolean isLineBreak(int c) {
        return Constant.NULL_OR_LINEBR.has(c);
    }

    public String get_snippet(int indent, int max_length) {
        float half = (max_length / 2.0f) - 1.0f;
        int start = this.pointer;
        String head = "";
        while (true) {
            if (start <= 0 || isLineBreak(this.buffer[start - 1])) {
                break;
            }
            start--;
            if (this.pointer - start > half) {
                head = " ... ";
                start += 5;
                break;
            }
        }
        String tail = "";
        int end = this.pointer;
        while (true) {
            if (end >= this.buffer.length || isLineBreak(this.buffer[end])) {
                break;
            }
            end++;
            if (end - this.pointer > half) {
                tail = " ... ";
                end -= 5;
                break;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            result.append(" ");
        }
        result.append(head);
        for (int i2 = start; i2 < end; i2++) {
            result.appendCodePoint(this.buffer[i2]);
        }
        result.append(tail);
        result.append("\n");
        for (int i3 = 0; i3 < ((indent + this.pointer) - start) + head.length(); i3++) {
            result.append(" ");
        }
        result.append("^");
        return result.toString();
    }

    public String get_snippet() {
        return get_snippet(4, 75);
    }

    public String toString() {
        String snippet = get_snippet();
        return " in " + this.name + ", line " + (this.line + 1) + ", column " + (this.column + 1) + ":\n" + snippet;
    }

    public String getName() {
        return this.name;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public int getIndex() {
        return this.index;
    }

    public int[] getBuffer() {
        return this.buffer;
    }

    public int getPointer() {
        return this.pointer;
    }
}
