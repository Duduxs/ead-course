package com.ead.course.dtos

import com.ead.course.entities.User
import com.ead.course.mappers.toDTO
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import org.hibernate.validator.constraints.br.CPF
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserDTO(

    @field:JsonProperty(access = READ_ONLY)
    @field:NotNull
    val id: UUID = UUID.randomUUID(),

    @field:Email
    @field:NotEmpty
    val email: String?,

    @field:CPF
    @field:NotEmpty
    val cpf: String?,

    @field:NotBlank
    @field:Size(max = 100)
    val fullName: String?,

    @field:NotEmpty
    val status: String,

    @field:NotEmpty
    val type: String,

    val imgUrl: String?,

    val courses: Set<CourseDTO> = hashSetOf()
) {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        cpf = user.cpf,
        fullName = user.fullName,
        status = user.status,
        type = user.type,
        imgUrl = user.imgUrl,
        courses = user.courses.mapTo(HashSet<CourseDTO>()) { it.toDTO() }
    )
}
