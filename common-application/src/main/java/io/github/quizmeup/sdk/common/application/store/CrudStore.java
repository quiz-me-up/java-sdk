package io.github.quizmeup.sdk.common.application.store;


import io.github.quizmeup.sdk.eventflow.core.domain.exception.ResourceNotFoundException;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.Page;
import io.github.quizmeup.sdk.eventflow.core.domain.pagination.PageRequest;

import java.util.Optional;

public interface CrudStore<ENTITY, ID> {
    String resourceName();

    void save(ENTITY entity);

    void deleteById(ID identifier);

    boolean existsById(ID identifier);

    Optional<ENTITY> findById(ID identifier);

    Page<ENTITY> findAll(PageRequest pageRequest);

    default ENTITY getById(ID identifier) {
        return findById(identifier).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource %s not found for id : %s", resourceName(), identifier)));
    }
}
