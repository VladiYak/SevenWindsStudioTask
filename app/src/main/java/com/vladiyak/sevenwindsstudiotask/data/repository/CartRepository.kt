package com.vladiyak.sevenwindsstudiotask.data.repository

import androidx.room.withTransaction
import com.vladiyak.sevenwindsstudiotask.data.local.AppDatabase
import com.vladiyak.sevenwindsstudiotask.data.local.CartDao
import com.vladiyak.sevenwindsstudiotask.data.local.CartItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val database: AppDatabase,
    private val cartDao: CartDao
) {

   fun getAllStream(): Flow<List<CartItemEntity>> = cartDao.observeAll()

   fun getByIdsStream(idList: List<Int>): Flow<List<CartItemEntity>> =
        cartDao.observeByIds(idList)

   suspend fun add(menuItemId: Int) {
        database.withTransaction {
            cartDao.getById(menuItemId)?.let {
                cartDao.add(menuItemId)
            } ?: run {
                cartDao.insert(CartItemEntity(menuItemId, 1))
            }
        }
    }

   suspend fun remove(menuItemId: Int) {
        database.withTransaction {
            cartDao.getById(menuItemId)?.let {
                if (it.count == 1) {
                    cartDao.deleteById(menuItemId)
                } else {
                    cartDao.remove(menuItemId)
                }
            }
        }
    }

   suspend fun deleteAll() = cartDao.deleteAll()
}