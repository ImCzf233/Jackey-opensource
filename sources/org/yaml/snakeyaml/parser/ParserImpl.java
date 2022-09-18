package org.yaml.snakeyaml.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.ImplicitTuple;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.TagTuple;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.util.ArrayStack;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl.class */
public class ParserImpl implements Parser {
    private static final Map<String, String> DEFAULT_TAGS = new HashMap();
    protected final Scanner scanner;
    private Event currentEvent;
    private final ArrayStack<Production> states;
    private final ArrayStack<Mark> marks;
    private Production state;
    private VersionTagsTuple directives;

    static {
        DEFAULT_TAGS.put("!", "!");
        DEFAULT_TAGS.put("!!", Tag.PREFIX);
    }

    public ParserImpl(StreamReader reader) {
        this(new ScannerImpl(reader));
    }

    public ParserImpl(StreamReader reader, boolean emitComments) {
        this(new ScannerImpl(reader).setEmitComments(emitComments));
    }

    public ParserImpl(Scanner scanner) {
        this.scanner = scanner;
        this.currentEvent = null;
        this.directives = new VersionTagsTuple(null, new HashMap(DEFAULT_TAGS));
        this.states = new ArrayStack<>(100);
        this.marks = new ArrayStack<>(10);
        this.state = new ParseStreamStart();
    }

    @Override // org.yaml.snakeyaml.parser.Parser
    public boolean checkEvent(Event.EnumC1814ID choice) {
        peekEvent();
        return this.currentEvent != null && this.currentEvent.m0is(choice);
    }

    @Override // org.yaml.snakeyaml.parser.Parser
    public Event peekEvent() {
        if (this.currentEvent == null && this.state != null) {
            this.currentEvent = this.state.produce();
        }
        return this.currentEvent;
    }

    @Override // org.yaml.snakeyaml.parser.Parser
    public Event getEvent() {
        peekEvent();
        Event value = this.currentEvent;
        this.currentEvent = null;
        return value;
    }

