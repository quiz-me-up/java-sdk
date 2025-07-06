package io.github.quizmeup.sdk.common.infrastructure.mongo.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@AutoConfiguration
@EnableMongoAuditing
@ComponentScan(basePackageClasses = SpringMongoStarter.class)
public class SpringMongoStarter {
}
