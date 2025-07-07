package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.ConflictError;

public class ConflictException extends EventFlowException {

    public ConflictException(String message) {
        super(ConflictError.create(message));
    }
}
