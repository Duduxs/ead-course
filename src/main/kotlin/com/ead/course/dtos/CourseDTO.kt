package com.ead.course.dtos

import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDTO(

    @field:NotBlank
    private val name: String,

    @field:NotBlank
    private val description: String,

    private val imgUrl: String,

    @field:NotNull
    private val status: CourseStatus,

    @field:NotNull
    private val userId: UUID,

    @field:NotNull
    private val level: CourseLevel,
)
