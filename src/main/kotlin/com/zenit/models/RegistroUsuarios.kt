package com.zenit.models

import kotlinx.serialization.Serializable

@Serializable

data class RegistroUsuarios(
    val username: String,
    val email: String,
    val password: String
)
