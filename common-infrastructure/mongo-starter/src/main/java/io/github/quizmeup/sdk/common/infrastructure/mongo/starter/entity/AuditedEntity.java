package io.github.quizmeup.sdk.common.infrastructure.mongo.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.Instant;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditedEntity implements Serializable , Persistable<String> {

    @CreatedDate
    private Instant entityCreatedAt;

    @LastModifiedDate
    private Instant entityLastModified;

    @Override
    public boolean isNew() {
        return isNull(entityCreatedAt);
    }
}
