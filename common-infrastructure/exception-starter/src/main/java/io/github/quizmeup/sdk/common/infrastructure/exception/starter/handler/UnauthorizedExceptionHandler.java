package io.github.quizmeup.sdk.common.infrastructure.exception.starter.handler;

import io.github.quizmeup.sdk.common.infrastructure.exception.starter.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class UnauthorizedExceptionHandler implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public UnauthorizedExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.resolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        resolver.resolveException(request, response, null,  new UnauthorizedException());
    }
}
