package com.ead.course.entities

import com.ead.course.core.entities.Auditable
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_modules")
class Module(

    @Id
    @GeneratedValue(strategy = AUTO)
    val id: UUID? = null,

    @Column(nullable = false, length = 150)
    val title: String,

    @Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

    @field:JsonProperty(access = WRITE_ONLY)
    @ManyToOne(optional = false, fetch = LAZY)
    val course: Course,

    @field:JsonProperty(access = WRITE_ONLY)
    @OneToMany(mappedBy = "module")
    @Fetch(SUBSELECT)
    val lessons: Set<Lesson> = HashSet()

) : Auditable()