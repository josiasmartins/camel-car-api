package com.techbuzzblogs.rest.camelproject.utils;

import com.techbuzzblogs.rest.camelproject.decorators.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (isMethodIgnorable(method)) {
                continue;
            }

            if (method.isAnnotationPresent(Logger.class)) {
                try {
                    Object value = method.invoke(object);
                    String propertyName = prefix.isEmpty() ? method.getName() : prefix + "." + method.getName();

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

                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void processArray(Object array, String propertyName, Map<String, String> propertyMap) {
        int length = java.lang.reflect.Array.getLength(array);
        for (int i = 0; i < length; i++) {
            extractPropertiesRecursively(java.lang.reflect.Array.get(array, i), concatFullNameProperty(propertyName, i), propertyMap);
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
            return new java.util.ArrayList<>(listImmutable);
        }
        return value;
    }

    private static boolean isCollectionImmutable(Object value) {
        return (value != null && value.getClass().getName().startsWith("java.util.ImmutableCollections$"));
    }

    private static boolean isSimpleType(Object value) {
        return (value instanceof String || value instanceof Number || value instanceof Boolean || value == null);
    }

    private static boolean isMethodIgnorable(Method method) {
        int modifiers = method.getModifiers();
        return method.isSynthetic() || Modifier.isStatic(modifiers);
    }

}
