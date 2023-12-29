package com.vladiyak.sevenwindsstudiotask.presentation.map

data class CameraMovePosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float? = null,
    val azimuth: Float? = null,
    val tilt: Float? = null
)
