package com.vladiyak.sevenwindsstudiotask.utils

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

interface MenuItemInteractionListener {

    fun onClick(cartMenuItem: CoffeeItem, position: Int) {}
    fun onAdd(cartMenuItem: CoffeeItem, position: Int) {}
    fun onRemove(cartMenuItem: CoffeeItem, position: Int) {}
}