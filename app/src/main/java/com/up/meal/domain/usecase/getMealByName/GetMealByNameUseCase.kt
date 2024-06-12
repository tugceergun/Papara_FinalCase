package com.up.meal.domain.usecase.getMealByName

import com.up.meal.common.Resource
import com.up.meal.data.remote.dto.toMealCards
import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealByNameUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke(name: String) = flow {
        try {
            emit(Resource.Loading())
            repository.getMealsByName(name).toMealCards().let {
                if (it.isNotEmpty()) emit(Resource.Success(it))
                else emit(Resource.Error(message = "Sorry we don't have any meal with $name :("))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}