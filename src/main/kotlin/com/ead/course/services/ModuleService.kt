package com.ead.course.services

import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.info
import com.ead.course.core.extensions.start
import com.ead.course.dtos.ModuleDTO
import com.ead.course.entities.Module
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
import com.ead.course.mappers.updateEntity
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import mu.KLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ModuleService(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
    private val logger: KLogger,
) {

    @Transactional(readOnly = true)
    fun findById(moduleId: UUID) = moduleRepository.findById(moduleId)
        .orElseThrow { NotFoundHttpException("Module with id $moduleId not found") }
        .toDTO()

    @Transactional(readOnly = true)
    fun findAll(courseId: UUID): Collection<ModuleDTO> = moduleRepository.findAllModulesBy(courseId).map { it.toDTO() }

    @Transactional
    fun save(courseId: UUID, dto: ModuleDTO): ModuleDTO {

        logger.start(this::save)

        val course = courseRepository.findById(courseId).orElseThrow {
            NotFoundHttpException("Course with id $courseId not found")
        }

        val entity = moduleRepository.save(dto.toDomain(course))

        logger.end(this::save, entity)

        return entity.toDTO()
    }

    @Transactional
    fun update(moduleId: UUID, dto: ModuleDTO): ModuleDTO {

        logger.start(this::update)

        val module = moduleRepository.findById(moduleId).orElseThrow { NotFoundHttpException("Module with id $moduleId not found") }

        val moduleUpdated = updateEntity(module, dto)

        moduleRepository.save(moduleUpdated)

        logger.end(this::update)

        return moduleUpdated.toDTO()
    }

    @Transactional
    fun deleteById(id: UUID) {

        logger.start(this::deleteById)

        val module = moduleRepository.findById(id).orElseThrow { NotFoundHttpException("Module with id $id not found") }

        delete(module)

        logger.end(this::deleteById)
    }

    @Transactional
    fun delete(module: Module) {

        logger.start(this::delete, parameters = arrayOf(module.id))

        val lessons = lessonRepository.findAllLessonsBy(module.id!!)

        logger.info(this::delete, message = "lessons size in module id ${module.id} ${lessons.size}")

        if(lessons.isNotEmpty()) { lessonRepository.deleteAll(lessons) }

        moduleRepository.delete(module)

        logger.end(this::delete)
    }
}