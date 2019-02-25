package org.reportengine.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {

    public static Object call(String clazzName, Object instance, String methodName) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            Method method = clazz.getDeclaredMethod(methodName);
            return method.invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            _logger.error("Exception,", ex);
            return new RuntimeException(ex.getMessage());
        }
    }
}
