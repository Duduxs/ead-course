package com.ead.course.services

import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.LessonDTO
import com.ead.course.dtos.ModuleDTO
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
import com.ead.course.mappers.updateEntity
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import mu.KLogger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class LessonService(
    private val lessonRepository: LessonRepository,
    private val moduleRepository: ModuleRepository,
    private val logger: KLogger,
) {

    @Transactional(readOnly = true)
    fun findById(lessonId: UUID) = lessonRepository.findById(lessonId)
        .orElseThrow { NotFoundHttpException("Lesson with id $lessonId not found") }
        .toDTO()

    @Transactional(readOnly = true)
    fun findAll(moduleId: UUID): Collection<LessonDTO> = lessonRepository.findAllLessonsBy(moduleId).map { it.toDTO() }

    @Transactional
    fun save(moduleId: UUID, dto: LessonDTO): LessonDTO {

        logger.start(this::save)

        val module = moduleRepository.findById(moduleId).orElseThrow {
            NotFoundHttpException("Module with id $moduleId not found")
        }

        val entity = lessonRepository.save(dto.toDomain(module))

        logger.end(this::save, entity)

        return entity.toDTO()
    }

    @Transactional
    fun update(lessonId: UUID, dto: LessonDTO): LessonDTO {

        logger.start(this::update)

        val lesson = lessonRepository.findById(lessonId)
            .orElseThrow { NotFoundHttpException("Lesson with id $lessonId not found") }

        val lessonUpdated = updateEntity(lesson, dto)

        lessonRepository.save(lessonUpdated)

        logger.end(this::update)

        return lessonUpdated.toDTO()
    }

    @Transactional
    fun deleteById(id: UUID) {

        logger.start(this::deleteById)

        try {
            lessonRepository.deleteById(id)
        } catch(e: RuntimeException) {
            throw NotFoundHttpException("Lesson with $id not found")
        } finally {
            logger.end(this::deleteById)
        }
    }

}
