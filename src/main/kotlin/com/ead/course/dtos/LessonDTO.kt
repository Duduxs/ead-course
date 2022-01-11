package com.ead.course.dtos

import com.ead.course.entities.Lesson
import org.hibernate.validator.constraints.URL
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class LessonDTO(

    @field:NotNull
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank
    val title: String,

    val description: String,

    @field:NotBlank
    @field:URL
    val videoUrl: String
) {
    constructor(lesson: Lesson) : this (
        id = lesson.id,
        title = lesson.title,
        description = lesson.description,
        videoUrl = lesson.videoUrl
    )
}