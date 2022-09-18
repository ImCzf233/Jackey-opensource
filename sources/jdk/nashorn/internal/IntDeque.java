package jdk.nashorn.internal;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/IntDeque.class */
public class IntDeque {
    private int[] deque = new int[16];
    private int nextFree = 0;

    public void push(int value) {
        if (this.nextFree == this.deque.length) {
            int[] newDeque = new int[this.nextFree * 2];
            System.arraycopy(this.deque, 0, newDeque, 0, this.nextFree);
            this.deque = newDeque;
        }
        int[] iArr = this.deque;
        int i = this.nextFree;
        this.nextFree = i + 1;
        iArr[i] = value;
    }

    public int pop() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        this.nextFree = i;
        return iArr[i];
    }

    public int peek() {
        return this.deque[this.nextFree - 1];
    }

    public int getAndIncrement() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        int i2 = iArr[i];
        iArr[i] = i2 + 1;
        return i2;
    }

    public int decrementAndGet() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        int i2 = iArr[i] - 1;
        iArr[i] = i2;
        return i2;
    }

    public boolean isEmpty() {
        return this.nextFree == 0;
    }
}
