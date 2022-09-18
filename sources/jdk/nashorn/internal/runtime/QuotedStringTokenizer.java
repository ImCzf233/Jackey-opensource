package jdk.nashorn.internal.runtime;

import java.util.LinkedList;
import java.util.Stack;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/QuotedStringTokenizer.class */
public final class QuotedStringTokenizer {
    private final LinkedList<String> tokens;
    private final char[] quotes;

    public QuotedStringTokenizer(String str) {
        this(str, " ");
    }

    public QuotedStringTokenizer(String str, String delim) {
        this(str, delim, new char[]{'\"', '\''});
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x009a, code lost:
        r5.tokens.add(stripQuotes(r11));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private QuotedStringTokenizer(java.lang.String r6, java.lang.String r7, char[] r8) {
        /*
            r5 = this;
            r0 = r5
            r0.<init>()
            r0 = r5
            r1 = r8
            r0.quotes = r1
            r0 = 1
            r9 = r0
            r0 = 0
            r10 = r0
        Lf:
            r0 = r10
            r1 = r7
            int r1 = r1.length()
            if (r0 >= r1) goto L30
            r0 = r7
            r1 = r10
            char r0 = r0.charAt(r1)
            boolean r0 = java.lang.Character.isWhitespace(r0)
            if (r0 != 0) goto L2a
            r0 = 0
            r9 = r0
            goto L30
        L2a:
            int r10 = r10 + 1
            goto Lf
        L30:
            java.util.StringTokenizer r0 = new java.util.StringTokenizer
            r1 = r0
            r2 = r6
            r3 = r7
            r1.<init>(r2, r3)
            r10 = r0
            r0 = r5
            java.util.LinkedList r1 = new java.util.LinkedList
            r2 = r1
            r2.<init>()
            r0.tokens = r1
        L46:
            r0 = r10
            boolean r0 = r0.hasMoreTokens()
            if (r0 == 0) goto Lab
            r0 = r10
            java.lang.String r0 = r0.nextToken()
            r11 = r0
        L55:
            r0 = r5
            r1 = r11
            boolean r0 = r0.unmatchedQuotesIn(r1)
            if (r0 == 0) goto L9a
            r0 = r10
            boolean r0 = r0.hasMoreTokens()
            if (r0 != 0) goto L70
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            r1 = r0
            r2 = r11
            r1.<init>(r2)
            throw r0
        L70:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            r1 = r11
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = r9
            if (r1 == 0) goto L86
            java.lang.String r1 = " "
            goto L87
        L86:
            r1 = r7
        L87:
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = r10
            java.lang.String r1 = r1.nextToken()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r11 = r0
            goto L55
        L9a:
            r0 = r5
            java.util.LinkedList<java.lang.String> r0 = r0.tokens
            r1 = r5
            r2 = r11
            java.lang.String r1 = r1.stripQuotes(r2)
            boolean r0 = r0.add(r1)
            goto L46
        Lab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.QuotedStringTokenizer.<init>(java.lang.String, java.lang.String, char[]):void");
    }

    public int countTokens() {
        return this.tokens.size();
    }

    public boolean hasMoreTokens() {
        return countTokens() > 0;
    }

    public String nextToken() {
        return this.tokens.removeFirst();
    }

    private String stripQuotes(String value0) {
        char[] cArr;
        String value = value0.trim();
        for (char q : this.quotes) {
            if (value.length() >= 2 && value.startsWith("" + q) && value.endsWith("" + q)) {
                value = value.substring(1, value.length() - 1).replace("\\" + q, "" + q);
            }
        }
        return value;
    }

    private boolean unmatchedQuotesIn(String str) {
        char[] cArr;
        Stack<Character> quoteStack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            for (char q : this.quotes) {
                if (c == q) {
                    if (quoteStack.isEmpty()) {
                        quoteStack.push(Character.valueOf(c));
                    } else {
                        char top = quoteStack.pop().charValue();
                        if (top != c) {
                            quoteStack.push(Character.valueOf(top));
                            quoteStack.push(Character.valueOf(c));
                        }
                    }
                }
            }
        }
        return !quoteStack.isEmpty();
    }
}
