package com.ead.course.repositories

import com.ead.course.entities.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface LessonRepository : JpaRepository<Lesson, UUID> {

    @Query(value = "SELECT * FROM tb_lessons where module_id = :moduleId", nativeQuery = true)
    fun findAllLessonsBy(moduleId: UUID) : Collection<Lesson>

}