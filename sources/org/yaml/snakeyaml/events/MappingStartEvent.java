package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/MappingStartEvent.class */
public final class MappingStartEvent extends CollectionStartEvent {
    public MappingStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
        super(anchor, tag, implicit, startMark, endMark, flowStyle);
    }

    @Deprecated
    public MappingStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, Boolean flowStyle) {
        this(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.fromBoolean(flowStyle));
    }

    @Override // org.yaml.snakeyaml.events.Event
    public Event.EnumC1814ID getEventId() {
        return Event.EnumC1814ID.MappingStart;
    }
}
