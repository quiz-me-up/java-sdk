package io.github.quizmeup.sdk.eventflow.core.usecase;

import io.github.quizmeup.sdk.eventflow.annotation.UseCase;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.Handler;

import java.util.Collection;

@UseCase
public interface ScanClass {

    Collection<Handler> scan(Class<?> clazz);
}
