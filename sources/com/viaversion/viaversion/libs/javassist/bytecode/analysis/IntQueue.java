package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/analysis/IntQueue.class */
class IntQueue {
    private Entry head;
    private Entry tail;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/analysis/IntQueue$Entry.class */
    public static class Entry {
        private Entry next;
        private int value;

        private Entry(int value) {
            this.value = value;
        }
    }

    public void add(int value) {
        Entry entry = new Entry(value);
        if (this.tail != null) {
            this.tail.next = entry;
        }
        this.tail = entry;
        if (this.head == null) {
            this.head = entry;
        }
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int take() {
        if (this.head != null) {
            int value = this.head.value;
            this.head = this.head.next;
            if (this.head == null) {
                this.tail = null;
            }
            return value;
        }
        throw new NoSuchElementException();
    }
}
