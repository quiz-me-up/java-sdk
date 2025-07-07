package io.github.quizmeup.sdk.eventflow.core.domain.aggregate;

import lombok.extern.slf4j.Slf4j;

/**
 * Static utility for managing the lifecycle of aggregates.
 */
@Slf4j
public class AggregateLifecycle {

    private static final ThreadLocal<Boolean> DELETED_MARKER = new ThreadLocal<>();

    /**
     * Marks the current aggregate as to be deleted.
     * This method should be called within a method annotated with @EventSourcingHandler.
     */
    public static void markDeleted() {
        DELETED_MARKER.set(true);
    }

    /**
     * Checks if the current aggregate has been marked for deletion.
     * @return true if the aggregate is marked for deletion
     */
    public static boolean isMarkedDeleted() {
        return Boolean.TRUE.equals(DELETED_MARKER.get());
    }

    /**
     * Resets the deletion mark state.
     */
    public static void resetDeletedMarker() {
        DELETED_MARKER.remove();
    }
}
