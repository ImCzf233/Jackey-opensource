package codes.som.anthony.koffee;

import codes.som.anthony.koffee.modifiers.C0000annotation;
import codes.som.anthony.koffee.modifiers.C0001bridge;
import codes.som.anthony.koffee.modifiers.C0002module;
import codes.som.anthony.koffee.modifiers.Cabstract;
import codes.som.anthony.koffee.modifiers.Cenum;
import codes.som.anthony.koffee.modifiers.Cfinal;
import codes.som.anthony.koffee.modifiers.Cinterface;
import codes.som.anthony.koffee.modifiers.Cnative;
import codes.som.anthony.koffee.modifiers.Cprivate;
import codes.som.anthony.koffee.modifiers.Cprotected;
import codes.som.anthony.koffee.modifiers.Cpublic;
import codes.som.anthony.koffee.modifiers.Cstatic;
import codes.som.anthony.koffee.modifiers.Csuper;
import codes.som.anthony.koffee.modifiers.Csynchronized;
import codes.som.anthony.koffee.modifiers.Ctransient;
import codes.som.anthony.koffee.modifiers.Cvolatile;
import codes.som.anthony.koffee.modifiers.Modifiers;
import codes.som.anthony.koffee.modifiers.deprecated;
import codes.som.anthony.koffee.modifiers.mandated;
import codes.som.anthony.koffee.modifiers.open;
import codes.som.anthony.koffee.modifiers.package_private;
import codes.som.anthony.koffee.modifiers.static_phase;
import codes.som.anthony.koffee.modifiers.strict;
import codes.som.anthony.koffee.modifiers.synthetic;
import codes.som.anthony.koffee.modifiers.transitive;
import codes.som.anthony.koffee.modifiers.varargs;
import codes.som.anthony.koffee.sugar.ModifiersAccess;
import codes.som.anthony.koffee.sugar.TypesAccess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* compiled from: ClassAssembly.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��2\u00020\u00012\u00020\u0002B9\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0010\u0010\n\u001a\f\u0012\b\u0012\u00060\fj\u0002`\r0\u000b¢\u0006\u0002\u0010\u000eB\u000f\b��\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J:\u0010-\u001a\u00020.2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010/\u001a\u00060\fj\u0002`\r2\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\fJz\u00101\u001a\u0002022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u00103\u001a\u00060\fj\u0002`\r2\u001a\u00104\u001a\u000e\u0012\n\b\u0001\u0012\u00060\fj\u0002`\r05\"\u00060\fj\u0002`\r2\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u00106\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u0001052\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020:08¢\u0006\u0002\b;¢\u0006\u0002\u0010<R$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R4\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u000b2\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u000b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b#\u0010$R(\u0010%\u001a\u0004\u0018\u00010\u00172\b\u0010\u0012\u001a\u0004\u0018\u00010\u00178F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b&\u0010$\"\u0004\b'\u0010(R$\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,¨\u0006="}, m53d2 = {"Lcodes/som/anthony/koffee/ClassAssembly;", "Lcodes/som/anthony/koffee/sugar/ModifiersAccess;", "Lcodes/som/anthony/koffee/sugar/TypesAccess;", "access", "Lcodes/som/anthony/koffee/modifiers/Modifiers;", "name", "", "version", "", "superName", "interfaces", "", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "(Lcodes/som/anthony/koffee/modifiers/Modifiers;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;)V", "node", "Lorg/objectweb/asm/tree/ClassNode;", "(Lorg/objectweb/asm/tree/ClassNode;)V", "value", "getAccess", "()Lcodes/som/anthony/koffee/modifiers/Modifiers;", "setAccess", "(Lcodes/som/anthony/koffee/modifiers/Modifiers;)V", "Lorg/objectweb/asm/Type;", "getInterfaces", "()Ljava/util/List;", "setInterfaces", "(Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getNode", "()Lorg/objectweb/asm/tree/ClassNode;", "self", "getSelf", "()Lorg/objectweb/asm/Type;", "superClass", "getSuperClass", "setSuperClass", "(Lorg/objectweb/asm/Type;)V", "getVersion", "()I", "setVersion", "(I)V", "field", "Lorg/objectweb/asm/tree/FieldNode;", "type", "signature", "method", "Lorg/objectweb/asm/tree/MethodNode;", "returnType", "parameterTypes", "", "exceptions", "routine", "Lkotlin/Function1;", "Lcodes/som/anthony/koffee/MethodAssembly;", "", "Lkotlin/ExtensionFunctionType;", "(Lcodes/som/anthony/koffee/modifiers/Modifiers;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;[Lorg/objectweb/asm/Type;Lkotlin/jvm/functions/Function1;)Lorg/objectweb/asm/tree/MethodNode;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/ClassAssembly.class */
public final class ClassAssembly implements ModifiersAccess, TypesAccess {
    @NotNull
    private final ClassNode node;

    @NotNull
    public final ClassNode getNode() {
        return this.node;
    }

    public ClassAssembly(@NotNull ClassNode node) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        this.node = node;
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public package_private getPackage_private() {
        return ModifiersAccess.DefaultImpls.getPackage_private(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cpublic getPublic() {
        return ModifiersAccess.DefaultImpls.getPublic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cprivate getPrivate() {
        return ModifiersAccess.DefaultImpls.getPrivate(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cprotected getProtected() {
        return ModifiersAccess.DefaultImpls.getProtected(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cstatic getStatic() {
        return ModifiersAccess.DefaultImpls.getStatic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cfinal getFinal() {
        return ModifiersAccess.DefaultImpls.getFinal(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csuper getSuper() {
        return ModifiersAccess.DefaultImpls.getSuper(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csuper get_super() {
        return ModifiersAccess.DefaultImpls.get_super(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Csynchronized getSynchronized() {
        return ModifiersAccess.DefaultImpls.getSynchronized(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public open getOpen() {
        return ModifiersAccess.DefaultImpls.getOpen(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public transitive getTransitive() {
        return ModifiersAccess.DefaultImpls.getTransitive(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cvolatile getVolatile() {
        return ModifiersAccess.DefaultImpls.getVolatile(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0001bridge getBridge() {
        return ModifiersAccess.DefaultImpls.getBridge(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public static_phase getStatic_phase() {
        return ModifiersAccess.DefaultImpls.getStatic_phase(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public varargs getVarargs() {
        return ModifiersAccess.DefaultImpls.getVarargs(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Ctransient getTransient() {
        return ModifiersAccess.DefaultImpls.getTransient(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cnative getNative() {
        return ModifiersAccess.DefaultImpls.getNative(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cinterface getInterface() {
        return ModifiersAccess.DefaultImpls.getInterface(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cinterface get_interface() {
        return ModifiersAccess.DefaultImpls.get_interface(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cabstract getAbstract() {
        return ModifiersAccess.DefaultImpls.getAbstract(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public strict getStrict() {
        return ModifiersAccess.DefaultImpls.getStrict(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public synthetic getSynthetic() {
        return ModifiersAccess.DefaultImpls.getSynthetic(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0000annotation getAnnotation() {
        return ModifiersAccess.DefaultImpls.getAnnotation(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public Cenum getEnum() {
        return ModifiersAccess.DefaultImpls.getEnum(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public mandated getMandated() {
        return ModifiersAccess.DefaultImpls.getMandated(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public C0002module getModule() {
        return ModifiersAccess.DefaultImpls.getModule(this);
    }

    @Override // codes.som.anthony.koffee.sugar.ModifiersAccess
    @NotNull
    public deprecated getDeprecated() {
        return ModifiersAccess.DefaultImpls.getDeprecated(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getVoid() {
        return TypesAccess.DefaultImpls.getVoid(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getChar() {
        return TypesAccess.DefaultImpls.getChar(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getByte() {
        return TypesAccess.DefaultImpls.getByte(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getShort() {
        return TypesAccess.DefaultImpls.getShort(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getInt() {
        return TypesAccess.DefaultImpls.getInt(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getFloat() {
        return TypesAccess.DefaultImpls.getFloat(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getLong() {
        return TypesAccess.DefaultImpls.getLong(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getDouble() {
        return TypesAccess.DefaultImpls.getDouble(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    public Type getBoolean() {
        return TypesAccess.DefaultImpls.getBoolean(this);
    }

    @Override // codes.som.anthony.koffee.sugar.TypesAccess
    @NotNull
    public Type coerceType(@NotNull Object value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return TypesAccess.DefaultImpls.coerceType(this, value);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ClassAssembly(@org.jetbrains.annotations.NotNull codes.som.anthony.koffee.modifiers.Modifiers r6, @org.jetbrains.annotations.NotNull java.lang.String r7, int r8, @org.jetbrains.annotations.NotNull java.lang.String r9, @org.jetbrains.annotations.NotNull java.util.List<? extends java.lang.Object> r10) {
        /*
            r5 = this;
            r0 = r6
            java.lang.String r1 = "access"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r7
            java.lang.String r1 = "name"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r9
            java.lang.String r1 = "superName"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r10
            java.lang.String r1 = "interfaces"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r5
            org.objectweb.asm.tree.ClassNode r1 = new org.objectweb.asm.tree.ClassNode
            r2 = r1
            r3 = 458752(0x70000, float:6.42848E-40)
            r2.<init>(r3)
            r11 = r1
            r28 = r0
            r0 = 0
            r12 = r0
            r0 = 0
            r13 = r0
            r0 = r11
            r14 = r0
            r0 = 0
            r15 = r0
            r0 = r14
            r1 = r6
            int r1 = r1.getAccess()
            r0.access = r1
            r0 = r14
            r1 = r7
            r0.name = r1
            r0 = r14
            r1 = r8
            r0.version = r1
            r0 = r14
            r1 = r9
            r0.superName = r1
            r0 = r14
            r1 = r10
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r16 = r1
            r17 = r0
            r0 = 0
            r18 = r0
            r0 = r16
            r19 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = r16
            r3 = 10
            int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r3)
            r1.<init>(r2)
            java.util.Collection r0 = (java.util.Collection) r0
            r20 = r0
            r0 = 0
            r21 = r0
            r0 = r19
            java.util.Iterator r0 = r0.iterator()
            r22 = r0
        L84:
            r0 = r22
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto Lb9
            r0 = r22
            java.lang.Object r0 = r0.next()
            r23 = r0
            r0 = r20
            r1 = r23
            r24 = r1
            r25 = r0
            r0 = 0
            r26 = r0
            r0 = r24
            org.objectweb.asm.Type r0 = codes.som.anthony.koffee.types.Types.coerceType(r0)
            java.lang.String r0 = r0.getInternalName()
            r27 = r0
            r0 = r25
            r1 = r27
            boolean r0 = r0.add(r1)
            goto L84
        Lb9:
            r0 = r20
            java.util.List r0 = (java.util.List) r0
            r25 = r0
            r0 = r17
            r1 = r25
            r0.interfaces = r1
            r0 = r11
            r29 = r0
            r0 = r28
            r1 = r29
            r0.<init>(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: codes.som.anthony.koffee.ClassAssembly.<init>(codes.som.anthony.koffee.modifiers.Modifiers, java.lang.String, int, java.lang.String, java.util.List):void");
    }

    @NotNull
    public final Modifiers getAccess() {
        return new Modifiers(this.node.access);
    }

    public final void setAccess(@NotNull Modifiers value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.node.access = value.getAccess();
    }

    @NotNull
    public final String getName() {
        String str = this.node.name;
        Intrinsics.checkExpressionValueIsNotNull(str, "node.name");
        return str;
    }

    public final void setName(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.node.name = value;
    }

    public final int getVersion() {
        return this.node.version;
    }

    public final void setVersion(int value) {
        this.node.version = value;
    }

    @Nullable
    public final Type getSuperClass() {
        Object p1 = this.node.superName;
        if (p1 != null) {
            return coerceType(p1);
        }
        return null;
    }

    public final void setSuperClass(@Nullable Type value) {
        this.node.superName = value != null ? value.getInternalName() : null;
    }

    @Nullable
    public final List<Type> getInterfaces() {
        Iterable iterable = this.node.interfaces;
        if (iterable != null) {
            Iterable $this$map$iv = iterable;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                destination$iv$iv.add(coerceType(item$iv$iv));
            }
            return (List) destination$iv$iv;
        }
        return null;
    }

    public final void setInterfaces(@Nullable List<Type> list) {
        ArrayList arrayList;
        ClassNode classNode = this.node;
        if (list != null) {
            List<Type> $this$map$iv = list;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                Type it = (Type) item$iv$iv;
                destination$iv$iv.add(it.getInternalName());
            }
            ArrayList arrayList2 = (List) destination$iv$iv;
            classNode = classNode;
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        classNode.interfaces = arrayList;
    }

    @NotNull
    public final Type getSelf() {
        String str = this.node.name;
        Intrinsics.checkExpressionValueIsNotNull(str, "node.name");
        return coerceType(str);
    }

    @NotNull
    public static /* synthetic */ FieldNode field$default(ClassAssembly classAssembly, Modifiers modifiers, String str, Object obj, String str2, Object obj2, int i, Object obj3) {
        if ((i & 8) != 0) {
            str2 = null;
        }
        if ((i & 16) != 0) {
            obj2 = null;
        }
        return classAssembly.field(modifiers, str, obj, str2, obj2);
    }

    @NotNull
    public final FieldNode field(@NotNull Modifiers access, @NotNull String name, @NotNull Object type, @Nullable String signature, @Nullable Object value) {
        Intrinsics.checkParameterIsNotNull(access, "access");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(type, "type");
        FieldNode fieldNode = new FieldNode(458752, access.getAccess(), name, coerceType(type).getDescriptor(), signature, value);
        this.node.fields.add(fieldNode);
        return fieldNode;
    }

    @NotNull
    public static /* synthetic */ MethodNode method$default(ClassAssembly classAssembly, Modifiers modifiers, String str, Object obj, Object[] objArr, String str2, Type[] typeArr, Function1 function1, int i, Object obj2) {
        if ((i & 16) != 0) {
            str2 = null;
        }
        if ((i & 32) != 0) {
            typeArr = null;
        }
        return classAssembly.method(modifiers, str, obj, objArr, str2, typeArr, function1);
    }

    @NotNull
    public final MethodNode method(@NotNull Modifiers access, @NotNull String name, @NotNull Object returnType, @NotNull Object[] parameterTypes, @Nullable String signature, @Nullable Type[] exceptions, @NotNull Function1<? super MethodAssembly, Unit> routine) {
        String[] strArr;
        Intrinsics.checkParameterIsNotNull(access, "access");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(routine, "routine");
        Type coerceType = coerceType(returnType);
        Collection destination$iv$iv = new ArrayList(parameterTypes.length);
        for (Object item$iv$iv : parameterTypes) {
            destination$iv$iv.add(coerceType(item$iv$iv));
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new Type[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Type[] typeArr = (Type[]) array;
        String descriptor = Type.getMethodDescriptor(coerceType, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        int i = 458752;
        int access2 = access.getAccess();
        String str = name;
        String str2 = descriptor;
        String str3 = signature;
        if (exceptions != null) {
            Collection destination$iv$iv2 = new ArrayList(exceptions.length);
            for (Type type : exceptions) {
                destination$iv$iv2.add(type.getInternalName());
            }
            Collection $this$toTypedArray$iv2 = (List) destination$iv$iv2;
            Object[] array2 = $this$toTypedArray$iv2.toArray(new String[0]);
            if (array2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            i = 458752;
            access2 = access2;
            str = str;
            str2 = str2;
            str3 = str3;
            strArr = (String[]) array2;
        } else {
            strArr = null;
        }
        int i2 = i;
        MethodNode methodNode = new MethodNode(i2, access2, str, str2, str3, strArr);
        MethodAssembly methodAssembly = new MethodAssembly(methodNode);
        routine.invoke(methodAssembly);
        this.node.methods.add(methodNode);
        return methodNode;
    }
}
