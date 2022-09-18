package com.viaversion.viaversion.libs.javassist.tools.web;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/web/BadHttpRequest.class */
public class BadHttpRequest extends Exception {
    private static final long serialVersionUID = 1;

    /* renamed from: e */
    private Exception f177e;

    public BadHttpRequest() {
        this.f177e = null;
    }

    public BadHttpRequest(Exception _e) {
        this.f177e = _e;
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (this.f177e == null) {
            return super.toString();
        }
        return this.f177e.toString();
    }
}
