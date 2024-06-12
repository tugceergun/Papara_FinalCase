package com.up.meal.domain.usecase.getRandomMeal

import com.up.meal.common.Resource
import com.up.meal.data.remote.dto.toMealDetail
import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomMealUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke(id: String) = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getMealById(id).toMealDetail()
            if (result == null) {
                emit(Resource.Error(message = "An unexpected error occurred!"))
            } else {
                emit(Resource.Success(result))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)
}