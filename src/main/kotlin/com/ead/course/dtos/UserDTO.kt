package com.ead.course.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import java.util.UUID
import javax.validation.constraints.NotBlank

data class UserDTO(

    @JsonProperty(access = READ_ONLY)
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank
    val username: String,

    val fullName: String,

    @field:NotBlank
    val email: String,

    val phone: String,

    val cpf: String,

    val imgUrl: String

)