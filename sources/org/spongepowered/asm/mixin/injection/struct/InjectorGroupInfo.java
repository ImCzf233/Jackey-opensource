package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.util.Annotations;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InjectorGroupInfo.class */
public class InjectorGroupInfo {
    private final String name;
    private final List<InjectionInfo> members;
    private final boolean isDefault;
    private int minCallbackCount;
    private int maxCallbackCount;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InjectorGroupInfo$Map.class */
    public static final class Map extends HashMap<String, InjectorGroupInfo> {
        private static final long serialVersionUID = 1;
        private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public InjectorGroupInfo get(Object key) {
            return forName(key.toString());
        }

        public InjectorGroupInfo forName(String name) {
            InjectorGroupInfo value = (InjectorGroupInfo) super.get((Object) name);
            if (value == null) {
                value = new InjectorGroupInfo(name);
                put(name, value);
            }
            return value;
        }

        public InjectorGroupInfo parseGroup(MethodNode method, String defaultGroup) {
            return parseGroup(Annotations.getInvisible(method, Group.class), defaultGroup);
        }

        public InjectorGroupInfo parseGroup(AnnotationNode annotation, String defaultGroup) {
            if (annotation == null) {
                return NO_GROUP;
            }
            String name = (String) Annotations.getValue(annotation, "name");
            if (name == null || name.isEmpty()) {
                name = defaultGroup;
            }
            InjectorGroupInfo groupInfo = forName(name);
            Integer min = (Integer) Annotations.getValue(annotation, "min");
            if (min != null && min.intValue() != -1) {
                groupInfo.setMinRequired(min.intValue());
            }
            Integer max = (Integer) Annotations.getValue(annotation, "max");
            if (max != null && max.intValue() != -1) {
                groupInfo.setMaxAllowed(max.intValue());
            }
            return groupInfo;
        }

        public void validateAll() throws InjectionValidationException {
            for (InjectorGroupInfo group : values()) {
                group.validate();
            }
        }
    }

    public InjectorGroupInfo(String name) {
        this(name, false);
    }

    InjectorGroupInfo(String name, boolean flag) {
        this.members = new ArrayList();
        this.minCallbackCount = -1;
        this.maxCallbackCount = Integer.MAX_VALUE;
        this.name = name;
        this.isDefault = flag;
    }

    public String toString() {
        return String.format("@Group(name=%s, min=%d, max=%d)", getName(), Integer.valueOf(getMinRequired()), Integer.valueOf(getMaxAllowed()));
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public String getName() {
        return this.name;
    }

    public int getMinRequired() {
        return Math.max(this.minCallbackCount, 1);
    }

    public int getMaxAllowed() {
        return Math.min(this.maxCallbackCount, Integer.MAX_VALUE);
    }

    public Collection<InjectionInfo> getMembers() {
        return Collections.unmodifiableCollection(this.members);
    }

    public void setMinRequired(int min) {
        if (min < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + min + " on " + this);
        }
        if (this.minCallbackCount > 0 && this.minCallbackCount != min) {
            LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[]{Integer.valueOf(min), this.name, Integer.valueOf(this.minCallbackCount)});
        }
        this.minCallbackCount = Math.max(this.minCallbackCount, min);
    }

    public void setMaxAllowed(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + max + " on " + this);
        }
        if (this.maxCallbackCount < Integer.MAX_VALUE && this.maxCallbackCount != max) {
            LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[]{Integer.valueOf(max), this.name, Integer.valueOf(this.maxCallbackCount)});
        }
        this.maxCallbackCount = Math.min(this.maxCallbackCount, max);
    }

    public InjectorGroupInfo add(InjectionInfo member) {
        this.members.add(member);
        return this;
    }

    public InjectorGroupInfo validate() throws InjectionValidationException {
        if (this.members.size() == 0) {
            return this;
        }
        int total = 0;
        for (InjectionInfo member : this.members) {
            total += member.getInjectedCallbackCount();
        }
        int min = getMinRequired();
        int max = getMaxAllowed();
        if (total < min) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", Integer.valueOf(min), Integer.valueOf(total)));
        }
        if (total > max) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", Integer.valueOf(max), Integer.valueOf(total)));
        }
        return this;
    }
}
