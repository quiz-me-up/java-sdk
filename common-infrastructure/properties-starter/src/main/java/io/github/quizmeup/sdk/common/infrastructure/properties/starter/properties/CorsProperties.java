package io.github.quizmeup.sdk.common.infrastructure.properties.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "quizmeup.cors")
public class CorsProperties {
    private List<String> allowedOrigin;
}
