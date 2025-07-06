package io.github.quizmeup.sdk.common.infrastructure.properties.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

@Getter
@Setter
@Primary
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private Boolean enabled;
    private Collection<String> unprotectedPath;
}
