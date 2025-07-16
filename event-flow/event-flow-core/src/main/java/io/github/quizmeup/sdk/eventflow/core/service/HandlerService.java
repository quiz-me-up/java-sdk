package io.github.quizmeup.sdk.eventflow.core.service;

import io.github.quizmeup.sdk.eventflow.annotation.*;
import io.github.quizmeup.sdk.eventflow.annotation.CommandHandler;
import io.github.quizmeup.sdk.eventflow.annotation.EventHandler;
import io.github.quizmeup.sdk.eventflow.annotation.EventSourcingHandler;
import io.github.quizmeup.sdk.eventflow.annotation.QueryHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageResultTopic;
import io.github.quizmeup.sdk.eventflow.core.usecase.RegisterHandler;
import io.github.quizmeup.sdk.eventflow.core.usecase.ScanClass;
import io.github.quizmeup.sdk.eventflow.core.usecase.ScanObject;
import io.github.quizmeup.sdk.eventflow.core.usecase.ScanPackage;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.*;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageTopic;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.CommandDispatcher;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.EventDispatcher;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.QueryDispatcher;
import io.github.quizmeup.sdk.eventflow.core.port.HandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

import static java.util.Objects.isNull;

/**
 * {@code HandlerService} is a domain service responsible for managing and registering different types of handlers
 * (e.g., {@link io.github.quizmeup.sdk.eventflow.core.domain.handler.EventHandler}, {@link io.github.quizmeup.sdk.eventflow.core.domain.handler.QueryHandler}, {@link io.github.quizmeup.sdk.eventflow.core.domain.handler.CommandHandler}, {@link io.github.quizmeup.sdk.eventflow.core.domain.handler.EventSourcingHandler}).
 * It provides functionalities to register handlers explicitly, scan for handlers within a package or object,
 * and subscribe handlers to their respective dispatchers.
 */
@Slf4j
@DomainService
public class HandlerService implements RegisterHandler, ScanPackage, ScanObject, ScanClass {
    private final HandlerRegistry handlerRegistry;

    private final EventDispatcher eventDispatcher;
    private final QueryDispatcher queryDispatcher;
    private final CommandDispatcher commandDispatcher;

    private final TopicService topicService;

    /**
     * Constructs a {@code HandlerService} with the necessary dependencies.
     *
     * @param topicService      The {@link TopicService} for managing topics.
     * @param handlerRegistry   The {@link HandlerRegistry} for storing and retrieving handlers.
     * @param eventDispatcher   The {@link EventDispatcher} for dispatching events.
     * @param queryDispatcher   The {@link QueryDispatcher} for dispatching queries.
     * @param commandDispatcher The {@link CommandDispatcher} for dispatching commands.
     */
    public HandlerService(TopicService topicService,
                          HandlerRegistry handlerRegistry,
                          EventDispatcher eventDispatcher,
                          QueryDispatcher queryDispatcher,
                          CommandDispatcher commandDispatcher) {
        this.handlerRegistry = handlerRegistry;
        this.eventDispatcher = eventDispatcher;
        this.queryDispatcher = queryDispatcher;
        this.commandDispatcher = commandDispatcher;
        this.topicService = topicService;
    }

    /**
     * Registers a given handler with the appropriate dispatcher and stores it in the handler registry.
     * It also creates and saves a topic based on the handler's payload class.
     *
     * @param handler The {@link Handler} to register.  Must not be null.
     * @throws BadArgumentException if the handler or its payload class is null.
     */
    @Override
    public <HANDLER extends Handler> void register(HANDLER handler) {

        if (isNull(handler)) throw new BadArgumentException("handler cannot be null");

        final Class<?> payloadClass = handler.payloadClass();

        if (isNull(payloadClass)) throw new BadArgumentException("payloadClass cannot be null");

        final String handlerInstanceName = handler.instance().getClass().getSimpleName();
        final MessageTopic messageTopic = new MessageTopic(payloadClass.getSimpleName());
        final MessageResultTopic messageResultTopic = new MessageResultTopic(payloadClass.getSimpleName());

        topicService.save(messageTopic);
        topicService.save(messageResultTopic);

        switch (handler) {
            case io.github.quizmeup.sdk.eventflow.core.domain.handler.EventHandler eventHandler -> {
                handlerRegistry.registerHandler(eventHandler);
                eventDispatcher.subscribeTo(messageTopic, handlerInstanceName);
            }
            case io.github.quizmeup.sdk.eventflow.core.domain.handler.QueryHandler queryHandler -> {
                handlerRegistry.registerHandler(queryHandler);
                queryDispatcher.subscribeTo(messageTopic, handlerInstanceName);
            }
            case io.github.quizmeup.sdk.eventflow.core.domain.handler.CommandHandler commandHandler -> {
                handlerRegistry.registerHandler(commandHandler);
                commandDispatcher.subscribeTo(messageTopic, handlerInstanceName);
            }
            case io.github.quizmeup.sdk.eventflow.core.domain.handler.EventSourcingHandler eventSourcingHandler -> {
                handlerRegistry.registerHandler(eventSourcingHandler);
            }
            default -> throw new BadArgumentException("Unexpected value: " + handler);
        }
    }


