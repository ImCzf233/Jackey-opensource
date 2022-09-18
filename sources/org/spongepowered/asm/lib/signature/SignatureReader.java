package org.spongepowered.asm.lib.signature;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/signature/SignatureReader.class */
public class SignatureReader {
    private final String signature;

    public SignatureReader(String signature) {
        this.signature = signature;
    }

    public void accept(SignatureVisitor v) {
        int pos;
        char c;
        String signature = this.signature;
        int len = signature.length();
        if (signature.charAt(0) == '<') {
            pos = 2;
            do {
                int end = signature.indexOf(58, pos);
                v.visitFormalTypeParameter(signature.substring(pos - 1, end));
                int pos2 = end + 1;
                char c2 = signature.charAt(pos2);
                if (c2 == 'L' || c2 == '[' || c2 == 'T') {
                    pos2 = parseType(signature, pos2, v.visitClassBound());
                }
                while (true) {
                    int i = pos2;
                    pos = pos2 + 1;
                    c = signature.charAt(i);
                    if (c == ':') {
                        pos2 = parseType(signature, pos, v.visitInterfaceBound());
                    }
                }
            } while (c != '>');
        } else {
            pos = 0;
        }
        if (signature.charAt(pos) == '(') {
            int pos3 = pos + 1;
            while (signature.charAt(pos3) != ')') {
                pos3 = parseType(signature, pos3, v.visitParameterType());
            }
            int parseType = parseType(signature, pos3 + 1, v.visitReturnType());
            while (true) {
                int pos4 = parseType;
                if (pos4 < len) {
                    parseType = parseType(signature, pos4 + 1, v.visitExceptionType());
                } else {
                    return;
                }
            }
        } else {
            int parseType2 = parseType(signature, pos, v.visitSuperclass());
            while (true) {
                int pos5 = parseType2;
                if (pos5 < len) {
                    parseType2 = parseType(signature, pos5, v.visitInterface());
                } else {
                    return;
                }
            }
        }
    }

    public void acceptType(SignatureVisitor v) {
        parseType(this.signature, 0, v);
    }

    private static int parseType(String signature, int pos, SignatureVisitor v) {
        int pos2 = pos + 1;
        char c = signature.charAt(pos);
        switch (c) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'V':
            case 'Z':
                v.visitBaseType(c);
                return pos2;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
                int start = pos2;
                boolean visited = false;
                boolean inner = false;
                while (true) {
                    int i = pos2;
                    pos2++;
                    char c2 = signature.charAt(i);
                    switch (c2) {
                        case '.':
                        case ';':
                            if (!visited) {
                                String name = signature.substring(start, pos2 - 1);
                                if (inner) {
                                    v.visitInnerClassType(name);
                                } else {
                                    v.visitClassType(name);
                                }
                            }
                            if (c2 == ';') {
                                v.visitEnd();
                                return pos2;
                            }
                            start = pos2;
                            visited = false;
                            inner = true;
                            break;
                        case '<':
                            String name2 = signature.substring(start, pos2 - 1);
                            if (inner) {
                                v.visitInnerClassType(name2);
                            } else {
                                v.visitClassType(name2);
                            }
                            visited = true;
                            while (true) {
                                char c3 = signature.charAt(pos2);
                                switch (c3) {
                                    case '*':
                                        pos2++;
                                        v.visitTypeArgument();
                                    case '+':
                                    case '-':
                                        pos2 = parseType(signature, pos2 + 1, v.visitTypeArgument(c3));
                                    case '>':
                                        break;
                                    default:
                                        pos2 = parseType(signature, pos2, v.visitTypeArgument('='));
                                }
                            }
                            break;
                    }
                }
                break;
            case 'T':
                int end = signature.indexOf(59, pos2);
                v.visitTypeVariable(signature.substring(pos2, end));
                return end + 1;
            case '[':
                return parseType(signature, pos2, v.visitArrayType());
        }
    }
}
