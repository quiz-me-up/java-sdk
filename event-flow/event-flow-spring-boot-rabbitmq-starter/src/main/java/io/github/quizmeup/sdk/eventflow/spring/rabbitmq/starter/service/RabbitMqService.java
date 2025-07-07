package io.github.quizmeup.sdk.eventflow.spring.rabbitmq.starter.service;

import io.github.quizmeup.sdk.eventflow.core.domain.flux.MessageSubscriber;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.Topic;

public interface RabbitMqService {

    void createOrModifyQueue(Topic topic);

    void createConsumer(MessageSubscriber messageSubscriber);
}
