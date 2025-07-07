package io.github.quizmeup.sdk.eventflow.core.service;

import io.github.quizmeup.sdk.eventflow.annotation.DomainService;
import io.github.quizmeup.sdk.eventflow.core.usecase.FindTopics;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageResultTopic;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageTopic;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.Topic;
import io.github.quizmeup.sdk.eventflow.core.port.TopicRegistry;

import java.util.Collection;

@DomainService
public class TopicService implements FindTopics {

    private final TopicRegistry topicRegistry;

    public TopicService(final TopicRegistry topicRegistry) {
        this.topicRegistry = topicRegistry;
    }

    @Override
    public Collection<Topic> findAll() {
        return topicRegistry.findAll();
    }

    public void save(final MessageTopic messageTopic) {
        topicRegistry.register(messageTopic);
    }

    public void save(final MessageResultTopic messageResultTopic){
        topicRegistry.register(messageResultTopic);
    }
}
