package com.ead.course.core.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.HttpMethod
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@JsonPropertyOrder("logType")
@JsonInclude(NON_EMPTY, content = NON_NULL)
data class LoggerSimpleObjectIn(

    val functionName: String,

    val body: Any? = null,

    val parameters: Set<Any?> = emptySet(),

    @get:JsonProperty(value = "additionalMessage")
    val message: String? = ""

) {
    val logType: String = "TASK - INIT"
}

@JsonPropertyOrder("logType")
@JsonInclude(NON_NULL)
data class LoggerSimpleObjectOut(

    val functionName: String,

    val body: Any? = null,
) {
    val logType: String = "TASK - FINISH"
}


@JsonPropertyOrder("logType", "method", "functionName", "X-Request-Id", "startDate")
@JsonInclude(NON_EMPTY, content = NON_NULL)
data class LoggerObjectIn(

    val method: HttpMethod,

    val functionName: String,

    @get:JsonProperty(value = "X-Request-Id")
    val requestId: String? = null,

    @get:JsonProperty(value = "requestBody")
    val body: Any? = null,

    val parameters: Set<Any?> = emptySet(),

    @get:JsonProperty(value = "additionalMessage")
    val message: String? = ""

) {
    val logType: String = "RESOURCE - START"

    val startDate: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
}

@JsonPropertyOrder("logType", "functionName", "endDate")
@JsonInclude(NON_NULL)
data class LoggerObjectOut(

    val functionName: String,

    @get:JsonProperty(value = "responseBody")
    val body: Any? = null,

    val totalRequisitionTime: String

) {
    val logType: String = "RESOURCE - END"

    val endDate: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
}

@JsonPropertyOrder("logType")
@JsonInclude(NON_EMPTY, content = NON_NULL)
data class LoggerSimpleObjectInfo(

    val functionName: String,

    @get:JsonProperty(value = "additionalMessage")
    val message: String? = "",

    val body: Any? = null,

) {
    val logType: String = "TASK - INFO"
}