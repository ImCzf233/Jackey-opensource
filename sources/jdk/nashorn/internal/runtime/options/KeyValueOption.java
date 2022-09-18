package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/KeyValueOption.class */
public class KeyValueOption extends Option<String> {
    protected Map<String, String> map;

    public KeyValueOption(String value) {
        super(value);
        initialize();
    }

    public Map<String, String> getValues() {
        return Collections.unmodifiableMap(this.map);
    }

    public boolean hasValue(String key) {
        return (this.map == null || this.map.get(key) == null) ? false : true;
    }

    String getValue(String key) {
        if (this.map == null) {
            return null;
        }
        String val = this.map.get(key);
        if (!"".equals(val)) {
            return val;
        }
        return null;
    }

    private void initialize() {
        if (getValue() == null) {
            return;
        }
        this.map = new LinkedHashMap();
        StringTokenizer st = new StringTokenizer(getValue(), ",");
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            String[] keyValue = token.split(CallSiteDescriptor.TOKEN_DELIMITER);
            if (keyValue.length == 1) {
                this.map.put(keyValue[0], "");
            } else if (keyValue.length == 2) {
                this.map.put(keyValue[0], keyValue[1]);
            } else {
                throw new IllegalArgumentException(token);
            }
        }
    }
}
