package net.ccbluex.liquidbounce.utils.misc;

import java.util.Arrays;
import java.util.HashMap;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/StringUtils.class */
public final class StringUtils {
    private static HashMap<String, String> stringCache = new HashMap<>();
    private static HashMap<String, String> stringReplaceCache = new HashMap<>();
    private static HashMap<String, String> stringRegexCache = new HashMap<>();
    private static HashMap<String, String> airCache = new HashMap<>();

    public static String fixString(String str) {
        char[] charArray;
        if (stringCache.containsKey(str)) {
            return stringCache.get(str);
        }
        String str2 = str.replaceAll("\uf8ff", "");
        StringBuilder sb = new StringBuilder();
        for (char c : str2.toCharArray()) {
            if (c > 65281 && c < 65376) {
                sb.append(Character.toChars(c - 65248));
            } else {
                sb.append(c);
            }
        }
        String result = sb.toString();
        stringCache.put(str2, result);
        return result;
    }

    public static String injectAirString(String str) {
        char[] charArray;
        if (airCache.containsKey(str)) {
            return airCache.get(str);
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasAdded = false;
        for (char c : str.toCharArray()) {
            stringBuilder.append(c);
            if (!hasAdded) {
                stringBuilder.append((char) 63743);
            }
            hasAdded = true;
        }
        String result = stringBuilder.toString();
        airCache.put(str, result);
        return result;
    }

    public static String toCompleteString(String[] args, int start) {
        return args.length <= start ? "" : String.join(" ", (CharSequence[]) Arrays.copyOfRange(args, start, args.length));
    }

    public static String replace(String string, String searchChars, String replaceChars) {
        return replace(string, searchChars, replaceChars, false);
    }

    public static String replace(String string, String searchChars, String replaceChars, boolean forceReload) {
        if (string.isEmpty() || searchChars.isEmpty() || searchChars.equals(replaceChars)) {
            return string;
        }
        if (!forceReload && stringRegexCache.get(searchChars) != null && stringRegexCache.get(searchChars).equals(replaceChars) && stringReplaceCache.containsKey(string)) {
            return stringReplaceCache.getOrDefault(string, replace(string, searchChars, replaceChars, true));
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        int stringLength = string.length();
        int searchCharsLength = searchChars.length();
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < stringLength; i++) {
            int start = stringBuilder.indexOf(searchChars, i);
            if (start == -1) {
                if (i == 0) {
                    return string;
                }
                return stringBuilder.toString();
            }
            stringBuilder.replace(start, start + searchCharsLength, replaceChars);
        }
        String result = stringBuilder.toString();
        stringReplaceCache.put(string, result);
        stringRegexCache.put(searchChars, replaceChars);
        return result;
    }
}
