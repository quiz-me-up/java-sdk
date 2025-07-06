package io.github.quizmeup.sdk.eventflow.core.service.gateway;

import io.github.quizmeup.sdk.eventflow.annotation.DomainService;
import io.github.quizmeup.sdk.eventflow.core.usecase.SendCommand;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageGateway;
import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageSubscriber;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Command;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;
import io.github.quizmeup.sdk.eventflow.core.domain.message.MessageResult;
import io.github.quizmeup.sdk.eventflow.core.port.MessageBus;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;


@Slf4j
@DomainService
public class CommandGateway implements MessageGateway<Command>, SendCommand {

    private final MessageBus messageBus;

    public CommandGateway(final MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public CompletableFuture<String> send(Command command) {
        return sendMessage(command)
                .thenApplyAsync(MessageResult::payload)
                .thenApplyAsync(Object::toString);
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
