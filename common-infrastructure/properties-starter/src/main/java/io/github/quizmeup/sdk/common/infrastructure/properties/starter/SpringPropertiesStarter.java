package io.github.quizmeup.sdk.common.infrastructure.properties.starter;

import io.github.quizmeup.sdk.common.infrastructure.properties.starter.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringPropertiesStarter.class)
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SpringPropertiesStarter {
}
