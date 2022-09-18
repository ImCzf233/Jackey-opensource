package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser.class */
public class PatternParser {
    private static final char ESCAPE_CHAR = '%';
    private static final int LITERAL_STATE = 0;
    private static final int CONVERTER_STATE = 1;
    private static final int DOT_STATE = 3;
    private static final int MIN_STATE = 4;
    private static final int MAX_STATE = 5;
    static final int FULL_LOCATION_CONVERTER = 1000;
    static final int METHOD_LOCATION_CONVERTER = 1001;
    static final int CLASS_LOCATION_CONVERTER = 1002;
    static final int LINE_LOCATION_CONVERTER = 1003;
    static final int FILE_LOCATION_CONVERTER = 1004;
    static final int RELATIVE_TIME_CONVERTER = 2000;
    static final int THREAD_CONVERTER = 2001;
    static final int LEVEL_CONVERTER = 2002;
    static final int NDC_CONVERTER = 2003;
    static final int MESSAGE_CONVERTER = 2004;
    protected int patternLength;

    /* renamed from: i */
    protected int f389i;
    PatternConverter head;
    PatternConverter tail;
    protected String pattern;
    static Class class$java$text$DateFormat;
    protected StringBuffer currentLiteral = new StringBuffer(32);
    protected FormattingInfo formattingInfo = new FormattingInfo();
    int state = 0;

    public PatternParser(String pattern) {
        this.pattern = pattern;
        this.patternLength = pattern.length();
    }

    private void addToList(PatternConverter pc) {
        if (this.head == null) {
            this.tail = pc;
            this.head = pc;
            return;
        }
        this.tail.next = pc;
        this.tail = pc;
    }

    protected String extractOption() {
        int end;
        if (this.f389i < this.patternLength && this.pattern.charAt(this.f389i) == '{' && (end = this.pattern.indexOf(125, this.f389i)) > this.f389i) {
            String r = this.pattern.substring(this.f389i + 1, end);
            this.f389i = end + 1;
            return r;
        }
        return null;
    }

