package com.techbuzzblogs.rest.camelproject.utils;

import com.techbuzzblogs.rest.camelproject.decorators.LoggerMethods;

import java.lang.reflect.Method;

public class LoggerMethodsProcessor {

    public static void processLoggerMethods(Object target) {
        if (target.getClass().isAnnotationPresent(LoggerMethods.class)) {
            Method[] methods = target.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (isGetter(method) || isSetter(method)) {
                    // Adicione a lógica para adicionar a anotação @Logger ao método, por exemplo:
                    // method.addAnnotation(Logger.class);
                }
            }
        }
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
                method.getParameterCount() == 0 &&
                !void.class.equals(method.getReturnType());
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set") &&
                method.getParameterCount() == 1 &&
                void.class.equals(method.getReturnType());
    }

}
