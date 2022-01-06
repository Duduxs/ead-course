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

fun updateEntity(module: Module, dto: ModuleDTO) = Module(
    id = module.id,
    course = module.course,
    title = dto.title,
    description = dto.description,
)