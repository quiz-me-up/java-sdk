package io.github.quizmeup.sdk.common.infrastructure.webflux.swagger.starter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
@OpenAPIDefinition(info = @Info(title = "${spring.application.name}"),
        security = {@SecurityRequirement(name = "OAuth2TokenBearer")})
@SecurityScheme(
        name = "OAuth2TokenBearer",
        type = SecuritySchemeType.OAUTH2,
        in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "${springdoc.oauth-flow.authorization-url}",
                        tokenUrl = "${springdoc.oauth-flow.token-url}",
                        scopes = {
                                @OAuthScope(
                                        name = "openid",
                                        description = "openid scope"
                                ),
                                @OAuthScope(
                                        name = "profile",
                                        description = "profile scope"
                                )
                        }
                )
        )
)
public class SpringSwaggerStarter {
}
