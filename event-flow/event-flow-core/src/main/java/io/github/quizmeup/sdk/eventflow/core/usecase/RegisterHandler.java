package io.github.quizmeup.sdk.eventflow.core.usecase;

import io.github.quizmeup.sdk.eventflow.annotation.UseCase;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.Handler;

/**
 * {@code RegisterHandler} defines the use case for registering a handler.
 * <p>
 * It allows for registering different types of handlers within the system.
 */
@UseCase
public interface RegisterHandler {

    /**
     * Registers a handler.
     *
     * @param handler The {@link Handler} to register.
     * @param <HANDLER> The type of the handler.
     */
    <HANDLER extends Handler> void register(HANDLER handler);
}
