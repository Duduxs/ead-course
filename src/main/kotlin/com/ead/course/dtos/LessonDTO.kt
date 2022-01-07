package com.ead.course.dtos

import com.ead.course.entities.Lesson
import com.ead.course.entities.Module
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class LessonDTO(

    @NotNull
    val id: UUID,

    @NotBlank
    val title: String,

    val description: String,

    @NotBlank
    val videoUrl: String
) {
    constructor(lesson: Lesson) : this (
        id = lesson.id ?: UUID.randomUUID(),
        title = lesson.title,
        description = lesson.description,
        videoUrl = lesson.videoUrl
    )
}