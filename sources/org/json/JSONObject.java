package org.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:org/json/JSONObject.class */
public class JSONObject {
    private final Map<String, Object> map;
    public static final Object NULL = new Null();

    /* loaded from: Jackey Client b2.jar:org/json/JSONObject$Null.class */
    private static final class Null {
        private Null() {
        }

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object object) {
            return object == null || object == this;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return Configurator.NULL;
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject jo, String[] names) {
        this(names.length);
        for (int i = 0; i < names.length; i++) {
            try {
                putOnce(names[i], jo.opt(names[i]));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(JSONTokener x) throws JSONException {
        this();
        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char c = x.nextClean();
            switch (c) {
                case 0:
                    throw x.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                default:
                    x.back();
                    String key = x.nextValue().toString();
                    char c2 = x.nextClean();
                    if (c2 != ':') {
                        throw x.syntaxError("Expected a ':' after a key");
                    }
                    if (key != null) {
                        if (opt(key) != null) {
                            throw x.syntaxError("Duplicate key \"" + key + "\"");
                        }
                        Object value = x.nextValue();
                        if (value != null) {
                            put(key, value);
                        }
                    }
                    switch (x.nextClean()) {
                        case ',':
                        case ';':
                            if (x.nextClean() == '}') {
                                return;
                            }
                            x.back();
                        case '}':
                            return;
                        default:
                            throw x.syntaxError("Expected a ',' or '}'");
                    }
            }
        }
    }

    public JSONObject(Map<?, ?> m) {
        if (m == null) {
            this.map = new HashMap();
            return;
        }
        this.map = new HashMap(m.size());
        for (Map.Entry<?, ?> e : m.entrySet()) {
            Object value = e.getValue();
            if (value != null) {
                this.map.put(String.valueOf(e.getKey()), wrap(value));
            }
        }
    }

    public JSONObject(Object bean) {
        this();
        populateMap(bean);
    }

    public JSONObject(Object object, String[] names) {
        this(names.length);
        Class<?> c = object.getClass();
        for (String name : names) {
            try {
                putOpt(name, c.getField(name).get(object));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(String source) throws JSONException {
        this(new JSONTokener(source));
    }

    public JSONObject(String baseName, Locale locale) throws JSONException {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key != null) {
                String[] path = ((String) key).split("\\.");
                int last = path.length - 1;
                JSONObject target = this;
                for (int i = 0; i < last; i++) {
                    String segment = path[i];
                    JSONObject nextTarget = target.optJSONObject(segment);
                    if (nextTarget == null) {
                        nextTarget = new JSONObject();
                        target.put(segment, nextTarget);
                    }
                    target = nextTarget;
                }
                target.put(path[last], bundle.getString((String) key));
            }
        }
    }

    public JSONObject(int initialCapacity) {
        this.map = new HashMap(initialCapacity);
    }

    public JSONObject accumulate(String key, Object value) throws JSONException {
        testValidity(value);
        Object object = opt(key);
        if (object == null) {
            put(key, value instanceof JSONArray ? new JSONArray().put(value) : value);
        } else if (object instanceof JSONArray) {
            ((JSONArray) object).put(value);
        } else {
            put(key, new JSONArray().put(object).put(value));
        }
        return this;
    }

    public JSONObject append(String key, Object value) throws JSONException {
        testValidity(value);
        Object object = opt(key);
        if (object == null) {
            put(key, new JSONArray().put(value));
        } else if (object instanceof JSONArray) {
            put(key, ((JSONArray) object).put(value));
        } else {
            throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return Configurator.NULL;
        }
        String string = Double.toString(d);
        if (string.indexOf(46) > 0 && string.indexOf(101) < 0 && string.indexOf(69) < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public Object get(String key) throws JSONException {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        Object object = opt(key);
        if (object == null) {
            throw new JSONException("JSONObject[" + quote(key) + "] not found.");
        }
        return object;
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
        E val = (E) optEnum(clazz, key);
        if (val == null) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not an enum of type " + quote(clazz.getSimpleName()) + ".");
        }
        return val;
    }

    public boolean getBoolean(String key) throws JSONException {
        Object object = get(key);
        if (!object.equals(Boolean.FALSE)) {
            if ((object instanceof String) && ((String) object).equalsIgnoreCase("false")) {
                return false;
            }
            if (!object.equals(Boolean.TRUE)) {
                if ((object instanceof String) && ((String) object).equalsIgnoreCase("true")) {
                    return true;
                }
                throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
            }
            return true;
        }
        return false;
    }

    public BigInteger getBigInteger(String key) throws JSONException {
        Object object = get(key);
        try {
            return new BigInteger(object.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigInteger.", e);
        }
    }

    public BigDecimal getBigDecimal(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof BigDecimal) {
            return (BigDecimal) object;
        }
        try {
            return new BigDecimal(object.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigDecimal.", e);
        }
    }

    public double getDouble(String key) throws JSONException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).doubleValue() : Double.parseDouble(object.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a number.", e);
        }
    }

    public float getFloat(String key) throws JSONException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).floatValue() : Float.parseFloat(object.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a number.", e);
        }
    }

    public Number getNumber(String key) throws JSONException {
        Object object = get(key);
        try {
            if (object instanceof Number) {
                return (Number) object;
            }
            return stringToNumber(object.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a number.", e);
        }
    }

    public int getInt(String key) throws JSONException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).intValue() : Integer.parseInt((String) object);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not an int.", e);
        }
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
    }

    public long getLong(String key) throws JSONException {
        Object object = get(key);
        try {
            return object instanceof Number ? ((Number) object).longValue() : Long.parseLong((String) object);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] is not a long.", e);
        }
    }

    public static String[] getNames(JSONObject jo) {
        int length = jo.length();
        if (length == 0) {
            return null;
        }
        return (String[]) jo.keySet().toArray(new String[length]);
    }

    public static String[] getNames(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> klass = object.getClass();
        Field[] fields = klass.getFields();
        int length = fields.length;
        if (length == 0) {
            return null;
        }
        String[] names = new String[length];
        for (int i = 0; i < length; i++) {
            names[i] = fields[i].getName();
        }
        return names;
    }

    public String getString(String key) throws JSONException {
        Object object = get(key);
        if (object instanceof String) {
            return (String) object;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] not a string.");
    }

    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    public JSONObject increment(String key) throws JSONException {
        Object value = opt(key);
        if (value == null) {
            put(key, 1);
        } else if (value instanceof BigInteger) {
            put(key, ((BigInteger) value).add(BigInteger.ONE));
        } else if (value instanceof BigDecimal) {
            put(key, ((BigDecimal) value).add(BigDecimal.ONE));
        } else if (value instanceof Integer) {
            put(key, ((Integer) value).intValue() + 1);
        } else if (value instanceof Long) {
            put(key, ((Long) value).longValue() + 1);
        } else if (value instanceof Double) {
            put(key, ((Double) value).doubleValue() + 1.0d);
        } else if (value instanceof Float) {
            put(key, ((Float) value).floatValue() + 1.0f);
        } else {
            throw new JSONException("Unable to increment [" + quote(key) + "].");
        }
        return this;
    }

    public boolean isNull(String key) {
        return NULL.equals(opt(key));
    }

    public Iterator<String> keys() {
        return keySet().iterator();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        if (this.map.isEmpty()) {
            return null;
        }
        return new JSONArray((Collection<?>) this.map.keySet());
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(number);
        String string = number.toString();
        if (string.indexOf(46) > 0 && string.indexOf(101) < 0 && string.indexOf(69) < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public Object opt(String key) {
        if (key == null) {
            return null;
        }
        return this.map.get(key);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
        return (E) optEnum(clazz, key, null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, String key, E defaultValue) {
        try {
            Object val = opt(key);
            if (NULL.equals(val)) {
                return defaultValue;
            }
            if (clazz.isAssignableFrom(val.getClass())) {
                E myE = (E) val;
                return myE;
            }
            return (E) Enum.valueOf(clazz, val.toString());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        } catch (NullPointerException e2) {
            return defaultValue;
        }
    }

    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Boolean) {
            return ((Boolean) val).booleanValue();
        }
        try {
            return getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }
        if (val instanceof BigInteger) {
            return new BigDecimal((BigInteger) val);
        }
        if ((val instanceof Double) || (val instanceof Float)) {
            return new BigDecimal(((Number) val).doubleValue());
        }
        if ((val instanceof Long) || (val instanceof Integer) || (val instanceof Short) || (val instanceof Byte)) {
            return new BigDecimal(((Number) val).longValue());
        }
        try {
            return new BigDecimal(val.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public BigInteger optBigInteger(String key, BigInteger defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof BigInteger) {
            return (BigInteger) val;
        }
        if (val instanceof BigDecimal) {
            return ((BigDecimal) val).toBigInteger();
        }
        if ((val instanceof Double) || (val instanceof Float)) {
            return new BigDecimal(((Number) val).doubleValue()).toBigInteger();
        }
        if ((val instanceof Long) || (val instanceof Integer) || (val instanceof Short) || (val instanceof Byte)) {
            return BigInteger.valueOf(((Number) val).longValue());
        }
        try {
            String valStr = val.toString();
            if (isDecimalNotation(valStr)) {
                return new BigDecimal(valStr).toBigInteger();
            }
            return new BigInteger(valStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double optDouble(String key) {
        return optDouble(key, Double.NaN);
    }

    public double optDouble(String key, double defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        if (val instanceof String) {
            try {
                return Double.parseDouble((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public float optFloat(String key) {
        return optFloat(key, Float.NaN);
    }

    public float optFloat(String key, float defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).floatValue();
        }
        if (val instanceof String) {
            try {
                return Float.parseFloat((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public int optInt(String key) {
        return optInt(key, 0);
    }

    public int optInt(String key, int defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        if (val instanceof String) {
            try {
                return new BigDecimal((String) val).intValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public JSONArray optJSONArray(String key) {
        Object o = opt(key);
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        return null;
    }

    public JSONObject optJSONObject(String key) {
        Object object = opt(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        return null;
    }

    public long optLong(String key) {
        return optLong(key, 0L);
    }

    public long optLong(String key, long defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        if (val instanceof String) {
            try {
                return new BigDecimal((String) val).longValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public Number optNumber(String key) {
        return optNumber(key, null);
    }

    public Number optNumber(String key, Number defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return (Number) val;
        }
        if (val instanceof String) {
            try {
                return stringToNumber((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public String optString(String key) {
        return optString(key, "");
    }

    public String optString(String key, String defaultValue) {
        Object object = opt(key);
        return NULL.equals(object) ? defaultValue : object.toString();
    }

    private void populateMap(Object bean) {
        String key;
        Class<?> klass = bean.getClass();
        boolean includeSuperClass = klass.getClassLoader() != null;
        Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && method.getParameterTypes().length == 0 && !method.isBridge() && method.getReturnType() != Void.TYPE) {
                String name = method.getName();
                if (name.startsWith(PropertyDescriptor.GET)) {
                    if (!"getClass".equals(name) && !"getDeclaringClass".equals(name)) {
                        key = name.substring(3);
                    }
                } else if (name.startsWith("is")) {
                    key = name.substring(2);
                }
                if (key.length() > 0 && Character.isUpperCase(key.charAt(0))) {
                    if (key.length() == 1) {
                        key = key.toLowerCase(Locale.ROOT);
                    } else if (!Character.isUpperCase(key.charAt(1))) {
                        key = key.substring(0, 1).toLowerCase(Locale.ROOT) + key.substring(1);
                    }
                    try {
                        Object result = method.invoke(bean, new Object[0]);
                        if (result != null) {
                            this.map.put(key, wrap(result));
                            if (result instanceof Closeable) {
                                try {
                                    ((Closeable) result).close();
                                } catch (IOException e) {
                                }
                            }
                        }
                    } catch (IllegalAccessException e2) {
                    } catch (IllegalArgumentException e3) {
                    } catch (InvocationTargetException e4) {
                    }
                }
            }
        }
    }

    public JSONObject put(String key, boolean value) throws JSONException {
        put(key, value ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String key, Collection<?> value) throws JSONException {
        put(key, new JSONArray(value));
        return this;
    }

    public JSONObject put(String key, double value) throws JSONException {
        put(key, Double.valueOf(value));
        return this;
    }

    public JSONObject put(String key, float value) throws JSONException {
        put(key, Float.valueOf(value));
        return this;
    }

    public JSONObject put(String key, int value) throws JSONException {
        put(key, Integer.valueOf(value));
        return this;
    }

    public JSONObject put(String key, long value) throws JSONException {
        put(key, Long.valueOf(value));
        return this;
    }

    public JSONObject put(String key, Map<?, ?> value) throws JSONException {
        put(key, new JSONObject(value));
        return this;
    }

    public JSONObject put(String key, Object value) throws JSONException {
        if (key == null) {
            throw new NullPointerException("Null key.");
        }
        if (value != null) {
            testValidity(value);
            this.map.put(key, value);
        } else {
            remove(key);
        }
        return this;
    }

    public JSONObject putOnce(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            if (opt(key) != null) {
                throw new JSONException("Duplicate key \"" + key + "\"");
            }
            put(key, value);
        }
        return this;
    }

    public JSONObject putOpt(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            put(key, value);
        }
        return this;
    }

    public Object query(String jsonPointer) {
        return query(new JSONPointer(jsonPointer));
    }

    public Object query(JSONPointer jsonPointer) {
        return jsonPointer.queryFrom(this);
    }

    public Object optQuery(String jsonPointer) {
        return optQuery(new JSONPointer(jsonPointer));
    }

    public Object optQuery(JSONPointer jsonPointer) {
        try {
            return jsonPointer.queryFrom(this);
        } catch (JSONPointerException e) {
            return null;
        }
    }

    public static String quote(String string) {
        String obj;
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            try {
                obj = quote(string, sw).toString();
            } catch (IOException e) {
                return "";
            }
        }
        return obj;
    }

    public static Writer quote(String string, Writer w) throws IOException {
        if (string == null || string.length() == 0) {
            w.write("\"\"");
            return w;
        }
        char c = 0;
        int len = string.length();
        w.write(34);
        for (int i = 0; i < len; i++) {
            char b = c;
            c = string.charAt(i);
            switch (c) {
                case '\b':
                    w.write("\\b");
                    break;
                case '\t':
                    w.write("\\t");
                    break;
                case '\n':
                    w.write("\\n");
                    break;
                case '\f':
                    w.write("\\f");
                    break;
                case '\r':
                    w.write("\\r");
                    break;
                case '\"':
                case '\\':
                    w.write(92);
                    w.write(c);
                    break;
                case '/':
                    if (b == '<') {
                        w.write(92);
                    }
                    w.write(c);
                    break;
                default:
                    if (c < ' ' || ((c >= 128 && c < 160) || (c >= 8192 && c < 8448))) {
                        w.write("\\u");
                        String hhhh = Integer.toHexString(c);
                        w.write("0000", 0, 4 - hhhh.length());
                        w.write(hhhh);
                        break;
                    } else {
                        w.write(c);
                        break;
                    }
            }
        }
        w.write(34);
        return w;
    }

    public Object remove(String key) {
        return this.map.remove(key);
    }

    public boolean similar(Object other) {
        try {
            if (!(other instanceof JSONObject) || !keySet().equals(((JSONObject) other).keySet())) {
                return false;
            }
            for (Map.Entry<String, ?> entry : entrySet()) {
                String name = entry.getKey();
                Object valueThis = entry.getValue();
                Object valueOther = ((JSONObject) other).get(name);
                if (valueThis == valueOther) {
                    return true;
                }
                if (valueThis == null) {
                    return false;
                }
                if (valueThis instanceof JSONObject) {
                    if (!((JSONObject) valueThis).similar(valueOther)) {
                        return false;
                    }
                } else if (valueThis instanceof JSONArray) {
                    if (!((JSONArray) valueThis).similar(valueOther)) {
                        return false;
                    }
                } else if (!valueThis.equals(valueOther)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isDecimalNotation(String val) {
        return val.indexOf(46) > -1 || val.indexOf(101) > -1 || val.indexOf(69) > -1 || "-0".equals(val);
    }

    public static Number stringToNumber(String val) throws NumberFormatException {
        char initial = val.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            if (isDecimalNotation(val)) {
                if (val.length() > 14) {
                    return new BigDecimal(val);
                }
                Double d = Double.valueOf(val);
                if (d.isInfinite() || d.isNaN()) {
                    return new BigDecimal(val);
                }
                return d;
            }
            BigInteger bi = new BigInteger(val);
            if (bi.bitLength() <= 31) {
                return Integer.valueOf(bi.intValue());
            }
            if (bi.bitLength() <= 63) {
                return Long.valueOf(bi.longValue());
            }
            return bi;
        }
        throw new NumberFormatException("val [" + val + "] is not a valid number.");
    }

    public static Object stringToValue(String string) {
        if (string.equals("")) {
            return string;
        }
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase(Configurator.NULL)) {
            return NULL;
        }
        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                if (isDecimalNotation(string)) {
                    Double d = Double.valueOf(string);
                    if (!d.isInfinite() && !d.isNaN()) {
                        return d;
                    }
                } else {
                    Long myLong = Long.valueOf(string);
                    if (string.equals(myLong.toString())) {
                        if (myLong.longValue() == myLong.intValue()) {
                            return Integer.valueOf(myLong.intValue());
                        }
                        return myLong;
                    }
                }
            } catch (Exception e) {
            }
        }
        return string;
    }

    public static void testValidity(Object o) throws JSONException {
        if (o != null) {
            if (o instanceof Double) {
                if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            } else if (o instanceof Float) {
                if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            }
        }
    }

    public JSONArray toJSONArray(JSONArray names) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        JSONArray ja = new JSONArray();
        for (int i = 0; i < names.length(); i++) {
            ja.put(opt(names.getString(i)));
        }
        return ja;
    }

    public String toString() {
        try {
            return toString(0);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(int indentFactor) throws JSONException {
        String obj;
        StringWriter w = new StringWriter();
        synchronized (w.getBuffer()) {
            obj = write(w, indentFactor, 0).toString();
        }
        return obj;
    }

    public static String valueToString(Object value) throws JSONException {
        if (value == null || value.equals(null)) {
            return Configurator.NULL;
        }
        if (value instanceof JSONString) {
            try {
                Object object = ((JSONString) value).toJSONString();
                if (object instanceof String) {
                    return (String) object;
                }
                throw new JSONException("Bad value from toJSONString: " + object);
            } catch (Exception e) {
                throw new JSONException(e);
            }
        } else if (value instanceof Number) {
            String numberAsString = numberToString((Number) value);
            try {
                new BigDecimal(numberAsString);
                return numberAsString;
            } catch (NumberFormatException e2) {
                return quote(numberAsString);
            }
        } else if ((value instanceof Boolean) || (value instanceof JSONObject) || (value instanceof JSONArray)) {
            return value.toString();
        } else {
            if (value instanceof Map) {
                Map<?, ?> map = (Map) value;
                return new JSONObject(map).toString();
            } else if (value instanceof Collection) {
                Collection<?> coll = (Collection) value;
                return new JSONArray(coll).toString();
            } else if (value.getClass().isArray()) {
                return new JSONArray(value).toString();
            } else {
                if (value instanceof Enum) {
                    return quote(((Enum) value).name());
                }
                return quote(value.toString());
            }
        }
    }

    public static Object wrap(Object object) {
        try {
            if (object == null) {
                return NULL;
            }
            if ((object instanceof JSONObject) || (object instanceof JSONArray) || NULL.equals(object) || (object instanceof JSONString) || (object instanceof Byte) || (object instanceof Character) || (object instanceof Short) || (object instanceof Integer) || (object instanceof Long) || (object instanceof Boolean) || (object instanceof Float) || (object instanceof Double) || (object instanceof String) || (object instanceof BigInteger) || (object instanceof BigDecimal) || (object instanceof Enum)) {
                return object;
            }
            if (object instanceof Collection) {
                Collection<?> coll = (Collection) object;
                return new JSONArray(coll);
            } else if (object.getClass().isArray()) {
                return new JSONArray(object);
            } else {
                if (object instanceof Map) {
                    Map<?, ?> map = (Map) object;
                    return new JSONObject(map);
                }
                Package objectPackage = object.getClass().getPackage();
                String objectPackageName = objectPackage != null ? objectPackage.getName() : "";
                if (objectPackageName.startsWith("java.") || objectPackageName.startsWith("javax.") || object.getClass().getClassLoader() == null) {
                    return object.toString();
                }
                return new JSONObject(object);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Writer write(Writer writer) throws JSONException {
        return write(writer, 0, 0);
    }

    public static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
        if (value == null || value.equals(null)) {
            writer.write(Configurator.NULL);
        } else if (value instanceof JSONString) {
            try {
                Object o = ((JSONString) value).toJSONString();
                writer.write(o != null ? o.toString() : quote(value.toString()));
            } catch (Exception e) {
                throw new JSONException(e);
            }
        } else if (value instanceof Number) {
            String numberAsString = numberToString((Number) value);
            try {
                new BigDecimal(numberAsString);
                writer.write(numberAsString);
            } catch (NumberFormatException e2) {
                quote(numberAsString, writer);
            }
        } else if (value instanceof Boolean) {
            writer.write(value.toString());
        } else if (value instanceof Enum) {
            writer.write(quote(((Enum) value).name()));
        } else if (value instanceof JSONObject) {
            ((JSONObject) value).write(writer, indentFactor, indent);
        } else if (value instanceof JSONArray) {
            ((JSONArray) value).write(writer, indentFactor, indent);
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map) value;
            new JSONObject(map).write(writer, indentFactor, indent);
        } else if (value instanceof Collection) {
            Collection<?> coll = (Collection) value;
            new JSONArray(coll).write(writer, indentFactor, indent);
        } else if (value.getClass().isArray()) {
            new JSONArray(value).write(writer, indentFactor, indent);
        } else {
            quote(value.toString(), writer);
        }
        return writer;
    }

    public static final void indent(Writer writer, int indent) throws IOException {
        for (int i = 0; i < indent; i++) {
            writer.write(32);
        }
    }

    public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
        try {
            boolean commanate = false;
            int length = length();
            writer.write(123);
            if (length == 1) {
                Map.Entry<String, ?> entry = entrySet().iterator().next();
                String key = entry.getKey();
                writer.write(quote(key));
                writer.write(58);
                if (indentFactor > 0) {
                    writer.write(32);
                }
                try {
                    writeValue(writer, entry.getValue(), indentFactor, indent);
                    writer.write(125);
                    return writer;
                } catch (Exception e) {
                    throw new JSONException("Unable to write JSONObject value for key: " + key, e);
                }
            }
            if (length != 0) {
                int newindent = indent + indentFactor;
                for (Map.Entry<String, ?> entry2 : entrySet()) {
                    if (commanate) {
                        writer.write(44);
                    }
                    if (indentFactor > 0) {
                        writer.write(10);
                    }
                    indent(writer, newindent);
                    String key2 = entry2.getKey();
                    writer.write(quote(key2));
                    writer.write(58);
                    if (indentFactor > 0) {
                        writer.write(32);
                    }
                    try {
                        writeValue(writer, entry2.getValue(), indentFactor, newindent);
                        commanate = true;
                    } catch (Exception e2) {
                        throw new JSONException("Unable to write JSONObject value for key: " + key2, e2);
                    }
                }
                if (indentFactor > 0) {
                    writer.write(10);
                }
                indent(writer, indent);
            }
            writer.write(125);
            return writer;
        } catch (IOException exception) {
            throw new JSONException(exception);
        }
    }

    public Map<String, Object> toMap() {
        Object obj;
        Map<String, Object> results = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet()) {
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                obj = null;
            } else if (entry.getValue() instanceof JSONObject) {
                obj = ((JSONObject) entry.getValue()).toMap();
            } else if (entry.getValue() instanceof JSONArray) {
                obj = ((JSONArray) entry.getValue()).toList();
            } else {
                obj = entry.getValue();
            }
            Object value = obj;
            results.put(entry.getKey(), value);
        }
        return results;
    }
}
