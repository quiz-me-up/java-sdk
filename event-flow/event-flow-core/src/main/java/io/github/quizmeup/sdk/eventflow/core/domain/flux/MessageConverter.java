package io.github.quizmeup.sdk.eventflow.core.domain.flux;

import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;

@FunctionalInterface
public interface MessageConverter<MESSAGE extends Message> {

    MESSAGE convert(Message message);

    default MESSAGE convert(Message message, Class<MESSAGE> messageClass){
        return MESSAGE.convert(message, messageClass);
    }
}
