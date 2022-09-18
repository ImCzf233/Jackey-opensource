package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo.class */
public final class ClassInfo {
    public static final int INCLUDE_PRIVATE = 2;
    public static final int INCLUDE_STATIC = 8;
    public static final int INCLUDE_ALL = 10;
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";
    private final String name;
    private final String superName;
    private final String outerName;
    private final boolean isProbablyStatic;
    private final Set<String> interfaces;
    private final Set<Method> methods;
    private final Set<Field> fields;
    private final Set<MixinInfo> mixins;
    private final Map<ClassInfo, ClassInfo> correspondingTypes;
    private final MixinInfo mixin;
    private final MethodMapper methodMapper;
    private final boolean isMixin;
    private final boolean isInterface;
    private final int access;
    private ClassInfo superClass;
    private ClassInfo outerClass;
    private ClassSignature signature;
    private static final Logger logger = LogManager.getLogger("mixin");
    private static final Profiler profiler = MixinEnvironment.getProfiler();
    private static final Map<String, ClassInfo> cache = new HashMap();
    private static final ClassInfo OBJECT = new ClassInfo();

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$SearchType.class */
    public enum SearchType {
        ALL_CLASSES,
        SUPER_CLASSES_ONLY
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Traversal.class */
    public enum Traversal {
        NONE(null, false, SearchType.SUPER_CLASSES_ONLY),
        ALL(null, true, SearchType.ALL_CLASSES),
        IMMEDIATE(NONE, true, SearchType.SUPER_CLASSES_ONLY),
        SUPER(ALL, false, SearchType.SUPER_CLASSES_ONLY);
        
        private final Traversal next;
        private final boolean traverse;
        private final SearchType searchType;

        Traversal(Traversal next, boolean traverse, SearchType searchType) {
            this.next = next != null ? next : this;
            this.traverse = traverse;
            this.searchType = searchType;
        }

        public Traversal next() {
            return this.next;
        }

        public boolean canTraverse() {
            return this.traverse;
        }

