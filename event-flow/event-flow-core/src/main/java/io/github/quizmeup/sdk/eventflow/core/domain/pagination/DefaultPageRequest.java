package io.github.quizmeup.sdk.eventflow.core.domain.pagination;

public record DefaultPageRequest(int pageNumber, int pageSize, SortDetails sort) implements PageRequest {

}
