package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/CompositeTypeBasedGuardingDynamicLinker.class */
public class CompositeTypeBasedGuardingDynamicLinker implements TypeBasedGuardingDynamicLinker, Serializable {
    private static final long serialVersionUID = 1;
    private final ClassValue<List<TypeBasedGuardingDynamicLinker>> classToLinker;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/CompositeTypeBasedGuardingDynamicLinker$ClassToLinker.class */
    public static class ClassToLinker extends ClassValue<List<TypeBasedGuardingDynamicLinker>> {
        private static final List<TypeBasedGuardingDynamicLinker> NO_LINKER = Collections.emptyList();
        private final TypeBasedGuardingDynamicLinker[] linkers;
        private final List<TypeBasedGuardingDynamicLinker>[] singletonLinkers;

        ClassToLinker(TypeBasedGuardingDynamicLinker[] linkers) {
            this.linkers = linkers;
            this.singletonLinkers = new List[linkers.length];
            for (int i = 0; i < linkers.length; i++) {
                this.singletonLinkers[i] = Collections.singletonList(linkers[i]);
            }
        }

        protected List<TypeBasedGuardingDynamicLinker> computeValue(Class<?> clazz) {
            List<TypeBasedGuardingDynamicLinker> list = NO_LINKER;
            for (int i = 0; i < this.linkers.length; i++) {
                TypeBasedGuardingDynamicLinker linker = this.linkers[i];
                if (linker.canLinkType(clazz)) {
                    switch (list.size()) {
                        case 0:
                            list = this.singletonLinkers[i];
                            break;
                        case 1:
                            list = new LinkedList<>(list);
                        default:
                            list.add(linker);
                            break;
                    }
                }
            }
            return list;
        }
    }

    public CompositeTypeBasedGuardingDynamicLinker(Iterable<? extends TypeBasedGuardingDynamicLinker> linkers) {
        List<TypeBasedGuardingDynamicLinker> l = new LinkedList<>();
        for (TypeBasedGuardingDynamicLinker linker : linkers) {
            l.add(linker);
        }
        this.classToLinker = new ClassToLinker((TypeBasedGuardingDynamicLinker[]) l.toArray(new TypeBasedGuardingDynamicLinker[l.size()]));
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return !((List) this.classToLinker.get(type)).isEmpty();
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        Object obj = linkRequest.getReceiver();
        if (obj == null) {
            return null;
        }
        for (TypeBasedGuardingDynamicLinker linker : (List) this.classToLinker.get(obj.getClass())) {
            GuardedInvocation invocation = linker.getGuardedInvocation(linkRequest, linkerServices);
            if (invocation != null) {
                return invocation;
            }
        }
        return null;
    }

    public static List<GuardingDynamicLinker> optimize(Iterable<? extends GuardingDynamicLinker> linkers) {
        List<GuardingDynamicLinker> llinkers = new LinkedList<>();
        List<TypeBasedGuardingDynamicLinker> tblinkers = new LinkedList<>();
        for (GuardingDynamicLinker linker : linkers) {
            if (linker instanceof TypeBasedGuardingDynamicLinker) {
                tblinkers.add((TypeBasedGuardingDynamicLinker) linker);
            } else {
                addTypeBased(llinkers, tblinkers);
                llinkers.add(linker);
            }
        }
        addTypeBased(llinkers, tblinkers);
        return llinkers;
    }

    private static void addTypeBased(List<GuardingDynamicLinker> llinkers, List<TypeBasedGuardingDynamicLinker> tblinkers) {
        switch (tblinkers.size()) {
            case 0:
                return;
            case 1:
                llinkers.addAll(tblinkers);
                tblinkers.clear();
                return;
            default:
                llinkers.add(new CompositeTypeBasedGuardingDynamicLinker(tblinkers));
                tblinkers.clear();
                return;
        }
    }
}
