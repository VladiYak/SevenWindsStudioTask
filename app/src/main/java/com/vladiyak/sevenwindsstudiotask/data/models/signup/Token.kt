package com.vladiyak.sevenwindsstudiotask.data.models.signup

data class Token(
    val token: String,
    val tokenLifetime: Int
)