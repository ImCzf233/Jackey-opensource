package jdk.nashorn.internal.runtime.options;

import java.util.Locale;
import java.util.TimeZone;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/options/OptionTemplate.class */
public final class OptionTemplate implements Comparable<OptionTemplate> {
    private final String resource;
    private final String key;
    private final boolean isHelp;
    private final boolean isXHelp;
    private String name;
    private String shortName;
    private String params;
    private String type;
    private String defaultValue;
    private String dependency;
    private String conflict;
    private boolean isUndocumented;
    private String description;
    private boolean valueNextArg;
    private static final int LINE_BREAK = 64;

    public OptionTemplate(String resource, String key, String value, boolean isHelp, boolean isXHelp) {
        this.resource = resource;
        this.key = key;
        this.isHelp = isHelp;
        this.isXHelp = isXHelp;
        parse(value);
    }

    public boolean isHelp() {
        return this.isHelp;
    }

    public boolean isXHelp() {
        return this.isXHelp;
    }

    public String getResource() {
        return this.resource;
    }

    public String getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultValue() {
        String type = getType();
        boolean z = true;
        switch (type.hashCode()) {
            case -2076227591:
                if (type.equals("timezone")) {
                    z = true;
                    break;
                }
                break;
            case -1097462182:
                if (type.equals("locale")) {
                    z = true;
                    break;
                }
                break;
            case 64711720:
                if (type.equals("boolean")) {
                    z = false;
                    break;
                }
                break;
            case 1958052158:
                if (type.equals("integer")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (this.defaultValue == null) {
                    this.defaultValue = "false";
                    break;
                }
                break;
            case true:
                if (this.defaultValue == null) {
                    this.defaultValue = "0";
                    break;
                }
                break;
            case true:
                this.defaultValue = TimeZone.getDefault().getID();
                break;
            case true:
                this.defaultValue = Locale.getDefault().toLanguageTag();
                break;
        }
        return this.defaultValue;
    }

    public String getDependency() {
        return this.dependency;
    }

    public String getConflict() {
        return this.conflict;
    }

    public boolean isUndocumented() {
        return this.isUndocumented;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isValueNextArg() {
        return this.valueNextArg;
    }

    private static String strip(String value, char start, char end) {
        int len = value.length();
        if (len > 1 && value.charAt(0) == start && value.charAt(len - 1) == end) {
            return value.substring(1, len - 1);
        }
        return null;
    }

    private void parse(String origValue) {
        String value = origValue.trim();
        try {
            QuotedStringTokenizer keyValuePairs = new QuotedStringTokenizer(strip(value, '{', '}'), ",");
            while (keyValuePairs.hasMoreTokens()) {
                String keyValue = keyValuePairs.nextToken();
                QuotedStringTokenizer st = new QuotedStringTokenizer(keyValue, "=");
                String keyToken = st.nextToken();
                String arg = st.nextToken();
                boolean z = true;
                switch (keyToken.hashCode()) {
                    case -2103299560:
                        if (keyToken.equals("value_next_arg")) {
                            z = true;
                            break;
                        }
                        break;
                    case -995427962:
                        if (keyToken.equals("params")) {
                            z = true;
                            break;
                        }
                        break;
                    case -580047918:
                        if (keyToken.equals("conflict")) {
                            z = true;
                            break;
                        }
                        break;
                    case -26291381:
                        if (keyToken.equals("dependency")) {
                            z = true;
                            break;
                        }
                        break;
                    case 3079825:
                        if (keyToken.equals("desc")) {
                            z = true;
                            break;
                        }
                        break;
                    case 3373707:
                        if (keyToken.equals("name")) {
                            z = true;
                            break;
                        }
                        break;
                    case 3575610:
                        if (keyToken.equals("type")) {
                            z = true;
                            break;
                        }
                        break;
                    case 572651336:
                        if (keyToken.equals("is_undocumented")) {
                            z = false;
                            break;
                        }
                        break;
                    case 1544803905:
                        if (keyToken.equals("default")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1565793390:
                        if (keyToken.equals("short_name")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        this.isUndocumented = Boolean.parseBoolean(arg);
                        break;
                    case true:
                        if (!arg.startsWith("-")) {
                            throw new IllegalArgumentException(arg);
                        }
                        this.name = arg;
                        break;
                    case true:
                        if (!arg.startsWith("-")) {
                            throw new IllegalArgumentException(arg);
                        }
                        this.shortName = arg;
                        break;
                    case true:
                        this.description = arg;
                        break;
                    case true:
                        this.params = arg;
                        break;
                    case true:
                        this.type = arg.toLowerCase(Locale.ENGLISH);
                        break;
                    case true:
                        this.defaultValue = arg;
                        break;
                    case true:
                        this.dependency = arg;
                        break;
                    case true:
                        this.conflict = arg;
                        break;
                    case true:
                        this.valueNextArg = Boolean.parseBoolean(arg);
                        break;
                    default:
                        throw new IllegalArgumentException(keyToken);
                }
            }
            if (this.type == null) {
                this.type = "boolean";
            }
            if (this.params == null && "boolean".equals(this.type)) {
                this.params = "[true|false]";
            }
            if (this.name == null && this.shortName == null) {
                throw new IllegalArgumentException(origValue);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(origValue);
        }
    }

    public boolean nameMatches(String aName) {
        return aName.equals(this.shortName) || aName.equals(this.name);
    }

    public String toString() {
        char[] charArray;
        StringBuilder sb = new StringBuilder();
        sb.append('\t');
        if (this.shortName != null) {
            sb.append(this.shortName);
            if (this.name != null) {
                sb.append(", ");
            }
        }
        if (this.name != null) {
            sb.append(this.name);
        }
        if (this.description != null) {
            int indent = sb.length();
            sb.append(' ');
            sb.append('(');
            int pos = 0;
            for (char c : this.description.toCharArray()) {
                sb.append(c);
                pos++;
                if (pos >= 64 && Character.isWhitespace(c)) {
                    pos = 0;
                    sb.append("\n\t");
                    for (int i = 0; i < indent; i++) {
                        sb.append(' ');
                    }
                }
            }
            sb.append(')');
        }
        if (this.params != null) {
            sb.append('\n');
            sb.append('\t');
            sb.append('\t');
            sb.append(Options.getMsg("nashorn.options.param", new String[0])).append(": ");
            sb.append(this.params);
            sb.append("   ");
            Object def = getDefaultValue();
            if (def != null) {
                sb.append(Options.getMsg("nashorn.options.default", new String[0])).append(": ");
                sb.append(getDefaultValue());
            }
        }
        return sb.toString();
    }

    public int compareTo(OptionTemplate o) {
        return getKey().compareTo(o.getKey());
    }
}
