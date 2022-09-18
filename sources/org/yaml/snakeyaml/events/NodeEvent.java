package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/NodeEvent.class */
public abstract class NodeEvent extends Event {
    private final String anchor;

    public NodeEvent(String anchor, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        this.anchor = anchor;
    }

    public String getAnchor() {
        return this.anchor;
    }

    @Override // org.yaml.snakeyaml.events.Event
    public String getArguments() {
        return "anchor=" + this.anchor;
    }
}
