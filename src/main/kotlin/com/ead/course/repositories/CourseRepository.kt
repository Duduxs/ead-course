package com.ead.course.repositories

import com.ead.course.entities.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CourseRepository : JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    @Query(
        value =
        """
            select 
                case when count(tcu) > 0 then true
                else false end
            from tb_courses_users tcu
                where tcu.course_id = :courseId
                and tcu.user_id = :userId
        """,
        nativeQuery = true
    )
    fun existsBy(courseId: UUID, userId: UUID): Boolean


    @Query(
        value =
            """
                insert into tb_courses_users values (:courseId, :userId)  
            """,
        nativeQuery = true
    )
    @Modifying
    fun saveUserInCourse(courseId: UUID, userId: UUID)
}