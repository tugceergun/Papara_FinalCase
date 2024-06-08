package com.example.final_case.domain.usecase.getMealCardsFromFavorite

import com.example.final_case.domain.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMealCardsFromFavorites @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke() = repository.getAllMealCardsFromFavorites().flowOn(Dispatchers.IO)
}