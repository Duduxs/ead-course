package com.ead.course.core.exceptions

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonFormat.*
import com.fasterxml.jackson.annotation.JsonFormat.Shape.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ResponseError(
    val status: HttpStatus,
    val message: String? = status.reasonPhrase,
    @field:JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val timestamp: LocalDateTime = LocalDateTime.now()
)