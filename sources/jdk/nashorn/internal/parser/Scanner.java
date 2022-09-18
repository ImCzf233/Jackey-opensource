package jdk.nashorn.internal.parser;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Scanner.class */
public class Scanner {
    protected final char[] content;
    protected int position;
    protected final int limit;
    protected int line;
    protected char ch0;
    protected char ch1;
    protected char ch2;
    protected char ch3;

    public Scanner(char[] content, int line, int start, int length) {
        this.content = content;
        this.position = start;
        this.limit = start + length;
        this.line = line;
        reset(this.position);
    }

    public Scanner(String content) {
        this(content.toCharArray(), 0, 0, content.length());
    }

    public Scanner(Scanner scanner, State state) {
        this.content = scanner.content;
        this.position = state.position;
        this.limit = state.limit;
        this.line = state.line;
        reset(this.position);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/Scanner$State.class */
    public static class State {
        public final int position;
        public int limit;
        public final int line;

        public State(int position, int limit, int line) {
            this.position = position;
            this.limit = limit;
            this.line = line;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public boolean isEmpty() {
            return this.position == this.limit;
        }
    }

    State saveState() {
        return new State(this.position, this.limit, this.line);
    }

    public void restoreState(State state) {
        this.position = state.position;
        this.line = state.line;
        reset(this.position);
    }

    public final boolean atEOF() {
        return this.position == this.limit;
    }

    protected final char charAt(int i) {
        if (i < this.limit) {
            return this.content[i];
        }
        return (char) 0;
    }

    public final void reset(int i) {
        this.ch0 = charAt(i);
        this.ch1 = charAt(i + 1);
        this.ch2 = charAt(i + 2);
        this.ch3 = charAt(i + 3);
        this.position = i < this.limit ? i : this.limit;
    }

    public final void skip(int n) {
        if (n == 1 && !atEOF()) {
            this.ch0 = this.ch1;
            this.ch1 = this.ch2;
            this.ch2 = this.ch3;
            this.ch3 = charAt(this.position + 4);
            this.position++;
        } else if (n != 0) {
            reset(this.position + n);
        }
    }
}
