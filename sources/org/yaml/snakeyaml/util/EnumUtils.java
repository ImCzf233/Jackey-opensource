package org.yaml.snakeyaml.util;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/util/EnumUtils.class */
public class EnumUtils {
    public static <T extends Enum<T>> T findEnumInsensitiveCase(Class<T> enumType, String name) {
        T[] enumConstants;
        for (T constant : enumType.getEnumConstants()) {
            if (constant.name().compareToIgnoreCase(name) == 0) {
                return constant;
            }
        }
        throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + name);
    }
}
