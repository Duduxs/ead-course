package com.ead.course.entities

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
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_courses")
class Course(

    @Id
    @GeneratedValue(strategy = AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 150)
    val name: String,

    @Column(nullable = false, length = 250, columnDefinition = "TEXT")
    val description: String,

    val imageUrl: String,

    @Column(nullable = false)
    @Enumerated(STRING)
    val status: CourseStatus,

    @Column(nullable = false)
    @Enumerated(STRING)
    val level: CourseLevel,

    @Column(nullable = false)
    val instructorId: UUID,

    @field:JsonProperty(access = WRITE_ONLY)
    @OneToMany(mappedBy = "course")
    @Fetch(SUBSELECT)
    val modules: Set<Module> = HashSet(),

    @field:JsonProperty(access = WRITE_ONLY)
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "tb_courses_users",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    private val users: Set<User> = HashSet()

) : Auditable()