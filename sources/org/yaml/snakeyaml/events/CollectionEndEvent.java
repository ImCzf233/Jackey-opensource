package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/events/CollectionEndEvent.class */
public abstract class CollectionEndEvent extends Event {
    public CollectionEndEvent(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }
}
