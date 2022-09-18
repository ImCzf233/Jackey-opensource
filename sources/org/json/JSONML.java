package org.json;

import java.util.Map;

/* loaded from: Jackey Client b2.jar:org/json/JSONML.class */
public class JSONML {
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01ee, code lost:
        throw r5.syntaxError("Reserved attribute.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object parse(org.json.XMLTokener r5, boolean r6, org.json.JSONArray r7, boolean r8) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 806
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONML.parse(org.json.XMLTokener, boolean, org.json.JSONArray, boolean):java.lang.Object");
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return (JSONArray) parse(new XMLTokener(string), true, null, false);
    }

    public static JSONArray toJSONArray(String string, boolean keepStrings) throws JSONException {
        return (JSONArray) parse(new XMLTokener(string), true, null, keepStrings);
    }

    public static JSONArray toJSONArray(XMLTokener x, boolean keepStrings) throws JSONException {
        return (JSONArray) parse(x, true, null, keepStrings);
    }

    public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
        return (JSONArray) parse(x, true, null, false);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return (JSONObject) parse(new XMLTokener(string), false, null, false);
    }

    public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
        return (JSONObject) parse(new XMLTokener(string), false, null, keepStrings);
    }

    public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
        return (JSONObject) parse(x, false, null, false);
    }

    public static JSONObject toJSONObject(XMLTokener x, boolean keepStrings) throws JSONException {
        return (JSONObject) parse(x, false, null, keepStrings);
    }

    public static String toString(JSONArray ja) throws JSONException {
        int i;
        StringBuilder sb = new StringBuilder();
        String tagName = ja.getString(0);
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        Object object = ja.opt(1);
        if (object instanceof JSONObject) {
            i = 2;
            JSONObject jo = (JSONObject) object;
            for (Map.Entry<String, ?> entry : jo.entrySet()) {
                String key = entry.getKey();
                XML.noSpace(key);
                Object value = entry.getValue();
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value.toString()));
                    sb.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = ja.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                Object object2 = ja.get(i);
                i++;
                if (object2 != null) {
                    if (object2 instanceof String) {
                        sb.append(XML.escape(object2.toString()));
                    } else if (object2 instanceof JSONObject) {
                        sb.append(toString((JSONObject) object2));
                    } else if (object2 instanceof JSONArray) {
                        sb.append(toString((JSONArray) object2));
                    } else {
                        sb.append(object2.toString());
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String toString(JSONObject jo) throws JSONException {
        StringBuilder sb = new StringBuilder();
        String tagName = jo.optString("tagName");
        if (tagName == null) {
            return XML.escape(jo.toString());
        }
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        for (Map.Entry<String, ?> entry : jo.entrySet()) {
            String key = entry.getKey();
            if (!"tagName".equals(key) && !"childNodes".equals(key)) {
                XML.noSpace(key);
                Object value = entry.getValue();
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value.toString()));
                    sb.append('\"');
                }
            }
        }
        JSONArray ja = jo.optJSONArray("childNodes");
        if (ja == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            int length = ja.length();
            for (int i = 0; i < length; i++) {
                Object object = ja.get(i);
                if (object != null) {
                    if (object instanceof String) {
                        sb.append(XML.escape(object.toString()));
                    } else if (object instanceof JSONObject) {
                        sb.append(toString((JSONObject) object));
                    } else if (object instanceof JSONArray) {
                        sb.append(toString((JSONArray) object));
                    } else {
                        sb.append(object.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }
}
