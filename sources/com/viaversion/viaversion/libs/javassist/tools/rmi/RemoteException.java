package com.viaversion.viaversion.libs.javassist.tools.rmi;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/rmi/RemoteException.class */
public class RemoteException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public RemoteException(String msg) {
        super(msg);
    }

    public RemoteException(Exception e) {
        super("by " + e.toString());
    }
}