        public SearchType getSearchType() {
            return this.searchType;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$FrameData.class */
    public static class FrameData {
        private static final String[] FRAMETYPES = {"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
        public final int index;
        public final int type;
        public final int locals;

        FrameData(int index, int type, int locals) {
            this.index = index;
            this.type = type;
            this.locals = locals;
        }

        FrameData(int index, FrameNode frameNode) {
            this.index = index;
            this.type = frameNode.type;
            this.locals = frameNode.local != null ? frameNode.local.size() : 0;
        }

        public String toString() {
            return String.format("FrameData[index=%d, type=%s, locals=%d]", Integer.valueOf(this.index), FRAMETYPES[this.type + 1], Integer.valueOf(this.locals));
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Member.class */
    public static abstract class Member {
        private final Type type;
        private final String memberName;
        private final String memberDesc;
        private final boolean isInjected;
        private final int modifiers;
        private String currentName;
        private String currentDesc;
        private boolean decoratedFinal;
        private boolean decoratedMutable;
        private boolean unique;

        /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Member$Type.class */
        public enum Type {
            METHOD,
            FIELD
        }

        public abstract ClassInfo getOwner();

        protected Member(Member member) {
            this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
            this.currentName = member.currentName;
            this.currentDesc = member.currentDesc;
            this.unique = member.unique;
        }

        protected Member(Type type, String name, String desc, int access) {
            this(type, name, desc, access, false);
        }

        protected Member(Type type, String name, String desc, int access, boolean injected) {
            this.type = type;
            this.memberName = name;
            this.memberDesc = desc;
            this.isInjected = injected;
            this.currentName = name;
            this.currentDesc = desc;
            this.modifiers = access;
        }

        public String getOriginalName() {
            return this.memberName;
        }

        public String getName() {
            return this.currentName;
        }

        public String getOriginalDesc() {
            return this.memberDesc;
        }

        public String getDesc() {
            return this.currentDesc;
        }

        public boolean isInjected() {
            return this.isInjected;
        }

        public boolean isRenamed() {
            return !this.currentName.equals(this.memberName);
        }

        public boolean isRemapped() {
            return !this.currentDesc.equals(this.memberDesc);
        }

        public boolean isPrivate() {
            return (this.modifiers & 2) != 0;
        }

        public boolean isStatic() {
            return (this.modifiers & 8) != 0;
        }

        public boolean isAbstract() {
            return (this.modifiers & 1024) != 0;
        }

        public boolean isFinal() {
            return (this.modifiers & 16) != 0;
        }

        public boolean isSynthetic() {
            return (this.modifiers & 4096) != 0;
        }

        public boolean isUnique() {
            return this.unique;
        }

        public void setUnique(boolean unique) {
            this.unique = unique;
        }

        public boolean isDecoratedFinal() {
            return this.decoratedFinal;
        }

        public boolean isDecoratedMutable() {
            return this.decoratedMutable;
        }

        public void setDecoratedFinal(boolean decoratedFinal, boolean decoratedMutable) {
            this.decoratedFinal = decoratedFinal;
            this.decoratedMutable = decoratedMutable;
        }

        public boolean matchesFlags(int flags) {
            return ((((this.modifiers ^ (-1)) | (flags & 2)) & 2) == 0 || (((this.modifiers ^ (-1)) | (flags & 8)) & 8) == 0) ? false : true;
        }

        public ClassInfo getImplementor() {
            return getOwner();
        }

        public int getAccess() {
            return this.modifiers;
        }

        public String renameTo(String name) {
            this.currentName = name;
            return name;
        }

        public String remapTo(String desc) {
            this.currentDesc = desc;
            return desc;
        }

        public boolean equals(String name, String desc) {
            return (this.memberName.equals(name) || this.currentName.equals(name)) && (this.memberDesc.equals(desc) || this.currentDesc.equals(desc));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Member)) {
                return false;
            }
            Member other = (Member) obj;
            return (other.memberName.equals(this.memberName) || other.currentName.equals(this.currentName)) && (other.memberDesc.equals(this.memberDesc) || other.currentDesc.equals(this.currentDesc));
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public String toString() {
            return String.format(getDisplayFormat(), this.memberName, this.memberDesc);
        }

        protected String getDisplayFormat() {
            return "%s%s";
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Method.class */
    public class Method extends Member {
        private final List<FrameData> frames;
        private boolean isAccessor;

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ int hashCode() {
            return super.hashCode();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean equals(String str, String str2) {
            return super.equals(str, str2);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String remapTo(String str) {
            return super.remapTo(str);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String renameTo(String str) {
            return super.renameTo(str);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ int getAccess() {
            return super.getAccess();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ ClassInfo getImplementor() {
            return super.getImplementor();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean matchesFlags(int i) {
            return super.matchesFlags(i);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ void setDecoratedFinal(boolean z, boolean z2) {
            super.setDecoratedFinal(z, z2);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ void setUnique(boolean z) {
            super.setUnique(z);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isUnique() {
            return super.isUnique();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isSynthetic() {
            return super.isSynthetic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isFinal() {
            return super.isFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isAbstract() {
            return super.isAbstract();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isStatic() {
            return super.isStatic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isPrivate() {
            return super.isPrivate();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isRemapped() {
            return super.isRemapped();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isRenamed() {
            return super.isRenamed();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ boolean isInjected() {
            return super.isInjected();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String getDesc() {
            return super.getDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String getOriginalDesc() {
            return super.getOriginalDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public /* bridge */ /* synthetic */ String getOriginalName() {
            return super.getOriginalName();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(Member member) {
            super(member);
            ClassInfo.this = this$0;
            this.frames = member instanceof Method ? ((Method) member).frames : null;
        }

        public Method(ClassInfo this$0, MethodNode method) {
            this(method, false);
            setUnique(Annotations.getVisible(method, Unique.class) != null);
            this.isAccessor = Annotations.getSingleVisible(method, Accessor.class, Invoker.class) != null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(MethodNode method, boolean injected) {
            super(Member.Type.METHOD, method.name, method.desc, method.access, injected);
            ClassInfo.this = this$0;
            this.frames = gatherFrames(method);
            setUnique(Annotations.getVisible(method, Unique.class) != null);
            this.isAccessor = Annotations.getSingleVisible(method, Accessor.class, Invoker.class) != null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name, String desc) {
            super(Member.Type.METHOD, name, desc, 1, false);
            ClassInfo.this = this$0;
            this.frames = null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name, String desc, int access) {
            super(Member.Type.METHOD, name, desc, access, false);
            ClassInfo.this = this$0;
            this.frames = null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name, String desc, int access, boolean injected) {
            super(Member.Type.METHOD, name, desc, access, injected);
            ClassInfo.this = this$0;
            this.frames = null;
        }

        private List<FrameData> gatherFrames(MethodNode method) {
            List<FrameData> frames = new ArrayList<>();
            Iterator<AbstractInsnNode> iter = method.instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode insn = iter.next();
                if (insn instanceof FrameNode) {
                    frames.add(new FrameData(method.instructions.indexOf(insn), (FrameNode) insn));
                }
            }
            return frames;
        }

        public List<FrameData> getFrames() {
            return this.frames;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return ClassInfo.this;
        }

        public boolean isAccessor() {
            return this.isAccessor;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(Object obj) {
            if (!(obj instanceof Method)) {
                return false;
            }
            return super.equals(obj);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$InterfaceMethod.class */
    public class InterfaceMethod extends Method {
        private final ClassInfo owner;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InterfaceMethod(Member member) {
            super(member);
            ClassInfo.this = this$0;
            this.owner = member.getOwner();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Method, org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return this.owner;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Method, org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getImplementor() {
            return ClassInfo.this;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Field.class */
    public class Field extends Member {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(Member member) {
            super(member);
            ClassInfo.this = this$0;
        }

        public Field(ClassInfo this$0, FieldNode field) {
            this(field, false);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(FieldNode field, boolean injected) {
            super(Member.Type.FIELD, field.name, field.desc, field.access, injected);
            ClassInfo.this = this$0;
            setUnique(Annotations.getVisible(field, Unique.class) != null);
            if (Annotations.getVisible(field, Shadow.class) != null) {
                boolean decoratedFinal = Annotations.getVisible(field, Final.class) != null;
                boolean decoratedMutable = Annotations.getVisible(field, Mutable.class) != null;
                setDecoratedFinal(decoratedFinal, decoratedMutable);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(String name, String desc, int access) {
            super(Member.Type.FIELD, name, desc, access, false);
            ClassInfo.this = this$0;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(String name, String desc, int access, boolean injected) {
            super(Member.Type.FIELD, name, desc, access, injected);
            ClassInfo.this = this$0;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return ClassInfo.this;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(Object obj) {
            if (!(obj instanceof Field)) {
                return false;
            }
            return super.equals(obj);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        protected String getDisplayFormat() {
            return "%s:%s";
        }
    }

    static {
        cache.put(JAVA_LANG_OBJECT, OBJECT);
    }

    private ClassInfo() {
        this.mixins = new HashSet();
        this.correspondingTypes = new HashMap();
        this.name = JAVA_LANG_OBJECT;
        this.superName = null;
        this.outerName = null;
        this.isProbablyStatic = true;
        this.methods = ImmutableSet.of(new Method("getClass", "()Ljava/lang/Class;"), new Method("hashCode", "()I"), new Method("equals", "(Ljava/lang/Object;)Z"), new Method("clone", "()Ljava/lang/Object;"), new Method("toString", "()Ljava/lang/String;"), new Method("notify", "()V"), new Method[]{new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V")});
        this.fields = Collections.emptySet();
        this.isInterface = false;
        this.interfaces = Collections.emptySet();
        this.access = 1;
        this.isMixin = false;
        this.mixin = null;
        this.methodMapper = null;
    }

    private ClassInfo(ClassNode classNode) {
        this.mixins = new HashSet();
        this.correspondingTypes = new HashMap();
        Profiler.Section timer = profiler.begin(1, "class.meta");
        try {
            this.name = classNode.name;
            this.superName = classNode.superName != null ? classNode.superName : JAVA_LANG_OBJECT;
            this.methods = new HashSet();
            this.fields = new HashSet();
            this.isInterface = (classNode.access & 512) != 0;
            this.interfaces = new HashSet();
            this.access = classNode.access;
            this.isMixin = classNode instanceof MixinInfo.MixinClassNode;
            this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode) classNode).getMixin() : null;
            this.interfaces.addAll(classNode.interfaces);
            for (MethodNode method : classNode.methods) {
                addMethod(method, this.isMixin);
            }
            boolean isProbablyStatic = true;
            String outerName = classNode.outerClass;
            for (FieldNode field : classNode.fields) {
                if ((field.access & 4096) != 0 && field.name.startsWith("this$")) {
                    isProbablyStatic = false;
                    if (outerName == null) {
                        outerName = field.desc;
                        if (outerName != null && outerName.startsWith("L")) {
                            outerName = outerName.substring(1, outerName.length() - 1);
                        }
                    }
                }
                this.fields.add(new Field(field, this.isMixin));
            }
            this.isProbablyStatic = isProbablyStatic;
            this.outerName = outerName;
            this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
            this.signature = ClassSignature.ofLazy(classNode);
            timer.end();
        } catch (Throwable th) {
            timer.end();
            throw th;
        }
    }

    public void addInterface(String iface) {
        this.interfaces.add(iface);
        getSignature().addInterface(iface);
    }

    public void addMethod(MethodNode method) {
        addMethod(method, true);
    }

    private void addMethod(MethodNode method, boolean injected) {
        if (!method.name.startsWith("<")) {
            this.methods.add(new Method(method, injected));
        }
    }

    public void addMixin(MixinInfo mixin) {
        if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixin.getClassName() + " because the target is a mixin");
        }
        this.mixins.add(mixin);
    }

    public Set<MixinInfo> getMixins() {
        return Collections.unmodifiableSet(this.mixins);
    }

    public boolean isMixin() {
        return this.isMixin;
    }

    public boolean isPublic() {
        return (this.access & 1) != 0;
    }

    public boolean isAbstract() {
        return (this.access & 1024) != 0;
    }

    public boolean isSynthetic() {
        return (this.access & 4096) != 0;
    }

    public boolean isProbablyStatic() {
        return this.isProbablyStatic;
    }

    public boolean isInner() {
        return this.outerName != null;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public Set<String> getInterfaces() {
        return Collections.unmodifiableSet(this.interfaces);
    }

    public String toString() {
        return this.name;
    }

    public MethodMapper getMethodMapper() {
        return this.methodMapper;
    }

    public int getAccess() {
        return this.access;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.name.replace('/', '.');
    }

    public String getSuperName() {
        return this.superName;
    }

    public ClassInfo getSuperClass() {
        if (this.superClass == null && this.superName != null) {
            this.superClass = forName(this.superName);
        }
        return this.superClass;
    }

    public String getOuterName() {
        return this.outerName;
    }

    public ClassInfo getOuterClass() {
        if (this.outerClass == null && this.outerName != null) {
            this.outerClass = forName(this.outerName);
        }
        return this.outerClass;
    }

    public ClassSignature getSignature() {
        return this.signature.wake();
    }

    public List<ClassInfo> getTargets() {
        if (this.mixin != null) {
            List<ClassInfo> targets = new ArrayList<>();
            targets.add(this);
            targets.addAll(this.mixin.getTargets());
            return targets;
        }
        return ImmutableList.of(this);
    }

    public Set<Method> getMethods() {
        return Collections.unmodifiableSet(this.methods);
    }

    public Set<Method> getInterfaceMethods(boolean includeMixins) {
        Set<Method> methods = new HashSet<>();
        ClassInfo supClass = addMethodsRecursive(methods, includeMixins);
        if (!this.isInterface) {
            while (supClass != null && supClass != OBJECT) {
                supClass = supClass.addMethodsRecursive(methods, includeMixins);
            }
        }
        Iterator<Method> it = methods.iterator();
        while (it.hasNext()) {
            if (!it.next().isAbstract()) {
                it.remove();
            }
        }
        return Collections.unmodifiableSet(methods);
    }

    private ClassInfo addMethodsRecursive(Set<Method> methods, boolean includeMixins) {
        if (this.isInterface) {
            for (Method method : this.methods) {
                if (!method.isAbstract()) {
                    methods.remove(method);
                }
                methods.add(method);
            }
        } else if (!this.isMixin && includeMixins) {
            for (MixinInfo mixin : this.mixins) {
                mixin.getClassInfo().addMethodsRecursive(methods, includeMixins);
            }
        }
        for (String iface : this.interfaces) {
            forName(iface).addMethodsRecursive(methods, includeMixins);
        }
        return getSuperClass();
    }

    public boolean hasSuperClass(String superClass) {
        return hasSuperClass(superClass, Traversal.NONE);
    }

    public boolean hasSuperClass(String superClass, Traversal traversal) {
        return JAVA_LANG_OBJECT.equals(superClass) || findSuperClass(superClass, traversal) != null;
    }

    public boolean hasSuperClass(ClassInfo superClass) {
        return hasSuperClass(superClass, Traversal.NONE, false);
    }

    public boolean hasSuperClass(ClassInfo superClass, Traversal traversal) {
        return hasSuperClass(superClass, traversal, false);
    }

    public boolean hasSuperClass(ClassInfo superClass, Traversal traversal, boolean includeInterfaces) {
        return OBJECT == superClass || findSuperClass(superClass.name, traversal, includeInterfaces) != null;
    }

    public ClassInfo findSuperClass(String superClass) {
        return findSuperClass(superClass, Traversal.NONE);
    }

    public ClassInfo findSuperClass(String superClass, Traversal traversal) {
        return findSuperClass(superClass, traversal, false, new HashSet());
    }

    public ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces) {
        if (OBJECT.name.equals(superClass)) {
            return null;
        }
        return findSuperClass(superClass, traversal, includeInterfaces, new HashSet());
    }

    private ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces, Set<String> traversed) {
        ClassInfo iface;
        ClassInfo superClassInfo = getSuperClass();
        if (superClassInfo != null) {
            for (ClassInfo superTarget : superClassInfo.getTargets()) {
                if (superClass.equals(superTarget.getName())) {
                    return superClassInfo;
                }
                ClassInfo found = superTarget.findSuperClass(superClass, traversal.next(), includeInterfaces, traversed);
                if (found != null) {
                    return found;
                }
            }
        }
        if (includeInterfaces && (iface = findInterface(superClass)) != null) {
            return iface;
        }
        if (traversal.canTraverse()) {
            for (MixinInfo mixin : this.mixins) {
                String mixinClassName = mixin.getClassName();
                if (!traversed.contains(mixinClassName)) {
                    traversed.add(mixinClassName);
                    ClassInfo mixinClass = mixin.getClassInfo();
                    if (superClass.equals(mixinClass.getName())) {
                        return mixinClass;
                    }
                    ClassInfo targetSuper = mixinClass.findSuperClass(superClass, Traversal.ALL, includeInterfaces, traversed);
                    if (targetSuper != null) {
                        return targetSuper;
                    }
                }
            }
            return null;
        }
        return null;
    }

    private ClassInfo findInterface(String superClass) {
        for (String ifaceName : getInterfaces()) {
            ClassInfo iface = forName(ifaceName);
            if (superClass.equals(ifaceName)) {
                return iface;
            }
            ClassInfo superIface = iface.findInterface(superClass);
            if (superIface != null) {
                return superIface;
            }
        }
        return null;
    }

    public ClassInfo findCorrespondingType(ClassInfo mixin) {
        if (mixin == null || !mixin.isMixin || this.isMixin) {
            return null;
        }
        ClassInfo correspondingType = this.correspondingTypes.get(mixin);
        if (correspondingType == null) {
            correspondingType = findSuperTypeForMixin(mixin);
            this.correspondingTypes.put(mixin, correspondingType);
        }
        return correspondingType;
    }

    private ClassInfo findSuperTypeForMixin(ClassInfo mixin) {
        ClassInfo classInfo = this;
        while (true) {
            ClassInfo superClass = classInfo;
            if (superClass != null && superClass != OBJECT) {
                for (MixinInfo minion : superClass.mixins) {
                    if (minion.getClassInfo().equals(mixin)) {
                        return superClass;
                    }
                }
                classInfo = superClass.getSuperClass();
            } else {
                return null;
            }
        }
    }

    public boolean hasMixinInHierarchy() {
        if (!this.isMixin) {
            return false;
        }
        ClassInfo superClass = getSuperClass();
        while (true) {
            ClassInfo supClass = superClass;
            if (supClass != null && supClass != OBJECT) {
                if (supClass.isMixin) {
                    return true;
                }
                superClass = supClass.getSuperClass();
            } else {
                return false;
            }
        }
    }

    public boolean hasMixinTargetInHierarchy() {
        if (this.isMixin) {
            return false;
        }
        ClassInfo superClass = getSuperClass();
        while (true) {
            ClassInfo supClass = superClass;
            if (supClass != null && supClass != OBJECT) {
                if (supClass.mixins.size() > 0) {
                    return true;
                }
                superClass = supClass.getSuperClass();
            } else {
                return false;
            }
        }
    }

    public Method findMethodInHierarchy(MethodNode method, SearchType searchType) {
        return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodNode method, SearchType searchType, int flags) {
        return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags);
    }

    public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType) {
        return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType, int flags) {
        return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags);
    }

    public Method findMethodInHierarchy(String name, String desc, SearchType searchType) {
        return findMethodInHierarchy(name, desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) {
        return findMethodInHierarchy(name, desc, searchType, traversal, 0);
    }

    public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) {
        return (Method) findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.METHOD);
    }

    public Field findFieldInHierarchy(FieldNode field, SearchType searchType) {
        return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldNode field, SearchType searchType, int flags) {
        return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags);
    }

    public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType) {
        return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType, int flags) {
        return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags);
    }

    public Field findFieldInHierarchy(String name, String desc, SearchType searchType) {
        return findFieldInHierarchy(name, desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) {
        return findFieldInHierarchy(name, desc, searchType, traversal, 0);
    }

    public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) {
        return (Field) findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.FIELD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <M extends Member> M findInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags, Member.Type type) {
        if (searchType == SearchType.ALL_CLASSES) {
            M member = (M) findMember(name, desc, flags, type);
            if (member != null) {
                return member;
            }
            if (traversal.canTraverse()) {
                for (MixinInfo mixin : this.mixins) {
                    Member findMember = mixin.getClassInfo().findMember(name, desc, flags, type);
                    if (findMember != null) {
                        return (M) cloneMember(findMember);
                    }
                }
            }
        }
        ClassInfo superClassInfo = getSuperClass();
        if (superClassInfo != null) {
            for (ClassInfo superTarget : superClassInfo.getTargets()) {
                M member2 = (M) superTarget.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & (-3), type);
                if (member2 != null) {
                    return member2;
                }
            }
        }
        if (type == Member.Type.METHOD) {
            if (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                for (String implemented : this.interfaces) {
                    ClassInfo iface = forName(implemented);
                    if (iface == null) {
                        logger.debug("Failed to resolve declared interface {} on {}", new Object[]{implemented, this.name});
                    } else {
                        M member3 = (M) iface.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & (-3), type);
                        if (member3 != null) {
                            return this.isInterface ? member3 : new InterfaceMethod(member3);
                        }
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private <M extends Member> M cloneMember(M member) {
        if (member instanceof Method) {
            return new Method(member);
        }
        return new Field(member);
    }

    public Method findMethod(MethodNode method) {
        return findMethod(method.name, method.desc, method.access);
    }

    public Method findMethod(MethodNode method, int flags) {
        return findMethod(method.name, method.desc, flags);
    }

    public Method findMethod(MethodInsnNode method) {
        return findMethod(method.name, method.desc, 0);
    }

    public Method findMethod(MethodInsnNode method, int flags) {
        return findMethod(method.name, method.desc, flags);
    }

    public Method findMethod(String name, String desc, int flags) {
        return (Method) findMember(name, desc, flags, Member.Type.METHOD);
    }

    public Field findField(FieldNode field) {
        return findField(field.name, field.desc, field.access);
    }

    public Field findField(FieldInsnNode field, int flags) {
        return findField(field.name, field.desc, flags);
    }

    public Field findField(String name, String desc, int flags) {
        return (Field) findMember(name, desc, flags, Member.Type.FIELD);
    }

    private <M extends Member> M findMember(String name, String desc, int flags, Member.Type memberType) {
        Set<M> members = memberType == Member.Type.METHOD ? this.methods : this.fields;
        for (M member : members) {
            if (member.equals(name, desc) && member.matchesFlags(flags)) {
                return member;
            }
        }
        return null;
    }

    public boolean equals(Object other) {
        if (!(other instanceof ClassInfo)) {
            return false;
        }
        return ((ClassInfo) other).name.equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public static ClassInfo fromClassNode(ClassNode classNode) {
        ClassInfo info = cache.get(classNode.name);
        if (info == null) {
            info = new ClassInfo(classNode);
            cache.put(classNode.name, info);
        }
        return info;
    }

    public static ClassInfo forName(String className) {
        String className2 = className.replace('.', '/');
        ClassInfo info = cache.get(className2);
        if (info == null) {
            try {
                ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(className2);
                info = new ClassInfo(classNode);
            } catch (Exception ex) {
                logger.catching(Level.TRACE, ex);
                logger.warn("Error loading class: {} ({}: {})", new Object[]{className2, ex.getClass().getName(), ex.getMessage()});
            }
            cache.put(className2, info);
            logger.trace("Added class metadata for {} to metadata cache", new Object[]{className2});
        }
        return info;
    }

    public static ClassInfo forType(Type type) {
        if (type.getSort() == 9) {
            return forType(type.getElementType());
        }
        if (type.getSort() < 9) {
            return null;
        }
        return forName(type.getClassName().replace('.', '/'));
    }

    public static ClassInfo getCommonSuperClass(String type1, String type2) {
        if (type1 == null || type2 == null) {
            return OBJECT;
        }
        return getCommonSuperClass(forName(type1), forName(type2));
    }

    public static ClassInfo getCommonSuperClass(Type type1, Type type2) {
        if (type1 == null || type2 == null || type1.getSort() != 10 || type2.getSort() != 10) {
            return OBJECT;
        }
        return getCommonSuperClass(forType(type1), forType(type2));
    }

    private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2) {
        return getCommonSuperClass(type1, type2, false);
    }

    public static ClassInfo getCommonSuperClassOrInterface(String type1, String type2) {
        if (type1 == null || type2 == null) {
            return OBJECT;
        }
        return getCommonSuperClassOrInterface(forName(type1), forName(type2));
    }

    public static ClassInfo getCommonSuperClassOrInterface(Type type1, Type type2) {
        if (type1 == null || type2 == null || type1.getSort() != 10 || type2.getSort() != 10) {
            return OBJECT;
        }
        return getCommonSuperClassOrInterface(forType(type1), forType(type2));
    }

    public static ClassInfo getCommonSuperClassOrInterface(ClassInfo type1, ClassInfo type2) {
        return getCommonSuperClass(type1, type2, true);
    }

    private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2, boolean includeInterfaces) {
        if (type1.hasSuperClass(type2, Traversal.NONE, includeInterfaces)) {
            return type2;
        }
        if (type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces)) {
            return type1;
        }
        if (type1.isInterface() || type2.isInterface()) {
            return OBJECT;
        }
        do {
            type1 = type1.getSuperClass();
            if (type1 == null) {
                return OBJECT;
            }
        } while (!type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces));
        return type1;
    }
}
