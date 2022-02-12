package com.ead.course.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest


class ResponsePageDTO<T> @JsonCreator(mode = PROPERTIES) constructor(
    @JsonProperty("content") content: List<T?>,
    @JsonProperty("number") number: Int,
    @JsonProperty("size") size: Int,
    @JsonProperty("totalElements") totalElements: Long,
    @JsonProperty("pageable") pageable: JsonNode,
    @JsonProperty("last") last: Boolean,
    @JsonProperty("totalPages") totalPages: Int,
    @JsonProperty("sort") sort: JsonNode,
    @JsonProperty("first") first: Boolean,
    @JsonProperty("empty") empty: Boolean
) : PageImpl<T>(
    content,
    PageRequest.of(number, size), totalElements
)