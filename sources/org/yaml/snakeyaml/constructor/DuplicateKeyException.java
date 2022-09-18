package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.Mark;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/constructor/DuplicateKeyException.class */
public class DuplicateKeyException extends ConstructorException {
    public DuplicateKeyException(Mark contextMark, Object key, Mark problemMark) {
        super("while constructing a mapping", contextMark, "found duplicate key " + String.valueOf(key), problemMark);
    }
}
