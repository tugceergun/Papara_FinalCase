package com.example.final_case.domain.usecase.removeMealCardFromFavorite

import com.example.final_case.common.Resource
import com.example.final_case.domain.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoveMealCardFromFavorite @Inject constructor(
    private val repository: MealRepository
) {

    operator fun invoke(mealId: String) = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.removeMealCardFromFavorite(mealId)))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}