package org.apache.log4j.pattern;

import java.util.Date;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/IntegerPatternConverter.class */
public final class IntegerPatternConverter extends PatternConverter {
    private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();

    private IntegerPatternConverter() {
        super("Integer", "integer");
    }

    public static IntegerPatternConverter newInstance(String[] options) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer toAppendTo) {
        if (obj instanceof Integer) {
            toAppendTo.append(obj.toString());
        }
        if (obj instanceof Date) {
            toAppendTo.append(Long.toString(((Date) obj).getTime()));
        }
    }
}
