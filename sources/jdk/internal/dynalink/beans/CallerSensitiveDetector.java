package jdk.internal.dynalink.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import sun.reflect.CallerSensitive;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector.class */
public class CallerSensitiveDetector {
    private static final DetectionStrategy DETECTION_STRATEGY = getDetectionStrategy();

    public static boolean isCallerSensitive(AccessibleObject ao) {
        return DETECTION_STRATEGY.isCallerSensitive(ao);
    }

    private static DetectionStrategy getDetectionStrategy() {
        try {
            return new PrivilegedDetectionStrategy();
        } catch (Throwable th) {
            return new UnprivilegedDetectionStrategy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$DetectionStrategy.class */
    public static abstract class DetectionStrategy {
        abstract boolean isCallerSensitive(AccessibleObject accessibleObject);

        private DetectionStrategy() {
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$PrivilegedDetectionStrategy.class */
    public static class PrivilegedDetectionStrategy extends DetectionStrategy {
        private static final Class<? extends Annotation> CALLER_SENSITIVE_ANNOTATION_CLASS = CallerSensitive.class;

        private PrivilegedDetectionStrategy() {
            super();
        }

        @Override // jdk.internal.dynalink.beans.CallerSensitiveDetector.DetectionStrategy
        boolean isCallerSensitive(AccessibleObject ao) {
            return ao.getAnnotation(CALLER_SENSITIVE_ANNOTATION_CLASS) != null;
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$UnprivilegedDetectionStrategy.class */
    public static class UnprivilegedDetectionStrategy extends DetectionStrategy {
        private static final String CALLER_SENSITIVE_ANNOTATION_STRING = "@sun.reflect.CallerSensitive()";

        private UnprivilegedDetectionStrategy() {
            super();
        }

        @Override // jdk.internal.dynalink.beans.CallerSensitiveDetector.DetectionStrategy
        boolean isCallerSensitive(AccessibleObject o) {
            Annotation[] annotations;
            for (Annotation a : o.getAnnotations()) {
                if (String.valueOf(a).equals(CALLER_SENSITIVE_ANNOTATION_STRING)) {
                    return true;
                }
            }
            return false;
        }
    }
}
