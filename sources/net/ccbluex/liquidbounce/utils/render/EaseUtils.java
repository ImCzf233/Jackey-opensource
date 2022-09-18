package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;

/* compiled from: EaseUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b \bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010!\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¨\u0006$"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils;", "", "()V", "easeInBack", "", "x", "easeInBounce", "easeInCirc", "easeInCubic", "easeInElastic", "easeInExpo", "easeInOutBack", "easeInOutBounce", "easeInOutCirc", "easeInOutCubic", "easeInOutElastic", "easeInOutExpo", "easeInOutQuad", "easeInOutQuart", "easeInOutQuint", "easeInOutSine", "easeInQuad", "easeInQuart", "easeInQuint", "easeInSine", "easeOutBack", "easeOutBounce", "animeX", "easeOutCirc", "easeOutCubic", "easeOutElastic", "easeOutExpo", "easeOutQuad", "easeOutQuart", "easeOutQuint", "easeOutSine", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/EaseUtils.class */
public final class EaseUtils {
    @NotNull
    public static final EaseUtils INSTANCE = new EaseUtils();

    private EaseUtils() {
    }

    @JvmStatic
    public static final double easeInSine(double x) {
        return 1 - Math.cos((x * 3.141592653589793d) / 2);
    }

    @JvmStatic
    public static final double easeOutSine(double x) {
        return Math.sin((x * 3.141592653589793d) / 2);
    }

    @JvmStatic
    public static final double easeInOutSine(double x) {
        return (-(Math.cos(3.141592653589793d * x) - 1)) / 2;
    }

    @JvmStatic
    public static final double easeInQuad(double x) {
        return x * x;
    }

    @JvmStatic
    public static final double easeOutQuad(double x) {
        return 1 - ((1 - x) * (1 - x));
    }

    @JvmStatic
    public static final double easeInOutQuad(double x) {
        return x < 0.5d ? 2 * x * x : 1 - (Math.pow(((-2) * x) + 2, 2) / 2);
    }

    @JvmStatic
    public static final double easeInCubic(double x) {
        return x * x * x;
    }

    @JvmStatic
    public static final double easeOutCubic(double x) {
        return 1 - Math.pow(1 - x, 3);
    }

    @JvmStatic
    public static final double easeInOutCubic(double x) {
        return x < 0.5d ? 4 * x * x * x : 1 - (Math.pow(((-2) * x) + 2, 3) / 2);
    }

    @JvmStatic
    public static final double easeInQuart(double x) {
        return x * x * x * x;
    }

    @JvmStatic
    public static final double easeOutQuart(double x) {
        return 1 - Math.pow(1 - x, 4);
    }

    @JvmStatic
    public static final double easeInOutQuart(double x) {
        return x < 0.5d ? 8 * x * x * x * x : 1 - (Math.pow(((-2) * x) + 2, 4) / 2);
    }

    @JvmStatic
    public static final double easeInQuint(double x) {
        return x * x * x * x * x;
    }

    @JvmStatic
    public static final double easeOutQuint(double x) {
        return 1 - Math.pow(1 - x, 5);
    }

    @JvmStatic
    public static final double easeInOutQuint(double x) {
        return x < 0.5d ? 16 * x * x * x * x * x : 1 - (Math.pow(((-2) * x) + 2, 5) / 2);
    }

    @JvmStatic
    public static final double easeInExpo(double x) {
        if (x == 0.0d) {
            return 0.0d;
        }
        return Math.pow(2.0d, (10 * x) - 10);
    }

    @JvmStatic
    public static final double easeOutExpo(double x) {
        if (x == 1.0d) {
            return 1.0d;
        }
        return 1 - Math.pow(2.0d, (-10) * x);
    }

    @JvmStatic
    public static final double easeInOutExpo(double x) {
        if (x == 0.0d) {
            return 0.0d;
        }
        if (x == 1.0d) {
            return 1.0d;
        }
        if (x < 0.5d) {
            return Math.pow(2.0d, (20 * x) - 10) / 2;
        }
        return (2 - Math.pow(2.0d, ((-20) * x) + 10)) / 2;
    }

