package org.json;

import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:org/json/XML.class */
public class XML {
    public static final Character AMP = '&';
    public static final Character APOS = '\'';
    public static final Character BANG = '!';

    /* renamed from: EQ */
    public static final Character f407EQ = '=';

    /* renamed from: GT */
    public static final Character f408GT = '>';

    /* renamed from: LT */
    public static final Character f409LT = '<';
    public static final Character QUEST = '?';
    public static final Character QUOT = '\"';
    public static final Character SLASH = '/';

    private static Iterable<Integer> codePointIterator(final String string) {
        return new Iterable<Integer>() { // from class: org.json.XML.1
            @Override // java.lang.Iterable
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() { // from class: org.json.XML.1.1
                    private int nextIndex = 0;
                    private int length;

                    {
                        C17681.this = this;
                        this.length = string.length();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }

                    @Override // java.util.Iterator
                    public Integer next() {
                        int result = string.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(result);
                        return Integer.valueOf(result);
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static String escape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        for (Integer num : codePointIterator(string)) {
            int cp = num.intValue();
            switch (cp) {
                case 34:
                    sb.append("&quot;");
                    break;
                case 38:
                    sb.append("&amp;");
                    break;
                case 39:
                    sb.append("&apos;");
                    break;
                case 60:
                    sb.append("&lt;");
                    break;
                case 62:
                    sb.append("&gt;");
                    break;
                default:
                    if (mustEscape(cp)) {
                        sb.append("&#x");
                        sb.append(Integer.toHexString(cp));
                        sb.append(';');
                        break;
                    } else {
                        sb.appendCodePoint(cp);
                        break;
                    }
            }
        }
        return sb.toString();
    }

    private static boolean mustEscape(int cp) {
        return !(!Character.isISOControl(cp) || cp == 9 || cp == 10 || cp == 13) || ((cp < 32 || cp > 55295) && ((cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111)));
    }

    public static String unescape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        int i = 0;
        int length = string.length();
        while (i < length) {
            char c = string.charAt(i);
            if (c == '&') {
                int semic = string.indexOf(59, i);
                if (semic > i) {
                    String entity = string.substring(i + 1, semic);
                    sb.append(XMLTokener.unescapeEntity(entity));
                    i += entity.length() + 1;
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
            i++;
        }
        return sb.toString();
    }

    public static void noSpace(String string) throws JSONException {
        int length = string.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                throw new JSONException("'" + string + "' contains a space character.");
            }
        }
    }

    private static boolean parse(XMLTokener x, JSONObject context, String name, boolean keepStrings) throws JSONException {
        Object token = x.nextToken();
        if (token == BANG) {
            char c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            } else if (c == '[') {
                if ("CDATA".equals(x.nextToken()) && x.next() == '[') {
                    String string = x.nextCDATA();
                    if (string.length() > 0) {
                        context.accumulate("content", string);
                        return false;
                    }
                    return false;
                }
                throw x.syntaxError("Expected 'CDATA['");
            }
            int i = 1;
            do {
                Object token2 = x.nextMeta();
                if (token2 == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                }
                if (token2 == f409LT) {
                    i++;
                } else if (token2 == f408GT) {
                    i--;
                }
            } while (i > 0);
            return false;
        } else if (token == QUEST) {
            x.skipPast("?>");
            return false;
        } else if (token == SLASH) {
            Object token3 = x.nextToken();
            if (name == null) {
                throw x.syntaxError("Mismatched close tag " + token3);
            }
            if (!token3.equals(name)) {
                throw x.syntaxError("Mismatched " + name + " and " + token3);
            }
            if (x.nextToken() != f408GT) {
                throw x.syntaxError("Misshaped close tag");
            }
            return true;
        } else if (token instanceof Character) {
            throw x.syntaxError("Misshaped tag");
        } else {
            String tagName = (String) token;
            Object token4 = null;
            JSONObject jsonobject = new JSONObject();
            while (true) {
                Object obj = token4;
                Object token5 = token4;
                if (obj == null) {
                    token5 = x.nextToken();
                }
                if (token5 instanceof String) {
                    String string2 = (String) token5;
                    token4 = x.nextToken();
                    if (token4 == f407EQ) {
                        Object token6 = x.nextToken();
                        if (!(token6 instanceof String)) {
                            throw x.syntaxError("Missing value");
                        }
                        jsonobject.accumulate(string2, keepStrings ? (String) token6 : stringToValue((String) token6));
                        token4 = null;
                    } else {
                        jsonobject.accumulate(string2, "");
                    }
                } else if (token5 == SLASH) {
                    if (x.nextToken() != f408GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    if (jsonobject.length() > 0) {
                        context.accumulate(tagName, jsonobject);
                        return false;
                    }
                    context.accumulate(tagName, "");
                    return false;
                } else if (token5 != f408GT) {
                    throw x.syntaxError("Misshaped tag");
                } else {
                    while (true) {
                        Object token7 = x.nextContent();
                        if (token7 == null) {
                            if (tagName != null) {
                                throw x.syntaxError("Unclosed tag " + tagName);
                            }
                            return false;
                        } else if (token7 instanceof String) {
                            String string3 = (String) token7;
                            if (string3.length() > 0) {
                                jsonobject.accumulate("content", keepStrings ? string3 : stringToValue(string3));
                            }
                        } else if (token7 == f409LT && parse(x, jsonobject, tagName, keepStrings)) {
                            if (jsonobject.length() == 0) {
                                context.accumulate(tagName, "");
                                return false;
                            } else if (jsonobject.length() == 1 && jsonobject.opt("content") != null) {
                                context.accumulate(tagName, jsonobject.opt("content"));
                                return false;
                            } else {
                                context.accumulate(tagName, jsonobject);
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    public static Object stringToValue(String string) {
        return JSONObject.stringToValue(string);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return toJSONObject(string, false);
    }

    public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(string);
        while (x.more() && x.skipPast("<")) {
            parse(x, jo, null, keepStrings);
        }
        return jo;
    }

    public static String toString(Object object) throws JSONException {
        return toString(object, null);
    }

    public static String toString(Object object, String tagName) throws JSONException {
        JSONArray ja;
        StringBuilder sb = new StringBuilder();
        if (object instanceof JSONObject) {
            if (tagName != null) {
                sb.append('<');
                sb.append(tagName);
                sb.append('>');
            }
            JSONObject jo = (JSONObject) object;
            for (Map.Entry<String, ?> entry : jo.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    value = "";
                } else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }
                if ("content".equals(key)) {
                    if (value instanceof JSONArray) {
                        JSONArray ja2 = (JSONArray) value;
                        int i = 0;
                        Iterator<Object> it = ja2.iterator();
                        while (it.hasNext()) {
                            Object val = it.next();
                            if (i > 0) {
                                sb.append('\n');
                            }
                            sb.append(escape(val.toString()));
                            i++;
                        }
                    } else {
                        sb.append(escape(value.toString()));
                    }
                } else if (value instanceof JSONArray) {
                    JSONArray ja3 = (JSONArray) value;
                    Iterator<Object> it2 = ja3.iterator();
                    while (it2.hasNext()) {
                        Object val2 = it2.next();
                        if (val2 instanceof JSONArray) {
                            sb.append('<');
                            sb.append(key);
                            sb.append('>');
                            sb.append(toString(val2));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        } else {
                            sb.append(toString(val2, key));
                        }
                    }
                } else if ("".equals(value)) {
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");
                } else {
                    sb.append(toString(value, key));
                }
            }
            if (tagName != null) {
                sb.append("</");
                sb.append(tagName);
                sb.append('>');
            }
            return sb.toString();
        } else if (object != null && ((object instanceof JSONArray) || object.getClass().isArray())) {
            if (object.getClass().isArray()) {
                ja = new JSONArray(object);
            } else {
                ja = (JSONArray) object;
            }
            Iterator<Object> it3 = ja.iterator();
            while (it3.hasNext()) {
                Object val3 = it3.next();
                sb.append(toString(val3, tagName == null ? "array" : tagName));
            }
            return sb.toString();
        } else {
            String string = object == null ? Configurator.NULL : escape(object.toString());
            return tagName == null ? "\"" + string + "\"" : string.length() == 0 ? "<" + tagName + "/>" : "<" + tagName + ">" + string + "</" + tagName + ">";
        }
    }
}
