package io.github.quizmeup.sdk.common.domain.response;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import io.github.quizmeup.sdk.eventflow.core.domain.exception.NotImplementedException;
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

    public AggregateRelatedMessageResponse(String id, Class<?> clazz, ActionType action){
        this(id, clazz.getSimpleName(), action);
    }

    @Override
    public String message() {
        switch (action) {
            case CREATE -> {
                return String.format("%s with id %s has been successfully created.", name, id);
            }
            case UPDATE -> {
                return String.format("%s with id %s has been successfully updated.", name, id);
            }
            case DELETE -> {
                return String.format("%s with id %s has been successfully deleted.", name, id);
            }
            default -> throw new NotImplementedException("action not implemented");
        }
    }
}
