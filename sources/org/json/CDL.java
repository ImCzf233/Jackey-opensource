package org.json;

/* loaded from: Jackey Client b2.jar:org/json/CDL.class */
public class CDL {
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0097, code lost:
        throw r4.syntaxError("Missing close quote '" + r0 + "'.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getValue(org.json.JSONTokener r4) throws org.json.JSONException {
        /*
        L0:
            r0 = r4
            char r0 = r0.next()
            r5 = r0
            r0 = r5
            r1 = 32
            if (r0 == r1) goto L0
            r0 = r5
            r1 = 9
            if (r0 == r1) goto L0
            r0 = r5
            switch(r0) {
                case 0: goto L3c;
                case 34: goto L3e;
                case 39: goto L3e;
                case 44: goto La6;
                default: goto Lad;
            }
        L3c:
            r0 = 0
            return r0
        L3e:
            r0 = r5
            r6 = r0
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r1 = r0
            r1.<init>()
            r7 = r0
        L48:
            r0 = r4
            char r0 = r0.next()
            r5 = r0
            r0 = r5
            r1 = r6
            if (r0 != r1) goto L6b
            r0 = r4
            char r0 = r0.next()
            r8 = r0
            r0 = r8
            r1 = 34
            if (r0 == r1) goto L6b
            r0 = r8
            if (r0 <= 0) goto La1
            r0 = r4
            r0.back()
            goto La1
        L6b:
            r0 = r5
            if (r0 == 0) goto L7b
            r0 = r5
            r1 = 10
            if (r0 == r1) goto L7b
            r0 = r5
            r1 = 13
            if (r0 != r1) goto L98
        L7b:
            r0 = r4
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = r1
            r2.<init>()
            java.lang.String r2 = "Missing close quote '"
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r6
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "'."
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L98:
            r0 = r7
            r1 = r5
            java.lang.StringBuffer r0 = r0.append(r1)
            goto L48
        La1:
            r0 = r7
            java.lang.String r0 = r0.toString()
            return r0
        La6:
            r0 = r4
            r0.back()
            java.lang.String r0 = ""
            return r0
        Lad:
            r0 = r4
            r0.back()
            r0 = r4
            r1 = 44
            java.lang.String r0 = r0.nextTo(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.CDL.getValue(org.json.JSONTokener):java.lang.String");
    }

    public static JSONArray rowToJSONArray(JSONTokener x) throws JSONException {
        JSONArray ja = new JSONArray();
        while (true) {
            String value = getValue(x);
            char c = x.next();
            if (value != null) {
                if (ja.length() == 0 && value.length() == 0 && c != ',') {
                    return null;
                }
                ja.put(value);
                while (c != ',') {
                    if (c != ' ') {
                        if (c == '\n' || c == '\r' || c == 0) {
                            return ja;
                        }
                        throw x.syntaxError("Bad character '" + c + "' (" + ((int) c) + ").");
                    }
                    c = x.next();
                }
            } else {
                return null;
            }
        }
    }

    public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) throws JSONException {
        JSONArray ja = rowToJSONArray(x);
        if (ja != null) {
            return ja.toJSONObject(names);
        }
        return null;
    }

    public static String rowToString(JSONArray ja) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ja.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            Object object = ja.opt(i);
            if (object != null) {
                String string = object.toString();
                if (string.length() > 0 && (string.indexOf(44) >= 0 || string.indexOf(10) >= 0 || string.indexOf(13) >= 0 || string.indexOf(0) >= 0 || string.charAt(0) == '\"')) {
                    sb.append('\"');
                    int length = string.length();
                    for (int j = 0; j < length; j++) {
                        char c = string.charAt(j);
                        if (c >= ' ' && c != '\"') {
                            sb.append(c);
                        }
                    }
                    sb.append('\"');
                } else {
                    sb.append(string);
                }
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return toJSONArray(new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONTokener x) throws JSONException {
        return toJSONArray(rowToJSONArray(x), x);
    }

    public static JSONArray toJSONArray(JSONArray names, String string) throws JSONException {
        return toJSONArray(names, new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONArray names, JSONTokener x) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        JSONArray ja = new JSONArray();
        while (true) {
            JSONObject jo = rowToJSONObject(names, x);
            if (jo == null) {
                break;
            }
            ja.put(jo);
        }
        if (ja.length() == 0) {
            return null;
        }
        return ja;
    }

    public static String toString(JSONArray ja) throws JSONException {
        JSONArray names;
        JSONObject jo = ja.optJSONObject(0);
        if (jo != null && (names = jo.names()) != null) {
            return rowToString(names) + toString(names, ja);
        }
        return null;
    }

    public static String toString(JSONArray names, JSONArray ja) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(i);
            if (jo != null) {
                sb.append(rowToString(jo.toJSONArray(names)));
            }
        }
        return sb.toString();
    }
}
