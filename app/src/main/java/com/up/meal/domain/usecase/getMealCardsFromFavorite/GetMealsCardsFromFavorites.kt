package com.up.meal.domain.usecase.getMealCardsFromFavorite

import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMealsCardsFromFavorites @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke() = repository.getAllMealsCardsFromFavorites().flowOn(Dispatchers.IO)
}