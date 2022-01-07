package com.ead.course.mappers

import com.ead.course.dtos.LessonDTO
import com.ead.course.dtos.ModuleDTO
import com.ead.course.entities.Course
import com.ead.course.entities.Lesson
import com.ead.course.entities.Module


fun LessonDTO.toDomain(module: Module) = Lesson(
    id = this.id,
    title = this.title,
    description = this.description,
    videoUrl = this.videoUrl,
    module = module,
)

fun Lesson.toDTO() = LessonDTO(this)

//fun updateEntity(module: Module, dto: ModuleDTO) = Module(
//    id = module.id,
//    course = module.course,
//    title = dto.title,
//    description = dto.description,
//)