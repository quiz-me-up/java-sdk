package io.github.quizmeup.sdk.eventflow.core.service.dispatcher;

import io.github.quizmeup.sdk.eventflow.annotation.DomainService;
import io.github.quizmeup.sdk.eventflow.core.domain.error.Error;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageDispatcher;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageSubscriber;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.EventHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;
import io.github.quizmeup.sdk.eventflow.core.port.ErrorConverter;
import io.github.quizmeup.sdk.eventflow.core.port.HandlerRegistry;
import io.github.quizmeup.sdk.eventflow.core.port.MessageBus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
public class EventDispatcher implements MessageDispatcher<Event, Void> {

    private final MessageBus messageBus;
    private final ErrorConverter errorConverter;
    private final HandlerRegistry handlerRegistry;

    public EventDispatcher(MessageBus messageBus, ErrorConverter errorConverter, HandlerRegistry handlerRegistry) {
        this.errorConverter = errorConverter;
        this.messageBus = messageBus;
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    public Event convert(Message message) {
        return Message.convert(message, Event.class);
    }

    @Override
    public Error convert(Throwable throwable) {
        return errorConverter.convert(throwable);
    }

    @Override
    public Void dispatch(Event message) {
        final EventHandler eventHandler = handlerRegistry.getEventHandler(message.payloadClass());
        eventHandler.onEvent(message);
        return null;
    }

    @Override
    public void subscribe(MessageSubscriber subscriber) {
        messageBus.subscribe(subscriber);
    }

    @Override
    public void publish(Message message) {
        messageBus.publish(message);
    }
}
