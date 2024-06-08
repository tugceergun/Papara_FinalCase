package com.example.final_case.domain.usecase.getMealsByName

import com.example.final_case.common.Resource
import com.example.final_case.data.remote.dto.toFoodCards
import com.example.final_case.domain.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealsByNameUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(name: String) = flow {
        try {
            emit(Resource.Loading())
            repository.getMealsByName(name).toFoodCards().let {
                if (it.isNotEmpty()) emit(Resource.Success(it))
                else emit(Resource.Error(message = "Sorry we don't have any drink with $name :("))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}