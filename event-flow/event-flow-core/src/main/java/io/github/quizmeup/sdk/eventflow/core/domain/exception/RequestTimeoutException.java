package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.RequestTimeoutError;

public class RequestTimeoutException extends EventFlowException {

    public RequestTimeoutException(String message) {
        super(RequestTimeoutError.create(message));
    }
}
