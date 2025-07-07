package io.github.quizmeup.sdk.eventflow.core.domain.error;

import java.time.Instant;

public record BadRequestError(Instant timestamp, String message, Object details) implements Error {

    public static Integer BAD_REQUEST_ERROR_STATUS = 400;
    public static String BAD_REQUEST_ERROR_REASON_PHRASE = "Bad Request";

    @Override
    public Integer status() {
        return BAD_REQUEST_ERROR_STATUS;
    }

    @Override
    public String reasonPhrase() {
        return BAD_REQUEST_ERROR_REASON_PHRASE;
    }

    public static BadRequestError create(String message) {
        return new BadRequestError(Instant.now(), message, null);
    }
}
