package com.vladiyak.sevenwindsstudiotask.domain.usecase

import android.util.Log
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.repository.CartRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.MenuRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartMenuItemsUseCase @Inject constructor(
    private val menuRepository: MenuRepositoryImpl,
    private val cartRepository: CartRepositoryImpl
) {

    operator fun invoke(coffeeShopId: Int = 0): Flow<List<CoffeeItem>> {
        return if (coffeeShopId > 0) {
            menuRepository.getByCoffeeShopIdStream(coffeeShopId).flatMapLatest { menuItems ->
                Log.d("UseCaseData", "${menuItems}")
                val idList = menuItems.map { it.id }
                cartRepository.getByIdsStream(idList).map { cartItems ->
                    val idToCount = cartItems.associate { it.menuItemId to it.count }
                    menuItems.map {
                        val itemCount = idToCount[it.id] ?: 0
                        CoffeeItem(it.id, it.name, it.imageURL, it.price, itemCount)
                    }
                }
            }
        } else {
            cartRepository.getAllStream().flatMapLatest { cartItems ->
                val idList = cartItems.map { it.menuItemId }
                val idToCount = cartItems.associate { it.menuItemId to it.count }
                menuRepository.getByIdsStream(idList).map { menuItems ->
                    menuItems.map {
                        val count = idToCount[it.id] ?: 0
                        CoffeeItem(it.id, it.name, it.imageURL, it.price, count)
                    }
                }
            }
        }
    }
}