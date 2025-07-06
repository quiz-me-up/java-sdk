package io.github.quizmeup.sdk.eventflow.core.domain.exception;

import io.github.quizmeup.sdk.eventflow.core.domain.error.Error;
import lombok.Getter;

import java.time.Instant;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
public class EventFlowException extends RuntimeException implements Error {

    private final Error error;

    public EventFlowException(Error error) {
        super(error.message());
        this.error = error;
    }

    public EventFlowException(Throwable cause) {
        super(cause);
        this.error = null;
    }

    public boolean hasError() {
        return nonNull(error);
    }

    @Override
    public Instant timestamp() {
        return error.timestamp();
    }

    @Override
    public Integer status() {
        return error.status();
    }

    @Override
    public String reasonPhrase() {
        return error.reasonPhrase();
    }

    @Override
    public String message() {
        return error.message();
    }

    @Override
    public Object details() {
        return error.details();
    }
}
