package jdk.nashorn.internal.parser;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/TokenLookup.class */
public final class TokenLookup {
    private static final TokenType[] table;
    private static final int tableBase = 32;
    private static final int tableLimit = 126;
    private static final int tableLength = 95;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        TokenType[] values;
        TokenType next;
        $assertionsDisabled = !TokenLookup.class.desiredAssertionStatus();
        table = new TokenType[95];
        for (TokenType tokenType : TokenType.getValues()) {
            String name = tokenType.getName();
            if (name != null && tokenType.getKind() != TokenKind.SPECIAL) {
                char first = name.charAt(0);
                int index = first - ' ';
                if (!$assertionsDisabled && index >= 95) {
                    throw new AssertionError("Token name does not fit lookup table");
                }
                int length = tokenType.getLength();
                TokenType prev = null;
                TokenType tokenType2 = table[index];
                while (true) {
                    next = tokenType2;
                    if (next == null || next.getLength() <= length) {
                        break;
                    }
                    prev = next;
                    tokenType2 = next.getNext();
                }
                tokenType.setNext(next);
                if (prev == null) {
                    table[index] = tokenType;
                } else {
                    prev.setNext(tokenType);
                }
            }
        }
    }

    private TokenLookup() {
    }

    public static TokenType lookupKeyword(char[] content, int position, int length) {
        if ($assertionsDisabled || table != null) {
            char first = content[position];
            if ('a' <= first && first <= 'z') {
                int index = first - ' ';
                TokenType tokenType = table[index];
                while (true) {
                    TokenType tokenType2 = tokenType;
                    if (tokenType2 == null) {
                        break;
                    }
                    int tokenLength = tokenType2.getLength();
                    if (tokenLength == length) {
                        String name = tokenType2.getName();
                        int i = 0;
                        while (i < length && content[position + i] == name.charAt(i)) {
                            i++;
                        }
                        if (i == length) {
                            return tokenType2;
                        }
                    } else if (tokenLength < length) {
                        break;
                    }
                    tokenType = tokenType2.getNext();
                }
            }
            return TokenType.IDENT;
        }
        throw new AssertionError("Token lookup table is not initialized");
    }

    public static TokenType lookupOperator(char ch0, char ch1, char ch2, char ch3) {
        if ($assertionsDisabled || table != null) {
            if (' ' >= ch0 || ch0 > '~') {
                return null;
            }
            if ('a' > ch0 || ch0 > 'z') {
                int index = ch0 - ' ';
                TokenType tokenType = table[index];
                while (true) {
                    TokenType tokenType2 = tokenType;
                    if (tokenType2 != null) {
                        String name = tokenType2.getName();
                        switch (name.length()) {
                            case 1:
                                return tokenType2;
                            case 2:
                                if (name.charAt(1) != ch1) {
                                    break;
                                } else {
                                    return tokenType2;
                                }
                            case 3:
                                if (name.charAt(1) == ch1 && name.charAt(2) == ch2) {
                                    return tokenType2;
                                }
                                break;
                            case 4:
                                if (name.charAt(1) == ch1 && name.charAt(2) == ch2 && name.charAt(3) == ch3) {
                                    return tokenType2;
                                }
                                break;
                        }
                        tokenType = tokenType2.getNext();
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        } else {
            throw new AssertionError("Token lookup table is not initialized");
        }
    }
}
