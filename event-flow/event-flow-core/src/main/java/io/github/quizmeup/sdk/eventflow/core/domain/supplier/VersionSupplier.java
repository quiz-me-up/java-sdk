package io.github.quizmeup.sdk.eventflow.core.domain.supplier;

@FunctionalInterface
public interface VersionSupplier {

    Long version();

    static Long create() {
        return 0L;
    }
}
