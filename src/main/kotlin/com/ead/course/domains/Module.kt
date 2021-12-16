package com.ead.course.domains

import com.ead.course.core.Auditable
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
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

    @field:JsonProperty(access = WRITE_ONLY)
    @field:ManyToOne(optional = false)
    val course: Course,

    @field:JsonProperty(access = WRITE_ONLY)
    @field:OneToMany(mappedBy = "module")
    @field:Fetch(SUBSELECT)
    val lessons: Set<Lesson> = HashSet()

) : Auditable()