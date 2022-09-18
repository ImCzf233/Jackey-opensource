package jdk.internal.dynalink;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/CallSiteDescriptor.class */
public interface CallSiteDescriptor {
    public static final int SCHEME = 0;
    public static final int OPERATOR = 1;
    public static final int NAME_OPERAND = 2;
    public static final String TOKEN_DELIMITER = ":";
    public static final String OPERATOR_DELIMITER = "|";

    int getNameTokenCount();

    String getNameToken(int i);

    String getName();

    MethodType getMethodType();

    MethodHandles.Lookup getLookup();

    CallSiteDescriptor changeMethodType(MethodType methodType);
}
