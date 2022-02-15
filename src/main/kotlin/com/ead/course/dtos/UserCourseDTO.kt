package com.ead.course.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.util.UUID
import javax.validation.constraints.NotNull

@JsonInclude(Include.NON_NULL)
class UserCourseDTO(

    val userId: UUID? = null,

    @field:NotNull
    val courseId: UUID
)