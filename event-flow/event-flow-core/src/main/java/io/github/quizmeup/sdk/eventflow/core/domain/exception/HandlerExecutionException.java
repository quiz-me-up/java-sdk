package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.InternalServerError;

public class HandlerExecutionException extends EventFlowException {

    public HandlerExecutionException(Throwable executionException) {
        super(executionException);
    }

    public HandlerExecutionException(String message) {
        super(InternalServerError.create(message));
    }
}
