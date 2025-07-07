package io.github.quizmeup.sdk.common.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.ConflictException;

public class AggregateAlreadyExistException extends ConflictException {
    public AggregateAlreadyExistException(Class<?> clazz, String id) {
        this(clazz.getSimpleName(), id);
    }

    public AggregateAlreadyExistException(String aggregateSimpleClassName, String id) {
        super(String.format("%s already exist with id ( %s )", aggregateSimpleClassName, id));
    }
}
