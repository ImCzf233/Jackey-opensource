package org.apache.log4j.spi;

import org.apache.log4j.p006or.ObjectRenderer;
import org.apache.log4j.p006or.RendererMap;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/RendererSupport.class */
public interface RendererSupport {
    RendererMap getRendererMap();

    void setRenderer(Class cls, ObjectRenderer objectRenderer);
}
