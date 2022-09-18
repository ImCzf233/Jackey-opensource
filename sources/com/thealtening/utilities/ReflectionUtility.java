package com.thealtening.utilities;

import java.lang.reflect.Field;

/* loaded from: Jackey Client b2.jar:com/thealtening/utilities/ReflectionUtility.class */
public class ReflectionUtility {
    private String className;
    private Class<?> clazz;

    public ReflectionUtility(String className) {
        try {
            this.clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setStaticField(String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & (-17));
        field.set(null, newValue);
    }
}
