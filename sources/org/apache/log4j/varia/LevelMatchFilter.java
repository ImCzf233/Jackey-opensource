package org.apache.log4j.varia;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/varia/LevelMatchFilter.class */
public class LevelMatchFilter extends Filter {
    boolean acceptOnMatch = true;
    Level levelToMatch;

    public void setLevelToMatch(String level) {
        this.levelToMatch = OptionConverter.toLevel(level, null);
    }

    public String getLevelToMatch() {
        if (this.levelToMatch == null) {
            return null;
        }
        return this.levelToMatch.toString();
    }

    public void setAcceptOnMatch(boolean acceptOnMatch) {
        this.acceptOnMatch = acceptOnMatch;
    }

    public boolean getAcceptOnMatch() {
        return this.acceptOnMatch;
    }

    @Override // org.apache.log4j.spi.Filter
    public int decide(LoggingEvent event) {
        if (this.levelToMatch == null) {
            return 0;
        }
        boolean matchOccured = false;
        if (this.levelToMatch.equals(event.getLevel())) {
            matchOccured = true;
        }
        if (matchOccured) {
            if (this.acceptOnMatch) {
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
