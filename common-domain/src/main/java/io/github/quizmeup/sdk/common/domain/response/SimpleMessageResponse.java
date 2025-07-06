package io.github.quizmeup.sdk.common.domain.response;

import io.github.quizmeup.sdk.eventflow.core.domain.exception.BadArgumentException;
import org.apache.commons.lang3.StringUtils;

public record SimpleMessageResponse(String message) implements MessageResponse {
    public SimpleMessageResponse {
        if (StringUtils.isBlank(message)) throw new BadArgumentException("Message cannot be blank");
    }
}
