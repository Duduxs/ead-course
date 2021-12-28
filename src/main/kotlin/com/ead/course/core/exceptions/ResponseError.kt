package com.ead.course.core.exceptions

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ResponseError(
    val status: HttpStatus,
    val message: String? = status.reasonPhrase,
    val timestamp: LocalDateTime = LocalDateTime.now()
)