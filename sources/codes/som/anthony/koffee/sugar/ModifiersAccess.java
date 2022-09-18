package codes.som.anthony.koffee.sugar;

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
import codes.som.anthony.koffee.modifiers.ModifiersKt;
import codes.som.anthony.koffee.modifiers.deprecated;
import codes.som.anthony.koffee.modifiers.mandated;
import codes.som.anthony.koffee.modifiers.open;
import codes.som.anthony.koffee.modifiers.package_private;
import codes.som.anthony.koffee.modifiers.static_phase;
import codes.som.anthony.koffee.modifiers.strict;
import codes.som.anthony.koffee.modifiers.synthetic;
import codes.som.anthony.koffee.modifiers.transitive;
import codes.som.anthony.koffee.modifiers.varargs;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModifiersAccess.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018��2\u00020\u0001R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0005R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u0002018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u0014\u00104\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0014\u00108\u001a\u0002098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0014\u0010<\u001a\u00020=8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0014\u0010@\u001a\u00020A8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0014\u0010D\u001a\u00020E8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0014\u0010H\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0014\u0010L\u001a\u00020M8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u0014\u0010P\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010\tR\u0014\u0010R\u001a\u00020S8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010UR\u0014\u0010V\u001a\u00020W8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010YR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u0014\u0010^\u001a\u00020_8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010aR\u0014\u0010b\u001a\u00020c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bd\u0010eR\u0014\u0010f\u001a\u00020g8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bh\u0010i¨\u0006j"}, m53d2 = {"Lcodes/som/anthony/koffee/sugar/ModifiersAccess;", "", "_interface", "Lcodes/som/anthony/koffee/modifiers/interface;", "get_interface", "()Lcodes/som/anthony/koffee/modifiers/interface;", "_super", "Lcodes/som/anthony/koffee/modifiers/super;", "get_super", "()Lcodes/som/anthony/koffee/modifiers/super;", "abstract", "Lcodes/som/anthony/koffee/modifiers/abstract;", "getAbstract", "()Lcodes/som/anthony/koffee/modifiers/abstract;", "annotation", "Lcodes/som/anthony/koffee/modifiers/annotation;", "getAnnotation", "()Lcodes/som/anthony/koffee/modifiers/annotation;", "bridge", "Lcodes/som/anthony/koffee/modifiers/bridge;", "getBridge", "()Lcodes/som/anthony/koffee/modifiers/bridge;", "deprecated", "Lcodes/som/anthony/koffee/modifiers/deprecated;", "getDeprecated", "()Lcodes/som/anthony/koffee/modifiers/deprecated;", "enum", "Lcodes/som/anthony/koffee/modifiers/enum;", "getEnum", "()Lcodes/som/anthony/koffee/modifiers/enum;", "final", "Lcodes/som/anthony/koffee/modifiers/final;", "getFinal", "()Lcodes/som/anthony/koffee/modifiers/final;", "interface", "getInterface", "mandated", "Lcodes/som/anthony/koffee/modifiers/mandated;", "getMandated", "()Lcodes/som/anthony/koffee/modifiers/mandated;", "module", "Lcodes/som/anthony/koffee/modifiers/module;", "getModule", "()Lcodes/som/anthony/koffee/modifiers/module;", "native", "Lcodes/som/anthony/koffee/modifiers/native;", "getNative", "()Lcodes/som/anthony/koffee/modifiers/native;", "open", "Lcodes/som/anthony/koffee/modifiers/open;", "getOpen", "()Lcodes/som/anthony/koffee/modifiers/open;", "package_private", "Lcodes/som/anthony/koffee/modifiers/package_private;", "getPackage_private", "()Lcodes/som/anthony/koffee/modifiers/package_private;", "private", "Lcodes/som/anthony/koffee/modifiers/private;", "getPrivate", "()Lcodes/som/anthony/koffee/modifiers/private;", "protected", "Lcodes/som/anthony/koffee/modifiers/protected;", "getProtected", "()Lcodes/som/anthony/koffee/modifiers/protected;", "public", "Lcodes/som/anthony/koffee/modifiers/public;", "getPublic", "()Lcodes/som/anthony/koffee/modifiers/public;", "static", "Lcodes/som/anthony/koffee/modifiers/static;", "getStatic", "()Lcodes/som/anthony/koffee/modifiers/static;", "static_phase", "Lcodes/som/anthony/koffee/modifiers/static_phase;", "getStatic_phase", "()Lcodes/som/anthony/koffee/modifiers/static_phase;", "strict", "Lcodes/som/anthony/koffee/modifiers/strict;", "getStrict", "()Lcodes/som/anthony/koffee/modifiers/strict;", "super", "getSuper", "synchronized", "Lcodes/som/anthony/koffee/modifiers/synchronized;", "getSynchronized", "()Lcodes/som/anthony/koffee/modifiers/synchronized;", "synthetic", "Lcodes/som/anthony/koffee/modifiers/synthetic;", "getSynthetic", "()Lcodes/som/anthony/koffee/modifiers/synthetic;", "transient", "Lcodes/som/anthony/koffee/modifiers/transient;", "getTransient", "()Lcodes/som/anthony/koffee/modifiers/transient;", "transitive", "Lcodes/som/anthony/koffee/modifiers/transitive;", "getTransitive", "()Lcodes/som/anthony/koffee/modifiers/transitive;", "varargs", "Lcodes/som/anthony/koffee/modifiers/varargs;", "getVarargs", "()Lcodes/som/anthony/koffee/modifiers/varargs;", "volatile", "Lcodes/som/anthony/koffee/modifiers/volatile;", "getVolatile", "()Lcodes/som/anthony/koffee/modifiers/volatile;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/sugar/ModifiersAccess.class */
public interface ModifiersAccess {
    @NotNull
    package_private getPackage_private();

