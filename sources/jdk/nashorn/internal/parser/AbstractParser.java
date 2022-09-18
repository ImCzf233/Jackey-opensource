package jdk.nashorn.internal.parser;

import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/parser/AbstractParser.class */
public abstract class AbstractParser {
    protected final Source source;
    protected final ErrorManager errors;
    protected TokenStream stream;
    protected long previousToken;
    protected int start;
    protected int finish;
    protected int line;
    protected int linePosition;
    protected Lexer lexer;
    protected boolean isStrictMode;
    protected final int lineOffset;
    private static final String SOURCE_URL_PREFIX = "sourceURL=";
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Map<String, String> canonicalNames = new HashMap();

    /* renamed from: k */
    protected int f254k = -1;
    protected long token = Token.toDesc(TokenType.EOL, 0, 1);
    protected TokenType type = TokenType.EOL;
    protected TokenType last = TokenType.EOL;

    static {
        $assertionsDisabled = !AbstractParser.class.desiredAssertionStatus();
    }

    public AbstractParser(Source source, ErrorManager errors, boolean strict, int lineOffset) {
        this.source = source;
        this.errors = errors;
        this.isStrictMode = strict;
        this.lineOffset = lineOffset;
    }

    protected final long getToken(int i) {
        while (i > this.stream.last()) {
            if (this.stream.isFull()) {
                this.stream.grow();
            }
            this.lexer.lexify();
        }
        return this.stream.get(i);
    }

    /* renamed from: T */
    public final TokenType m68T(int i) {
        return Token.descType(getToken(i));
    }

    public final TokenType next() {
        while (true) {
            nextOrEOL();
            if (this.type != TokenType.EOL && this.type != TokenType.COMMENT) {
                return this.type;
            }
        }
    }

    public final TokenType nextOrEOL() {
        while (true) {
            nextToken();
            if (this.type == TokenType.DIRECTIVE_COMMENT) {
                checkDirectiveComment();
            }
            if (this.type != TokenType.COMMENT && this.type != TokenType.DIRECTIVE_COMMENT) {
                return this.type;
            }
        }
    }

    private void checkDirectiveComment() {
        if (this.source.getExplicitURL() != null) {
            return;
        }
        String comment = (String) this.lexer.getValueOf(this.token, this.isStrictMode);
        int len = comment.length();
        if (len > 4 && comment.substring(4).startsWith(SOURCE_URL_PREFIX)) {
            this.source.setExplicitURL(comment.substring(4 + SOURCE_URL_PREFIX.length()));
        }
    }

    private TokenType nextToken() {
        if (this.type != TokenType.COMMENT) {
            this.last = this.type;
        }
        if (this.type != TokenType.EOF) {
            this.f254k++;
            long lastToken = this.token;
            this.previousToken = this.token;
            this.token = getToken(this.f254k);
            this.type = Token.descType(this.token);
            if (this.last != TokenType.EOL) {
                this.finish = this.start + Token.descLength(lastToken);
            }
            if (this.type == TokenType.EOL) {
                this.line = Token.descLength(this.token);
                this.linePosition = Token.descPosition(this.token);
            } else {
                this.start = Token.descPosition(this.token);
            }
        }
        return this.type;
    }

    public static String message(String msgId, String... args) {
        return ECMAErrors.getMessage("parser.error." + msgId, args);
    }

    public final ParserException error(String message, long errorToken) {
        return error(JSErrorType.SYNTAX_ERROR, message, errorToken);
    }

    public final ParserException error(JSErrorType errorType, String message, long errorToken) {
        int position = Token.descPosition(errorToken);
        int lineNum = this.source.getLine(position);
        int columnNum = this.source.getColumn(position);
        String formatted = ErrorManager.format(message, this.source, lineNum, columnNum, errorToken);
        return new ParserException(errorType, formatted, this.source, lineNum, columnNum, errorToken);
    }

    public final ParserException error(String message) {
        return error(JSErrorType.SYNTAX_ERROR, message);
    }

    protected final ParserException error(JSErrorType errorType, String message) {
        int position = Token.descPosition(this.token);
        int column = position - this.linePosition;
        String formatted = ErrorManager.format(message, this.source, this.line, column, this.token);
        return new ParserException(errorType, formatted, this.source, this.line, column, this.token);
    }

