package com.ead.course.core.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND

sealed class ClientErrorHttpException(
    status: HttpStatus,
    payload: String? = status.reasonPhrase
) : RuntimeException(payload) {
    var status: HttpStatus = status
        private set

    init {
        if (status.value() < 400 || status.value() > 499) throw IllegalArgumentException()
    }
}

sealed class ServerErrorHttpException(
    private val status: HttpStatus,
    payload: String? = status.reasonPhrase
) : RuntimeException(payload) {
    init {
        if (status.value() < 500 || status.value() > 599) throw IllegalArgumentException()
    }
}

class BadRequestHttpException(payload: String? = null) : ClientErrorHttpException(BAD_REQUEST, payload)
class NotFoundHttpException(payload: String? = null) : ClientErrorHttpException(NOT_FOUND, payload)
class ConflictHttpException(payload: String? = null) : ClientErrorHttpException(CONFLICT, payload)