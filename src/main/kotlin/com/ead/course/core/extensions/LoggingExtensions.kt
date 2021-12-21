package com.ead.course.core.extensions

import com.ead.course.core.entities.LoggerObjectIn
import com.ead.course.core.entities.LoggerObjectOut
import com.ead.course.core.entities.LoggerSimpleObjectIn
import com.ead.course.core.entities.LoggerSimpleObjectOut
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import mu.KLogger
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.SECONDS
import java.util.UUID
import kotlin.reflect.KFunction

var time: LocalDateTime = LocalDateTime.now()

val mapper = ObjectMapper().also {
    it.configure(WRITE_DATES_AS_TIMESTAMPS, false)
    it.findAndRegisterModules()
}

inline fun <reified R : Any, T : Any> KLogger.makeLogged(
    function: KFunction<T>,
    requestId: UUID? = null,
    requestBody: T? = null,
    message: String? = "",
    vararg parameters: T?,
    crossinline executable: () -> ResponseEntity<R>
): ResponseEntity<R> {
    this.start(function, requestId, requestBody, message, parameters)
    return executable().also { this.end(function, it.body) }
}

fun <T : Any> KLogger.start(
    function: KFunction<T>,
    requestId: UUID? = null,
    body: T? = null,
    message: String? = "",
    vararg parameters: T?
) = let {

    val method = function.annotations.lookForAnyHttpMethodAnnotation()

    if (method != null)
        this.startHttpLayer(function, body, method, requestId?.toString() ?: "", message, parameters)
    else
        this.startAnotherLayer(function, body, message, parameters)

}

private fun <T : Any> KLogger.startHttpLayer(
    function: KFunction<T>,
    body: T? = null,
    method: HttpMethod,
    requestId: String,
    message: String? = "",
    vararg parameters: T?,
) {
    time = LocalDateTime.now()

    this.info {
        mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(
                LoggerObjectIn(
                    method = method,
                    functionName = "${function.name}()",
                    requestId = requestId,
                    body = body,
                    message = message,
                    parameters = parameters.toSet()
                )
            )
    }
}

private fun <T : Any> KLogger.startAnotherLayer(
    function: KFunction<T>,
    body: T? = null,
    message: String? = "",
    vararg parameters: T?,
) = this.info {
    mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(
            LoggerSimpleObjectIn(
                functionName = "${function.name}()",
                body = body,
                message = message,
                parameters = parameters.toSet()
            )
        )
}


fun <T : Any> KLogger.end(
    function: KFunction<T>,
    body: T? = null,
) = let {

    val method = function.annotations.lookForAnyHttpMethodAnnotation()

    if (method != null)
        this.endHttpLayer(function, body)
    else
        this.endAnotherLayer(function, body)

}

private fun <T : Any> KLogger.endHttpLayer(
    function: KFunction<T>,
    body: T? = null,
) = this.info {
    mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(
            LoggerObjectOut(
                functionName = "${function.name}()",
                body = body,
                totalRequisitionTime = "${SECONDS.between(time, LocalDateTime.now())} SECONDS"
            )
        )
}

private fun <T : Any> KLogger.endAnotherLayer(
    function: KFunction<T>,
    body: T? = null,
) = this.info {
    mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(
            LoggerSimpleObjectOut(
                functionName = "${function.name}()",
                body = body,
            )
        )
}

inline fun KLogger.error(
    throwable: Throwable,
    message: () -> String = { "Something went wrong" },
) = this.error(message(), throwable)
