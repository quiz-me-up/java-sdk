package io.github.quizmeup.sdk.common.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.ResourceNotFoundException;

public class AggregateNotFoundException extends ResourceNotFoundException {

    public AggregateNotFoundException(Class<?> clazz, String id) {
        this(clazz.getSimpleName(), id);
    }

    public AggregateNotFoundException(String aggregateSimpleClassName, String id) {
        super(String.format("%s with id ( %s ) not found", aggregateSimpleClassName, id));
    }
}
