package io.github.quizmeup.sdk.common.infrastructure.password.encoder.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = SpringPasswordEncoderStarter.class)
public class SpringPasswordEncoderStarter {
}
