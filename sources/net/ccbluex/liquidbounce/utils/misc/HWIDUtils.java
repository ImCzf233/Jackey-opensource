package net.ccbluex.liquidbounce.utils.misc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackType;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/HWIDUtils.class */
public class HWIDUtils {
    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder s = new StringBuilder();
        String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
        byte[] bytes = main.getBytes("UTF-8");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        for (byte b : md5) {
            s.append((CharSequence) Integer.toHexString((b & 255) | StackType.REPEAT_INC), 0, 3);
            if (i != md5.length - 1) {
                s.append("-");
            }
            i++;
        }
        return s.toString();
    }
}
