package org.apache.log4j.pattern;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/PatternConverter.class */
public abstract class PatternConverter {
    private final String name;
    private final String style;

    public abstract void format(Object obj, StringBuffer stringBuffer);

    public PatternConverter(String name, String style) {
        this.name = name;
        this.style = style;
    }

    public final String getName() {
        return this.name;
    }

    public String getStyleClass(Object e) {
        return this.style;
    }
}
