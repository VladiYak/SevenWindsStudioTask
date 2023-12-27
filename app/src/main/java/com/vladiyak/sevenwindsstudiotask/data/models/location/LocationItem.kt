package com.vladiyak.sevenwindsstudiotask.data.models.location


data class LocationItem(
    val id: Int,
    val name: String,
    val point: Point,
    var distance: Double
)