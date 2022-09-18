package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint.class */
public abstract class InjectionPoint {
    public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
    public static final int MAX_ALLOWED_SHIFT_BY = 5;
    private static Map<String, Class<? extends InjectionPoint>> types = new HashMap();
    private final String slice;
    private final Selector selector;

    /* renamed from: id */
    private final String f435id;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$AtCode.class */
    public @interface AtCode {
        String value();
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Selector.class */
    public enum Selector {
        FIRST,
        LAST,
        ONE;
        
        public static final Selector DEFAULT = FIRST;
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$ShiftByViolationBehaviour.class */
    public enum ShiftByViolationBehaviour {
        IGNORE,
        WARN,
        ERROR
    }

    public abstract boolean find(String str, InsnList insnList, Collection<AbstractInsnNode> collection);

    static {
        register(BeforeFieldAccess.class);
        register(BeforeInvoke.class);
        register(BeforeNew.class);
        register(BeforeReturn.class);
        register(BeforeStringInvoke.class);
        register(JumpInsnPoint.class);
        register(MethodHead.class);
        register(AfterInvoke.class);
        register(BeforeLoadLocal.class);
        register(AfterStoreLocal.class);
        register(BeforeFinalReturn.class);
        register(BeforeConstant.class);
    }

    public InjectionPoint() {
        this("", Selector.DEFAULT, null);
    }

    public InjectionPoint(InjectionPointData data) {
        this(data.getSlice(), data.getSelector(), data.getId());
    }

    public InjectionPoint(String slice, Selector selector, String id) {
        this.slice = slice;
        this.selector = selector;
        this.f435id = id;
    }

    public String getSlice() {
        return this.slice;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public String getId() {
        return this.f435id;
    }

    public boolean checkPriority(int targetPriority, int mixinPriority) {
        return targetPriority < mixinPriority;
    }

    public String toString() {
        return String.format("@At(\"%s\")", getAtCode());
    }

    public static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
        int index = insns.indexOf(insn) + 1;
        if (index > 0 && index < insns.size()) {
            return insns.get(index);
        }
        return insn;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$CompositeInjectionPoint.class */
    public static abstract class CompositeInjectionPoint extends InjectionPoint {
        protected final InjectionPoint[] components;

        protected CompositeInjectionPoint(InjectionPoint... components) {
            if (components == null || components.length < 2) {
                throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
            }
            this.components = components;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public String toString() {
            return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join(this.components) + "]";
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Intersection.class */
    static final class Intersection extends CompositeInjectionPoint {
        public Intersection(InjectionPoint... points) {
            super(points);
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
            boolean found = false;
            ArrayList<AbstractInsnNode>[] allNodes = (ArrayList[]) Array.newInstance(ArrayList.class, this.components.length);
            for (int i = 0; i < this.components.length; i++) {
                allNodes[i] = new ArrayList<>();
                this.components[i].find(desc, insns, allNodes[i]);
            }
            ArrayList<AbstractInsnNode> alpha = allNodes[0];
            for (int nodeIndex = 0; nodeIndex < alpha.size(); nodeIndex++) {
                AbstractInsnNode node = alpha.get(nodeIndex);
                for (int b = 1; b < allNodes.length && allNodes[b].contains(node); b++) {
                }
                if (1 != 0) {
                    nodes.add(node);
                    found = true;
                }
            }
            return found;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Union.class */
    static final class Union extends CompositeInjectionPoint {
        public Union(InjectionPoint... points) {
            super(points);
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
            LinkedHashSet<AbstractInsnNode> allNodes = new LinkedHashSet<>();
            for (int i = 0; i < this.components.length; i++) {
                this.components[i].find(desc, insns, allNodes);
            }
            nodes.addAll(allNodes);
            return allNodes.size() > 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Shift.class */
    public static final class Shift extends InjectionPoint {
        private final InjectionPoint input;
        private final int shift;

        public Shift(InjectionPoint input, int shift) {
            if (input == null) {
                throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
            }
            this.input = input;
            this.shift = shift;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public String toString() {
            return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
            List<AbstractInsnNode> list = nodes instanceof List ? (List) nodes : new ArrayList<>(nodes);
            this.input.find(desc, insns, nodes);
            for (int i = 0; i < list.size(); i++) {
                list.set(i, insns.get(insns.indexOf(list.get(i)) + this.shift));
            }
            if (nodes != list) {
                nodes.clear();
                nodes.addAll(list);
            }
            return nodes.size() > 0;
        }
    }

    public static InjectionPoint and(InjectionPoint... operands) {
        return new Intersection(operands);
    }

    /* renamed from: or */
    public static InjectionPoint m21or(InjectionPoint... operands) {
        return new Union(operands);
    }

    public static InjectionPoint after(InjectionPoint point) {
        return new Shift(point, 1);
    }

    public static InjectionPoint before(InjectionPoint point) {
        return new Shift(point, -1);
    }

    public static InjectionPoint shift(InjectionPoint point, int count) {
        return new Shift(point, count);
    }

    public static List<InjectionPoint> parse(IInjectionPointContext owner, List<AnnotationNode> ats) {
        return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), ats);
    }

    public static List<InjectionPoint> parse(IMixinContext context, MethodNode method, AnnotationNode parent, List<AnnotationNode> ats) {
        ImmutableList.Builder<InjectionPoint> injectionPoints = ImmutableList.builder();
        for (AnnotationNode at : ats) {
            InjectionPoint injectionPoint = parse(context, method, parent, at);
            if (injectionPoint != null) {
                injectionPoints.add(injectionPoint);
            }
        }
        return injectionPoints.build();
    }

    public static InjectionPoint parse(IInjectionPointContext owner, AbstractC1790At at) {
        return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), at.value(), at.shift(), at.m25by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.m24id());
    }

    public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, AbstractC1790At at) {
        return parse(context, method, parent, at.value(), at.shift(), at.m25by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.m24id());
    }

    public static InjectionPoint parse(IInjectionPointContext owner, AnnotationNode node) {
        return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), node);
    }

    public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, AnnotationNode node) {
        String at = (String) Annotations.getValue(node, "value");
        List<String> args = (List) Annotations.getValue(node, "args");
        String target = (String) Annotations.getValue(node, "target", "");
        String slice = (String) Annotations.getValue(node, "slice", "");
        AbstractC1790At.Shift shift = (AbstractC1790At.Shift) Annotations.getValue(node, "shift", AbstractC1790At.Shift.class, AbstractC1790At.Shift.NONE);
        int by = ((Integer) Annotations.getValue(node, "by", 0)).intValue();
        int ordinal = ((Integer) Annotations.getValue(node, "ordinal", -1)).intValue();
        int opcode = ((Integer) Annotations.getValue(node, "opcode", 0)).intValue();
        String id = (String) Annotations.getValue(node, "id");
        if (args == null) {
            args = ImmutableList.of();
        }
        return parse(context, method, parent, at, shift, by, args, target, slice, ordinal, opcode, id);
    }

    public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, String at, AbstractC1790At.Shift shift, int by, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
        InjectionPointData data = new InjectionPointData(context, method, parent, at, args, target, slice, ordinal, opcode, id);
        Class<? extends InjectionPoint> ipClass = findClass(context, data);
        InjectionPoint point = create(context, data, ipClass);
        return shift(context, method, parent, point, shift, by);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Class<? extends InjectionPoint> findClass(IMixinContext context, InjectionPointData data) {
        String type = data.getType();
        Class cls = types.get(type);
        if (cls == null) {
            if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
                try {
                    cls = Class.forName(type);
                    types.put(type, cls);
                } catch (Exception ex) {
                    throw new InvalidInjectionException(context, data + " could not be loaded or is not a valid InjectionPoint", ex);
                }
            } else {
                throw new InvalidInjectionException(context, data + " is not a valid injection point specifier");
            }
        }
        return cls;
    }

