package org.yaml.snakeyaml.composer;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/composer/ComposerException.class */
public class ComposerException extends MarkedYAMLException {
    private static final long serialVersionUID = 2146314636913113935L;

    public ComposerException(String context, Mark contextMark, String problem, Mark problemMark) {
        super(context, contextMark, problem, problemMark);
    }
}
