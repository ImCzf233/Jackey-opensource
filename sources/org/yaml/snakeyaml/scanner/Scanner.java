package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.tokens.Token;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/scanner/Scanner.class */
public interface Scanner {
    boolean checkToken(Token.EnumC1822ID... enumC1822IDArr);

    Token peekToken();

    Token getToken();
}
