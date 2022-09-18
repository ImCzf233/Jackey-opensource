package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/constructor/Construct.class */
public interface Construct {
    Object construct(Node node);

    void construct2ndStep(Node node, Object obj);
}
