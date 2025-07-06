package io.github.quizmeup.sdk.eventflow.core.service.dispatcher;

import io.github.quizmeup.sdk.eventflow.annotation.DomainService;
import io.github.quizmeup.sdk.eventflow.core.domain.error.Error;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageDispatcher;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageSubscriber;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.QueryHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Query;
import io.github.quizmeup.sdk.eventflow.core.port.ErrorConverter;
import io.github.quizmeup.sdk.eventflow.core.port.HandlerRegistry;
import io.github.quizmeup.sdk.eventflow.core.port.MessageBus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
public class QueryDispatcher implements MessageDispatcher<Query, Object> {

    private final MessageBus messageBus;
    private final ErrorConverter errorConverter;
    private final HandlerRegistry handlerRegistry;

    public QueryDispatcher(MessageBus messageBus,
                           ErrorConverter errorConverter,
                           HandlerRegistry handlerRegistry) {
        this.errorConverter = errorConverter;
        this.messageBus = messageBus;
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    public Query convert(Message message) {
        return convert(message, Query.class);
    }

    @Override
    public Error convert(Throwable throwable) {
        return errorConverter.convert(throwable);
    }

    @Override
    public Object dispatch(Query message) {
        final QueryHandler queryHandler = handlerRegistry.getQueryHandler(message.payloadClass());
        return queryHandler.handle(message);
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
