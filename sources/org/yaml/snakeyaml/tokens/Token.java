package org.yaml.snakeyaml.tokens;

import jdk.internal.dynalink.CallSiteDescriptor;
import org.apache.log4j.spi.LocationInfo;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/tokens/Token.class */
public abstract class Token {
    private final Mark startMark;
    private final Mark endMark;

    public abstract EnumC1822ID getTokenId();

    /* renamed from: org.yaml.snakeyaml.tokens.Token$ID */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/tokens/Token$ID.class */
    public enum EnumC1822ID {
        Alias("<alias>"),
        Anchor("<anchor>"),
        BlockEnd("<block end>"),
        BlockEntry("-"),
        BlockMappingStart("<block mapping start>"),
        BlockSequenceStart("<block sequence start>"),
        Directive("<directive>"),
        DocumentEnd("<document end>"),
        DocumentStart("<document start>"),
        FlowEntry(","),
        FlowMappingEnd("}"),
        FlowMappingStart("{"),
        FlowSequenceEnd("]"),
        FlowSequenceStart("["),
        Key(LocationInfo.f402NA),
        Scalar("<scalar>"),
        StreamEnd("<stream end>"),
        StreamStart("<stream start>"),
        Tag("<tag>"),
        Value(CallSiteDescriptor.TOKEN_DELIMITER),
        Whitespace("<whitespace>"),
        Comment("#"),
        Error("<error>");
        
        private final String description;

        EnumC1822ID(String s) {
            this.description = s;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    public Token(Mark startMark, Mark endMark) {
        if (startMark == null || endMark == null) {
            throw new YAMLException("Token requires marks.");
        }
        this.startMark = startMark;
        this.endMark = endMark;
    }

    public Mark getStartMark() {
        return this.startMark;
    }

    public Mark getEndMark() {
        return this.endMark;
    }
}
