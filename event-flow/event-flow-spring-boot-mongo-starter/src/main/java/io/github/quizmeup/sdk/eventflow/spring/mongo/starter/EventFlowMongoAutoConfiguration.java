package io.github.quizmeup.sdk.eventflow.spring.mongo.starter;

import io.github.quizmeup.sdk.eventflow.spring.mongo.starter.config.EventFlowMongoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = EventFlowMongoConfiguration.class)
public class EventFlowMongoAutoConfiguration {
}
