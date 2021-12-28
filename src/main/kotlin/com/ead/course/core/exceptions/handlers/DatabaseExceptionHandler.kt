package com.ead.course.core.exceptions.handlers

import com.ead.course.core.exceptions.ResponseError
import mu.KotlinLogging.logger
import org.hibernate.QueryException
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DatabaseExceptionHandler {

    private val log = logger {}

    @ExceptionHandler(QueryException::class)
    fun handleQueryException(ex: QueryException) = let {
        val response = ResponseError(
            status = UNPROCESSABLE_ENTITY,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, UNPROCESSABLE_ENTITY)
    }

}
