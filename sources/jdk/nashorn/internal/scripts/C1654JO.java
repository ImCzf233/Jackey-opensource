package jdk.nashorn.internal.scripts;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

/* renamed from: jdk.nashorn.internal.scripts.JO */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/scripts/JO.class */
public class C1654JO extends ScriptObject {
    private static final PropertyMap map$ = PropertyMap.newMap(C1654JO.class);

    public static PropertyMap getInitialMap() {
        return map$;
    }

    public C1654JO(PropertyMap map) {
        super(map);
    }

    public C1654JO(ScriptObject proto) {
        super(proto, getInitialMap());
    }

    public C1654JO(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
        super(map, primitiveSpill, objectSpill);
    }

    public static ScriptObject allocate(PropertyMap map) {
        return new C1654JO(map);
    }
}
