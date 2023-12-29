package com.vladiyak.sevenwindsstudiotask.domain.repository

import com.vladiyak.sevenwindsstudiotask.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getAllStream(): Flow<List<CartItemEntity>>
    fun getByIdsStream(idList: List<Int>): Flow<List<CartItemEntity>>

    suspend fun add(menuItemId: Int)
    suspend fun remove(menuItemId: Int)

    suspend fun deleteAll()
}
