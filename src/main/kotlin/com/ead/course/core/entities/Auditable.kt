package com.ead.course.core.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditable {

    @field:CreatedDate
    @field:JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @field:Column(nullable = false, updatable = false)
    open var createdDate: LocalDateTime = LocalDateTime.now()

    @field:LastModifiedDate
    @field:JsonFormat(shape = STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @field:Column(nullable = false)
    open var modifiedDate: LocalDateTime = LocalDateTime.now()
}