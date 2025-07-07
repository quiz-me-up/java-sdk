package io.github.quizmeup.sdk.eventflow.core.domain.handler;

import io.github.quizmeup.sdk.eventflow.core.domain.aggregate.Aggregate;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.HandlerExecutionException;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;

public interface EventSourcingHandler extends Handler {

    Aggregate apply(Event event, Aggregate aggregate) throws HandlerExecutionException;
}