    public CommentEvent produceCommentEvent(CommentToken token) {
        Mark startMark = token.getStartMark();
        Mark endMark = token.getEndMark();
        String value = token.getValue();
        CommentType type = token.getCommentType();
        return new CommentEvent(type, value, startMark, endMark);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseStreamStart.class */
    public class ParseStreamStart implements Production {
        private ParseStreamStart() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            StreamStartToken token = (StreamStartToken) ParserImpl.this.scanner.getToken();
            Event event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.state = new ParseImplicitDocumentStart();
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseImplicitDocumentStart.class */
    private class ParseImplicitDocumentStart implements Production {
        private ParseImplicitDocumentStart() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Directive, Token.EnumC1822ID.DocumentStart, Token.EnumC1822ID.StreamEnd)) {
                ParserImpl.this.directives = new VersionTagsTuple(null, ParserImpl.DEFAULT_TAGS);
                Token token = ParserImpl.this.scanner.peekToken();
                Mark startMark = token.getStartMark();
                Event event = new DocumentStartEvent(startMark, startMark, false, null, null);
                ParserImpl.this.states.push(new ParseDocumentEnd());
                ParserImpl.this.state = new ParseBlockNode();
                return event;
            }
            Production p = new ParseDocumentStart();
            return p.produce();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseDocumentStart.class */
    private class ParseDocumentStart implements Production {
        private ParseDocumentStart() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            while (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.DocumentEnd)) {
                ParserImpl.this.scanner.getToken();
            }
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.StreamEnd)) {
                Mark startMark = ParserImpl.this.scanner.peekToken().getStartMark();
                VersionTagsTuple tuple = ParserImpl.this.processDirectives();
                while (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                    ParserImpl.this.scanner.getToken();
                }
                if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.StreamEnd)) {
                    if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.DocumentStart)) {
                        throw new ParserException(null, null, "expected '<document start>', but found '" + ParserImpl.this.scanner.peekToken().getTokenId() + "'", ParserImpl.this.scanner.peekToken().getStartMark());
                    }
                    Mark endMark = ParserImpl.this.scanner.getToken().getEndMark();
                    Event event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
                    ParserImpl.this.states.push(new ParseDocumentEnd());
                    ParserImpl.this.state = new ParseDocumentContent();
                    return event;
                }
            }
            StreamEndToken token = (StreamEndToken) ParserImpl.this.scanner.getToken();
            Event event2 = new StreamEndEvent(token.getStartMark(), token.getEndMark());
            if (ParserImpl.this.states.isEmpty()) {
                if (ParserImpl.this.marks.isEmpty()) {
                    ParserImpl.this.state = null;
                    return event2;
                }
                throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
            }
            throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseDocumentEnd.class */
    private class ParseDocumentEnd implements Production {
        private ParseDocumentEnd() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.peekToken();
            Mark startMark = token.getStartMark();
            Mark endMark = startMark;
            boolean explicit = false;
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.DocumentEnd)) {
                Token token2 = ParserImpl.this.scanner.getToken();
                endMark = token2.getEndMark();
                explicit = true;
            }
            Event event = new DocumentEndEvent(startMark, endMark, explicit);
            ParserImpl.this.state = new ParseDocumentStart();
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseDocumentContent.class */
    private class ParseDocumentContent implements Production {
        private ParseDocumentContent() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Directive, Token.EnumC1822ID.DocumentStart, Token.EnumC1822ID.DocumentEnd, Token.EnumC1822ID.StreamEnd)) {
                Event event = ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
                ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
                return event;
            }
            Production p = new ParseBlockNode();
            return p.produce();
        }
    }

    public VersionTagsTuple processDirectives() {
        DumperOptions.Version yamlVersion = null;
        HashMap<String, String> tagHandles = new HashMap<>();
        while (this.scanner.checkToken(Token.EnumC1822ID.Directive)) {
            DirectiveToken token = (DirectiveToken) this.scanner.getToken();
            if (token.getName().equals("YAML")) {
                if (yamlVersion != null) {
                    throw new ParserException(null, null, "found duplicate YAML directive", token.getStartMark());
                }
                List<Integer> value = token.getValue();
                Integer major = value.get(0);
                if (major.intValue() != 1) {
                    throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token.getStartMark());
                }
                Integer minor = value.get(1);
                switch (minor.intValue()) {
                    case 0:
                        yamlVersion = DumperOptions.Version.V1_0;
                        continue;
                    default:
                        yamlVersion = DumperOptions.Version.V1_1;
                        continue;
                }
            } else if (token.getName().equals("TAG")) {
                List<String> value2 = token.getValue();
                String handle = value2.get(0);
                String prefix = value2.get(1);
                if (tagHandles.containsKey(handle)) {
                    throw new ParserException(null, null, "duplicate tag handle " + handle, token.getStartMark());
                }
                tagHandles.put(handle, prefix);
            } else {
                continue;
            }
        }
        if (yamlVersion != null || !tagHandles.isEmpty()) {
            for (String key : DEFAULT_TAGS.keySet()) {
                if (!tagHandles.containsKey(key)) {
                    tagHandles.put(key, DEFAULT_TAGS.get(key));
                }
            }
            this.directives = new VersionTagsTuple(yamlVersion, tagHandles);
        }
        return this.directives;
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockNode.class */
    public class ParseBlockNode implements Production {
        private ParseBlockNode() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            return ParserImpl.this.parseNode(true, false);
        }
    }

    public Event parseFlowNode() {
        return parseNode(false, false);
    }

    public Event parseBlockNodeOrIndentlessSequence() {
        return parseNode(true, true);
    }

    public Event parseNode(boolean block, boolean indentlessSequence) {
        Event event;
        String node;
        Token token;
        ImplicitTuple implicitValues;
        Mark startMark = null;
        Mark endMark = null;
        Mark tagMark = null;
        if (this.scanner.checkToken(Token.EnumC1822ID.Alias)) {
            AliasToken token2 = (AliasToken) this.scanner.getToken();
            event = new AliasEvent(token2.getValue(), token2.getStartMark(), token2.getEndMark());
            this.state = this.states.pop();
        } else {
            String anchor = null;
            TagTuple tagTokenTag = null;
            if (this.scanner.checkToken(Token.EnumC1822ID.Anchor)) {
                AnchorToken token3 = (AnchorToken) this.scanner.getToken();
                startMark = token3.getStartMark();
                endMark = token3.getEndMark();
                anchor = token3.getValue();
                if (this.scanner.checkToken(Token.EnumC1822ID.Tag)) {
                    TagToken tagToken = (TagToken) this.scanner.getToken();
                    tagMark = tagToken.getStartMark();
                    endMark = tagToken.getEndMark();
                    tagTokenTag = tagToken.getValue();
                }
            } else if (this.scanner.checkToken(Token.EnumC1822ID.Tag)) {
                TagToken tagToken2 = (TagToken) this.scanner.getToken();
                startMark = tagToken2.getStartMark();
                tagMark = startMark;
                endMark = tagToken2.getEndMark();
                tagTokenTag = tagToken2.getValue();
                if (this.scanner.checkToken(Token.EnumC1822ID.Anchor)) {
                    AnchorToken token4 = (AnchorToken) this.scanner.getToken();
                    endMark = token4.getEndMark();
                    anchor = token4.getValue();
                }
            }
            String tag = null;
            if (tagTokenTag != null) {
                String handle = tagTokenTag.getHandle();
                String suffix = tagTokenTag.getSuffix();
                if (handle != null) {
                    if (!this.directives.getTags().containsKey(handle)) {
                        throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
                    }
                    tag = this.directives.getTags().get(handle) + suffix;
                } else {
                    tag = suffix;
                }
            }
            if (startMark == null) {
                startMark = this.scanner.peekToken().getStartMark();
                endMark = startMark;
            }
            boolean implicit = tag == null || tag.equals("!");
            if (indentlessSequence && this.scanner.checkToken(Token.EnumC1822ID.BlockEntry)) {
                Mark endMark2 = this.scanner.peekToken().getEndMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark2, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseIndentlessSequenceEntryKey();
            } else if (this.scanner.checkToken(Token.EnumC1822ID.Scalar)) {
                ScalarToken token5 = (ScalarToken) this.scanner.getToken();
                Mark endMark3 = token5.getEndMark();
                if ((token5.getPlain() && tag == null) || "!".equals(tag)) {
                    implicitValues = new ImplicitTuple(true, false);
                } else if (tag == null) {
                    implicitValues = new ImplicitTuple(false, true);
                } else {
                    implicitValues = new ImplicitTuple(false, false);
                }
                event = new ScalarEvent(anchor, tag, implicitValues, token5.getValue(), startMark, endMark3, token5.getStyle());
                this.state = this.states.pop();
            } else if (this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                event = produceCommentEvent((CommentToken) this.scanner.getToken());
            } else if (this.scanner.checkToken(Token.EnumC1822ID.FlowSequenceStart)) {
                Mark endMark4 = this.scanner.peekToken().getEndMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark4, DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowSequenceFirstEntry();
            } else if (this.scanner.checkToken(Token.EnumC1822ID.FlowMappingStart)) {
                Mark endMark5 = this.scanner.peekToken().getEndMark();
                event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark5, DumperOptions.FlowStyle.FLOW);
                this.state = new ParseFlowMappingFirstKey();
            } else if (block && this.scanner.checkToken(Token.EnumC1822ID.BlockSequenceStart)) {
                Mark endMark6 = this.scanner.peekToken().getStartMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark6, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockSequenceFirstEntry();
            } else if (block && this.scanner.checkToken(Token.EnumC1822ID.BlockMappingStart)) {
                Mark endMark7 = this.scanner.peekToken().getStartMark();
                event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark7, DumperOptions.FlowStyle.BLOCK);
                this.state = new ParseBlockMappingFirstKey();
            } else if (anchor != null || tag != null) {
                event = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, DumperOptions.ScalarStyle.PLAIN);
                this.state = this.states.pop();
            } else {
                if (block) {
                    node = "block";
                } else {
                    node = "flow";
                }
                throw new ParserException("while parsing a " + node + " node", startMark, "expected the node content, but found '" + token.getTokenId() + "'", this.scanner.peekToken().getStartMark());
            }
        }
        return event;
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockSequenceFirstEntry.class */
    public class ParseBlockSequenceFirstEntry implements Production {
        private ParseBlockSequenceFirstEntry() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.getToken();
            ParserImpl.this.marks.push(token.getStartMark());
            return new ParseBlockSequenceEntryKey().produce();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockSequenceEntryKey.class */
    public class ParseBlockSequenceEntryKey implements Production {
        private ParseBlockSequenceEntryKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEntry)) {
                return new ParseBlockSequenceEntryValue((BlockEntryToken) ParserImpl.this.scanner.getToken()).produce();
            }
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEnd)) {
                Token token = ParserImpl.this.scanner.peekToken();
                throw new ParserException("while parsing a block collection", (Mark) ParserImpl.this.marks.pop(), "expected <block end>, but found '" + token.getTokenId() + "'", token.getStartMark());
            }
            Token token2 = ParserImpl.this.scanner.getToken();
            Event event = new SequenceEndEvent(token2.getStartMark(), token2.getEndMark());
            ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
            ParserImpl.this.marks.pop();
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockSequenceEntryValue.class */
    public class ParseBlockSequenceEntryValue implements Production {
        BlockEntryToken token;

        public ParseBlockSequenceEntryValue(BlockEntryToken token) {
            ParserImpl.this = r4;
            this.token = token;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = new ParseBlockSequenceEntryValue(this.token);
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEntry, Token.EnumC1822ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParseBlockSequenceEntryKey());
                return new ParseBlockNode().produce();
            } else {
                ParserImpl.this.state = new ParseBlockSequenceEntryKey();
                return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseIndentlessSequenceEntryKey.class */
    public class ParseIndentlessSequenceEntryKey implements Production {
        private ParseIndentlessSequenceEntryKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEntry)) {
                return new ParseIndentlessSequenceEntryValue((BlockEntryToken) ParserImpl.this.scanner.getToken()).produce();
            }
            Token token = ParserImpl.this.scanner.peekToken();
            Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseIndentlessSequenceEntryValue.class */
    private class ParseIndentlessSequenceEntryValue implements Production {
        BlockEntryToken token;

        public ParseIndentlessSequenceEntryValue(BlockEntryToken token) {
            ParserImpl.this = r4;
            this.token = token;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = new ParseIndentlessSequenceEntryValue(this.token);
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEntry, Token.EnumC1822ID.Key, Token.EnumC1822ID.Value, Token.EnumC1822ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParseIndentlessSequenceEntryKey());
                return new ParseBlockNode().produce();
            } else {
                ParserImpl.this.state = new ParseIndentlessSequenceEntryKey();
                return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockMappingFirstKey.class */
    public class ParseBlockMappingFirstKey implements Production {
        private ParseBlockMappingFirstKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.getToken();
            ParserImpl.this.marks.push(token.getStartMark());
            return new ParseBlockMappingKey().produce();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockMappingKey.class */
    private class ParseBlockMappingKey implements Production {
        private ParseBlockMappingKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            }
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key)) {
                Token token = ParserImpl.this.scanner.getToken();
                if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key, Token.EnumC1822ID.Value, Token.EnumC1822ID.BlockEnd)) {
                    ParserImpl.this.states.push(new ParseBlockMappingValue());
                    return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
                }
                ParserImpl.this.state = new ParseBlockMappingValue();
                return ParserImpl.this.processEmptyScalar(token.getEndMark());
            } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.BlockEnd)) {
                Token token2 = ParserImpl.this.scanner.peekToken();
                throw new ParserException("while parsing a block mapping", (Mark) ParserImpl.this.marks.pop(), "expected <block end>, but found '" + token2.getTokenId() + "'", token2.getStartMark());
            } else {
                Token token3 = ParserImpl.this.scanner.getToken();
                Event event = new MappingEndEvent(token3.getStartMark(), token3.getEndMark());
                ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
                ParserImpl.this.marks.pop();
                return event;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockMappingValue.class */
    public class ParseBlockMappingValue implements Production {
        private ParseBlockMappingValue() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Value)) {
                if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Scalar)) {
                    ParserImpl.this.states.push(new ParseBlockMappingKey());
                    return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
                }
                ParserImpl.this.state = new ParseBlockMappingKey();
                Token token = ParserImpl.this.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            Token token2 = ParserImpl.this.scanner.getToken();
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = new ParseBlockMappingValueComment();
                return ParserImpl.this.state.produce();
            } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key, Token.EnumC1822ID.Value, Token.EnumC1822ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParseBlockMappingKey());
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            } else {
                ParserImpl.this.state = new ParseBlockMappingKey();
                return ParserImpl.this.processEmptyScalar(token2.getEndMark());
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseBlockMappingValueComment.class */
    private class ParseBlockMappingValueComment implements Production {
        private ParseBlockMappingValueComment() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key, Token.EnumC1822ID.Value, Token.EnumC1822ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParseBlockMappingKey());
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
            ParserImpl.this.state = new ParseBlockMappingKey();
            Token token = ParserImpl.this.scanner.getToken();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowSequenceFirstEntry.class */
    public class ParseFlowSequenceFirstEntry implements Production {
        private ParseFlowSequenceFirstEntry() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.getToken();
            ParserImpl.this.marks.push(token.getStartMark());
            return new ParseFlowSequenceEntry(true).produce();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowSequenceEntry.class */
    private class ParseFlowSequenceEntry implements Production {
        private boolean first;

        public ParseFlowSequenceEntry(boolean first) {
            ParserImpl.this = r4;
            this.first = false;
            this.first = first;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowSequenceEnd)) {
                if (!this.first) {
                    if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowEntry)) {
                        ParserImpl.this.scanner.getToken();
                    } else {
                        Token token = ParserImpl.this.scanner.peekToken();
                        throw new ParserException("while parsing a flow sequence", (Mark) ParserImpl.this.marks.pop(), "expected ',' or ']', but got " + token.getTokenId(), token.getStartMark());
                    }
                }
                if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key)) {
                    Token token2 = ParserImpl.this.scanner.peekToken();
                    Event event = new MappingStartEvent((String) null, (String) null, true, token2.getStartMark(), token2.getEndMark(), DumperOptions.FlowStyle.FLOW);
                    ParserImpl.this.state = new ParseFlowSequenceEntryMappingKey();
                    return event;
                } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowSequenceEnd)) {
                    ParserImpl.this.states.push(new ParseFlowSequenceEntry(false));
                    return ParserImpl.this.parseFlowNode();
                }
            }
            Token token3 = ParserImpl.this.scanner.getToken();
            Event event2 = new SequenceEndEvent(token3.getStartMark(), token3.getEndMark());
            ParserImpl.this.marks.pop();
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
            } else {
                ParserImpl.this.state = new ParseFlowEndComment();
            }
            return event2;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowEndComment.class */
    public class ParseFlowEndComment implements Production {
        private ParseFlowEndComment() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Event event = ParserImpl.this.produceCommentEvent((CommentToken) ParserImpl.this.scanner.getToken());
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
            }
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowSequenceEntryMappingKey.class */
    public class ParseFlowSequenceEntryMappingKey implements Production {
        private ParseFlowSequenceEntryMappingKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Value, Token.EnumC1822ID.FlowEntry, Token.EnumC1822ID.FlowSequenceEnd)) {
                ParserImpl.this.states.push(new ParseFlowSequenceEntryMappingValue());
                return ParserImpl.this.parseFlowNode();
            }
            ParserImpl.this.state = new ParseFlowSequenceEntryMappingValue();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowSequenceEntryMappingValue.class */
    private class ParseFlowSequenceEntryMappingValue implements Production {
        private ParseFlowSequenceEntryMappingValue() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Value)) {
                ParserImpl.this.state = new ParseFlowSequenceEntryMappingEnd();
                Token token = ParserImpl.this.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            Token token2 = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowEntry, Token.EnumC1822ID.FlowSequenceEnd)) {
                ParserImpl.this.states.push(new ParseFlowSequenceEntryMappingEnd());
                return ParserImpl.this.parseFlowNode();
            }
            ParserImpl.this.state = new ParseFlowSequenceEntryMappingEnd();
            return ParserImpl.this.processEmptyScalar(token2.getEndMark());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowSequenceEntryMappingEnd.class */
    private class ParseFlowSequenceEntryMappingEnd implements Production {
        private ParseFlowSequenceEntryMappingEnd() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            ParserImpl.this.state = new ParseFlowSequenceEntry(false);
            Token token = ParserImpl.this.scanner.peekToken();
            return new MappingEndEvent(token.getStartMark(), token.getEndMark());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowMappingFirstKey.class */
    public class ParseFlowMappingFirstKey implements Production {
        private ParseFlowMappingFirstKey() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            Token token = ParserImpl.this.scanner.getToken();
            ParserImpl.this.marks.push(token.getStartMark());
            return new ParseFlowMappingKey(true).produce();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowMappingKey.class */
    private class ParseFlowMappingKey implements Production {
        private boolean first;

        public ParseFlowMappingKey(boolean first) {
            ParserImpl.this = r4;
            this.first = false;
            this.first = first;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowMappingEnd)) {
                if (!this.first) {
                    if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowEntry)) {
                        ParserImpl.this.scanner.getToken();
                    } else {
                        Token token = ParserImpl.this.scanner.peekToken();
                        throw new ParserException("while parsing a flow mapping", (Mark) ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark());
                    }
                }
                if (ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Key)) {
                    Token token2 = ParserImpl.this.scanner.getToken();
                    if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Value, Token.EnumC1822ID.FlowEntry, Token.EnumC1822ID.FlowMappingEnd)) {
                        ParserImpl.this.states.push(new ParseFlowMappingValue());
                        return ParserImpl.this.parseFlowNode();
                    }
                    ParserImpl.this.state = new ParseFlowMappingValue();
                    return ParserImpl.this.processEmptyScalar(token2.getEndMark());
                } else if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowMappingEnd)) {
                    ParserImpl.this.states.push(new ParseFlowMappingEmptyValue());
                    return ParserImpl.this.parseFlowNode();
                }
            }
            Token token3 = ParserImpl.this.scanner.getToken();
            Event event = new MappingEndEvent(token3.getStartMark(), token3.getEndMark());
            ParserImpl.this.marks.pop();
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Comment)) {
                ParserImpl.this.state = (Production) ParserImpl.this.states.pop();
            } else {
                ParserImpl.this.state = new ParseFlowEndComment();
            }
            return event;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowMappingValue.class */
    public class ParseFlowMappingValue implements Production {
        private ParseFlowMappingValue() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.Value)) {
                ParserImpl.this.state = new ParseFlowMappingKey(false);
                Token token = ParserImpl.this.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            Token token2 = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.EnumC1822ID.FlowEntry, Token.EnumC1822ID.FlowMappingEnd)) {
                ParserImpl.this.states.push(new ParseFlowMappingKey(false));
                return ParserImpl.this.parseFlowNode();
            }
            ParserImpl.this.state = new ParseFlowMappingKey(false);
            return ParserImpl.this.processEmptyScalar(token2.getEndMark());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/ParserImpl$ParseFlowMappingEmptyValue.class */
    public class ParseFlowMappingEmptyValue implements Production {
        private ParseFlowMappingEmptyValue() {
            ParserImpl.this = r4;
        }

        @Override // org.yaml.snakeyaml.parser.Production
        public Event produce() {
            ParserImpl.this.state = new ParseFlowMappingKey(false);
            return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
        }
    }

    public Event processEmptyScalar(Mark mark) {
        return new ScalarEvent((String) null, (String) null, new ImplicitTuple(true, false), "", mark, mark, DumperOptions.ScalarStyle.PLAIN);
    }
}
