package com.up.meal.domain.usecase.getCategories

import com.up.meal.common.Resource
import com.up.meal.data.remote.dto.toCategories
import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealCategoriesUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getAllCategories().toCategories()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}