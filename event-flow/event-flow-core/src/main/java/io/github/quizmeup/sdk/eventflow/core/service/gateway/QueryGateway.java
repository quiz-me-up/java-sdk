package io.github.quizmeup.sdk.eventflow.core.service.gateway;

import io.github.quizmeup.sdk.eventflow.annotation.DomainService;
import io.github.quizmeup.sdk.eventflow.core.usecase.SendQuery;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageGateway;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageSubscriber;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;
import io.github.quizmeup.sdk.eventflow.core.domain.message.MessageResult;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Query;
import io.github.quizmeup.sdk.eventflow.core.domain.response.ResponseType;
import io.github.quizmeup.sdk.eventflow.core.port.MessageBus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * {@code QueryGateway} is a domain service that acts as a gateway for sending queries to the system.
 * It implements the {@link SendQuery} interface and uses a {@link MessageBus} for sending and receiving query messages.
 * It provides a method to asynchronously send a query and get its result via a {@link CompletableFuture}.
 */
@Slf4j
@DomainService
public class QueryGateway implements MessageGateway<Query>, SendQuery {

    private final MessageBus messageBus;

    /**
     * Constructs a {@code QueryGateway} with the necessary {@link MessageBus} dependency.
     *
     * @param messageBus The {@link MessageBus} used for sending and receiving query messages.
     */
    public QueryGateway(final MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public <QUERY_RESPONSE> CompletableFuture<QUERY_RESPONSE> send(Query query, ResponseType<QUERY_RESPONSE> responseType) {
        return sendMessage(query)
                .thenApplyAsync(MessageResult::payload)
                .thenApplyAsync(responseType::convert);
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
