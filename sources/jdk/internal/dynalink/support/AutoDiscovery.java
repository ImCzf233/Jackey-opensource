package jdk.internal.dynalink.support;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/AutoDiscovery.class */
public class AutoDiscovery {
    private AutoDiscovery() {
    }

    public static List<GuardingDynamicLinker> loadLinkers() {
        return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class));
    }

    public static List<GuardingDynamicLinker> loadLinkers(ClassLoader cl) {
        return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class, cl));
    }

    private static <T> List<T> getLinkers(ServiceLoader<T> loader) {
        List<T> list = new LinkedList<>();
        Iterator<T> it = loader.iterator();
        while (it.hasNext()) {
            T linker = it.next();
            list.add(linker);
        }
        return list;
    }
}
