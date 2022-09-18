package org.yaml.snakeyaml.representer;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import org.apache.log4j.spi.Configurator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter.class */
public class SafeRepresenter extends BaseRepresenter {
    protected Map<Class<? extends Object>, Tag> classTags;
    protected TimeZone timeZone;
    protected DumperOptions.NonPrintableStyle nonPrintableStyle;
    private static final Pattern MULTILINE_PATTERN = Pattern.compile("\n|\u0085|\u2028|\u2029");

    public SafeRepresenter() {
        this(new DumperOptions());
    }

    public SafeRepresenter(DumperOptions options) {
        this.timeZone = null;
        this.nullRepresenter = new RepresentNull();
        this.representers.put(String.class, new RepresentString());
        this.representers.put(Boolean.class, new RepresentBoolean());
        this.representers.put(Character.class, new RepresentString());
        this.representers.put(UUID.class, new RepresentUuid());
        this.representers.put(byte[].class, new RepresentByteArray());
        Represent primitiveArray = new RepresentPrimitiveArray();
        this.representers.put(short[].class, primitiveArray);
        this.representers.put(int[].class, primitiveArray);
        this.representers.put(long[].class, primitiveArray);
        this.representers.put(float[].class, primitiveArray);
        this.representers.put(double[].class, primitiveArray);
        this.representers.put(char[].class, primitiveArray);
        this.representers.put(boolean[].class, primitiveArray);
        this.multiRepresenters.put(Number.class, new RepresentNumber());
        this.multiRepresenters.put(List.class, new RepresentList());
        this.multiRepresenters.put(Map.class, new RepresentMap());
        this.multiRepresenters.put(Set.class, new RepresentSet());
        this.multiRepresenters.put(Iterator.class, new RepresentIterator());
        this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray());
        this.multiRepresenters.put(Date.class, new RepresentDate());
        this.multiRepresenters.put(Enum.class, new RepresentEnum());
        this.multiRepresenters.put(Calendar.class, new RepresentDate());
        this.classTags = new HashMap();
        this.nonPrintableStyle = options.getNonPrintableStyle();
    }

    protected Tag getTag(Class<?> clazz, Tag defaultTag) {
        if (this.classTags.containsKey(clazz)) {
            return this.classTags.get(clazz);
        }
        return defaultTag;
    }

    public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Tag must be provided.");
        }
        return this.classTags.put(clazz, tag);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentNull.class */
    public class RepresentNull implements Represent {
        protected RepresentNull() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            return SafeRepresenter.this.representScalar(Tag.NULL, Configurator.NULL);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentString.class */
    public class RepresentString implements Represent {
        protected RepresentString() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Tag tag = Tag.STR;
            DumperOptions.ScalarStyle style = null;
            String value = data.toString();
            if (SafeRepresenter.this.nonPrintableStyle == DumperOptions.NonPrintableStyle.BINARY && !StreamReader.isPrintable(value)) {
                tag = Tag.BINARY;
                try {
                    byte[] bytes = value.getBytes("UTF-8");
                    String checkValue = new String(bytes, "UTF-8");
                    if (!checkValue.equals(value)) {
                        throw new YAMLException("invalid string value has occurred");
                    }
                    char[] binary = Base64Coder.encode(bytes);
                    value = String.valueOf(binary);
                    style = DumperOptions.ScalarStyle.LITERAL;
                } catch (UnsupportedEncodingException e) {
                    throw new YAMLException(e);
                }
            }
            if (SafeRepresenter.this.defaultScalarStyle == DumperOptions.ScalarStyle.PLAIN && SafeRepresenter.MULTILINE_PATTERN.matcher(value).find()) {
                style = DumperOptions.ScalarStyle.LITERAL;
            }
            return SafeRepresenter.this.representScalar(tag, value, style);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentBoolean.class */
    public class RepresentBoolean implements Represent {
        protected RepresentBoolean() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            String value;
            if (Boolean.TRUE.equals(data)) {
                value = "true";
            } else {
                value = "false";
            }
            return SafeRepresenter.this.representScalar(Tag.BOOL, value);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentNumber.class */
    public class RepresentNumber implements Represent {
        protected RepresentNumber() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            String value;
            Tag tag;
            if ((data instanceof Byte) || (data instanceof Short) || (data instanceof Integer) || (data instanceof Long) || (data instanceof BigInteger)) {
                tag = Tag.INT;
                value = data.toString();
            } else {
                Number number = (Number) data;
                tag = Tag.FLOAT;
                if (number.equals(Double.valueOf(Double.NaN))) {
                    value = ".NaN";
                } else if (number.equals(Double.valueOf(Double.POSITIVE_INFINITY))) {
                    value = ".inf";
                } else if (number.equals(Double.valueOf(Double.NEGATIVE_INFINITY))) {
                    value = "-.inf";
                } else {
                    value = number.toString();
                }
            }
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentList.class */
    public class RepresentList implements Represent {
        protected RepresentList() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List) data, DumperOptions.FlowStyle.AUTO);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentIterator.class */
    public class RepresentIterator implements Represent {
        protected RepresentIterator() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Iterator<Object> iter = (Iterator) data;
            return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new IteratorWrapper(iter), DumperOptions.FlowStyle.AUTO);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$IteratorWrapper.class */
    private static class IteratorWrapper implements Iterable<Object> {
        private Iterator<Object> iter;

        public IteratorWrapper(Iterator<Object> iter) {
            this.iter = iter;
        }

        @Override // java.lang.Iterable
        public Iterator<Object> iterator() {
            return this.iter;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentArray.class */
    public class RepresentArray implements Represent {
        protected RepresentArray() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Object[] array = (Object[]) data;
            List<Object> list = Arrays.asList(array);
            return SafeRepresenter.this.representSequence(Tag.SEQ, list, DumperOptions.FlowStyle.AUTO);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentPrimitiveArray.class */
    public class RepresentPrimitiveArray implements Represent {
        protected RepresentPrimitiveArray() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Class<?> type = data.getClass().getComponentType();
            if (Byte.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asByteList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Short.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asShortList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Integer.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asIntList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Long.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asLongList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Float.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asFloatList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Double.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asDoubleList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Character.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asCharList(data), DumperOptions.FlowStyle.AUTO);
            }
            if (Boolean.TYPE == type) {
                return SafeRepresenter.this.representSequence(Tag.SEQ, asBooleanList(data), DumperOptions.FlowStyle.AUTO);
            }
            throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
        }

        private List<Byte> asByteList(Object in) {
            byte[] array = (byte[]) in;
            List<Byte> list = new ArrayList<>(array.length);
            for (byte b : array) {
                list.add(Byte.valueOf(b));
            }
            return list;
        }

        private List<Short> asShortList(Object in) {
            short[] array = (short[]) in;
            List<Short> list = new ArrayList<>(array.length);
            for (short s : array) {
                list.add(Short.valueOf(s));
            }
            return list;
        }

        private List<Integer> asIntList(Object in) {
            int[] array = (int[]) in;
            List<Integer> list = new ArrayList<>(array.length);
            for (int i : array) {
                list.add(Integer.valueOf(i));
            }
            return list;
        }

        private List<Long> asLongList(Object in) {
            long[] array = (long[]) in;
            List<Long> list = new ArrayList<>(array.length);
            for (long j : array) {
                list.add(Long.valueOf(j));
            }
            return list;
        }

        private List<Float> asFloatList(Object in) {
            float[] array = (float[]) in;
            List<Float> list = new ArrayList<>(array.length);
            for (float f : array) {
                list.add(Float.valueOf(f));
            }
            return list;
        }

        private List<Double> asDoubleList(Object in) {
            double[] array = (double[]) in;
            List<Double> list = new ArrayList<>(array.length);
            for (double d : array) {
                list.add(Double.valueOf(d));
            }
            return list;
        }

        private List<Character> asCharList(Object in) {
            char[] array = (char[]) in;
            List<Character> list = new ArrayList<>(array.length);
            for (char c : array) {
                list.add(Character.valueOf(c));
            }
            return list;
        }

        private List<Boolean> asBooleanList(Object in) {
            boolean[] array = (boolean[]) in;
            List<Boolean> list = new ArrayList<>(array.length);
            for (boolean z : array) {
                list.add(Boolean.valueOf(z));
            }
            return list;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentMap.class */
    public class RepresentMap implements Represent {
        protected RepresentMap() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map) data, DumperOptions.FlowStyle.AUTO);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentSet.class */
    public class RepresentSet implements Represent {
        protected RepresentSet() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Map<Object, Object> value = new LinkedHashMap<>();
            Set<Object> set = (Set) data;
            for (Object key : set) {
                value.put(key, null);
            }
            return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, DumperOptions.FlowStyle.AUTO);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentDate.class */
    public class RepresentDate implements Represent {
        protected RepresentDate() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Calendar calendar;
            if (data instanceof Calendar) {
                calendar = (Calendar) data;
            } else {
                calendar = Calendar.getInstance(SafeRepresenter.this.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : SafeRepresenter.this.timeZone);
                calendar.setTime((Date) data);
            }
            int years = calendar.get(1);
            int months = calendar.get(2) + 1;
            int days = calendar.get(5);
            int hour24 = calendar.get(11);
            int minutes = calendar.get(12);
            int seconds = calendar.get(13);
            int millis = calendar.get(14);
            StringBuilder buffer = new StringBuilder(String.valueOf(years));
            while (buffer.length() < 4) {
                buffer.insert(0, "0");
            }
            buffer.append("-");
            if (months < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(months));
            buffer.append("-");
            if (days < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(days));
            buffer.append("T");
            if (hour24 < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(hour24));
            buffer.append(CallSiteDescriptor.TOKEN_DELIMITER);
            if (minutes < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(minutes));
            buffer.append(CallSiteDescriptor.TOKEN_DELIMITER);
            if (seconds < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(seconds));
            if (millis > 0) {
                if (millis < 10) {
                    buffer.append(".00");
                } else if (millis < 100) {
                    buffer.append(".0");
                } else {
                    buffer.append(".");
                }
                buffer.append(String.valueOf(millis));
            }
            int gmtOffset = calendar.getTimeZone().getOffset(calendar.getTime().getTime());
            if (gmtOffset == 0) {
                buffer.append('Z');
            } else {
                if (gmtOffset < 0) {
                    buffer.append('-');
                    gmtOffset *= -1;
                } else {
                    buffer.append('+');
                }
                int minutesOffset = gmtOffset / AutoReconnect.MAX;
                int hoursOffset = minutesOffset / 60;
                int partOfHour = minutesOffset % 60;
                if (hoursOffset < 10) {
                    buffer.append('0');
                }
                buffer.append(hoursOffset);
                buffer.append(':');
                if (partOfHour < 10) {
                    buffer.append('0');
                }
                buffer.append(partOfHour);
            }
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), DumperOptions.ScalarStyle.PLAIN);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentEnum.class */
    public class RepresentEnum implements Represent {
        protected RepresentEnum() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            Tag tag = new Tag((Class<? extends Object>) data.getClass());
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum) data).name());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentByteArray.class */
    public class RepresentByteArray implements Represent {
        protected RepresentByteArray() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            char[] binary = Base64Coder.encode((byte[]) data);
            return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), DumperOptions.ScalarStyle.LITERAL);
        }
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/representer/SafeRepresenter$RepresentUuid.class */
    public class RepresentUuid implements Represent {
        protected RepresentUuid() {
            SafeRepresenter.this = r4;
        }

        @Override // org.yaml.snakeyaml.representer.Represent
        public Node representData(Object data) {
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), new Tag(UUID.class)), data.toString());
        }
    }
}
