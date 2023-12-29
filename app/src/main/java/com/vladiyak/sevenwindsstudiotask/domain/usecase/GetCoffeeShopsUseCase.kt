package com.vladiyak.sevenwindsstudiotask.domain.usecase

import android.util.Log
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.repository.CoffeeShopsRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoffeeShopsUseCase @Inject constructor(
    private val repository: CoffeeShopsRepositoryImpl
) {

    operator fun invoke(): Flow<Resource<List<LocationItem>>> = flow {
        emit(Resource.Loading())
        try {
            repository.getCoffeeShopsStream().collect {
                emit(Resource.Success(data = it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "An error occurred!"))
        }
    }

}


