package io.github.quizmeup.sdk.common.infrastructure.exception.starter.handler;

import io.github.quizmeup.sdk.common.infrastructure.exception.starter.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class ForbiddenExceptionHandler implements AccessDeniedHandler {
    private final HandlerExceptionResolver resolver;

    public ForbiddenExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.resolver = handlerExceptionResolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
        resolver.resolveException(request, response, null,  new ForbiddenException());
    }
}
