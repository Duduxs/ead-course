package com.ead.course.dtos

import com.ead.course.domains.Course
import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDTO(

    @JsonProperty(access = READ_ONLY)
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    val imgUrl: String,

    @field:NotNull
    val status: CourseStatus,

    @field:NotNull
    val instructorId: UUID,

    @field:NotNull
    val level: CourseLevel,
) {
    constructor(course: Course) : this(
        id = course.id,
        name = course.name,
        description = course.description,
        imgUrl = course.imageUrl,
        status = course.status,
        instructorId = course.instructorId,
        level = course.level
    )
}
