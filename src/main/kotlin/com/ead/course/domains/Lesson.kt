package com.ead.course.domains

import com.ead.course.core.entities.Auditable
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "tb_lessons")
data class Lesson(

    @field:Id
    @field:GeneratedValue(strategy = AUTO)
    val id: UUID,

    @field:Column(nullable = false, length = 150)
    val title: String,

    @field:Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

    @field:Column(nullable = false)
    val videoUrl: String,

    @field:JsonProperty(access = WRITE_ONLY)
    @field:ManyToOne(optional = false)
    val module: Module,

) : Auditable()