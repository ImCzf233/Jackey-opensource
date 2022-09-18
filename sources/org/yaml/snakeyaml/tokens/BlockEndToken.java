package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.tokens.Token;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/tokens/BlockEndToken.class */
public final class BlockEndToken extends Token {
    public BlockEndToken(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }

    @Override // org.yaml.snakeyaml.tokens.Token
    public Token.EnumC1822ID getTokenId() {
        return Token.EnumC1822ID.BlockEnd;
    }
}
