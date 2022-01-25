package com.ead.course.entities

import com.ead.course.core.entities.Auditable
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "tb_lessons")
class Lesson(

    @Id
    @GeneratedValue(strategy = AUTO)
    val id: UUID,

    @Column(nullable = false, length = 150)
    val title: String,

    @Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    val videoUrl: String,

    @field:JsonProperty(access = WRITE_ONLY)
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "module_id")
    val module: Module,

) : Auditable()