    private static InjectionPoint create(IMixinContext context, InjectionPointData data, Class<? extends InjectionPoint> ipClass) {
        try {
            Constructor<? extends InjectionPoint> ipCtor = ipClass.getDeclaredConstructor(InjectionPointData.class);
            ipCtor.setAccessible(true);
            try {
                InjectionPoint point = ipCtor.newInstance(data);
                return point;
            } catch (Exception ex) {
                throw new InvalidInjectionException(context, "Error whilst instancing injection point " + ipClass.getName() + " for " + data.getAt(), ex);
            }
        } catch (NoSuchMethodException ex2) {
            throw new InvalidInjectionException(context, ipClass.getName() + " must contain a constructor which accepts an InjectionPointData", ex2);
        }
    }

    private static InjectionPoint shift(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, AbstractC1790At.Shift shift, int by) {
        if (point != null) {
            if (shift == AbstractC1790At.Shift.BEFORE) {
                return before(point);
            }
            if (shift == AbstractC1790At.Shift.AFTER) {
                return after(point);
            }
            if (shift == AbstractC1790At.Shift.BY) {
                validateByValue(context, method, parent, point, by);
                return shift(point, by);
            }
        }
        return point;
    }

    private static void validateByValue(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, int by) {
        MixinEnvironment env = context.getMixin().getConfig().getEnvironment();
        ShiftByViolationBehaviour err = (ShiftByViolationBehaviour) env.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
        if (err == ShiftByViolationBehaviour.IGNORE) {
            return;
        }
        String limitBreached = "the maximum allowed value: ";
        String advice = "Increase the value of maxShiftBy to suppress this warning.";
        int allowed = 0;
        if (context instanceof MixinTargetContext) {
            allowed = ((MixinTargetContext) context).getMaxShiftByValue();
        }
        if (by <= allowed) {
            return;
        }
        if (by > 5) {
            limitBreached = "MAX_ALLOWED_SHIFT_BY=";
            advice = "You must use an alternate query or a custom injection point.";
            allowed = 5;
        }
        String message = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", Bytecode.getSimpleName(parent), point, Integer.valueOf(by), context, method.name, limitBreached, Integer.valueOf(allowed), advice);
        if (err == ShiftByViolationBehaviour.WARN && allowed < 5) {
            LogManager.getLogger("mixin").warn(message);
            return;
        }
        throw new InvalidInjectionException(context, message);
    }

    public String getAtCode() {
        AtCode code = (AtCode) getClass().getAnnotation(AtCode.class);
        return code == null ? getClass().getName() : code.value();
    }

    public static void register(Class<? extends InjectionPoint> type) {
        AtCode code = (AtCode) type.getAnnotation(AtCode.class);
        if (code == null) {
            throw new IllegalArgumentException("Injection point class " + type + " is not annotated with @AtCode");
        }
        Class<? extends InjectionPoint> existing = types.get(code.value());
        if (existing != null && !existing.equals(type)) {
            LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[]{code.value(), type.getName(), existing.getName()});
        }
        types.put(code.value(), type);
    }
}
