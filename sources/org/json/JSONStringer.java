package org.json;

import java.io.StringWriter;

/* loaded from: Jackey Client b2.jar:org/json/JSONStringer.class */
public class JSONStringer extends JSONWriter {
    public JSONStringer() {
        super(new StringWriter());
    }

    public String toString() {
        if (this.mode == 'd') {
            return this.writer.toString();
        }
        return null;
    }
}
