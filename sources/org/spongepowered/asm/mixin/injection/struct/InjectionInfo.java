package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InjectionInfo.class */
public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
    protected final boolean isStatic;
    protected final Deque<MethodNode> targets;
    protected final MethodSlices slices;
    protected final String atKey;
    protected final List<InjectionPoint> injectionPoints;
    protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes;
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List<MethodNode> injectedMethods;
    private int expectedCallbackCount;
    private int requiredCallbackCount;
    private int maxCallbackCount;
    private int injectedCallbackCount;

    protected abstract Injector parseInjector(AnnotationNode annotationNode);

    public InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        this(mixin, method, annotation, "at");
    }

    public InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation);
        this.targets = new ArrayDeque();
        this.injectionPoints = new ArrayList();
        this.targetNodes = new LinkedHashMap();
        this.injectedMethods = new ArrayList(0);
        this.expectedCallbackCount = 1;
        this.requiredCallbackCount = 0;
        this.maxCallbackCount = Integer.MAX_VALUE;
        this.injectedCallbackCount = 0;
        this.isStatic = Bytecode.methodIsStatic(method);
        this.slices = MethodSlices.parse(this);
        this.atKey = atKey;
        readAnnotation();
    }

    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        String type = "@" + Bytecode.getSimpleName(this.annotation);
        List<AnnotationNode> injectionPoints = readInjectionPoints(type);
        findMethods(parseTargets(type), type);
        parseInjectionPoints(injectionPoints);
        parseRequirements();
        this.injector = parseInjector(this.annotation);
    }

    protected Set<MemberInfo> parseTargets(String type) {
        List<String> methods = Annotations.getValue(this.annotation, "method", false);
        if (methods == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", type, this.method.name));
        }
        Set<MemberInfo> members = new LinkedHashSet<>();
        for (String method : methods) {
            try {
                MemberInfo targetMember = MemberInfo.parseAndValidate(method, this.mixin);
                if (targetMember.owner != null && !targetMember.owner.equals(this.mixin.getTargetClassRef())) {
                    throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", type, this.method.name, targetMember.owner));
                }
                members.add(targetMember);
            } catch (InvalidMemberDescriptorException e) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", type, this.method.name, method, this.mixin.getReferenceMapper().getStatus()));
            }
        }
        return members;
    }

    public List<AnnotationNode> readInjectionPoints(String type) {
        List<AnnotationNode> ats = Annotations.getValue(this.annotation, this.atKey, false);
        if (ats == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", type, this.method.name, this.atKey));
        }
        return ats;
    }

    protected void parseInjectionPoints(List<AnnotationNode> ats) {
        this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, ats));
    }

    protected void parseRequirements() {
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        Integer expect = (Integer) Annotations.getValue(this.annotation, "expect");
        if (expect != null) {
            this.expectedCallbackCount = expect.intValue();
        }
        Integer require = (Integer) Annotations.getValue(this.annotation, "require");
        if (require != null && require.intValue() > -1) {
            this.requiredCallbackCount = require.intValue();
        } else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
        Integer allow = (Integer) Annotations.getValue(this.annotation, "allow");
        if (allow != null) {
            this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), allow.intValue());
        }
    }

    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }

    public void prepare() {
        this.targetNodes.clear();
        for (MethodNode targetMethod : this.targets) {
            Target target = this.mixin.getTargetMethod(targetMethod);
            InjectorTarget injectorTarget = new InjectorTarget(this, target);
            this.targetNodes.put(target, this.injector.find(injectorTarget, this.injectionPoints));
            injectorTarget.dispose();
        }
    }

    public void inject() {
        for (Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet()) {
            this.injector.inject(entry.getKey(), entry.getValue());
        }
        this.targets.clear();
    }

    public void postInject() {
        for (MethodNode method : this.injectedMethods) {
            this.classNode.methods.add(method);
        }
        String description = getDescription();
        String refMapStatus = this.mixin.getReferenceMapper().getStatus();
        String dynamicInfo = getDynamicInfo();
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.expectedCallbackCount), Integer.valueOf(this.injectedCallbackCount), refMapStatus, dynamicInfo));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.requiredCallbackCount), refMapStatus, dynamicInfo));
        }
        if (this.injectedCallbackCount > this.maxCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", description, this.method.name, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.maxCallbackCount), dynamicInfo));
        }
    }

    public void notifyInjected(Target target) {
    }

    protected String getDescription() {
        return "Callback method";
    }

    public String toString() {
        return describeInjector(this.mixin, this.annotation, this.method);
    }

    public Collection<MethodNode> getTargets() {
        return this.targets;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.ISliceContext
    public MethodSlice getSlice(String id) {
        return this.slices.get(getSliceId(id));
    }

    public String getSliceId(String id) {
        return "";
    }

    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }

    public MethodNode addMethod(int access, String name, String desc) {
        MethodNode method = new MethodNode(Opcodes.ASM5, access | 4096, name, desc, null, null);
        this.injectedMethods.add(method);
        return method;
    }

    public void addCallbackInvocation(MethodNode handler) {
        this.injectedCallbackCount++;
    }

    private void findMethods(Set<MemberInfo> searchFor, String type) {
        this.targets.clear();
        int passes = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
        Iterator<MemberInfo> it = searchFor.iterator();
        while (it.hasNext()) {
            MemberInfo member = it.next();
            int count = 0;
            for (int pass = 0; pass < passes && count < 1; pass++) {
                int ordinal = 0;
                for (MethodNode target : this.classNode.methods) {
                    if (member.matches(target.name, target.desc, ordinal)) {
                        boolean isMixinMethod = Annotations.getVisible(target, MixinMerged.class) != null;
                        if (!member.matchAll || (Bytecode.methodIsStatic(target) == this.isStatic && target != this.method && !isMixinMethod)) {
                            checkTarget(target);
                            this.targets.add(target);
                            ordinal++;
                            count++;
                        }
                    }
                }
                member = member.transform(null);
            }
        }
        if (this.targets.size() == 0) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", type, this.method.name, namesOf(searchFor), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), getDynamicInfo()));
        }
    }

    private void checkTarget(MethodNode target) {
        AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
        if (merged != null && Annotations.getVisible(target, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, target.name, target.desc, Annotations.getValue(merged, "mixin")));
        }
    }

    protected String getDynamicInfo() {
        AnnotationNode annotation = Annotations.getInvisible(this.method, Dynamic.class);
        String description = Strings.nullToEmpty((String) Annotations.getValue(annotation));
        Type upstream = (Type) Annotations.getValue(annotation, "mixin");
        if (upstream != null) {
            description = String.format("{%s} %s", upstream.getClassName(), description).trim();
        }
        return description.length() > 0 ? String.format(" Method is @Dynamic(%s)", description) : "";
    }

    public static InjectionInfo parse(MixinTargetContext mixin, MethodNode method) {
        AnnotationNode annotation = getInjectorAnnotation(mixin.getMixin(), method);
        if (annotation == null) {
            return null;
        }
        if (annotation.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return new CallbackInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return new ModifyArgsInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return new ModifyConstantInjectionInfo(mixin, method, annotation);
        }
        return null;
    }

    public static AnnotationNode getInjectorAnnotation(IMixinInfo mixin, MethodNode method) {
        try {
            AnnotationNode annotation = Annotations.getSingleVisible(method, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
            return annotation;
        } catch (IllegalArgumentException ex) {
            throw new InvalidMixinException(mixin, String.format("Error parsing annotations on %s in %s: %s", method.name, mixin.getClassName(), ex.getMessage()));
        }
    }

    public static String getInjectorPrefix(AnnotationNode annotation) {
        if (annotation != null) {
            if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
                return "modify";
            }
            if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
                return "args";
            }
            if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
                return "redirect";
            }
            if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
                return "localvar";
            }
            if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
                return "constant";
            }
            return "handler";
        }
        return "handler";
    }

    public static String describeInjector(IMixinContext mixin, AnnotationNode annotation, MethodNode method) {
        return String.format("%s->@%s::%s%s", mixin.toString(), Bytecode.getSimpleName(annotation), method.name, method.desc);
    }

    private static String namesOf(Collection<MemberInfo> members) {
        int index = 0;
        int count = members.size();
        StringBuilder sb = new StringBuilder();
        for (MemberInfo member : members) {
            if (index > 0) {
                if (index == count - 1) {
                    sb.append(" or ");
                } else {
                    sb.append(", ");
                }
            }
            sb.append('\'').append(member.name).append('\'');
            index++;
        }
        return sb.toString();
    }
}
