package com.viaversion.viaversion.libs.javassist.compiler;

import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/Lex.class */
public class Lex implements TokenId {
    private String input;
    private int maxlen;
    private static final int[] equalOps = {TokenId.NEQ, 0, 0, 0, TokenId.MOD_E, TokenId.AND_E, 0, 0, 0, TokenId.MUL_E, TokenId.PLUS_E, 0, TokenId.MINUS_E, 0, TokenId.DIV_E, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, TokenId.f174LE, TokenId.f175EQ, TokenId.f176GE, 0};
    private static final KeywordTable ktable = new KeywordTable();
    private int lastChar = -1;
    private StringBuffer textBuffer = new StringBuffer();
    private Token currentToken = new Token();
    private Token lookAheadTokens = null;
    private int position = 0;
    private int lineNumber = 0;

    public Lex(String s) {
        this.input = s;
        this.maxlen = s.length();
    }

    public int get() {
        if (this.lookAheadTokens == null) {
            return get(this.currentToken);
        }
        Token t = this.lookAheadTokens;
        this.currentToken = t;
        this.lookAheadTokens = this.lookAheadTokens.next;
        return t.tokenId;
    }

    public int lookAhead() {
        return lookAhead(0);
    }

    public int lookAhead(int i) {
        Token tk = this.lookAheadTokens;
        if (tk == null) {
            Token token = this.currentToken;
            tk = token;
            this.lookAheadTokens = token;
            tk.next = null;
            get(tk);
        }
        while (true) {
            int i2 = i;
            i--;
            if (i2 > 0) {
                if (tk.next == null) {
                    Token tk2 = new Token();
                    tk.next = tk2;
                    get(tk2);
                }
                tk = tk.next;
            } else {
                this.currentToken = tk;
                return tk.tokenId;
            }
        }
    }

    public String getString() {
        return this.currentToken.textValue;
    }

    public long getLong() {
        return this.currentToken.longValue;
    }

    public double getDouble() {
        return this.currentToken.doubleValue;
    }

    private int get(Token token) {
        int t;
        do {
            t = readLine(token);
        } while (t == 10);
        token.tokenId = t;
        return t;
    }

    private int readLine(Token token) {
        int c = getNextNonWhiteChar();
        if (c < 0) {
            return c;
        }
        if (c == 10) {
            this.lineNumber++;
            return 10;
        } else if (c == 39) {
            return readCharConst(token);
        } else {
            if (c == 34) {
                return readStringL(token);
            }
            if (48 <= c && c <= 57) {
                return readNumber(c, token);
            }
            if (c == 46) {
                int c2 = getc();
                if (48 <= c2 && c2 <= 57) {
                    StringBuffer tbuf = this.textBuffer;
                    tbuf.setLength(0);
                    tbuf.append('.');
                    return readDouble(tbuf, c2, token);
                }
                ungetc(c2);
                return readSeparator(46);
            } else if (Character.isJavaIdentifierStart((char) c)) {
                return readIdentifier(c, token);
            } else {
                return readSeparator(c);
            }
        }
    }

    private int getNextNonWhiteChar() {
        int c;
        do {
            c = getc();
            if (c == 47) {
                int c2 = getc();
                if (c2 == 47) {
                    do {
                        c = getc();
                        if (c == 10 || c == 13) {
                            break;
                        }
                    } while (c != -1);
                } else if (c2 == 42) {
                    while (true) {
                        c = getc();
                        if (c == -1) {
                            break;
                        } else if (c == 42) {
                            int c3 = getc();
                            if (c3 == 47) {
                                c = 32;
                                break;
                            }
                            ungetc(c3);
                        }
                    }
                } else {
                    ungetc(c2);
                    c = 47;
                }
            }
        } while (isBlank(c));
        return c;
    }

    private int readCharConst(Token token) {
        int i = 0;
        while (true) {
            int value = i;
            int c = getc();
            if (c != 39) {
                if (c == 92) {
                    i = readEscapeChar();
                } else if (c < 32) {
                    if (c == 10) {
                        this.lineNumber++;
                        return 500;
                    }
                    return 500;
                } else {
                    i = c;
                }
            } else {
                token.longValue = value;
                return TokenId.CharConstant;
            }
        }
    }

