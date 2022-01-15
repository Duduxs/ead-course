package com.ead.course.repositories

import com.ead.course.entities.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface CourseRepository : JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course>