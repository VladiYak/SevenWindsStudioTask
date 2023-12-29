package com.vladiyak.sevenwindsstudiotask.data.repository

import android.util.Log
import com.vladiyak.sevenwindsstudiotask.data.local.dao.CoffeeShopsDao
import com.vladiyak.sevenwindsstudiotask.data.local.entity.toEntity
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import com.vladiyak.sevenwindsstudiotask.domain.repository.CoffeeShopsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import retrofit2.HttpException
import javax.inject.Inject

class CoffeeShopsRepositoryImpl @Inject constructor(
    private val coffeeShopsDao: CoffeeShopsDao,
    private val apiService: CoffeeApiService,
    private val geoLocationRepository: GeoLocationRepositoryImpl
): CoffeeShopsRepository {

    override suspend fun refreshAll() {
        geoLocationRepository.refreshCurrentLocation(true)

        val response = apiService.getLocationsList()
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        val body = response.body() ?: throw RuntimeException()
        coffeeShopsDao.replaceAll(body.toEntity())
        Log.d("RepositoryRefreshAll", "$body")
    }

    override fun getCoffeeShopsStream(): Flow<List<LocationItem>> {

        return coffeeShopsDao.observeAll()
            .combine(geoLocationRepository.currentLocation) { coffeeShopList, _ ->
                coffeeShopList.map {
                    Log.d("RepositoryData", "$coffeeShopList")
                    val distance = geoLocationRepository.getDistanceToLocation(
                        it.point.latitude, it.point.longitude
                    )
                    LocationItem(it.id, it.name, it.point, distance?.toDouble() ?: 0.0)
                }
            }
    }

    override suspend fun getCoffeeShopById(id: Int): LocationItem? {
        return coffeeShopsDao.getById(id)?.run {
            val distance = geoLocationRepository.getDistanceToLocation(
                point.latitude.toDouble(), point.longitude.toDouble()
            )
            LocationItem(id, name, point, distance?.toDouble() ?: 0.0)
        }
    }
}