package net.ccbluex.liquidbounce.utils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/AnimationUtils.class */
public final class AnimationUtils {
    public static double animate(double target, double current, double speed) {
        if (current == target) {
            return current;
        }
        boolean larger = target > current;
        if (speed < 0.0d) {
            speed = 0.0d;
        } else if (speed > 1.0d) {
            speed = 1.0d;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1d) {
            factor = 0.1d;
        }
        if (larger) {
            current += factor;
            if (current >= target) {
                current = target;
            }
        } else if (target < current) {
            current -= factor;
            if (current <= target) {
                current = target;
            }
        }
        return current;
    }

    public static float animate(float target, float current, float speed) {
        if (current == target) {
            return current;
        }
        boolean larger = target > current;
        if (speed < 0.0f) {
            speed = 0.0f;
        } else if (speed > 1.0f) {
            speed = 1.0f;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1d) {
            factor = 0.1d;
        }
        if (larger) {
            current += (float) factor;
            if (current >= target) {
                current = target;
            }
        } else if (target < current) {
            current -= (float) factor;
            if (current <= target) {
                current = target;
            }
        }
        return current;
    }

    public static double changer(double current, double add, double min, double max) {
        double current2 = current + add;
        if (current2 > max) {
            current2 = max;
        }
        if (current2 < min) {
            current2 = min;
        }
        return current2;
    }

    public static float changer(float current, float add, float min, float max) {
        float current2 = current + add;
        if (current2 > max) {
            current2 = max;
        }
        if (current2 < min) {
            current2 = min;
        }
        return current2;
    }
}
