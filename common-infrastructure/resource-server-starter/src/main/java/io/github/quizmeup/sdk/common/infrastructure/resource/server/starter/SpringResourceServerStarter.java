package io.github.quizmeup.sdk.common.infrastructure.resource.server.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringResourceServerStarter.class)
public class SpringResourceServerStarter {
}
