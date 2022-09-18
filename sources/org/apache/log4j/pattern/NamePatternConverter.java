package org.apache.log4j.pattern;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/pattern/NamePatternConverter.class */
public abstract class NamePatternConverter extends LoggingEventPatternConverter {
    private final NameAbbreviator abbreviator;

    public NamePatternConverter(String name, String style, String[] options) {
        super(name, style);
        if (options != null && options.length > 0) {
            this.abbreviator = NameAbbreviator.getAbbreviator(options[0]);
        } else {
            this.abbreviator = NameAbbreviator.getDefaultAbbreviator();
        }
    }

    public final void abbreviate(int nameStart, StringBuffer buf) {
        this.abbreviator.abbreviate(nameStart, buf);
    }
}
