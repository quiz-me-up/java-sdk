package io.github.quizmeup.sdk.eventflow.core.usecase;

import io.github.quizmeup.sdk.eventflow.annotation.UseCase;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Command;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;

import java.util.concurrent.CompletableFuture;


@UseCase
public interface SendEvent {

    CompletableFuture<Void> send(Event event);

    default CompletableFuture<Void> send(Object payload) {
        return send(new Event(payload));
    }
}
