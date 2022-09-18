package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/gen/AccessorInfo.class */
public class AccessorInfo extends SpecialMethodInfo {
    protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
    protected final Type[] argTypes;
    protected final Type returnType;
    protected final AccessorType type;
    private final Type targetFieldType;
    protected final MemberInfo target;
    protected FieldNode targetField;
    protected MethodNode targetMethod;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/gen/AccessorInfo$AccessorType.class */
    public enum AccessorType {
        FIELD_GETTER(ImmutableSet.of(PropertyDescriptor.GET, "is")) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.1
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo info) {
                return new AccessorGeneratorFieldGetter(info);
            }
        },
        FIELD_SETTER(ImmutableSet.of(PropertyDescriptor.SET)) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.2
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo info) {
                return new AccessorGeneratorFieldSetter(info);
            }
        },
        METHOD_PROXY(ImmutableSet.of("call", "invoke")) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.3
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo info) {
                return new AccessorGeneratorMethodProxy(info);
            }
        };
        
        private final Set<String> expectedPrefixes;

        abstract AccessorGenerator getGenerator(AccessorInfo accessorInfo);

        AccessorType(Set set) {
            this.expectedPrefixes = set;
        }

        public boolean isExpectedPrefix(String prefix) {
            return this.expectedPrefixes.contains(prefix);
        }

        public String getExpectedPrefixes() {
            return this.expectedPrefixes.toString();
        }
    }

    public AccessorInfo(MixinTargetContext mixin, MethodNode method) {
        this(mixin, method, Accessor.class);
    }

    public AccessorInfo(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> annotationClass) {
        super(mixin, method, Annotations.getVisible(method, annotationClass));
        this.argTypes = Type.getArgumentTypes(method.desc);
        this.returnType = Type.getReturnType(method.desc);
        this.type = initType();
        this.targetFieldType = initTargetFieldType();
        this.target = initTarget();
    }

    protected AccessorType initType() {
        if (this.returnType.equals(Type.VOID_TYPE)) {
            return AccessorType.FIELD_SETTER;
        }
        return AccessorType.FIELD_GETTER;
    }

    protected Type initTargetFieldType() {
        switch (this.type) {
            case FIELD_GETTER:
                if (this.argTypes.length > 0) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
                }
                return this.returnType;
            case FIELD_SETTER:
                if (this.argTypes.length != 1) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
                }
                return this.argTypes[0];
            default:
                throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
        }
    }

    protected MemberInfo initTarget() {
        MemberInfo target = new MemberInfo(getTargetName(), (String) null, this.targetFieldType.getDescriptor());
        this.annotation.visit("target", target.toString());
        return target;
    }

    public String getTargetName() {
        String name = (String) Annotations.getValue(this.annotation);
        if (Strings.isNullOrEmpty(name)) {
            String inflectedTarget = inflectTarget();
            if (inflectedTarget == null) {
                throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
            }
            return inflectedTarget;
        }
        return MemberInfo.parse(name, this.mixin).name;
    }

    protected String inflectTarget() {
        return inflectTarget(this.method.name, this.type, toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
    }

    public static String inflectTarget(String accessorName, AccessorType accessorType, String accessorDescription, IMixinContext context, boolean verbose) {
        Matcher nameMatcher = PATTERN_ACCESSOR.matcher(accessorName);
        if (nameMatcher.matches()) {
            String prefix = nameMatcher.group(1);
            String firstChar = nameMatcher.group(3);
            String remainder = nameMatcher.group(4);
            Object[] objArr = new Object[2];
            objArr[0] = toLowerCase(firstChar, !isUpperCase(remainder));
            objArr[1] = remainder;
            String name = String.format("%s%s", objArr);
            if (!accessorType.isExpectedPrefix(prefix) && verbose) {
                LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[]{accessorDescription, prefix, accessorType.getExpectedPrefixes()});
            }
            return MemberInfo.parse(name, context).name;
        }
        return null;
    }

    public final MemberInfo getTarget() {
        return this.target;
    }

    public final Type getTargetFieldType() {
        return this.targetFieldType;
    }

    public final FieldNode getTargetField() {
        return this.targetField;
    }

    public final MethodNode getTargetMethod() {
        return this.targetMethod;
    }

    public final Type getReturnType() {
        return this.returnType;
    }

    public final Type[] getArgTypes() {
        return this.argTypes;
    }

    public String toString() {
        return String.format("%s->@%s[%s]::%s%s", this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc);
    }

    public void locate() {
        this.targetField = findTargetField();
    }

    public MethodNode generate() {
        MethodNode generatedAccessor = this.type.getGenerator(this).generate();
        Bytecode.mergeAnnotations(this.method, generatedAccessor);
        return generatedAccessor;
    }

    private FieldNode findTargetField() {
        return (FieldNode) findTarget(this.classNode.fields);
    }

    public <TNode> TNode findTarget(List<TNode> nodes) {
        String name;
        TNode exactMatch = null;
        List<TNode> candidates = new ArrayList<>();
        for (TNode node : nodes) {
            String desc = getNodeDesc(node);
            if (desc != null && desc.equals(this.target.desc) && (name = getNodeName(node)) != null) {
                if (name.equals(this.target.name)) {
                    exactMatch = node;
                }
                if (name.equalsIgnoreCase(this.target.name)) {
                    candidates.add(node);
                }
            }
        }
        if (exactMatch != null) {
            if (candidates.size() > 1) {
                LogManager.getLogger("mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[]{this, this.target});
            }
            return exactMatch;
        } else if (candidates.size() == 1) {
            return candidates.get(0);
        } else {
            String number = candidates.size() == 0 ? "No" : "Multiple";
            throw new InvalidAccessorException(this, number + " candidates were found matching " + this.target + " in " + this.classNode.name + " for " + this);
        }
    }

    private static <TNode> String getNodeDesc(TNode node) {
        if (node instanceof MethodNode) {
            return ((MethodNode) node).desc;
        }
        if (!(node instanceof FieldNode)) {
            return null;
        }
        return ((FieldNode) node).desc;
    }

    private static <TNode> String getNodeName(TNode node) {
        if (node instanceof MethodNode) {
            return ((MethodNode) node).name;
        }
        if (!(node instanceof FieldNode)) {
            return null;
        }
        return ((FieldNode) node).name;
    }

    /* renamed from: of */
    public static AccessorInfo m26of(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> type) {
        if (type == Accessor.class) {
            return new AccessorInfo(mixin, method);
        }
        if (type == Invoker.class) {
            return new InvokerInfo(mixin, method);
        }
        throw new InvalidAccessorException(mixin, "Could not parse accessor for unknown type " + type.getName());
    }

    private static String toLowerCase(String string, boolean condition) {
        return condition ? string.toLowerCase() : string;
    }

    private static boolean isUpperCase(String string) {
        return string.toUpperCase().equals(string);
    }
}
