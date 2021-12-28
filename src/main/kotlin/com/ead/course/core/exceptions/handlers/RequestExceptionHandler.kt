package com.ead.course.core.exceptions.handlers

import com.ead.course.core.exceptions.ResponseError
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.regex.Pattern

@ControllerAdvice
class RequestExceptionHandler {

    private val log = logger {}

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException) = let {
        val response = ResponseError(
            status = BAD_REQUEST,
            message = "Missing parameter ${ex.parameterName} in URI"
        )

        log.error { "Error: $response" }

        ResponseEntity(response, BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException) = let {
        val response = ResponseError(
            status = BAD_REQUEST,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException) = let {
        val anyDateInString = "\\d\\d\\d\\d-\\d\\d-\\d\\d"

        val hasDate = Pattern.compile(anyDateInString).matcher(ex.mostSpecificCause.localizedMessage).find()
        var shortMessage = ex.mostSpecificCause.localizedMessage.split(":")[0]

        if (hasDate) {
            shortMessage = ex.mostSpecificCause.localizedMessage
        }

        val error = ResponseError(
            status = BAD_REQUEST,
            message = shortMessage
        )

        log.error { "Error: $error" }

        ResponseEntity(error, BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleConstraintViolationException(ex: MethodArgumentNotValidException) = let {
        val violations = HashSet<ResponseError>()

        ex.bindingResult.fieldErrors.forEach {
            val response = ResponseError(
                status = BAD_REQUEST,
                message = "${it.field} ${it.defaultMessage}"
            )

            violations.add(response)
        }

        ResponseEntity(violations, BAD_REQUEST)
    }
}
