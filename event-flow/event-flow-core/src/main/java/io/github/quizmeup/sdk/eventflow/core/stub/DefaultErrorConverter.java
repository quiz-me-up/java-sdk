package io.github.quizmeup.sdk.eventflow.core.stub;

import io.github.quizmeup.sdk.eventflow.annotation.Stub;
import io.github.quizmeup.sdk.eventflow.core.domain.error.Error;
import io.github.quizmeup.sdk.eventflow.core.port.ErrorConverter;

import java.util.Optional;

@Stub
public class DefaultErrorConverter implements ErrorConverter {

    @Override
    public Optional<Error> tryConvert(Throwable throwable) {
        return Optional.empty();
    }
}
