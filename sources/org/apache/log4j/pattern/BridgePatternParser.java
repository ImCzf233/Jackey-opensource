package org.apache.log4j.pattern;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/BridgePatternParser.class */
public final class BridgePatternParser extends org.apache.log4j.helpers.PatternParser {
    public BridgePatternParser(String conversionPattern) {
        super(conversionPattern);
    }

    @Override // org.apache.log4j.helpers.PatternParser
    public org.apache.log4j.helpers.PatternConverter parse() {
        return new BridgePatternConverter(this.pattern);
    }
}
