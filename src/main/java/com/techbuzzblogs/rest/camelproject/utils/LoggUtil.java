package com.techbuzzblogs.rest.camelproject.utils;

import com.techbuzzblogs.rest.camelproject.decorators.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggUtil {

    public static Map<String, String> extractPropertyInSaveMap(Object object) {
        Map<String, String> mappearProperties = new HashMap<>();

        extractPropertyInSaveMapRecursively(object, "", mappearProperties);

        System.out.println(mappearProperties);

        return mappearProperties;
    }

    private static void extractPropertyInSaveMapRecursively(Object object, String prefix, Map<String, String> mappearProperties) {
        if (object == null) {
            return;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // deixa o campo acessível, mesmo com a propriedade privada

            // ignora campos estáticos, finais e sintéticos
            if (isFieldIgnorable(field)) {
                continue;
            }

            // verifica se o campo está anotado com o Logger
            if (field.isAnnotationPresent(Logger.class)) {

                try {
                    Object valueField = field.get(object);
                    String nameProperty = prefix.isEmpty() ? field.getName() : prefix + "." + field.getName();

                    if (isTypeSimples(valueField)) {
                        mappearProperties.put(nameProperty, valueField != null ? valueField.toString() : "null");
                    } else if (valueField != null && valueField.getClass().isArray()) {
                        int lenghtValueField = Array.getLength(valueField);

                        for (int i = 0; i < lenghtValueField; i++) {
                            extractPropertyInSaveMapRecursively(
                                    Array.get(valueField, i),
                                    concatFullNameProperty(nameProperty, i),
                                    mappearProperties);
                        }

                    } else if (valueField instanceof List<?>) {
                        List<?> list = (List<?>) valueField;

                        for (int i = 0; i < list.size(); i++) {
                            extractPropertyInSaveMapRecursively(
                                    list.get(i),
                                    concatFullNameProperty(nameProperty, i),
                                    mappearProperties);
                        }
                    } else if (isCollectionImmutable(valueField)) {
                        // se for uma collecão imutável, cria uma copia
                        Object collectionMmutable = createMmutableCopy(valueField);

                        extractPropertyInSaveMapRecursively(
                                valueField,
                                nameProperty,
                                mappearProperties);
                    } else {
                        // se o valor não for simples, array, lista ou coleção imutável, chama recursivamente
                        extractPropertyInSaveMapRecursively(
                                valueField,
                                nameProperty,
                                mappearProperties);
                    }

                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private static String concatFullNameProperty(String nameProperty, int index) {
        return nameProperty + "["+ index + "]";
    }

    private static Object createMmutableCopy(Object value) {
        // cria uma copía mutável da coleção imutável
        if (value instanceof List) {
            List<?> listImmutable = (List<?>) value;
            return new ArrayList<>(listImmutable);
        }

        return value;
    }

    private static boolean isCollectionImmutable(Object value) {
        return (value != null && value.getClass().getName().startsWith("java.util.ImmutableCollections$"));
    }

    private static boolean isTypeSimples(Object value) {
        return (
                value instanceof String ||
                        value instanceof Number ||
                        value instanceof Boolean ||
                        value == null);
    }

    private static boolean isFieldIgnorable(Field field) {
        int modifierNumber = field.getModifiers();

        return (
                Modifier.isStatic(modifierNumber) ||
                        Modifier.isFinal(modifierNumber) ||
                        field.isSynthetic());
    }

}
