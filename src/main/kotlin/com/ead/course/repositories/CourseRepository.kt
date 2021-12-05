package com.ead.course.repositories

import com.ead.course.domains.Course
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourseRepository : JpaRepository<Course, UUID>