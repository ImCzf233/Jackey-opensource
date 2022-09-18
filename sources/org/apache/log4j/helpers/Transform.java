package org.apache.log4j.helpers;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/helpers/Transform.class */
public class Transform {
    private static final String CDATA_START = "<![CDATA[";
    private static final String CDATA_PSEUDO_END = "]]&gt;";
    private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
    private static final String CDATA_END = "]]>";
    private static final int CDATA_END_LEN = CDATA_END.length();

    public static String escapeTags(String input) {
        if (input == null || input.length() == 0 || (input.indexOf(34) == -1 && input.indexOf(38) == -1 && input.indexOf(60) == -1 && input.indexOf(62) == -1)) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length() + 6);
        int len = input.length();
        for (int i = 0; i < len; i++) {
            char ch = input.charAt(i);
            if (ch > '>') {
                buf.append(ch);
            } else if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '&') {
                buf.append("&amp;");
            } else if (ch == '\"') {
                buf.append("&quot;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    public static void appendEscapingCDATA(StringBuffer buf, String str) {
        if (str != null) {
            int end = str.indexOf(CDATA_END);
            if (end < 0) {
                buf.append(str);
                return;
            }
            int start = 0;
            while (end > -1) {
                buf.append(str.substring(start, end));
                buf.append(CDATA_EMBEDED_END);
                start = end + CDATA_END_LEN;
                if (start < str.length()) {
                    end = str.indexOf(CDATA_END, start);
                } else {
                    return;
                }
            }
            buf.append(str.substring(start));
        }
    }
}
