package net.ccbluex.liquidbounce.utils.render;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/AnimationUtils.class */
public class AnimationUtils {
    public static float easeOut(float t, float d) {
        float t2 = (t / d) - 1.0f;
        return (t2 * t2 * t2) + 1.0f;
    }
}
