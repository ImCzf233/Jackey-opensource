package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/parser/Parser.class */
public interface Parser {
    boolean checkEvent(Event.EnumC1814ID enumC1814ID);

    Event peekEvent();

    Event getEvent();
}