    private int readEscapeChar() {
        int c = getc();
        if (c == 110) {
            c = 10;
        } else if (c == 116) {
            c = 9;
        } else if (c == 114) {
            c = 13;
        } else if (c == 102) {
            c = 12;
        } else if (c == 10) {
            this.lineNumber++;
        }
        return c;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
        r4.lineNumber++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
        return 500;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readStringL(com.viaversion.viaversion.libs.javassist.compiler.Token r5) {
        /*
            r4 = this;
            r0 = r4
            java.lang.StringBuffer r0 = r0.textBuffer
            r7 = r0
            r0 = r7
            r1 = 0
            r0.setLength(r1)
        La:
            r0 = r4
            int r0 = r0.getc()
            r1 = r0
            r6 = r1
            r1 = 34
            if (r0 == r1) goto L45
            r0 = r6
            r1 = 92
            if (r0 != r1) goto L23
            r0 = r4
            int r0 = r0.readEscapeChar()
            r6 = r0
            goto L3b
        L23:
            r0 = r6
            r1 = 10
            if (r0 == r1) goto L2d
            r0 = r6
            if (r0 >= 0) goto L3b
        L2d:
            r0 = r4
            r1 = r0
            int r1 = r1.lineNumber
            r2 = 1
            int r1 = r1 + r2
            r0.lineNumber = r1
            r0 = 500(0x1f4, float:7.0E-43)
            return r0
        L3b:
            r0 = r7
            r1 = r6
            char r1 = (char) r1
            java.lang.StringBuffer r0 = r0.append(r1)
            goto La
        L45:
            r0 = r4
            int r0 = r0.getc()
            r6 = r0
            r0 = r6
            r1 = 10
            if (r0 != r1) goto L5d
            r0 = r4
            r1 = r0
            int r1 = r1.lineNumber
            r2 = 1
            int r1 = r1 + r2
            r0.lineNumber = r1
            goto L45
        L5d:
            r0 = r6
            boolean r0 = isBlank(r0)
            if (r0 != 0) goto L45
            goto L67
        L67:
            r0 = r6
            r1 = 34
            if (r0 == r1) goto La
            r0 = r4
            r1 = r6
            r0.ungetc(r1)
            goto L75
        L75:
            r0 = r5
            r1 = r7
            java.lang.String r1 = r1.toString()
            r0.textValue = r1
            r0 = 406(0x196, float:5.69E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.javassist.compiler.Lex.readStringL(com.viaversion.viaversion.libs.javassist.compiler.Token):int");
    }

    private int readNumber(int c, Token token) {
        int c2;
        long value;
        int c3;
        long value2 = 0;
        int c22 = getc();
        if (c == 48) {
            if (c22 == 88 || c22 == 120) {
                while (true) {
                    c2 = getc();
                    if (48 <= c2 && c2 <= 57) {
                        value2 = (value2 * 16) + (c2 - 48);
                    } else if (65 <= c2 && c2 <= 70) {
                        value2 = (value2 * 16) + (c2 - 65) + 10;
                    } else if (97 > c2 || c2 > 102) {
                        break;
                    } else {
                        value2 = (value2 * 16) + (c2 - 97) + 10;
                    }
                }
                token.longValue = value2;
                if (c2 == 76 || c2 == 108) {
                    return TokenId.LongConstant;
                }
                ungetc(c2);
                return TokenId.IntConstant;
            } else if (48 <= c22 && c22 <= 55) {
                long j = c22 - 48;
                while (true) {
                    value = j;
                    c3 = getc();
                    if (48 > c3 || c3 > 55) {
                        break;
                    }
                    j = (value * 8) + (c3 - 48);
                }
                token.longValue = value;
                if (c3 == 76 || c3 == 108) {
                    return TokenId.LongConstant;
                }
                ungetc(c3);
                return TokenId.IntConstant;
            }
        }
        long value3 = c - 48;
        while (48 <= c22 && c22 <= 57) {
            value3 = ((value3 * 10) + c22) - 48;
            c22 = getc();
        }
        token.longValue = value3;
        if (c22 == 70 || c22 == 102) {
            token.doubleValue = value3;
            return TokenId.FloatConstant;
        } else if (c22 == 69 || c22 == 101 || c22 == 68 || c22 == 100 || c22 == 46) {
            StringBuffer tbuf = this.textBuffer;
            tbuf.setLength(0);
            tbuf.append(value3);
            return readDouble(tbuf, c22, token);
        } else if (c22 == 76 || c22 == 108) {
            return TokenId.LongConstant;
        } else {
            ungetc(c22);
            return TokenId.IntConstant;
        }
    }

    private int readDouble(StringBuffer sbuf, int c, Token token) {
        if (c != 69 && c != 101 && c != 68 && c != 100) {
            sbuf.append((char) c);
            while (true) {
                c = getc();
                if (48 > c || c > 57) {
                    break;
                }
                sbuf.append((char) c);
            }
        }
        if (c == 69 || c == 101) {
            sbuf.append((char) c);
            c = getc();
            if (c == 43 || c == 45) {
                sbuf.append((char) c);
                c = getc();
            }
            while (48 <= c && c <= 57) {
                sbuf.append((char) c);
                c = getc();
            }
        }
        try {
            token.doubleValue = Double.parseDouble(sbuf.toString());
            if (c == 70 || c == 102) {
                return TokenId.FloatConstant;
            }
            if (c != 68 && c != 100) {
                ungetc(c);
                return TokenId.DoubleConstant;
            }
            return TokenId.DoubleConstant;
        } catch (NumberFormatException e) {
            return 500;
        }
    }

    static {
        ktable.append("abstract", TokenId.ABSTRACT);
        ktable.append("boolean", TokenId.BOOLEAN);
        ktable.append("break", TokenId.BREAK);
        ktable.append("byte", TokenId.BYTE);
        ktable.append("case", TokenId.CASE);
        ktable.append("catch", TokenId.CATCH);
        ktable.append("char", TokenId.CHAR);
        ktable.append("class", TokenId.CLASS);
        ktable.append("const", TokenId.CONST);
        ktable.append("continue", TokenId.CONTINUE);
        ktable.append("default", TokenId.DEFAULT);
        ktable.append("do", TokenId.f172DO);
        ktable.append("double", TokenId.DOUBLE);
        ktable.append("else", TokenId.ELSE);
        ktable.append("extends", TokenId.EXTENDS);
        ktable.append("false", TokenId.FALSE);
        ktable.append("final", TokenId.FINAL);
        ktable.append("finally", TokenId.FINALLY);
        ktable.append("float", TokenId.FLOAT);
        ktable.append("for", TokenId.FOR);
        ktable.append("goto", TokenId.GOTO);
        ktable.append("if", 320);
        ktable.append("implements", 321);
        ktable.append("import", TokenId.IMPORT);
        ktable.append("instanceof", TokenId.INSTANCEOF);
        ktable.append("int", TokenId.INT);
        ktable.append("interface", TokenId.INTERFACE);
        ktable.append("long", TokenId.LONG);
        ktable.append("native", TokenId.NATIVE);
        ktable.append("new", TokenId.NEW);
        ktable.append(Configurator.NULL, TokenId.NULL);
        ktable.append("package", TokenId.PACKAGE);
        ktable.append("private", TokenId.PRIVATE);
        ktable.append("protected", TokenId.PROTECTED);
        ktable.append("public", TokenId.PUBLIC);
        ktable.append("return", TokenId.RETURN);
        ktable.append("short", TokenId.SHORT);
        ktable.append("static", TokenId.STATIC);
        ktable.append("strictfp", TokenId.STRICT);
        ktable.append("super", TokenId.SUPER);
        ktable.append("switch", TokenId.SWITCH);
        ktable.append("synchronized", TokenId.SYNCHRONIZED);
        ktable.append("this", TokenId.THIS);
        ktable.append("throw", TokenId.THROW);
        ktable.append("throws", 341);
        ktable.append("transient", TokenId.TRANSIENT);
        ktable.append("true", TokenId.TRUE);
        ktable.append("try", TokenId.TRY);
        ktable.append("void", TokenId.VOID);
        ktable.append("volatile", TokenId.VOLATILE);
        ktable.append("while", TokenId.WHILE);
    }

    private int readSeparator(int c) {
        int c2;
        if (33 <= c && c <= 63) {
            int t = equalOps[c - 33];
            if (t == 0) {
                return c;
            }
            c2 = getc();
            if (c == c2) {
                switch (c) {
                    case 38:
                        return TokenId.ANDAND;
                    case 43:
                        return TokenId.PLUSPLUS;
                    case 45:
                        return TokenId.MINUSMINUS;
                    case 60:
                        int c3 = getc();
                        if (c3 == 61) {
                            return TokenId.LSHIFT_E;
                        }
                        ungetc(c3);
                        return TokenId.LSHIFT;
                    case 61:
                        return TokenId.f175EQ;
                    case 62:
                        int c32 = getc();
                        if (c32 == 61) {
                            return TokenId.RSHIFT_E;
                        }
                        if (c32 == 62) {
                            int c33 = getc();
                            if (c33 == 61) {
                                return TokenId.ARSHIFT_E;
                            }
                            ungetc(c33);
                            return TokenId.ARSHIFT;
                        }
                        ungetc(c32);
                        return TokenId.RSHIFT;
                }
            } else if (c2 == 61) {
                return t;
            }
        } else if (c == 94) {
            c2 = getc();
            if (c2 == 61) {
                return TokenId.EXOR_E;
            }
        } else if (c == 124) {
            c2 = getc();
            if (c2 == 61) {
                return TokenId.OR_E;
            }
            if (c2 == 124) {
                return TokenId.OROR;
            }
        } else {
            return c;
        }
        ungetc(c2);
        return c;
    }

    private int readIdentifier(int c, Token token) {
        StringBuffer tbuf = this.textBuffer;
        tbuf.setLength(0);
        do {
            tbuf.append((char) c);
            c = getc();
        } while (Character.isJavaIdentifierPart((char) c));
        ungetc(c);
        String name = tbuf.toString();
        int t = ktable.lookup(name);
        if (t >= 0) {
            return t;
        }
        token.textValue = name;
        return TokenId.Identifier;
    }

    private static boolean isBlank(int c) {
        return c == 32 || c == 9 || c == 12 || c == 13 || c == 10;
    }

    private static boolean isDigit(int c) {
        return 48 <= c && c <= 57;
    }

    private void ungetc(int c) {
        this.lastChar = c;
    }

    public String getTextAround() {
        int begin = this.position - 10;
        if (begin < 0) {
            begin = 0;
        }
        int end = this.position + 10;
        if (end > this.maxlen) {
            end = this.maxlen;
        }
        return this.input.substring(begin, end);
    }

    private int getc() {
        if (this.lastChar < 0) {
            if (this.position < this.maxlen) {
                String str = this.input;
                int i = this.position;
                this.position = i + 1;
                return str.charAt(i);
            }
            return -1;
        }
        int c = this.lastChar;
        this.lastChar = -1;
        return c;
    }
}
