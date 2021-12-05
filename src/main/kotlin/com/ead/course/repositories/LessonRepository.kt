package com.ead.course.repositories

import com.ead.course.domains.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LessonRepository : JpaRepository<Lesson, UUID>