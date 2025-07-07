package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.ResourceNotFoundError;

public class ResourceNotFoundException extends EventFlowException {

    public ResourceNotFoundException(ResourceNotFoundError notFoundError) {
        super(notFoundError);
    }

    public ResourceNotFoundException(String message) {
        super(ResourceNotFoundError.create(message));
    }
}
