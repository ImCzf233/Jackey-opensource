package org.apache.log4j;

import java.util.Vector;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/ProvisionNode.class */
class ProvisionNode extends Vector {
    private static final long serialVersionUID = -4479121426311014469L;

    public ProvisionNode(Logger logger) {
        addElement(logger);
    }
}
