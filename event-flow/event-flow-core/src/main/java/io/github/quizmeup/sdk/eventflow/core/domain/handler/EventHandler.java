package io.github.quizmeup.sdk.eventflow.core.domain.handler;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.HandlerExecutionException;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;

public interface EventHandler extends Handler {

    void onEvent(Event event) throws HandlerExecutionException;
}
