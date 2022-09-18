package jdk.nashorn.api.scripting;

import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/DefaultValueImpl.class */
public class DefaultValueImpl {
    private static final String[] DEFAULT_VALUE_FNS_NUMBER = {"valueOf", "toString"};
    private static final String[] DEFAULT_VALUE_FNS_STRING = {"toString", "valueOf"};

    DefaultValueImpl() {
    }

    public static Object getDefaultValue(JSObject jsobj, Class<?> hint) throws UnsupportedOperationException {
        String[] strArr;
        boolean isNumber = hint == null || hint == Number.class;
        for (String methodName : isNumber ? DEFAULT_VALUE_FNS_NUMBER : DEFAULT_VALUE_FNS_STRING) {
            Object objMember = jsobj.getMember(methodName);
            if (objMember instanceof JSObject) {
                JSObject member = (JSObject) objMember;
                if (member.isFunction()) {
                    Object value = member.call(jsobj, new Object[0]);
                    if (JSType.isPrimitive(value)) {
                        return value;
                    }
                } else {
                    continue;
                }
            }
        }
        throw new UnsupportedOperationException(isNumber ? "cannot.get.default.number" : "cannot.get.default.string");
    }
}
