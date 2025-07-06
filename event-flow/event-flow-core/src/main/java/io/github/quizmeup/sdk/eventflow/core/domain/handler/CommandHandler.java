package io.github.quizmeup.sdk.eventflow.core.domain.handler;

import io.github.quizmeup.sdk.eventflow.core.domain.aggregate.Aggregate;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.HandlerExecutionException;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Command;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;

import java.util.List;

public interface CommandHandler extends Handler {

    List<Event> handle(Command command, Aggregate aggregate) throws HandlerExecutionException;
}
