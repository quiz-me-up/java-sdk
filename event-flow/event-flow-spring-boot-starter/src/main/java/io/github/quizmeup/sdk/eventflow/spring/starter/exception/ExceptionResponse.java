package io.github.quizmeup.sdk.eventflow.spring.starter.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.quizmeup.sdk.eventflow.core.domain.error.Error;
import io.github.quizmeup.sdk.eventflow.core.domain.error.InternalServerError;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(Instant timestamp,
                                Integer status,
                                String error,
                                String message,
                                Object details) {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.resolve(InternalServerError.INTERNAL_SERVER_ERROR_STATUS);

    public ExceptionResponse {
        if (Objects.isNull(timestamp)) throw new BadArgumentException("timestamp cannot be null");
        if (Objects.isNull(status)) throw new BadArgumentException("status cannot be null");
        if (StringUtils.isBlank(error)) throw new BadArgumentException("error cannot be blank");
        if (StringUtils.isBlank(message)) throw new BadArgumentException("message cannot be blank");
    }


    public static ExceptionResponse create(Error error) {
        return new ExceptionResponse(
                error.timestamp(),
                error.status(),
                error.reasonPhrase(),
                error.message(),
                error.details()
        );
    }

    public static ExceptionResponse create(HttpStatus httpStatus, String message, Object details) {
        return new ExceptionResponse(
                Instant.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                details
        );
    }

    public static ExceptionResponse create(BaseException baseException) {
        return new ExceptionResponse(
                Instant.now(),
                Optional.ofNullable(baseException).map(ResponseStatusException::getStatusCode).map(HttpStatusCode::value).orElseGet(DEFAULT_STATUS::value),
                Optional.ofNullable(baseException).map(ResponseStatusException::getStatusCode).map(HttpStatusCode::value).map(HttpStatus::resolve).map(HttpStatus::getReasonPhrase).orElseGet(DEFAULT_STATUS::getReasonPhrase),
                Optional.ofNullable(baseException).map(BaseException::getReason).orElse(null),
                null
        );
    }
}
