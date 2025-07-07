package io.github.quizmeup.sdk.common.domain.response;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.isNull;

public record AggregateRelatedMessageResponse(String id,
                                              String name,
                                              ActionType action) implements AggregateActionMessageResponse {

    public AggregateRelatedMessageResponse {
        if (isNull(action)) throw new BadArgumentException("action cannot be null");
        if (StringUtils.isBlank(id)) throw new BadArgumentException("id cannot be blank");
        if (StringUtils.isBlank(name)) throw new BadArgumentException("name cannot be blank");
    }

    public AggregateRelatedMessageResponse(String id, Class<?> clazz, ActionType action) {
        this(id, clazz.getSimpleName(), action);
    }

    @Override
    public String message() {
        final String baseMessage = String.format("%s with id %s has been successfully ", name, id);
        return switch (action()) {
            case CREATE -> baseMessage + "created.";
            case UPDATE -> baseMessage + "updated.";
            case DELETE -> baseMessage + "deleted.";
        };
    }
}
