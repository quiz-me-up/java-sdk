package io.github.quizmeup.sdk.eventflow.core.stub;

import io.github.quizmeup.sdk.eventflow.annotation.Stub;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.*;
import io.github.quizmeup.sdk.eventflow.core.port.HandlerRegistry;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.isNull;

@Stub
public class InMemoryHandlerRegistry implements HandlerRegistry {

    private final ConcurrentMap<Class<?>, EventHandler> eventHandlers = new ConcurrentHashMap<>();
    private final ConcurrentMap<Class<?>, QueryHandler> queryHandlers = new ConcurrentHashMap<>();
    private final ConcurrentMap<Class<?>, CommandHandler> commandHandlers = new ConcurrentHashMap<>();
    private final ConcurrentMap<Class<?>, EventSourcingHandler> eventSourcingHandlers = new ConcurrentHashMap<>();

    @Override
    public void registerHandler(Handler handler) {
        if (isNull(handler)) throw new BadArgumentException("Handler cannot be null");

        final Class<?> payloadClass = handler.payloadClass();

        if (isNull(payloadClass)) throw new BadArgumentException("PayloadClass cannot be null");

        switch (handler) {
            case CommandHandler commandHandler -> commandHandlers.put(payloadClass, commandHandler);
            case QueryHandler queryHandler -> queryHandlers.put(payloadClass, queryHandler);
            case EventHandler eventHandler -> eventHandlers.put(payloadClass, eventHandler);
            case EventSourcingHandler eventSourcingHandler -> eventSourcingHandlers.put(payloadClass, eventSourcingHandler);
            default -> throw new BadArgumentException("Unexpected value: " + handler);
        }
    }

    @Override
    public Optional<CommandHandler> findCommandHandler(Class<?> messagePayloadClass) {
        return Optional.ofNullable(commandHandlers.getOrDefault(messagePayloadClass, null));
    }

    @Override
    public Optional<EventHandler> findEventHandler(Class<?> messagePayloadClass) {
        return Optional.ofNullable(eventHandlers.getOrDefault(messagePayloadClass, null));
    }

    @Override
    public Optional<EventSourcingHandler> findEventSourcingHandler(Class<?> messagePayloadClass) {
        return Optional.ofNullable(eventSourcingHandlers.getOrDefault(messagePayloadClass, null));
    }

    @Override
    public Optional<QueryHandler> findQueryHandler(Class<?> messagePayloadClass) {
        return Optional.ofNullable(queryHandlers.getOrDefault(messagePayloadClass, null));
    }
}
