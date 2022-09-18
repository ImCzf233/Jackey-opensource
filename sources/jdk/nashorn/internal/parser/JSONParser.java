package jdk.nashorn.internal.parser;

import java.util.ArrayList;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.SpillProperty;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.scripts.C1653JD;
import jdk.nashorn.internal.scripts.C1654JO;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/JSONParser.class */
public class JSONParser {
    private final String source;
    private final Global global;
    private final boolean dualFields;
    final int length;
    int pos = 0;
    private static final int EOF = -1;
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String NULL = "null";
    private static final int STATE_EMPTY = 0;
    private static final int STATE_ELEMENT_PARSED = 1;
    private static final int STATE_COMMA_PARSED = 2;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSONParser.class.desiredAssertionStatus();
    }

    public JSONParser(String source, Global global, boolean dualFields) {
        this.source = source;
        this.global = global;
        this.length = source.length();
        this.dualFields = dualFields;
    }

    public static String quote(String value) {
        char[] charArray;
        StringBuilder product = new StringBuilder();
        product.append("\"");
        for (char ch : value.toCharArray()) {
            switch (ch) {
                case '\b':
                    product.append("\\b");
                    break;
                case '\t':
                    product.append("\\t");
                    break;
                case '\n':
                    product.append("\\n");
                    break;
                case '\f':
                    product.append("\\f");
                    break;
                case '\r':
                    product.append("\\r");
                    break;
                case '\"':
                    product.append("\\\"");
                    break;
                case '\\':
                    product.append("\\\\");
                    break;
                default:
                    if (ch < ' ') {
                        product.append(Lexer.unicodeEscape(ch));
                        break;
                    } else {
                        product.append(ch);
                        break;
                    }
            }
        }
        product.append("\"");
        return product.toString();
    }

    public Object parse() {
        Object value = parseLiteral();
        skipWhiteSpace();
        if (this.pos < this.length) {
            throw expectedError(this.pos, "eof", toString(peek()));
        }
        return value;
    }

    private Object parseLiteral() {
        skipWhiteSpace();
        int c = peek();
        if (c == -1) {
            throw expectedError(this.pos, "json literal", "eof");
        }
        switch (c) {
            case 34:
                return parseString();
            case 91:
                return parseArray();
            case 102:
                return parseKeyword(FALSE, Boolean.FALSE);
            case 110:
                return parseKeyword("null", null);
            case 116:
                return parseKeyword(TRUE, Boolean.TRUE);
            case 123:
                return parseObject();
            default:
                if (isDigit(c) || c == 45) {
                    return parseNumber();
                }
                if (c == 46) {
                    throw numberError(this.pos);
                }
                throw expectedError(this.pos, "json literal", toString(c));
        }
    }

    private Object parseObject() {
        PropertyMap propertyMap = this.dualFields ? C1653JD.getInitialMap() : C1654JO.getInitialMap();
        ArrayData arrayData = ArrayData.EMPTY_ARRAY;
        ArrayList<Object> values = new ArrayList<>();
        int state = 0;
        if ($assertionsDisabled || peek() == 123) {
            this.pos++;
            while (this.pos < this.length) {
                skipWhiteSpace();
                int c = peek();
                switch (c) {
                    case 34:
                        if (state == 1) {
                            throw expectedError(this.pos - 1, ", or }", toString(c));
                        }
                        String id = parseString();
                        expectColon();
                        Object value = parseLiteral();
                        int index = ArrayIndex.getArrayIndex(id);
                        if (ArrayIndex.isValidArrayIndex(index)) {
                            arrayData = addArrayElement(arrayData, index, value);
                        } else {
                            propertyMap = addObjectProperty(propertyMap, values, id, value);
                        }
                        state = 1;
                        break;
                    case 44:
                        if (state != 1) {
                            throw error(AbstractParser.message("trailing.comma.in.json", new String[0]), this.pos);
                        }
                        state = 2;
                        this.pos++;
                        break;
                    case 125:
                        if (state == 2) {
                            throw error(AbstractParser.message("trailing.comma.in.json", new String[0]), this.pos);
                        }
                        this.pos++;
                        return createObject(propertyMap, values, arrayData);
                    default:
                        throw expectedError(this.pos, ", or }", toString(c));
                }
            }
            throw expectedError(this.pos, ", or }", "eof");
        }
        throw new AssertionError();
    }

    private static ArrayData addArrayElement(ArrayData arrayData, int index, Object value) {
        long oldLength = arrayData.length();
        long longIndex = ArrayIndex.toLongIndex(index);
        ArrayData newArrayData = arrayData;
        if (longIndex >= oldLength) {
            newArrayData = newArrayData.ensure(longIndex);
            if (longIndex > oldLength) {
                newArrayData = newArrayData.delete(oldLength, longIndex - 1);
            }
        }
        return newArrayData.set(index, value, false);
    }

    private PropertyMap addObjectProperty(PropertyMap propertyMap, List<Object> values, String id, Object value) {
        int flags;
        Class<?> type;
        PropertyMap newMap;
        Property oldProperty = propertyMap.findProperty(id);
        if (this.dualFields) {
            type = getType(value);
            flags = 2048;
        } else {
            type = Object.class;
            flags = 0;
        }
        if (oldProperty != null) {
            values.set(oldProperty.getSlot(), value);
            newMap = propertyMap.replaceProperty(oldProperty, new SpillProperty(id, flags, oldProperty.getSlot(), type));
        } else {
            values.add(value);
            newMap = propertyMap.addProperty(new SpillProperty(id, flags, propertyMap.size(), type));
        }
        return newMap;
    }

    private Object createObject(PropertyMap propertyMap, List<Object> values, ArrayData arrayData) {
        Property[] properties;
        long[] primitiveSpill = this.dualFields ? new long[values.size()] : null;
        Object[] objectSpill = new Object[values.size()];
        for (Property property : propertyMap.getProperties()) {
            if (!this.dualFields || property.getType() == Object.class) {
                objectSpill[property.getSlot()] = values.get(property.getSlot());
            } else {
                primitiveSpill[property.getSlot()] = ObjectClassGenerator.pack((Number) values.get(property.getSlot()));
            }
        }
        ScriptObject object = this.dualFields ? new C1653JD(propertyMap, primitiveSpill, objectSpill) : new C1654JO(propertyMap, null, objectSpill);
        object.setInitialProto(this.global.getObjectPrototype());
        object.setArray(arrayData);
        return object;
    }

    private static Class<?> getType(Object value) {
        if (value instanceof Integer) {
            return Integer.TYPE;
        }
        if (value instanceof Double) {
            return Double.TYPE;
        }
        return Object.class;
    }

    private void expectColon() {
        skipWhiteSpace();
        int n = next();
        if (n != 58) {
            throw expectedError(this.pos - 1, CallSiteDescriptor.TOKEN_DELIMITER, toString(n));
        }
    }

    private Object parseArray() {
        ArrayData arrayData = ArrayData.EMPTY_ARRAY;
        int state = 0;
        if ($assertionsDisabled || peek() == 91) {
            this.pos++;
            while (this.pos < this.length) {
                skipWhiteSpace();
                int c = peek();
                switch (c) {
                    case 44:
                        if (state != 1) {
                            throw error(AbstractParser.message("trailing.comma.in.json", new String[0]), this.pos);
                        }
                        state = 2;
                        this.pos++;
                        break;
                    case 93:
                        if (state == 2) {
                            throw error(AbstractParser.message("trailing.comma.in.json", new String[0]), this.pos);
                        }
                        this.pos++;
                        return this.global.wrapAsObject(arrayData);
                    default:
                        if (state == 1) {
                            throw expectedError(this.pos, ", or ]", toString(c));
                        }
                        long index = arrayData.length();
                        arrayData = arrayData.ensure(index).set((int) index, parseLiteral(), true);
                        state = 1;
                        break;
                }
            }
            throw expectedError(this.pos, ", or ]", "eof");
        }
        throw new AssertionError();
    }

    private String parseString() {
        int i = this.pos + 1;
        this.pos = i;
        int start = i;
        StringBuilder sb = null;
        while (this.pos < this.length) {
            int c = next();
            if (c <= 31) {
                throw syntaxError(this.pos, "String contains control character");
            }
            if (c == 92) {
                if (sb == null) {
                    sb = new StringBuilder((this.pos - start) + 16);
                }
                sb.append((CharSequence) this.source, start, this.pos - 1);
                sb.append(parseEscapeSequence());
                start = this.pos;
            } else if (c == 34) {
                if (sb != null) {
                    sb.append((CharSequence) this.source, start, this.pos - 1);
                    return sb.toString();
                }
                return this.source.substring(start, this.pos - 1);
            }
        }
        throw error(Lexer.message("missing.close.quote", new String[0]), this.pos, this.length);
    }

    private char parseEscapeSequence() {
        int c = next();
        switch (c) {
            case 34:
                return '\"';
            case 47:
                return '/';
            case 92:
                return '\\';
            case 98:
                return '\b';
            case 102:
                return '\f';
            case 110:
                return '\n';
            case 114:
                return '\r';
            case 116:
                return '\t';
            case 117:
                return parseUnicodeEscape();
            default:
                throw error(Lexer.message("invalid.escape.char", new String[0]), this.pos - 1, this.length);
        }
    }

    private char parseUnicodeEscape() {
        return (char) ((parseHexDigit() << 12) | (parseHexDigit() << 8) | (parseHexDigit() << 4) | parseHexDigit());
    }

    private int parseHexDigit() {
        int c = next();
        if (c >= 48 && c <= 57) {
            return c - 48;
        }
        if (c >= 65 && c <= 70) {
            return (c + 10) - 65;
        }
        if (c >= 97 && c <= 102) {
            return (c + 10) - 97;
        }
        throw error(Lexer.message("invalid.hex", new String[0]), this.pos - 1, this.length);
    }

    private boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    private void skipDigits() {
        while (this.pos < this.length) {
            int c = peek();
            if (isDigit(c)) {
                this.pos++;
            } else {
                return;
            }
        }
    }

    private Number parseNumber() {
        int start = this.pos;
        int c = next();
        if (c == 45) {
            c = next();
        }
        if (!isDigit(c)) {
            throw numberError(start);
        }
        if (c != 48) {
            skipDigits();
        }
        if (peek() == 46) {
            this.pos++;
            if (!isDigit(next())) {
                throw numberError(this.pos - 1);
            }
            skipDigits();
        }
        int c2 = peek();
        if (c2 == 101 || c2 == 69) {
            this.pos++;
            int c3 = next();
            if (c3 == 45 || c3 == 43) {
                c3 = next();
            }
            if (!isDigit(c3)) {
                throw numberError(this.pos - 1);
            }
            skipDigits();
        }
        double d = Double.parseDouble(this.source.substring(start, this.pos));
        if (JSType.isRepresentableAsInt(d)) {
            return Integer.valueOf((int) d);
        }
        return Double.valueOf(d);
    }

    private Object parseKeyword(String keyword, Object value) {
        if (!this.source.regionMatches(this.pos, keyword, 0, keyword.length())) {
            throw expectedError(this.pos, "json literal", "ident");
        }
        this.pos += keyword.length();
        return value;
    }

    private int peek() {
        if (this.pos >= this.length) {
            return -1;
        }
        return this.source.charAt(this.pos);
    }

    private int next() {
        int next = peek();
        this.pos++;
        return next;
    }

    private void skipWhiteSpace() {
        while (this.pos < this.length) {
            switch (peek()) {
                case 9:
                case 10:
                case 13:
                case 32:
                    this.pos++;
                default:
                    return;
            }
        }
    }

    private static String toString(int c) {
        return c == -1 ? "eof" : String.valueOf((char) c);
    }

    ParserException error(String message, int start, int length) throws ParserException {
        long token = Token.toDesc(TokenType.STRING, start, length);
        int pos = Token.descPosition(token);
        Source src = Source.sourceFor("<json>", this.source);
        int lineNum = src.getLine(pos);
        int columnNum = src.getColumn(pos);
        String formatted = ErrorManager.format(message, src, lineNum, columnNum, token);
        return new ParserException(JSErrorType.SYNTAX_ERROR, formatted, src, lineNum, columnNum, token);
    }

    private ParserException error(String message, int start) {
        return error(message, start, this.length);
    }

    private ParserException numberError(int start) {
        return error(Lexer.message("json.invalid.number", new String[0]), start);
    }

    private ParserException expectedError(int start, String expected, String found) {
        return error(AbstractParser.message("expected", expected, found), start);
    }

    private ParserException syntaxError(int start, String reason) {
        String message = ECMAErrors.getMessage("syntax.error.invalid.json", reason);
        return error(message, start);
    }
}
