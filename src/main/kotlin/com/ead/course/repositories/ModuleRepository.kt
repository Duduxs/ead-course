package com.ead.course.repositories

import com.ead.course.domains.Module
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ModuleRepository : JpaRepository<Module, UUID> {

    @Query(value = "SELECT * FROM tb_modules where course_id = :courseId", nativeQuery = true)
    fun findAllModulesBy(courseId: UUID) : Collection<Module>

}