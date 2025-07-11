package io.github.quizmeup.sdk.common.infrastructure.mongo.starter.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class AuditConfiguration implements AuditorAware<String> {
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Optional<String> optionalUserName = Optional.of(securityContext).map(SecurityContext::getAuthentication).map(Principal::getName);
        return optionalUserName.or(() -> Optional.of("server"));
    }
}