    public final void warning(JSErrorType errorType, String message, long errorToken) {
        this.errors.warning(error(errorType, message, errorToken));
    }

    protected final String expectMessage(TokenType expected) {
        String msg;
        String tokenString = Token.toString(this.source, this.token);
        if (expected == null) {
            msg = message("expected.stmt", tokenString);
        } else {
            String expectedName = expected.getNameOrType();
            msg = message("expected", expectedName, tokenString);
        }
        return msg;
    }

    public final void expect(TokenType expected) throws ParserException {
        expectDontAdvance(expected);
        next();
    }

    public final void expectDontAdvance(TokenType expected) throws ParserException {
        if (this.type != expected) {
            throw error(expectMessage(expected));
        }
    }

    public final Object expectValue(TokenType expected) throws ParserException {
        if (this.type != expected) {
            throw error(expectMessage(expected));
        }
        Object value = getValue();
        next();
        return value;
    }

    public final Object getValue() {
        return getValue(this.token);
    }

    public final Object getValue(long valueToken) {
        try {
            return this.lexer.getValueOf(valueToken, this.isStrictMode);
        } catch (ParserException e) {
            this.errors.error(e);
            return null;
        }
    }

    public final boolean isNonStrictModeIdent() {
        return !this.isStrictMode && this.type.getKind() == TokenKind.FUTURESTRICT;
    }

    public final IdentNode getIdent() {
        long identToken = this.token;
        if (isNonStrictModeIdent()) {
            long identToken2 = Token.recast(this.token, TokenType.IDENT);
            next();
            return createIdentNode(identToken2, this.finish, (String) getValue(identToken2)).setIsFutureStrictName();
        }
        String ident = (String) expectValue(TokenType.IDENT);
        if (ident == null) {
            return null;
        }
        return createIdentNode(identToken, this.finish, ident);
    }

    public IdentNode createIdentNode(long identToken, int identFinish, String name) {
        String existingName = this.canonicalNames.putIfAbsent(name, name);
        String canonicalName = existingName != null ? existingName : name;
        return new IdentNode(identToken, identFinish, canonicalName);
    }

    protected final boolean isIdentifierName() {
        TokenKind kind = this.type.getKind();
        if (kind == TokenKind.KEYWORD || kind == TokenKind.FUTURE || kind == TokenKind.FUTURESTRICT) {
            return true;
        }
        if (kind == TokenKind.LITERAL) {
            switch (this.type) {
                case FALSE:
                case NULL:
                case TRUE:
                    return true;
                default:
                    return false;
            }
        }
        long identToken = Token.recast(this.token, TokenType.IDENT);
        String ident = (String) getValue(identToken);
        return !ident.isEmpty() && Character.isJavaIdentifierStart(ident.charAt(0));
    }

    public final IdentNode getIdentifierName() {
        if (this.type == TokenType.IDENT) {
            return getIdent();
        }
        if (isIdentifierName()) {
            long identToken = Token.recast(this.token, TokenType.IDENT);
            String ident = (String) getValue(identToken);
            next();
            return createIdentNode(identToken, this.finish, ident);
        }
        expect(TokenType.IDENT);
        return null;
    }

    public final LiteralNode<?> getLiteral() throws ParserException {
        long literalToken = this.token;
        Object value = getValue();
        next();
        LiteralNode<?> node = null;
        if (value == null) {
            node = LiteralNode.newInstance(literalToken, this.finish);
        } else if (value instanceof Number) {
            node = LiteralNode.newInstance(literalToken, this.finish, (Number) value);
        } else if (value instanceof String) {
            node = LiteralNode.newInstance(literalToken, this.finish, (String) value);
        } else if (value instanceof Lexer.LexerToken) {
            if (value instanceof Lexer.RegexToken) {
                Lexer.RegexToken regex = (Lexer.RegexToken) value;
                try {
                    RegExpFactory.validate(regex.getExpression(), regex.getOptions());
                } catch (ParserException e) {
                    throw error(e.getMessage());
                }
            }
            node = LiteralNode.newInstance(literalToken, this.finish, (Lexer.LexerToken) value);
        } else if (!$assertionsDisabled) {
            throw new AssertionError("unknown type for LiteralNode: " + value.getClass());
        }
        return node;
    }
}
