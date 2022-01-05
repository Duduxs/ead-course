package com.ead.course.mappers

import com.ead.course.dtos.ModuleDTO
import com.ead.course.entities.Course
import com.ead.course.entities.Module


fun ModuleDTO.toDomain(course: Course) = Module(
    title = this.title,
    description = this.description,
    course = course,
)

fun Module.toDTO() = ModuleDTO(this)

//fun updateEntity(course: Course, dto: CourseDTO) = Course(
//    id = course.id,
//    name = dto.name,
//    description = dto.description,
//    imageUrl = dto.imgUrl,
//    status = dto.status,
//    instructorId = dto.instructorId,
//    level = dto.level,
//)