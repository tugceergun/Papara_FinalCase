package com.example.final_case.domain.usecase.getIngredientDetails

import com.example.final_case.common.Resource
import com.example.final_case.domain.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetIngredientDetailsUseCase @Inject constructor(
    private val repository: MealRepository
) {

    operator fun invoke(ingredientName: String) = flow {
        try {
            emit(Resource.Loading())
            repository.getIngredientDetails(ingredientName).toIngredientDetail()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(message = "Conversion Error: Failed to convert IngredientDto to IngredientDetail"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}