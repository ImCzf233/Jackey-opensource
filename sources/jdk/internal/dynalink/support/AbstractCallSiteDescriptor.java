package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/AbstractCallSiteDescriptor.class */
public abstract class AbstractCallSiteDescriptor implements CallSiteDescriptor {
    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getName() {
        return appendName(new StringBuilder(getNameLength())).toString();
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodHandles.Lookup getLookup() {
        return MethodHandles.publicLookup();
    }

    public boolean equals(Object obj) {
        return (obj instanceof CallSiteDescriptor) && equals((CallSiteDescriptor) obj);
    }

    public boolean equals(CallSiteDescriptor csd) {
        if (csd == null) {
            return false;
        }
        if (csd == this) {
            return true;
        }
        int ntc = getNameTokenCount();
        if (ntc != csd.getNameTokenCount()) {
            return false;
        }
        int i = ntc;
        do {
            int i2 = i;
            i--;
            if (i2 <= 0) {
                if (!getMethodType().equals(csd.getMethodType())) {
                    return false;
                }
                return lookupsEqual(getLookup(), csd.getLookup());
            }
        } while (Objects.equals(getNameToken(i), csd.getNameToken(i)));
        return false;
    }

    public int hashCode() {
        MethodHandles.Lookup lookup = getLookup();
        int h = lookup.lookupClass().hashCode() + (31 * lookup.lookupModes());
        int c = getNameTokenCount();
        for (int i = 0; i < c; i++) {
            h = (h * 31) + getNameToken(i).hashCode();
        }
        return (h * 31) + getMethodType().hashCode();
    }

    public String toString() {
        String mt = getMethodType().toString();
        String l = getLookup().toString();
        StringBuilder b = new StringBuilder(l.length() + 1 + mt.length() + getNameLength());
        return appendName(b).append(mt).append("@").append(l).toString();
    }

    private int getNameLength() {
        int c = getNameTokenCount();
        int l = 0;
        for (int i = 0; i < c; i++) {
            l += getNameToken(i).length();
        }
        return (l + c) - 1;
    }

    private StringBuilder appendName(StringBuilder b) {
        b.append(getNameToken(0));
        int c = getNameTokenCount();
        for (int i = 1; i < c; i++) {
            b.append(':').append(getNameToken(i));
        }
        return b;
    }

    private static boolean lookupsEqual(MethodHandles.Lookup l1, MethodHandles.Lookup l2) {
        if (l1 == l2) {
            return true;
        }
        return l1.lookupClass() == l2.lookupClass() && l1.lookupModes() == l2.lookupModes();
    }
}
