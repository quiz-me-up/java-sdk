package io.github.quizmeup.sdk.eventflow.spring.starter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

import static io.github.quizmeup.sdk.eventflow.spring.starter.config.JacksonConfig.DATE_TIME_FORMATTER;
import static io.github.quizmeup.sdk.eventflow.spring.starter.config.JacksonConfig.ZONE_OFFSET;


public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public LocalDateTimeDeserializer() {
        this(null);
    }

    public LocalDateTimeDeserializer(Class<Instant> t) {
        super(t);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        final String dateAsString = jsonParser.readValueAs(String.class);
        return LocalDateTime.parse(dateAsString, DATE_TIME_FORMATTER);
    }
}
