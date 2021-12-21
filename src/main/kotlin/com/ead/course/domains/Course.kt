package com.ead.course.domains

import com.ead.course.core.entities.Auditable
import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_courses")
data class Course(

    @field:Id
    @field:GeneratedValue(strategy = AUTO)
    val id: UUID,

    @field:Column(nullable = false, length = 150)
    val name: String,

    @field:Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

    val imageUrl: String,

    @field:Column(nullable = false)
    @field:Enumerated(STRING)
    val status: CourseStatus,

    @field:Column(nullable = false)
    @field:Enumerated(STRING)
    val level: CourseLevel,

    @field:Column(nullable = false)
    val instructorId: UUID,

    @field:JsonProperty(access = WRITE_ONLY)
    @field:OneToMany(mappedBy = "course")
    @field:Fetch(SUBSELECT)
    val modules: Set<Module> = HashSet(),

) : Auditable()