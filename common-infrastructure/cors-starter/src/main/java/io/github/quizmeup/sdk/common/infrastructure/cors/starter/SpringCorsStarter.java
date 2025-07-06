package io.github.quizmeup.sdk.common.infrastructure.cors.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringCorsStarter.class)
public class SpringCorsStarter {
}
