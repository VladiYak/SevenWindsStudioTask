package com.vladiyak.sevenwindsstudiotask.utils

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

interface OnClickListenerPlusButton {
    fun onItemClick(coffeeItem: CoffeeItem)
}