package com.vladiyak.sevenwindsstudiotask.data.repository

import android.util.Log
import android.view.MenuItem
import androidx.annotation.experimental.R
import com.vladiyak.sevenwindsstudiotask.data.local.MenuDao
import com.vladiyak.sevenwindsstudiotask.data.local.toEntity
import com.vladiyak.sevenwindsstudiotask.data.local.toModel
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuDao: MenuDao,
    private val apiService: CoffeeApiService
) {

    suspend fun refreshById(coffeeShopId: Int) {
        val response = apiService.getLocationMenu(coffeeShopId.toString())
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        val body = response.body() ?: throw RuntimeException()
        menuDao.replaceAll(body.toEntity(coffeeShopId))
    }

    fun getByCoffeeShopIdStream(coffeeShopId: Int): Flow<List<CoffeeItem>> {
        return menuDao.observeByCoffeeShopId(coffeeShopId).map { menuItemList ->
            menuItemList.toModel()
        }
    }

    fun getByIdsStream(idList: List<Int>): Flow<List<CoffeeItem>> {
        return menuDao.observeByIds(idList).map { menuItemList ->
            menuItemList.toModel()
        }
    }

    suspend fun getMenuItemById(id: Int): CoffeeItem? {
        return menuDao.getById(id)?.toModel()
    }
}