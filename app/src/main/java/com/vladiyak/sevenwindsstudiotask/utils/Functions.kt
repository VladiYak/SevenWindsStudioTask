package com.vladiyak.sevenwindsstudiotask.utils

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