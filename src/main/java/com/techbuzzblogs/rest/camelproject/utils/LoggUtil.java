package com.techbuzzblogs.rest.camelproject.utils;

import com.techbuzzblogs.rest.camelproject.decorators.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class LoggUtil {

    /**
     * Extracts properties annotated with @Logger from an object and returns them in a map.
     *
     * @param object The object to extract properties from.
     * @return A map containing extracted properties.
     */
    public static Map<String, String> extractProperties(Object object) {
        Map<String, String> propertyMap = new HashMap<>();
        extractPropertiesRecursively(object, "", propertyMap);
        return propertyMap;
    }

    /**
     * Recursively extracts properties from an object and populates the property map.
     *
     * @param object      The object to extract properties from.
     * @param prefix      The prefix for the property name.
     * @param propertyMap The map to store extracted properties.
     */
    private static void extractPropertiesRecursively(Object object, String prefix, Map<String, String> propertyMap) {
        if (object == null) {
            return;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();


        for (Field field : fields) {
            field.setAccessible(true);

            if (isFieldIgnorable(field)) {
                continue;
            }

            if (field.isAnnotationPresent(Logger.class)) {
                try {
                    Object value = field.get(object);
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

                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Processes an array, extracting properties and updating the property map.
     *
     * @param array       The array to process.
     * @param propertyName The name of the property.
     * @param propertyMap  The map to store extracted properties.
     */
    private static void processArray(Object array, String propertyName, Map<String, String> propertyMap) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            extractPropertiesRecursively(Array.get(array, i), concatFullNameProperty(propertyName, i), propertyMap);
        }
    }

    /**
     * Processes a list, extracting properties and updating the property map.
     *
     * @param list         The list to process.
     * @param propertyName The name of the property.
     * @param propertyMap  The map to store extracted properties.
     */
    private static void processList(List<?> list, String propertyName, Map<String, String> propertyMap) {
        for (int i = 0; i < list.size(); i++) {
            extractPropertiesRecursively(list.get(i), concatFullNameProperty(propertyName, i), propertyMap);
        }
    }

    /**
     * Concatenates the property name with the index for array or list elements.
     *
     * @param propertyName The name of the property.
     * @param index        The index of the array or list element.
     * @return The concatenated property name.
     */
    private static String concatFullNameProperty(String propertyName, int index) {
        return propertyName + "[" + index + "]";
    }

    /**
     * Creates a mutable copy of a collection if it is immutable.
     *
     * @param value The collection to copy.
     * @return A mutable copy of the collection.
     */
    private static Object createMutableCopy(Object value) {
        if (value instanceof List) {
            List<?> listImmutable = (List<?>) value;
            return new ArrayList<>(listImmutable);
        }
        return value;
    }

    /**
     * Checks if a collection is immutable.
     *
     * @param value The collection to check.
     * @return True if the collection is immutable, false otherwise.
     */
    private static boolean isCollectionImmutable(Object value) {
        return (value != null && value.getClass().getName().startsWith("java.util.ImmutableCollections$"));
    }

    /**
     * Checks if a value is a simple type (String, Number, Boolean, or null).
     *
     * @param value The value to check.
     * @return True if the value is a simple type, false otherwise.
     */
    private static boolean isSimpleType(Object value) {
        return (value instanceof String || value instanceof Number || value instanceof Boolean || value == null);
    }

    /**
     * Checks if a field should be ignored (static, final, or synthetic).
     *
     * @param field The field to check.
     * @return True if the field should be ignored, false otherwise.
     */
    private static boolean isFieldIgnorable(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || field.isSynthetic();
    }
}
