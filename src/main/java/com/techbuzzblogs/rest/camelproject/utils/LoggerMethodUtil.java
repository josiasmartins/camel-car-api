package com.techbuzzblogs.rest.camelproject.utils;

import com.techbuzzblogs.rest.camelproject.decorators.Logger;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggerMethodUtil {

    public static Map<String, String> extractProperties(Object object) {
        Map<String, String> propertyMap = new HashMap<>();
        extractPropertiesRecursively(object, "", propertyMap);
        return propertyMap;
    }

    private static void extractPropertiesRecursively(Object object, String prefix, Map<String, String> propertyMap) {
        if (object == null) {
            return;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isFieldIgnorable(field)) {
                continue;
            }

            if (field.isAnnotationPresent(Logger.class)) {
                try {
                    Object value = getFieldValueUsingGetter(object, field);
                    String propertyName = prefix.isEmpty() ? field.getName() : prefix + "." + field.getName();

                    if (isSimpleType(value)) {
                        propertyMap.put(propertyName, value != null ? value.toString() : "null");
                    } else if (value != null && value.getClass().isArray()) {
                        processArray(value, propertyName, propertyMap);
                    } else if (value instanceof List<?>) {
                        processList((List<?>) value, propertyName, propertyMap);
                    } else if (isCollectionImmutable(value)) {
                        Object mutableCopy = createMutableCopy(value);
                        extractPropertiesRecursively(mutableCopy, propertyName, propertyMap);
                    } else {
                        extractPropertiesRecursively(value, propertyName, propertyMap);
                    }

                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static Object getFieldValueUsingGetter(Object object, Field field) throws NoSuchMethodException {
        try {
            String getterMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method getterMethod = object.getClass().getMethod(getterMethodName);
            return getterMethod.invoke(object);
        } catch (Exception e) {
            throw new NoSuchMethodException("Getter not found for field: " + field.getName());
        }
    }

    private static void processArray(Object array, String propertyName, Map<String, String> propertyMap) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            extractPropertiesRecursively(Array.get(array, i), concatFullNameProperty(propertyName, i), propertyMap);
        }
    }

    private static void processList(List<?> list, String propertyName, Map<String, String> propertyMap) {
        for (int i = 0; i < list.size(); i++) {
            extractPropertiesRecursively(list.get(i), concatFullNameProperty(propertyName, i), propertyMap);
        }
    }

    private static String concatFullNameProperty(String propertyName, int index) {
        return propertyName + "[" + index + "]";
    }

    private static Object createMutableCopy(Object value) {
        if (value instanceof List) {
            List<?> listImmutable = (List<?>) value;
            return new ArrayList<>(listImmutable);
        }
        return value;
    }

    private static boolean isCollectionImmutable(Object value) {
        return (value != null && value.getClass().getName().startsWith("java.util.ImmutableCollections$"));
    }

    private static boolean isSimpleType(Object value) {
        return (value instanceof String || value instanceof Number || value instanceof Boolean || value == null);
    }

    private static boolean isFieldIgnorable(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || field.isSynthetic();
    }

}
