package com.ead.course.dtos

import com.ead.course.enums.UserStatus
import com.ead.course.enums.UserType
import java.util.UUID
import javax.validation.constraints.NotBlank

data class UserDTO(

    val id: UUID,

    @field:NotBlank
    val username: String,

    val fullName: String,

    @field:NotBlank
    val email: String,

    val phone: String,

    val cpf: String,

    val imgUrl: String,

    val type: UserType,

    val status: UserStatus

)