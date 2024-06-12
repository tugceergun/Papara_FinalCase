package com.up.meal.domain.usecase.getIngredientDetails

import com.up.meal.common.Resource
import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetIngredientDetailsUseCase @Inject constructor(
    private val repository: MealsRepository
) {

    operator fun invoke(ingredientName: String) = flow {
        try {
            emit(Resource.Loading())
            repository.getIngredientDetailsByName(ingredientName).toIngredientDetail(ingredientName)
                .let {
                    emit(Resource.Success(it))
                }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}