    @Override
    public Collection<Handler> scan(Class<?> clazz) {
        final List<Handler> handlers = new ArrayList<>();

        if (isNotInterfaceOrAbstract(clazz)) {
            tryCreateInstance(clazz)
                    .map(this::scan)
                    .ifPresent(handlers::addAll);
        }

        return handlers;
    }

    /**
     * Scans an object instance for methods annotated with {@link EventHandler}, {@link QueryHandler}, {@link CommandHandler},
     * or {@link EventSourcingHandler} and creates corresponding handlers.
     *
     * @param instance The object instance to scan.
     * @return A collection of {@link Handler} instances found in the object.  Returns an empty collection if the instance is null.
     */
    @Override
    public Collection<Handler> scan(Object instance) {
        final Collection<Handler> handlers = new ArrayList<>();

        if (isNull(instance)) {
            return handlers;
        }

        final Class<?> clazz = instance.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            final Class<?>[] methodParameterTypes = method.getParameterTypes();

            if (methodParameterTypes.length > 0) {
                final Class<?> messagePayloadClass = method.getParameterTypes()[0];

                if (method.isAnnotationPresent(EventHandler.class)) {
                    final DefaultEventHandler eventHandler = new DefaultEventHandler(messagePayloadClass, instance, method);
                    handlers.add(eventHandler);
                } else if (method.isAnnotationPresent(QueryHandler.class)) {
                    final DefaultQueryHandler queryHandler = new DefaultQueryHandler(messagePayloadClass, instance, method);
                    handlers.add(queryHandler);
                } else if (method.isAnnotationPresent(CommandHandler.class)) {
                    final DefaultCommandHandler commandHandler = new DefaultCommandHandler(messagePayloadClass, instance, method);
                    handlers.add(commandHandler);
                } else if (method.isAnnotationPresent(EventSourcingHandler.class)) {
                    final DefaultEventSourcingHandler eventSourcingHandler = new DefaultEventSourcingHandler(messagePayloadClass, instance, method);
                    handlers.add(eventSourcingHandler);
                }
            }
        }

        return handlers;
    }


    /**
     * Scans a package for classes and creates handler instances from those classes.
     * It searches for classes that are not interfaces or abstract and attempts to create instances of them.
     *
     * @param packageName The name of the package to scan.
     * @return A collection of {@link Handler} instances found in the package.
     */
    @Override
    public Collection<Handler> scan(String packageName) {
        final List<Handler> handlers = new ArrayList<>();

        if (StringUtils.isBlank(packageName)) {
            return handlers;
        }

        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final String path = packageName.replace('.', '/');
            final Enumeration<URL> resources = classLoader.getResources(path);

            final List<File> dirs = new ArrayList<>();

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }

            for (File directory : dirs) {
                final Collection<Handler> handlerCollection = findHandlers(directory, packageName);
                handlers.addAll(handlerCollection);
            }

        } catch (IOException ignored) {
        }

        return handlers;
    }

    /**
     * Attempts to create an instance of a given class using its default constructor.
     *
     * @param clazz The class to instantiate.
     * @return An {@link Optional} containing the created instance, or an empty {@link Optional} if instantiation fails.
     */
    private static Optional<Object> tryCreateInstance(Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return Optional.of(constructor.newInstance());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }


    /**
     * Recursively finds handler instances within a directory and its subdirectories.
     *
     * @param directory   The directory to scan.
     * @param packageName The package name corresponding to the directory.
     * @return A list of {@link Handler} instances found in the directory and its subdirectories.
     */
    private List<Handler> findHandlers(File directory, String packageName) {
        List<Handler> handlers = new ArrayList<>();

        if (!directory.exists()) {
            return handlers;
        }

        final File[] files = directory.listFiles();

        if (isNull(files)) { // Guard against null files array
            return handlers;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                handlers.addAll(findHandlers(file, packageName + "." + file.getName()));
                continue; // Move to the next file
            }

            if (!file.getName().endsWith(".class")) {
                continue; // Move to the next file
            }

            final String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);

            try {
                final Class<?> clazz = Class.forName(className);
                final Collection<Handler> handlerCollection = scan(clazz);
                handlers.addAll(handlerCollection);
            } catch (ClassNotFoundException ignored) {
                // Log this, even if ignored
                log.warn("Class not found: {}", className);
            }
        }

        return handlers;
    }

    /**
     * Checks if a given class is not an interface or an abstract class.
     *
     * @param clazz The class to check.
     * @return {@code true} if the class is not an interface or abstract, {@code false} otherwise.
     */
    private boolean isNotInterfaceOrAbstract(Class<?> clazz) {
        return !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }
}
