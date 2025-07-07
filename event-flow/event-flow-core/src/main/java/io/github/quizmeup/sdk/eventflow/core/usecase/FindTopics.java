package io.github.quizmeup.sdk.eventflow.core.usecase;

import io.github.quizmeup.sdk.eventflow.annotation.UseCase;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.Topic;

import java.util.Collection;

/**
 * {@code FindTopics} defines the use case for retrieving all available topics.
 */
@UseCase
public interface FindTopics {

    /**
     * Retrieves all available topics.
     *
     * @return A collection of {@link Topic} objects.
     */
    Collection<Topic> findAll();
}
