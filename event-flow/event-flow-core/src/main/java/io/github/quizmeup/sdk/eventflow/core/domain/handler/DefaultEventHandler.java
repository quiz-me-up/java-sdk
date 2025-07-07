package io.github.quizmeup.sdk.eventflow.core.domain.handler;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.HandlerExecutionException;
import io.github.quizmeup.sdk.eventflow.core.domain.message.Event;

import java.lang.reflect.Method;

public record DefaultEventHandler(Class<?> payloadClass, Object instance, Method method) implements EventHandler {

    @Override
    public void onEvent(Event event) throws HandlerExecutionException {
        invoke(instance, method, event.payload());
    }
}
