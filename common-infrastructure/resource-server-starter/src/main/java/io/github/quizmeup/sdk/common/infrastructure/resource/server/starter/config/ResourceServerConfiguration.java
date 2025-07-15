package io.github.quizmeup.sdk.common.infrastructure.resource.server.starter.config;

import io.github.quizmeup.sdk.common.infrastructure.exception.starter.handler.ForbiddenExceptionHandler;
import io.github.quizmeup.sdk.common.infrastructure.exception.starter.handler.UnauthorizedExceptionHandler;
import io.github.quizmeup.sdk.common.infrastructure.properties.starter.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Configuration
@EnableWebSecurity
public class ResourceServerConfiguration {

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(final ClientRegistrationRepository clientRegistrationRepository,
                                                                 final OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
    }

    @Bean
    @Order
    public SecurityFilterChain securityFilterChain(final HttpSecurity http,
                                                   final SecurityProperties securityProperties,
                                                   final ForbiddenExceptionHandler forbiddenExceptionHandler,
                                                   final UnauthorizedExceptionHandler unauthorizedExceptionHandler,
                                                   @Qualifier("defaultCorsConfigurationSource") final CorsConfigurationSource corsConfigurationSource) throws Exception {
        final boolean isSecurityEnabled = Optional.ofNullable(securityProperties)
                .map(SecurityProperties::getEnabled)
                .orElse(true);

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            if (isSecurityEnabled) {
                log.debug("Security is enabled, configuring request matchers.");

                final Collection<String> unprotectedPaths = Optional.ofNullable(securityProperties)
                        .map(SecurityProperties::getUnprotectedPath)
                        .orElse(Collections.emptyList());

                if (CollectionUtils.isNotEmpty(unprotectedPaths)) {
                    log.debug("Unprotected paths configured: {}", unprotectedPaths);
                    unprotectedPaths.forEach(path -> authorizationManagerRequestMatcherRegistry.requestMatchers(path).permitAll());
                }

                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
            } else {
                log.debug("Security is disabled, all requests will be permitted.");
                authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
            }
        });

        // Configuration de la gestion des exceptions
        http.exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer
                        .authenticationEntryPoint(unauthorizedExceptionHandler)
                        .accessDeniedHandler(forbiddenExceptionHandler)
        );

        // Configuration CORS
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource));

        // Désactivation CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Configuration de la gestion des sessions
        http.sessionManagement(securitySessionManagementConfigurer -> securitySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configuration des en-têtes
        http.headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // Configuration OAuth2 Client
        http.oauth2Client(Customizer.withDefaults());

        // Configuration OAuth2 Resource Server avec JWT
        http.oauth2ResourceServer(oAuth2ResourceServerConfigurer -> oAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
