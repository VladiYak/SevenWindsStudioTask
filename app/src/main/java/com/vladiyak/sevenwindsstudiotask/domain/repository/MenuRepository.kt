package com.vladiyak.sevenwindsstudiotask.domain.repository

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    suspend fun refreshById(coffeeShopId: Int)

    fun getByCoffeeShopIdStream(coffeeShopId: Int): Flow<List<CoffeeItem>>
    fun getByIdsStream(idList: List<Int>): Flow<List<CoffeeItem>>
    suspend fun getMenuItemById(id: Int): CoffeeItem?
}
