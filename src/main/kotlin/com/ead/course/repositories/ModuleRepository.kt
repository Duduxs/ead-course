package com.ead.course.repositories

import com.ead.course.domains.Module
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ModuleRepository : JpaRepository<Module, UUID>