package com.ead.course.dtos

import java.util.UUID

data class NotificationCommandDTO(
    val title: String,
    val message: String,
    val userId: UUID
)