package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.InternalServerError;

public class NotImplementedException extends EventFlowException {

    public NotImplementedException(String message) {
        super(InternalServerError.create(message));
    }
}
