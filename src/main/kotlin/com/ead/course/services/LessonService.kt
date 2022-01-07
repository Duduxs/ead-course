package com.ead.course.services

import com.ead.course.core.exceptions.NotFoundHttpException
import com.ead.course.core.extensions.end
import com.ead.course.core.extensions.start
import com.ead.course.dtos.LessonDTO
import com.ead.course.mappers.toDTO
import com.ead.course.mappers.toDomain
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
}