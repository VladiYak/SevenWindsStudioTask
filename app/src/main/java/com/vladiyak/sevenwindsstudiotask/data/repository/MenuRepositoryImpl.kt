package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.local.dao.MenuDao
import com.vladiyak.sevenwindsstudiotask.data.local.entity.toEntity
import com.vladiyak.sevenwindsstudiotask.data.local.entity.toModel
import com.vladiyak.sevenwindsstudiotask.data.local.toEntity
import com.vladiyak.sevenwindsstudiotask.data.local.toModel
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import com.vladiyak.sevenwindsstudiotask.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuDao: MenuDao,
    private val apiService: CoffeeApiService
): MenuRepository {

    override suspend fun refreshById(coffeeShopId: Int) {
        val response = apiService.getLocationMenu(coffeeShopId.toString())
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        val body = response.body() ?: throw RuntimeException()
        menuDao.replaceAll(body.toEntity(coffeeShopId))
    }

    override fun getByCoffeeShopIdStream(coffeeShopId: Int): Flow<List<CoffeeItem>> {
        return menuDao.observeByCoffeeShopId(coffeeShopId).map { menuItemList ->
            menuItemList.toModel()
        }
    }

    override fun getByIdsStream(idList: List<Int>): Flow<List<CoffeeItem>> {
        return menuDao.observeByIds(idList).map { menuItemList ->
            menuItemList.toModel()
        }
    }

    override suspend fun getMenuItemById(id: Int): CoffeeItem? {
        return menuDao.getById(id)?.toModel()
    }
}