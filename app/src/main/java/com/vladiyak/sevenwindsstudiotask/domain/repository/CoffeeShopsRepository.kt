package com.vladiyak.sevenwindsstudiotask.domain.repository

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import kotlinx.coroutines.flow.Flow

interface CoffeeShopsRepository {

    suspend fun refreshAll()

    fun getCoffeeShopsStream(): Flow<List<LocationItem>>
    suspend fun getCoffeeShopById(id: Int): LocationItem?
}
