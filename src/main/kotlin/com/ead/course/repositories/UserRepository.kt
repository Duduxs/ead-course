package com.ead.course.repositories

import com.ead.course.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID>, JpaSpecificationExecutor<User>