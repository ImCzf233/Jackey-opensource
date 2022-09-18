package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.C$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/JsonPrimitive.class */
public final class JsonPrimitive extends JsonElement {
    private final Object value;

    public JsonPrimitive(Boolean bool) {
        this.value = C$Gson$Preconditions.checkNotNull(bool);
    }

    public JsonPrimitive(Number number) {
        this.value = C$Gson$Preconditions.checkNotNull(number);
    }

    public JsonPrimitive(String string) {
        this.value = C$Gson$Preconditions.checkNotNull(string);
    }

    public JsonPrimitive(Character c) {
        this.value = ((Character) C$Gson$Preconditions.checkNotNull(c)).toString();
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public JsonPrimitive deepCopy() {
        return this;
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public boolean getAsBoolean() {
        if (isBoolean()) {
            return ((Boolean) this.value).booleanValue();
        }
        return Boolean.parseBoolean(getAsString());
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public Number getAsNumber() {
        return this.value instanceof String ? new LazilyParsedNumber((String) this.value) : (Number) this.value;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public String getAsString() {
        if (isNumber()) {
            return getAsNumber().toString();
        }
        if (isBoolean()) {
            return ((Boolean) this.value).toString();
        }
        return (String) this.value;
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public double getAsDouble() {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public BigDecimal getAsBigDecimal() {
        return this.value instanceof BigDecimal ? (BigDecimal) this.value : new BigDecimal(this.value.toString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public BigInteger getAsBigInteger() {
        return this.value instanceof BigInteger ? (BigInteger) this.value : new BigInteger(this.value.toString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public float getAsFloat() {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public long getAsLong() {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public short getAsShort() {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public int getAsInt() {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public byte getAsByte() {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    @Override // com.viaversion.viaversion.libs.gson.JsonElement
    public char getAsCharacter() {
        return getAsString().charAt(0);
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (isIntegral(this)) {
            long value = getAsNumber().longValue();
            return (int) (value ^ (value >>> 32));
        } else if (this.value instanceof Number) {
            long value2 = Double.doubleToLongBits(getAsNumber().doubleValue());
            return (int) (value2 ^ (value2 >>> 32));
        } else {
            return this.value.hashCode();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        JsonPrimitive other = (JsonPrimitive) obj;
        if (this.value == null) {
            return other.value == null;
        } else if (isIntegral(this) && isIntegral(other)) {
            return getAsNumber().longValue() == other.getAsNumber().longValue();
        } else if ((this.value instanceof Number) && (other.value instanceof Number)) {
            double a = getAsNumber().doubleValue();
            double b = other.getAsNumber().doubleValue();
            return a == b || (Double.isNaN(a) && Double.isNaN(b));
        } else {
            return this.value.equals(other.value);
        }
    }

    private static boolean isIntegral(JsonPrimitive primitive) {
        if (primitive.value instanceof Number) {
            Number number = (Number) primitive.value;
            return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
        }
        return false;
    }
}
