package org.apache.log4j.p006or;

/* renamed from: org.apache.log4j.or.DefaultRenderer */
/* loaded from: Jackey Client b2.jar:org/apache/log4j/or/DefaultRenderer.class */
class DefaultRenderer implements ObjectRenderer {
    @Override // org.apache.log4j.p006or.ObjectRenderer
    public String doRender(Object o) {
        try {
            return o.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