    @NotNull
    Cpublic getPublic();

    @NotNull
    Cprivate getPrivate();

    @NotNull
    Cprotected getProtected();

    @NotNull
    Cstatic getStatic();

    @NotNull
    Cfinal getFinal();

    @NotNull
    Csuper getSuper();

    @NotNull
    Csuper get_super();

    @NotNull
    Csynchronized getSynchronized();

    @NotNull
    open getOpen();

    @NotNull
    transitive getTransitive();

    @NotNull
    Cvolatile getVolatile();

    @NotNull
    C0001bridge getBridge();

    @NotNull
    static_phase getStatic_phase();

    @NotNull
    varargs getVarargs();

    @NotNull
    Ctransient getTransient();

    @NotNull
    Cnative getNative();

    @NotNull
    Cinterface getInterface();

    @NotNull
    Cinterface get_interface();

    @NotNull
    Cabstract getAbstract();

    @NotNull
    strict getStrict();

    @NotNull
    synthetic getSynthetic();

    @NotNull
    C0000annotation getAnnotation();

    @NotNull
    Cenum getEnum();

    @NotNull
    mandated getMandated();

    @NotNull
    C0002module getModule();

    @NotNull
    deprecated getDeprecated();

    /* compiled from: ModifiersAccess.kt */
    @Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 3)
    /* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/sugar/ModifiersAccess$DefaultImpls.class */
    public static final class DefaultImpls {
        @NotNull
        public static package_private getPackage_private(ModifiersAccess $this) {
            return package_private.INSTANCE;
        }

        @NotNull
        public static Cpublic getPublic(ModifiersAccess $this) {
            return Cpublic.INSTANCE;
        }

        @NotNull
        public static Cprivate getPrivate(ModifiersAccess $this) {
            return Cprivate.INSTANCE;
        }

        @NotNull
        public static Cprotected getProtected(ModifiersAccess $this) {
            return Cprotected.INSTANCE;
        }

        @NotNull
        public static Cstatic getStatic(ModifiersAccess $this) {
            return Cstatic.INSTANCE;
        }

        @NotNull
        public static Cfinal getFinal(ModifiersAccess $this) {
            return Cfinal.INSTANCE;
        }

        @NotNull
        public static Csuper getSuper(ModifiersAccess $this) {
            return Csuper.INSTANCE;
        }

        @NotNull
        public static Csuper get_super(ModifiersAccess $this) {
            return ModifiersKt.get_super();
        }

        @NotNull
        public static Csynchronized getSynchronized(ModifiersAccess $this) {
            return Csynchronized.INSTANCE;
        }

        @NotNull
        public static open getOpen(ModifiersAccess $this) {
            return open.INSTANCE;
        }

        @NotNull
        public static transitive getTransitive(ModifiersAccess $this) {
            return transitive.INSTANCE;
        }

        @NotNull
        public static Cvolatile getVolatile(ModifiersAccess $this) {
            return Cvolatile.INSTANCE;
        }

        @NotNull
        public static C0001bridge getBridge(ModifiersAccess $this) {
            return C0001bridge.INSTANCE;
        }

        @NotNull
        public static static_phase getStatic_phase(ModifiersAccess $this) {
            return static_phase.INSTANCE;
        }

        @NotNull
        public static varargs getVarargs(ModifiersAccess $this) {
            return varargs.INSTANCE;
        }

        @NotNull
        public static Ctransient getTransient(ModifiersAccess $this) {
            return Ctransient.INSTANCE;
        }

        @NotNull
        public static Cnative getNative(ModifiersAccess $this) {
            return Cnative.INSTANCE;
        }

        @NotNull
        public static Cinterface getInterface(ModifiersAccess $this) {
            return Cinterface.INSTANCE;
        }

        @NotNull
        public static Cinterface get_interface(ModifiersAccess $this) {
            return ModifiersKt.get_interface();
        }

        @NotNull
        public static Cabstract getAbstract(ModifiersAccess $this) {
            return Cabstract.INSTANCE;
        }

        @NotNull
        public static strict getStrict(ModifiersAccess $this) {
            return strict.INSTANCE;
        }

        @NotNull
        public static synthetic getSynthetic(ModifiersAccess $this) {
            return synthetic.INSTANCE;
        }

        @NotNull
        public static C0000annotation getAnnotation(ModifiersAccess $this) {
            return C0000annotation.INSTANCE;
        }

        @NotNull
        public static Cenum getEnum(ModifiersAccess $this) {
            return Cenum.INSTANCE;
        }

        @NotNull
        public static mandated getMandated(ModifiersAccess $this) {
            return mandated.INSTANCE;
        }

        @NotNull
        public static C0002module getModule(ModifiersAccess $this) {
            return C0002module.INSTANCE;
        }

        @NotNull
        public static deprecated getDeprecated(ModifiersAccess $this) {
            return deprecated.INSTANCE;
        }
    }
}
