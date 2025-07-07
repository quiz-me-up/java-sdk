package io.github.quizmeup.sdk.common.infrastructure.exception.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringExceptionStarter.class)
public class SpringExceptionStarter {
}
