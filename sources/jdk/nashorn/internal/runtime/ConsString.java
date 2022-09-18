package jdk.nashorn.internal.runtime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ConsString.class */
public final class ConsString implements CharSequence {
    private CharSequence left;
    private CharSequence right;
    private final int length;
    private volatile int state = 0;
    private static final int STATE_NEW = 0;
    private static final int STATE_THRESHOLD = 2;
    private static final int STATE_FLATTENED = -1;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ConsString.class.desiredAssertionStatus();
    }

    public ConsString(CharSequence left, CharSequence right) {
        if ($assertionsDisabled || JSType.isString(left)) {
            if (!$assertionsDisabled && !JSType.isString(right)) {
                throw new AssertionError();
            }
            this.left = left;
            this.right = right;
            this.length = left.length() + right.length();
            if (this.length < 0) {
                throw new IllegalArgumentException("too big concatenated String");
            }
            return;
        }
        throw new AssertionError();
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return (String) flattened(true);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    @Override // java.lang.CharSequence
    public char charAt(int index) {
        return flattened(true).charAt(index);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return flattened(true).subSequence(start, end);
    }

    public synchronized CharSequence[] getComponents() {
        return new CharSequence[]{this.left, this.right};
    }

    private CharSequence flattened(boolean flattenNested) {
        if (this.state != -1) {
            flatten(flattenNested);
        }
        return this.left;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004f, code lost:
        if (r1 >= 2) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void flatten(boolean r7) {
        /*
            r6 = this;
            r0 = r6
            int r0 = r0.length
            char[] r0 = new char[r0]
            r8 = r0
            r0 = r6
            int r0 = r0.length
            r9 = r0
            java.util.ArrayDeque r0 = new java.util.ArrayDeque
            r1 = r0
            r1.<init>()
            r10 = r0
            r0 = r10
            r1 = r6
            java.lang.CharSequence r1 = r1.left
            r0.addFirst(r1)
            r0 = r6
            java.lang.CharSequence r0 = r0.right
            r11 = r0
        L26:
            r0 = r11
            boolean r0 = r0 instanceof jdk.nashorn.internal.runtime.ConsString
            if (r0 == 0) goto L73
            r0 = r11
            jdk.nashorn.internal.runtime.ConsString r0 = (jdk.nashorn.internal.runtime.ConsString) r0
            r12 = r0
            r0 = r12
            int r0 = r0.state
            r1 = -1
            if (r0 == r1) goto L52
            r0 = r7
            if (r0 == 0) goto L5d
            r0 = r12
            r1 = r0
            int r1 = r1.state
            r2 = 1
            int r1 = r1 + r2
            r2 = r1; r1 = r0; r0 = r2; 
            r1.state = r2
            r1 = 2
            if (r0 < r1) goto L5d
        L52:
            r0 = r12
            r1 = 0
            java.lang.CharSequence r0 = r0.flattened(r1)
            r11 = r0
            goto L70
        L5d:
            r0 = r10
            r1 = r12
            java.lang.CharSequence r1 = r1.left
            r0.addFirst(r1)
            r0 = r12
            java.lang.CharSequence r0 = r0.right
            r11 = r0
        L70:
            goto La9
        L73:
            r0 = r11
            java.lang.String r0 = (java.lang.String) r0
            r12 = r0
            r0 = r9
            r1 = r12
            int r1 = r1.length()
            int r0 = r0 - r1
            r9 = r0
            r0 = r12
            r1 = 0
            r2 = r12
            int r2 = r2.length()
            r3 = r8
            r4 = r9
            r0.getChars(r1, r2, r3, r4)
            r0 = r10
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L9d
            r0 = 0
            goto La7
        L9d:
            r0 = r10
            java.lang.Object r0 = r0.pollFirst()
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
        La7:
            r11 = r0
        La9:
            r0 = r11
            if (r0 != 0) goto L26
            r0 = r6
            java.lang.String r1 = new java.lang.String
            r2 = r1
            r3 = r8
            r2.<init>(r3)
            r0.left = r1
            r0 = r6
            java.lang.String r1 = ""
            r0.right = r1
            r0 = r6
            r1 = -1
            r0.state = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.ConsString.flatten(boolean):void");
    }
}
