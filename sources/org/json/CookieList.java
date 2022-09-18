package org.json;

import java.util.Map;

/* loaded from: Jackey Client b2.jar:org/json/CookieList.class */
public class CookieList {
    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject jo = new JSONObject();
        JSONTokener x = new JSONTokener(string);
        while (x.more()) {
            String name = Cookie.unescape(x.nextTo('='));
            x.next('=');
            jo.put(name, Cookie.unescape(x.nextTo(';')));
            x.next();
        }
        return jo;
    }

    public static String toString(JSONObject jo) throws JSONException {
        boolean b = false;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> entry : jo.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!JSONObject.NULL.equals(value)) {
                if (b) {
                    sb.append(';');
                }
                sb.append(Cookie.escape(key));
                sb.append("=");
                sb.append(Cookie.escape(value.toString()));
                b = true;
            }
        }
        return sb.toString();
    }
}
