package jdk.nashorn.internal.objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction.class */
public @interface SpecializedFunction {
    String name() default "";

    Class<?> linkLogic() default LinkLogic.Empty.class;

    boolean isConstructor() default false;

    boolean isOptimistic() default false;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction$LinkLogic.class */
    public static abstract class LinkLogic {
        public static final LinkLogic EMPTY_INSTANCE = new Empty();

        public abstract boolean canLink(Object obj, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest);

        /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction$LinkLogic$Empty.class */
        private static final class Empty extends LinkLogic {
            private Empty() {
            }

            @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
            public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
                return true;
            }

            @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
            public boolean isEmpty() {
                return true;
            }
        }

        public static Class<? extends LinkLogic> getEmptyLinkLogicClass() {
            return Empty.class;
        }

        public Class<? extends Throwable> getRelinkException() {
            return null;
        }

        public static boolean isEmpty(Class<? extends LinkLogic> clazz) {
            return clazz == Empty.class;
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean needsGuard(Object self) {
            return true;
        }

        public boolean needsGuard(Object self, Object... args) {
            return true;
        }

        public MethodHandle getGuard() {
            return null;
        }

        public boolean checkLinkable(Object self, CallSiteDescriptor desc, LinkRequest request) {
            return canLink(self, desc, request);
        }
    }
}
