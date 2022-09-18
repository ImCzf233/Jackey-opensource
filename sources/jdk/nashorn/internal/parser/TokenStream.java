package jdk.nashorn.internal.parser;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/TokenStream.class */
public class TokenStream {
    private static final int INITIAL_SIZE = 256;
    private long[] buffer = new long[256];
    private int count = 0;

    /* renamed from: in */
    private int f257in = 0;
    private int out = 0;
    private int base = 0;

    private int next(int position) {
        int next = position + 1;
        if (next >= this.buffer.length) {
            next = 0;
        }
        return next;
    }

    private int index(int k) {
        int index = k - (this.base - this.out);
        if (index >= this.buffer.length) {
            index -= this.buffer.length;
        }
        return index;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public boolean isFull() {
        return this.count == this.buffer.length;
    }

    public int count() {
        return this.count;
    }

    public int first() {
        return this.base;
    }

    public int last() {
        return (this.base + this.count) - 1;
    }

    public void removeLast() {
        if (this.count != 0) {
            this.count--;
            this.f257in--;
            if (this.f257in < 0) {
                this.f257in = this.buffer.length - 1;
            }
        }
    }

    public void put(long token) {
        if (this.count == this.buffer.length) {
            grow();
        }
        this.buffer[this.f257in] = token;
        this.count++;
        this.f257in = next(this.f257in);
    }

    public long get(int k) {
        return this.buffer[index(k)];
    }

    public void commit(int k) {
        this.out = index(k);
        this.count -= k - this.base;
        this.base = k;
    }

    public void grow() {
        long[] newBuffer = new long[this.buffer.length * 2];
        if (this.f257in > this.out) {
            System.arraycopy(this.buffer, this.out, newBuffer, 0, this.count);
        } else {
            int portion = this.buffer.length - this.out;
            System.arraycopy(this.buffer, this.out, newBuffer, 0, portion);
            System.arraycopy(this.buffer, 0, newBuffer, portion, this.count - portion);
        }
        this.out = 0;
        this.f257in = this.count;
        this.buffer = newBuffer;
    }

    public void reset() {
        this.base = 0;
        this.count = 0;
        this.out = 0;
        this.f257in = 0;
    }
}
