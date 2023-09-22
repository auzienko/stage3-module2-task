package com.mjc.school.view.console.command;

import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.controller.annotation.CommandHandler;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandDispatcher {

    private final Map<String, Context> routMap = new HashMap<>();

    public CommandDispatcher(NewsController newsController, AuthorController authorController) {
        initializeRoutMap(newsController);
        initializeRoutMap(authorController);
    }

    @SneakyThrows
    private void initializeRoutMap(Object object) {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                CommandHandler annotation = method.getAnnotation(CommandHandler.class);
                String value = annotation.value();
                routMap.put(value, new Context(object, method));
            }
        }
    }


    public void execute(String command, Object... args) throws IllegalAccessException {
        Context context = routMap.get(command);
        try {
            Object invoke = context.method.invoke(context.object, args);
            if (invoke instanceof Collection<?> collection) {
                collection.forEach(System.out::println);
            } else {
                System.out.println(invoke);
            }
        } catch (InvocationTargetException e) {
            throw (RuntimeException) e.getCause();
        }
    }

    private record Context(Object object, Method method) {
    }
}