    protected int extractPrecisionOption() {
        String opt = extractOption();
        int r = 0;
        if (opt != null) {
            try {
                r = Integer.parseInt(opt);
                if (r <= 0) {
                    LogLog.error(new StringBuffer().append("Precision option (").append(opt).append(") isn't a positive integer.").toString());
                    r = 0;
                }
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("Category option \"").append(opt).append("\" not a decimal integer.").toString(), e);
            }
        }
        return r;
    }

    public PatternConverter parse() {
        this.f389i = 0;
        while (this.f389i < this.patternLength) {
            String str = this.pattern;
            int i = this.f389i;
            this.f389i = i + 1;
            char c = str.charAt(i);
            switch (this.state) {
                case 0:
                    if (this.f389i == this.patternLength) {
                        this.currentLiteral.append(c);
                        break;
                    } else if (c == '%') {
                        switch (this.pattern.charAt(this.f389i)) {
                            case '%':
                                this.currentLiteral.append(c);
                                this.f389i++;
                                continue;
                            case 'n':
                                this.currentLiteral.append(Layout.LINE_SEP);
                                this.f389i++;
                                continue;
                            default:
                                if (this.currentLiteral.length() != 0) {
                                    addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
                                }
                                this.currentLiteral.setLength(0);
                                this.currentLiteral.append(c);
                                this.state = 1;
                                this.formattingInfo.reset();
                                continue;
                        }
                    } else {
                        this.currentLiteral.append(c);
                        break;
                    }
                case 1:
                    this.currentLiteral.append(c);
                    switch (c) {
                        case '-':
                            this.formattingInfo.leftAlign = true;
                            continue;
                        case '.':
                            this.state = 3;
                            continue;
                        default:
                            if (c >= '0' && c <= '9') {
                                this.formattingInfo.min = c - '0';
                                this.state = 4;
                                continue;
                            } else {
                                finalizeConverter(c);
                                break;
                            }
                            break;
                    }
                case 3:
                    this.currentLiteral.append(c);
                    if (c >= '0' && c <= '9') {
                        this.formattingInfo.max = c - '0';
                        this.state = 5;
                        break;
                    } else {
                        LogLog.error(new StringBuffer().append("Error occured in position ").append(this.f389i).append(".\n Was expecting digit, instead got char \"").append(c).append("\".").toString());
                        this.state = 0;
                        break;
                    }
                case 4:
                    this.currentLiteral.append(c);
                    if (c >= '0' && c <= '9') {
                        this.formattingInfo.min = (this.formattingInfo.min * 10) + (c - '0');
                        break;
                    } else if (c == '.') {
                        this.state = 3;
                        break;
                    } else {
                        finalizeConverter(c);
                        break;
                    }
                case 5:
                    this.currentLiteral.append(c);
                    if (c >= '0' && c <= '9') {
                        this.formattingInfo.max = (this.formattingInfo.max * 10) + (c - '0');
                        break;
                    } else {
                        finalizeConverter(c);
                        this.state = 0;
                        break;
                    }
            }
        }
        if (this.currentLiteral.length() != 0) {
            addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
        }
        return this.head;
    }

    protected void finalizeConverter(char c) {
        PatternConverter pc;
        DateFormat df;
        Class cls;
        switch (c) {
            case 'C':
                pc = new ClassNamePatternConverter(this, this.formattingInfo, extractPrecisionOption());
                this.currentLiteral.setLength(0);
                break;
            case 'D':
            case 'E':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'n':
            case 'o':
            case 'q':
            case 's':
            case 'u':
            case 'v':
            case 'w':
            default:
                LogLog.error(new StringBuffer().append("Unexpected char [").append(c).append("] at position ").append(this.f389i).append(" in conversion patterrn.").toString());
                pc = new LiteralPatternConverter(this.currentLiteral.toString());
                this.currentLiteral.setLength(0);
                break;
            case 'F':
                pc = new LocationPatternConverter(this, this.formattingInfo, FILE_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'L':
                pc = new LocationPatternConverter(this, this.formattingInfo, LINE_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'M':
                pc = new LocationPatternConverter(this, this.formattingInfo, METHOD_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'X':
                String xOpt = extractOption();
                pc = new MDCPatternConverter(this.formattingInfo, xOpt);
                this.currentLiteral.setLength(0);
                break;
            case 'c':
                pc = new CategoryPatternConverter(this, this.formattingInfo, extractPrecisionOption());
                this.currentLiteral.setLength(0);
                break;
            case 'd':
                String dateFormatStr = AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT;
                String dOpt = extractOption();
                if (dOpt != null) {
                    dateFormatStr = dOpt;
                }
                if (dateFormatStr.equalsIgnoreCase(AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT)) {
                    df = new ISO8601DateFormat();
                } else if (dateFormatStr.equalsIgnoreCase(AbsoluteTimeDateFormat.ABS_TIME_DATE_FORMAT)) {
                    df = new AbsoluteTimeDateFormat();
                } else if (dateFormatStr.equalsIgnoreCase(AbsoluteTimeDateFormat.DATE_AND_TIME_DATE_FORMAT)) {
                    df = new DateTimeDateFormat();
                } else {
                    try {
                        df = new SimpleDateFormat(dateFormatStr);
                    } catch (IllegalArgumentException e) {
                        LogLog.error(new StringBuffer().append("Could not instantiate SimpleDateFormat with ").append(dateFormatStr).toString(), e);
                        if (class$java$text$DateFormat == null) {
                            cls = class$("java.text.DateFormat");
                            class$java$text$DateFormat = cls;
                        } else {
                            cls = class$java$text$DateFormat;
                        }
                        df = (DateFormat) OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", cls, null);
                    }
                }
                pc = new DatePatternConverter(this.formattingInfo, df);
                this.currentLiteral.setLength(0);
                break;
            case 'l':
                pc = new LocationPatternConverter(this, this.formattingInfo, 1000);
                this.currentLiteral.setLength(0);
                break;
            case 'm':
                pc = new BasicPatternConverter(this.formattingInfo, MESSAGE_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'p':
                pc = new BasicPatternConverter(this.formattingInfo, LEVEL_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'r':
                pc = new BasicPatternConverter(this.formattingInfo, RELATIVE_TIME_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 't':
                pc = new BasicPatternConverter(this.formattingInfo, THREAD_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'x':
                pc = new BasicPatternConverter(this.formattingInfo, NDC_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
        }
        addConverter(pc);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    protected void addConverter(PatternConverter pc) {
        this.currentLiteral.setLength(0);
        addToList(pc);
        this.state = 0;
        this.formattingInfo.reset();
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$BasicPatternConverter.class */
    public static class BasicPatternConverter extends PatternConverter {
        int type;

        BasicPatternConverter(FormattingInfo formattingInfo, int type) {
            super(formattingInfo);
            this.type = type;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            switch (this.type) {
                case PatternParser.RELATIVE_TIME_CONVERTER /* 2000 */:
                    return Long.toString(event.timeStamp - LoggingEvent.getStartTime());
                case PatternParser.THREAD_CONVERTER /* 2001 */:
                    return event.getThreadName();
                case PatternParser.LEVEL_CONVERTER /* 2002 */:
                    return event.getLevel().toString();
                case PatternParser.NDC_CONVERTER /* 2003 */:
                    return event.getNDC();
                case PatternParser.MESSAGE_CONVERTER /* 2004 */:
                    return event.getRenderedMessage();
                default:
                    return null;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$LiteralPatternConverter.class */
    public static class LiteralPatternConverter extends PatternConverter {
        private String literal;

        LiteralPatternConverter(String value) {
            this.literal = value;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public final void format(StringBuffer sbuf, LoggingEvent event) {
            sbuf.append(this.literal);
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            return this.literal;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$DatePatternConverter.class */
    public static class DatePatternConverter extends PatternConverter {

        /* renamed from: df */
        private DateFormat f390df;
        private Date date = new Date();

        DatePatternConverter(FormattingInfo formattingInfo, DateFormat df) {
            super(formattingInfo);
            this.f390df = df;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            this.date.setTime(event.timeStamp);
            String converted = null;
            try {
                converted = this.f390df.format(this.date);
            } catch (Exception ex) {
                LogLog.error("Error occured while converting date.", ex);
            }
            return converted;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$MDCPatternConverter.class */
    public static class MDCPatternConverter extends PatternConverter {
        private String key;

        MDCPatternConverter(FormattingInfo formattingInfo, String key) {
            super(formattingInfo);
            this.key = key;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            if (this.key == null) {
                StringBuffer buf = new StringBuffer("{");
                Map properties = event.getProperties();
                if (properties.size() > 0) {
                    Object[] keys = properties.keySet().toArray();
                    Arrays.sort(keys);
                    for (int i = 0; i < keys.length; i++) {
                        buf.append('{');
                        buf.append(keys[i]);
                        buf.append(',');
                        buf.append(properties.get(keys[i]));
                        buf.append('}');
                    }
                }
                buf.append('}');
                return buf.toString();
            }
            Object val = event.getMDC(this.key);
            if (val == null) {
                return null;
            }
            return val.toString();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$LocationPatternConverter.class */
    public class LocationPatternConverter extends PatternConverter {
        int type;
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LocationPatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int type) {
            super(formattingInfo);
            this.this$0 = patternParser;
            this.type = type;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            LocationInfo locationInfo = event.getLocationInformation();
            switch (this.type) {
                case 1000:
                    return locationInfo.fullInfo;
                case PatternParser.METHOD_LOCATION_CONVERTER /* 1001 */:
                    return locationInfo.getMethodName();
                case PatternParser.CLASS_LOCATION_CONVERTER /* 1002 */:
                default:
                    return null;
                case PatternParser.LINE_LOCATION_CONVERTER /* 1003 */:
                    return locationInfo.getLineNumber();
                case PatternParser.FILE_LOCATION_CONVERTER /* 1004 */:
                    return locationInfo.getFileName();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$NamedPatternConverter.class */
    public static abstract class NamedPatternConverter extends PatternConverter {
        int precision;

        abstract String getFullyQualifiedName(LoggingEvent loggingEvent);

        NamedPatternConverter(FormattingInfo formattingInfo, int precision) {
            super(formattingInfo);
            this.precision = precision;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent event) {
            String n = getFullyQualifiedName(event);
            if (this.precision <= 0) {
                return n;
            }
            int len = n.length();
            int end = len - 1;
            for (int i = this.precision; i > 0; i--) {
                end = n.lastIndexOf(46, end - 1);
                if (end == -1) {
                    return n;
                }
            }
            return n.substring(end + 1, len);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$ClassNamePatternConverter.class */
    public class ClassNamePatternConverter extends NamedPatternConverter {
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ClassNamePatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int precision) {
            super(formattingInfo, precision);
            this.this$0 = patternParser;
        }

        @Override // org.apache.log4j.helpers.PatternParser.NamedPatternConverter
        String getFullyQualifiedName(LoggingEvent event) {
            return event.getLocationInformation().getClassName();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/PatternParser$CategoryPatternConverter.class */
    public class CategoryPatternConverter extends NamedPatternConverter {
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        CategoryPatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int precision) {
            super(formattingInfo, precision);
            this.this$0 = patternParser;
        }

        @Override // org.apache.log4j.helpers.PatternParser.NamedPatternConverter
        String getFullyQualifiedName(LoggingEvent event) {
            return event.getLoggerName();
        }
    }
}
