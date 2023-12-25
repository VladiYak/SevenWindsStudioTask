package com.vladiyak.sevenwindsstudiotask.data.models.signup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val token: String,
    val tokenLifetime: Int
): Parcelable