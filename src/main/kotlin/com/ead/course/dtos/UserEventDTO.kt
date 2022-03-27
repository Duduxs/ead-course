package com.ead.course.dtos

import com.ead.course.core.annotations.NoArg
import java.util.UUID

@NoArg
data class UserEventDTO(

    val id: UUID,

    val username: String,

    val email: String,

    val fullName: String,

    val status: String,

    val type: String,

    val phone: String,

    val cpf: String,

    val imgUrl: String,

    val actionType: String

)