package io.github.quizmeup.sdk.eventflow.spring.mongo.starter.mapper;

import io.github.quizmeup.sdk.eventflow.core.domain.aggregate.Aggregate;
import io.github.quizmeup.sdk.eventflow.spring.mongo.starter.entity.MongoAggregateEntity;

public class AggregateMapper {

    public static MongoAggregateEntity toEntity(Aggregate aggregate) {
        final MongoAggregateEntity aggregateEntity = new MongoAggregateEntity();
        aggregateEntity.setVersion(aggregate.version());
        aggregateEntity.setPayload(aggregate.payload());
        aggregateEntity.setAggregateId(aggregate.aggregateId());
        return aggregateEntity;
    }

    public static Aggregate toDomain(MongoAggregateEntity aggregateEntity) {
        return new Aggregate(aggregateEntity.getVersion(), aggregateEntity.getPayload(), aggregateEntity.getAggregateId());
    }
}
