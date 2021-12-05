package com.ead.course.domains

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_modules")
data class Module(

    @field:Id
    @field:GeneratedValue(strategy = AUTO)
    val id: UUID,

    @field:Column(nullable = false, length = 150)
    val title: String,

    @field:Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

) : Auditable()