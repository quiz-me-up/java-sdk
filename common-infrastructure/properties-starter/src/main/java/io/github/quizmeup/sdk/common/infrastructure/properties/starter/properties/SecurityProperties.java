package io.github.quizmeup.sdk.common.infrastructure.properties.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;

@Getter
@Setter
@ConfigurationProperties(prefix = "quizmeup.security")
public class SecurityProperties {
    private Boolean enabled;
    private Collection<String> unprotectedPath;
}
