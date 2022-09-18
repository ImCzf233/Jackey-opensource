package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/DefaultDateTypeAdapter.class */
final class DefaultDateTypeAdapter extends TypeAdapter<Date> {
    private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
    private final Class<? extends Date> dateType;
    private final List<DateFormat> dateFormats;

    DefaultDateTypeAdapter(Class<? extends Date> dateType) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(dateType);
        this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    public DefaultDateTypeAdapter(Class<? extends Date> dateType, String datePattern) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(dateType);
        this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(new SimpleDateFormat(datePattern));
        }
    }

    DefaultDateTypeAdapter(Class<? extends Date> dateType, int style) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(dateType);
        this.dateFormats.add(DateFormat.getDateInstance(style, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateInstance(style));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(style));
        }
    }

    public DefaultDateTypeAdapter(int dateStyle, int timeStyle) {
        this(Date.class, dateStyle, timeStyle);
    }

    public DefaultDateTypeAdapter(Class<? extends Date> dateType, int dateStyle, int timeStyle) {
        this.dateFormats = new ArrayList();
        this.dateType = verifyDateType(dateType);
        this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(dateStyle, timeStyle));
        }
    }

    private static Class<? extends Date> verifyDateType(Class<? extends Date> dateType) {
        if (dateType != Date.class && dateType != java.sql.Date.class && dateType != Timestamp.class) {
            throw new IllegalArgumentException("Date type must be one of " + Date.class + ", " + Timestamp.class + ", or " + java.sql.Date.class + " but was " + dateType);
        }
        return dateType;
    }

    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        synchronized (this.dateFormats) {
            String dateFormatAsString = this.dateFormats.get(0).format(value);
            out.value(dateFormatAsString);
        }
    }

    @Override // com.viaversion.viaversion.libs.gson.TypeAdapter
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        Date date = deserializeToDate(in.nextString());
        if (this.dateType == Date.class) {
            return date;
        }
        if (this.dateType == Timestamp.class) {
            return new Timestamp(date.getTime());
        }
        if (this.dateType == java.sql.Date.class) {
            return new java.sql.Date(date.getTime());
        }
        throw new AssertionError();
    }

    private Date deserializeToDate(String s) {
        synchronized (this.dateFormats) {
            for (DateFormat dateFormat : this.dateFormats) {
                try {
                    return dateFormat.parse(s);
                } catch (ParseException e) {
                }
            }
            try {
                return ISO8601Utils.parse(s, new ParsePosition(0));
            } catch (ParseException e2) {
                throw new JsonSyntaxException(s, e2);
            }
        }
    }

    public String toString() {
        DateFormat defaultFormat = this.dateFormats.get(0);
        if (defaultFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat) defaultFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
    }
}
