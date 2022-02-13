package com.ead.course.repositories

import com.ead.course.entities.CourseUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CourseUserRepository : JpaRepository<CourseUser, UUID> {

    @Query(
        """
            select count(*) >= 1 from tb_courses_user tcu
            where tcu.course_id = :courseId
            and tcu.user_id = :userId
        """,
        nativeQuery = true
    )
    fun existsByCourseAndUserId(courseId: UUID, userId: UUID): Boolean
}