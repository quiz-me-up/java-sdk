package io.github.quizmeup.sdk.eventflow.core.domain.message;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import io.github.quizmeup.sdk.eventflow.core.domain.supplier.AggregateIdSupplier;
import io.github.quizmeup.sdk.eventflow.core.domain.supplier.IdSupplier;
import io.github.quizmeup.sdk.eventflow.core.domain.supplier.TimestampSupplier;
import io.github.quizmeup.sdk.eventflow.core.domain.supplier.TopicSupplier;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageTopic;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;

import static java.util.Objects.isNull;

/**
 * Represents an event message in the event flow system.
 * An event is a notification that something has happened in the system.
 * Events are typically published after a command has been processed and can be handled by multiple handlers.
 *
 * @param id          The unique identifier of the event
 * @param payload     The payload of the event, containing the data about what happened
 * @param timestamp   The time when the event occurred
 * @param aggregateId The identifier of the aggregate that this event is associated with
 */
public record Event(String id,
                    Object payload,
                    Instant timestamp,
                    String aggregateId,
                    MessageTopic topic) implements Message, AggregateIdSupplier, TimestampSupplier {

    /**
     * Compact constructor for the Event record.
     * Validates that the payload, id, and aggregateId are not null or empty.
     *
     * @throws BadArgumentException if payload is null, id is null, or aggregateId is empty
     */
    public Event {
        if (isNull(topic)) throw new BadArgumentException("topic cannot be null");
        if (isNull(payload)) throw new BadArgumentException("payload cannot be null");
        if (StringUtils.isBlank(id)) throw new BadArgumentException("id cannot be null");
        if (StringUtils.isBlank(aggregateId)) throw new BadArgumentException("aggregateId cannot be empty");
    }

    public Event(Object payload) {
        this(IdSupplier.create(), payload, TimestampSupplier.create(), AggregateIdSupplier.getAggregateId(payload), TopicSupplier.create(payload));
    }
}
