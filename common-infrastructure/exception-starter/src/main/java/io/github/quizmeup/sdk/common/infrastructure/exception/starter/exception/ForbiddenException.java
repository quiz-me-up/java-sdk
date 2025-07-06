package io.github.quizmeup.sdk.common.infrastructure.exception.starter.exception;

import io.github.quizmeup.sdk.eventflow.spring.starter.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "");
    }
}
