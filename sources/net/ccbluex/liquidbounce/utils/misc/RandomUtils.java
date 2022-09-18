package net.ccbluex.liquidbounce.utils.misc;

import java.util.Random;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/RandomUtils.class */
public final class RandomUtils {
    public static boolean nextBoolean() {
        return new Random().nextBoolean();
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        if (endExclusive - startInclusive <= 0) {
            return startInclusive;
        }
        return startInclusive + new Random().nextInt(endExclusive - startInclusive);
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0d) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * Math.random());
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float) (startInclusive + ((endInclusive - startInclusive) * Math.random()));
    }

    public static String randomNumber(int length) {
        return random(length, "123456789");
    }

    public static String randomString(int length) {
        return random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    public static String random(int length, String chars) {
        return random(length, chars.toCharArray());
    }

    public static String random(int length, char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(chars[new Random().nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }
}
