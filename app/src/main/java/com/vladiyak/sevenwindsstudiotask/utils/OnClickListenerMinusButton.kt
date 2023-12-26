package com.vladiyak.sevenwindsstudiotask.utils

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

interface OnClickListenerMinusButton {
    fun onItemClick(coffeeItem: CoffeeItem)
}