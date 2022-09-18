package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/StreamEndEvent.class */
public final class StreamEndEvent extends Event {
    public StreamEndEvent(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }

    @Override // org.yaml.snakeyaml.events.Event
    public Event.EnumC1814ID getEventId() {
        return Event.EnumC1814ID.StreamEnd;
    }
}
