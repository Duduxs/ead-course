package com.ead.course.repositories

import com.ead.course.entities.CourseUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourseUserRepository : JpaRepository<CourseUser, UUID>