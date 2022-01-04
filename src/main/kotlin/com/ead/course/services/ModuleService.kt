package com.ead.course.services

import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import org.springframework.stereotype.Service

@Service
class ModuleService(
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
) {

//    @Transactional(readOnly = true)
//    fun findById(id: UUID) = courseRepository.findById(id)
//        .orElseThrow { NotFoundHttpException("Course with id $id not found") }
//        .toDTO()
//
//    @Transactional(readOnly = true)
//    fun findAll(): Collection<CourseDTO> = courseRepository.findAll().map { it.toDTO() }
//
//    @Transactional
//    fun save(dto: CourseDTO) = courseRepository.save(dto.toDomain()).toDTO()
//
//    @Transactional
//    fun update(id: UUID, dto: CourseDTO): CourseDTO {
//        val course = courseRepository.findById(id).orElseThrow { NotFoundHttpException("Course with id $id not found") }
//
//        val courseUpdated = updateEntity(course, dto)
//
//        courseRepository.save(courseUpdated)
//
//        return courseUpdated.toDTO()
//    }
//
//    @Transactional
//    fun delete(id: UUID) {
//        val module = moduleRepository.findById(id).orElseThrow { NotFoundHttpException("Module with id $id not found") }
//        delete(module)
//    }
//
//    @Transactional
//    fun delete(module: Module) {
//        val lessons = lessonRepository.findAllLessonsBy(module.id)
//
//        if(lessons.isNotEmpty()) { lessonRepository.deleteAll(lessons) }
//
//        moduleRepository.delete(module)
//    }
}