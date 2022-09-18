package jdk.nashorn.internal.scripts;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

/* renamed from: jdk.nashorn.internal.scripts.JD */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/scripts/JD.class */
public class C1653JD extends ScriptObject {
    private static final PropertyMap map$ = PropertyMap.newMap(C1653JD.class);

    public static PropertyMap getInitialMap() {
        return map$;
    }

    public C1653JD(PropertyMap map) {
        super(map);
    }

    public C1653JD(ScriptObject proto) {
        super(proto, getInitialMap());
    }

    public C1653JD(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
        super(map, primitiveSpill, objectSpill);
    }

    public static ScriptObject allocate(PropertyMap map) {
        return new C1653JD(map);
    }
}
