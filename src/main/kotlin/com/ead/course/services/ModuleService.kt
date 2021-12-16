package com.ead.course.services

import com.ead.course.domains.Module
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ModuleService(
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
) {

    @Transactional
    fun delete(module: Module) {
        val lessons = lessonRepository.findAllLessonsBy(module.id)

        if(lessons.isNotEmpty()) { lessonRepository.deleteAll(lessons) }

        moduleRepository.delete(module)
    }
}