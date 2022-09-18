package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CaffeineSpec.class */
public final class CaffeineSpec {
    static final String SPLIT_OPTIONS = ",";
    static final String SPLIT_KEY_VALUE = "=";
    final String specification;
    int initialCapacity = -1;
    long maximumWeight = -1;
    long maximumSize = -1;
    boolean recordStats;
    Caffeine.Strength keyStrength;
    Caffeine.Strength valueStrength;
    Duration expireAfterWrite;
    Duration expireAfterAccess;
    Duration refreshAfterWrite;

    private CaffeineSpec(String specification) {
        this.specification = (String) Objects.requireNonNull(specification);
    }

    public Caffeine<Object, Object> toBuilder() {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (this.initialCapacity != -1) {
            builder.initialCapacity(this.initialCapacity);
        }
        if (this.maximumSize != -1) {
            builder.maximumSize(this.maximumSize);
        }
        if (this.maximumWeight != -1) {
            builder.maximumWeight(this.maximumWeight);
        }
        if (this.keyStrength != null) {
            Caffeine.requireState(this.keyStrength == Caffeine.Strength.WEAK);
            builder.weakKeys();
        }
        if (this.valueStrength != null) {
            if (this.valueStrength == Caffeine.Strength.WEAK) {
                builder.weakValues();
            } else if (this.valueStrength == Caffeine.Strength.SOFT) {
                builder.softValues();
            } else {
                throw new IllegalStateException();
            }
        }
        if (this.expireAfterWrite != null) {
            builder.expireAfterWrite(this.expireAfterWrite);
        }
        if (this.expireAfterAccess != null) {
            builder.expireAfterAccess(this.expireAfterAccess);
        }
        if (this.refreshAfterWrite != null) {
            builder.refreshAfterWrite(this.refreshAfterWrite);
        }
        if (this.recordStats) {
            builder.recordStats();
        }
        return builder;
    }

    public static CaffeineSpec parse(String specification) {
        String[] split;
        CaffeineSpec spec = new CaffeineSpec(specification);
        for (String option : specification.split(SPLIT_OPTIONS)) {
            spec.parseOption(option.trim());
        }
        return spec;
    }

    void parseOption(String option) {
        if (option.isEmpty()) {
            return;
        }
        String[] keyAndValue = option.split(SPLIT_KEY_VALUE);
        Caffeine.requireArgument(keyAndValue.length <= 2, "key-value pair %s with more than one equals sign", option);
        String key = keyAndValue[0].trim();
        String value = keyAndValue.length == 1 ? null : keyAndValue[1].trim();
        configure(key, value);
    }

