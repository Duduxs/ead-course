package com.ead.course.mappers

import com.ead.course.domains.Course
import com.ead.course.dtos.CourseDTO


fun CourseDTO.toDomain() = Course(
    name = this.name,
    description = this.description,
    imageUrl = this.imgUrl,
    status = this.status,
    instructorId = this.instructorId,
    level = this.level
)

fun Course.toDTO() = CourseDTO(this)