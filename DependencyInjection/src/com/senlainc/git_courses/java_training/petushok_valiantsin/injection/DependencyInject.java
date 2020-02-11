package com.senlainc.git_courses.java_training.petushok_valiantsin.injection;

import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.annotation.DependencyPrimary;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.utility.ClassReader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DependencyInject {
    private static DependencyInject instance;
    private static final Map<String, Constructor<?>> interfaceConstructorMap = new HashMap<>();
    private static List<Class<?>> projectClasses;

    private DependencyInject() {
        try {
            projectClasses = ClassReader.getClasses().stream().filter(i -> i.getInterfaces().length > 0).collect(Collectors.toList());
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DependencyInject getInstance() {
        if (instance == null) {
            instance = new DependencyInject();
        }
        return instance;
    }

    public Constructor<?> injection(Field field) throws NoSuchMethodException, ClassNotFoundException {
        if (field.getType().isInterface()) {
            return interfaceInjection(field);
        }
        return classInjection(field);
    }

    private Constructor<?> classInjection(Field field) throws NoSuchMethodException {
        return field.getType().getDeclaredConstructor();
    }

    private Constructor<?> interfaceInjection(Field field) throws NoSuchMethodException, ClassNotFoundException {
        if (interfaceConstructorMap.containsKey(field.getType().getName())) {
            return interfaceConstructorMap.get(field.getType().getName());
        }
        final Class<?> clazz = (Class<?>) multiImplementation(field);
        interfaceConstructorMap.put(field.getType().getName(), clazz.getDeclaredConstructor());
        return clazz.getDeclaredConstructor();
    }

    private Object multiImplementation(Field field) throws ClassNotFoundException {
        final List<Class<?>> interfaceClass = new ArrayList<>();
        for (Class<?> clazz : projectClasses) {
            if (Arrays.stream(clazz.getInterfaces()).anyMatch(i -> i.equals(field.getType()))) {
                interfaceClass.add(clazz);
            }
        }
        if (interfaceClass.size() == 1) {
            return interfaceClass.get(0);
        } else {
            return interfaceClass.stream().filter(i -> i.isAnnotationPresent(DependencyPrimary.class)).findFirst().orElseThrow(ClassNotFoundException::new);
        }
    }
}
