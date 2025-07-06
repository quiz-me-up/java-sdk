package io.github.quizmeup.sdk.eventflow.core.domain.handler;

import io.github.quizmeup.sdk.eventflow.core.domain.message.Query;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.HandlerExecutionException;

import java.lang.reflect.Method;

public interface QueryHandler extends Handler {

    Object handle(Query query) throws HandlerExecutionException;
}
