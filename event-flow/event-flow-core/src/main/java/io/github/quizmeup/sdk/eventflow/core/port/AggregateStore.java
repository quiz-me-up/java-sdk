package io.github.quizmeup.sdk.eventflow.core.port;

import io.github.quizmeup.sdk.eventflow.core.domain.aggregate.Aggregate;

import java.util.Optional;

/**
 * Service Provider Interface for aggregate storage.
 * The aggregate store is responsible for persisting and retrieving aggregate snapshots.
 * It provides methods to save aggregates, delete aggregates, and find the latest version of an aggregate.
 */
public interface AggregateStore {

    /**
     * Saves an aggregate to the store.
     *
     * @param aggregate The aggregate to save
     */
    void save(Aggregate aggregate);

    /**
     * Deletes all aggregates with a specific aggregate ID.
     *
     * @param aggregateId The ID of the aggregates to delete
     */
    void deleteAllByAggregateId(String aggregateId);

    /**
     * Finds the latest version of an aggregate with a specific ID.
     *
     * @param aggregateId The ID of the aggregate to find
     * @return An Optional containing the latest version of the aggregate if found, or empty if not found
     */
    Optional<Aggregate> findTopByAggregateIdOrderByVersionDesc(String aggregateId);
}