    void configure(String key, String value) {
        boolean z = true;
        switch (key.hashCode()) {
            case -1076762142:
                if (key.equals("expireAfterWrite")) {
                    z = true;
                    break;
                }
                break;
            case -737229428:
                if (key.equals("weakKeys")) {
                    z = true;
                    break;
                }
                break;
            case -83937812:
                if (key.equals("softValues")) {
                    z = true;
                    break;
                }
                break;
            case 336225217:
                if (key.equals("expireAfterAccess")) {
                    z = true;
                    break;
                }
                break;
            case 502967994:
                if (key.equals("weakValues")) {
                    z = true;
                    break;
                }
                break;
            case 706249886:
                if (key.equals("refreshAfterWrite")) {
                    z = true;
                    break;
                }
                break;
            case 817286328:
                if (key.equals("maximumWeight")) {
                    z = true;
                    break;
                }
                break;
            case 1306358478:
                if (key.equals("recordStats")) {
                    z = true;
                    break;
                }
                break;
            case 1685649985:
                if (key.equals("maximumSize")) {
                    z = true;
                    break;
                }
                break;
            case 1725385758:
                if (key.equals("initialCapacity")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                initialCapacity(key, value);
                return;
            case true:
                maximumSize(key, value);
                return;
            case true:
                maximumWeight(key, value);
                return;
            case true:
                weakKeys(value);
                return;
            case true:
                valueStrength(key, value, Caffeine.Strength.WEAK);
                return;
            case true:
                valueStrength(key, value, Caffeine.Strength.SOFT);
                return;
            case true:
                expireAfterAccess(key, value);
                return;
            case true:
                expireAfterWrite(key, value);
                return;
            case true:
                refreshAfterWrite(key, value);
                return;
            case true:
                recordStats(value);
                return;
            default:
                throw new IllegalArgumentException("Unknown key " + key);
        }
    }

    void initialCapacity(String key, String value) {
        Caffeine.requireArgument(this.initialCapacity == -1, "initial capacity was already set to %,d", Integer.valueOf(this.initialCapacity));
        this.initialCapacity = parseInt(key, value);
    }

    void maximumSize(String key, String value) {
        Caffeine.requireArgument(this.maximumSize == -1, "maximum size was already set to %,d", Long.valueOf(this.maximumSize));
        Caffeine.requireArgument(this.maximumWeight == -1, "maximum weight was already set to %,d", Long.valueOf(this.maximumWeight));
        this.maximumSize = parseLong(key, value);
    }

    void maximumWeight(String key, String value) {
        Caffeine.requireArgument(this.maximumWeight == -1, "maximum weight was already set to %,d", Long.valueOf(this.maximumWeight));
        Caffeine.requireArgument(this.maximumSize == -1, "maximum size was already set to %,d", Long.valueOf(this.maximumSize));
        this.maximumWeight = parseLong(key, value);
    }

    void weakKeys(String value) {
        Caffeine.requireArgument(value == null, "weak keys does not take a value", new Object[0]);
        Caffeine.requireArgument(this.keyStrength == null, "weak keys was already set", new Object[0]);
        this.keyStrength = Caffeine.Strength.WEAK;
    }

    void valueStrength(String key, String value, Caffeine.Strength strength) {
        Caffeine.requireArgument(value == null, "%s does not take a value", key);
        Caffeine.requireArgument(this.valueStrength == null, "%s was already set to %s", key, this.valueStrength);
        this.valueStrength = strength;
    }

    void expireAfterAccess(String key, String value) {
        Caffeine.requireArgument(this.expireAfterAccess == null, "expireAfterAccess was already set", new Object[0]);
        this.expireAfterAccess = parseDuration(key, value);
    }

    void expireAfterWrite(String key, String value) {
        Caffeine.requireArgument(this.expireAfterWrite == null, "expireAfterWrite was already set", new Object[0]);
        this.expireAfterWrite = parseDuration(key, value);
    }

    void refreshAfterWrite(String key, String value) {
        Caffeine.requireArgument(this.refreshAfterWrite == null, "refreshAfterWrite was already set", new Object[0]);
        this.refreshAfterWrite = parseDuration(key, value);
    }

    void recordStats(String value) {
        Caffeine.requireArgument(value == null, "record stats does not take a value", new Object[0]);
        Caffeine.requireArgument(!this.recordStats, "record stats was already set", new Object[0]);
        this.recordStats = true;
    }

    static int parseInt(String key, String value) {
        Caffeine.requireArgument(value != null && !value.isEmpty(), "value of key %s was omitted", key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("key %s value was set to %s, must be an integer", key, value), e);
        }
    }

    static long parseLong(String key, String value) {
        Caffeine.requireArgument(value != null && !value.isEmpty(), "value of key %s was omitted", key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("key %s value was set to %s, must be a long", key, value), e);
        }
    }

    static Duration parseDuration(String key, String value) {
        Caffeine.requireArgument(value != null && !value.isEmpty(), "value of key %s omitted", key);
        boolean isIsoFormat = value.contains("p") || value.contains("P");
        if (isIsoFormat) {
            Duration duration = Duration.parse(value);
            Caffeine.requireArgument(!duration.isNegative(), "key %s invalid format; was %s, but the duration cannot be negative", key, value);
            return duration;
        }
        long duration2 = parseLong(key, value.substring(0, value.length() - 1));
        TimeUnit unit = parseTimeUnit(key, value);
        return Duration.ofNanos(unit.toNanos(duration2));
    }

    static TimeUnit parseTimeUnit(String key, String value) {
        Caffeine.requireArgument(value != null && !value.isEmpty(), "value of key %s omitted", key);
        char lastChar = Character.toLowerCase(value.charAt(value.length() - 1));
        switch (lastChar) {
            case 'd':
                return TimeUnit.DAYS;
            case 'h':
                return TimeUnit.HOURS;
            case 'm':
                return TimeUnit.MINUTES;
            case 's':
                return TimeUnit.SECONDS;
            default:
                throw new IllegalArgumentException(String.format("key %s invalid format; was %s, must end with one of [dDhHmMsS]", key, value));
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaffeineSpec)) {
            return false;
        }
        CaffeineSpec spec = (CaffeineSpec) o;
        return Objects.equals(this.refreshAfterWrite, spec.refreshAfterWrite) && Objects.equals(this.expireAfterAccess, spec.expireAfterAccess) && Objects.equals(this.expireAfterWrite, spec.expireAfterWrite) && this.initialCapacity == spec.initialCapacity && this.maximumWeight == spec.maximumWeight && this.valueStrength == spec.valueStrength && this.keyStrength == spec.keyStrength && this.maximumSize == spec.maximumSize && this.recordStats == spec.recordStats;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.initialCapacity), Long.valueOf(this.maximumSize), Long.valueOf(this.maximumWeight), this.keyStrength, this.valueStrength, Boolean.valueOf(this.recordStats), this.expireAfterWrite, this.expireAfterAccess, this.refreshAfterWrite);
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return getClass().getSimpleName() + '{' + toParsableString() + '}';
    }
}
