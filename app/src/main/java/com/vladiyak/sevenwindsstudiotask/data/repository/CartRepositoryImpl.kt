package com.vladiyak.sevenwindsstudiotask.data.repository

import androidx.room.withTransaction
import com.vladiyak.sevenwindsstudiotask.data.local.AppDatabase
import com.vladiyak.sevenwindsstudiotask.data.local.dao.CartDao
import com.vladiyak.sevenwindsstudiotask.data.local.entity.CartItemEntity
import com.vladiyak.sevenwindsstudiotask.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val cartDao: CartDao
): CartRepository {

   override fun getAllStream(): Flow<List<CartItemEntity>> = cartDao.observeAll()

   override fun getByIdsStream(idList: List<Int>): Flow<List<CartItemEntity>> =
        cartDao.observeByIds(idList)

   override suspend fun add(menuItemId: Int) {
        database.withTransaction {
            cartDao.getById(menuItemId)?.let {
                cartDao.add(menuItemId)
            } ?: run {
                cartDao.insert(CartItemEntity(menuItemId, 1))
            }
        }
    }

   override suspend fun remove(menuItemId: Int) {
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

   override suspend fun deleteAll() = cartDao.deleteAll()
}