    @JvmStatic
    public static final double easeInCirc(double x) {
        return 1 - Math.sqrt(1 - Math.pow(x, 2));
    }

    @JvmStatic
    public static final double easeOutCirc(double x) {
        return Math.sqrt(1 - Math.pow(x - 1, 2));
    }

    @JvmStatic
    public static final double easeInOutCirc(double x) {
        return x < 0.5d ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(((-2) * x) + 2, 2)) + 1) / 2;
    }

    @JvmStatic
    public static final double easeInBack(double x) {
        double c3 = 1.70158d + 1;
        return (((c3 * x) * x) * x) - ((1.70158d * x) * x);
    }

    @JvmStatic
    public static final double easeOutBack(double x) {
        double c3 = 1.70158d + 1;
        return 1 + (c3 * Math.pow(x - 1, 3)) + (1.70158d * Math.pow(x - 1, 2));
    }

    @JvmStatic
    public static final double easeInOutBack(double x) {
        double c2 = 1.70158d * 1.525d;
        if (x < 0.5d) {
            return (Math.pow(2 * x, 2) * ((((c2 + 1) * 2) * x) - c2)) / 2;
        }
        return ((Math.pow((2 * x) - 2, 2) * (((c2 + 1) * ((x * 2) - 2)) + c2)) + 2) / 2;
    }

    @JvmStatic
    public static final double easeInElastic(double x) {
        if (x == 0.0d) {
            return 0.0d;
        }
        if (!(x == 1.0d)) {
            return Math.pow(-2.0d, (10 * x) - 10) * Math.sin(((x * 10) - 10.75d) * 2.0943951023931953d);
        }
        return 1.0d;
    }

    @JvmStatic
    public static final double easeOutElastic(double x) {
        if (x == 0.0d) {
            return 0.0d;
        }
        if (!(x == 1.0d)) {
            return (Math.pow(2.0d, (-10) * x) * Math.sin(((x * 10) - 0.75d) * 2.0943951023931953d)) + 1;
        }
        return 1.0d;
    }

    @JvmStatic
    public static final double easeInOutElastic(double x) {
        if (x == 0.0d) {
            return 0.0d;
        }
        if (x == 1.0d) {
            return 1.0d;
        }
        if (x < 0.5d) {
            return (-(Math.pow(2.0d, (20 * x) - 10) * Math.sin(((20 * x) - 11.125d) * 1.3962634015954636d))) / 2;
        }
        return ((Math.pow(2.0d, ((-20) * x) + 10) * Math.sin(((20 * x) - 11.125d) * 1.3962634015954636d)) / 2) + 1;
    }

    @JvmStatic
    public static final double easeInBounce(double x) {
        EaseUtils easeUtils = INSTANCE;
        return 1 - easeOutBounce(1 - x);
    }

    @JvmStatic
    public static final double easeOutBounce(double animeX) {
        if (animeX < 1 / 2.75d) {
            return 7.5625d * animeX * animeX;
        }
        if (animeX < 2 / 2.75d) {
            double x = animeX - 1.5d;
            return (7.5625d * (x / 2.75d) * x) + 0.75d;
        } else if (animeX < 2.5d / 2.75d) {
            double x2 = animeX - 2.25d;
            return (7.5625d * (x2 / 2.75d) * x2) + 0.9375d;
        } else {
            double x3 = animeX - 2.625d;
            return (7.5625d * (x3 / 2.75d) * x3) + 0.984375d;
        }
    }

    @JvmStatic
    public static final double easeInOutBounce(double x) {
        if (x < 0.5d) {
            EaseUtils easeUtils = INSTANCE;
            return (1 - easeOutBounce(1 - (2 * x))) / 2;
        }
        EaseUtils easeUtils2 = INSTANCE;
        return (1 + easeOutBounce((2 * x) - 1)) / 2;
    }
}
