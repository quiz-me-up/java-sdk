package io.github.quizmeup.sdk.common.infrastructure.rabbitmq.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringRabbitMqStarter.class)
public class SpringRabbitMqStarter {
}
