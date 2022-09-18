package jdk.nashorn.api.scripting;

import java.util.Collection;
import java.util.Set;
import jdk.Exported;

@Exported
/* loaded from: Jackey Client b2.jar:jdk/nashorn/api/scripting/JSObject.class */
public interface JSObject {
    Object call(Object obj, Object... objArr);

    Object newObject(Object... objArr);

    Object eval(String str);

    Object getMember(String str);

    Object getSlot(int i);

    boolean hasMember(String str);

    boolean hasSlot(int i);

    void removeMember(String str);

    void setMember(String str, Object obj);

    void setSlot(int i, Object obj);

    Set<String> keySet();

    Collection<Object> values();

    boolean isInstance(Object obj);

    boolean isInstanceOf(Object obj);

    String getClassName();

    boolean isFunction();

    boolean isStrictFunction();

    boolean isArray();

    @Deprecated
    double toNumber();
}
