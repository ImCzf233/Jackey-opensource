package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/Event.class */
public abstract class Event {
    private final Mark startMark;
    private final Mark endMark;

    /* renamed from: org.yaml.snakeyaml.events.Event$ID */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/Event$ID.class */
    public enum EnumC1814ID {
        Alias,
        Comment,
        DocumentEnd,
        DocumentStart,
        MappingEnd,
        MappingStart,
        Scalar,
        SequenceEnd,
        SequenceStart,
        StreamEnd,
        StreamStart
    }

    public abstract EnumC1814ID getEventId();

    public Event(Mark startMark, Mark endMark) {
        this.startMark = startMark;
        this.endMark = endMark;
    }

    public String toString() {
        return "<" + getClass().getName() + "(" + getArguments() + ")>";
    }

    public Mark getStartMark() {
        return this.startMark;
    }

    public Mark getEndMark() {
        return this.endMark;
    }

    public String getArguments() {
        return "";
    }

    /* renamed from: is */
    public boolean m0is(EnumC1814ID id) {
        return getEventId() == id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
