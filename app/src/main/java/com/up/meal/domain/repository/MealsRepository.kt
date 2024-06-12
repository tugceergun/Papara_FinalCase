package com.up.meal.domain.repository

import com.up.meal.data.remote.dto.IngredientsDto
import com.up.meal.data.remote.dto.MealsDto
import com.up.meal.domain.model.MealCard
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun getAllCategories(): MealsDto
    suspend fun getMealsBasedOnCategory(category: String): MealsDto
    suspend fun getMealById(id: String): MealsDto
    suspend fun getMealsByName(mealName: String): MealsDto
    suspend fun getRandomMeal(): MealsDto
    suspend fun addMealCardToFavorite(id: MealCard)
    suspend fun removeMealCardFromFavorite(id: String)
    fun getAllMealsCardsFromFavorites(): Flow<List<MealCard>>
    suspend fun isMealCardAddedToFavorites(id: String): Boolean
    suspend fun getIngredientDetailsByName(ingredientName: String): IngredientsDto
}