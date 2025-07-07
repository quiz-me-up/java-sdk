package io.github.quizmeup.sdk.eventflow.core.domain.flux;

import io.github.quizmeup.sdk.eventflow.core.domain.message.Message;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.Topic;


public interface MessageSubscriber extends Subscriber<Message> {

    Topic topic();

    String handlerName();

    default void onError(Throwable throwable) {

    }

    default void onComplete() {

    }
}
