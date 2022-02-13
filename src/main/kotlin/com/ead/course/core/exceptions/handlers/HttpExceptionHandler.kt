package com.ead.course.core.exceptions.handlers

import com.ead.course.core.exceptions.BadRequestHttpException
import com.ead.course.core.exceptions.ConflictHttpException
import com.ead.course.core.exceptions.ForbiddenHttpException
import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.exceptions.ResponseError
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HttpExceptionHandler {

    private val log = logger {}

    @ExceptionHandler(BadRequestHttpException::class)
    fun handleBadRequestHttpException(ex: Exception) = let {
        val response = ResponseError(
            status = BAD_REQUEST,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundHttpException::class)
    fun handleNotFoundHttpException(ex: Exception) = let {
        val response = ResponseError(
            status = NOT_FOUND,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, NOT_FOUND)
    }

    @ExceptionHandler(ConflictHttpException::class)
    fun handleConflictHttpException(ex: Exception) = let {
        val response = ResponseError(
            status = CONFLICT,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, CONFLICT)
    }

    @ExceptionHandler(ForbiddenHttpException::class)
    fun handleForbiddenHttpException(ex: Exception) = let {
        val response = ResponseError(
            status = FORBIDDEN,
            message = ex.message
        )

        log.error { "Error: $response" }

        ResponseEntity(response, FORBIDDEN)
    }

}
