package com.vladiyak.sevenwindsstudiotask.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

fun correctId(item: CoffeeItem): Int {
    if (item.id in 5..8) {
        return item.id - 4
    } else if (
        item.id in 9..12
    ) {
        return item.id - 8
    }
    return item.id
}

fun String.addSuffix(value: String) = this + value

fun String.toKilometers(distance: Double) {
    this.format("%.2f", distance)
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
