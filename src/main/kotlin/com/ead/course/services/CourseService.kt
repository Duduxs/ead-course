package com.ead.course.services

import com.ead.course.domains.Course
import com.ead.course.domains.Module
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
) {

    @Transactional
    fun delete(course: Course) {
        val modules: Collection<Module> = moduleRepository.findAllModulesBy(course.id)

        if (modules.isNotEmpty()) {
            for (module in modules) {
                val lessons = lessonRepository.findAllLessonsBy(module.id)
                if (lessons.isNotEmpty()) lessonRepository.deleteAll(lessons)
            }
            moduleRepository.deleteAll(modules)
        }

        courseRepository.delete(course)
    }
}