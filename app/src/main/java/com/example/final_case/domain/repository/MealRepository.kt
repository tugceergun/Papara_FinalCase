package com.example.final_case.domain.repository

import com.example.final_case.data.remote.dto.MealDto
import com.example.final_case.data.remote.dto.IngredientsDto
import com.example.final_case.domain.model.FoodCard
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun getAllCategories(): MealDto

    suspend fun getMealsBasedOnCategory(category: String): MealDto

    suspend fun getMealById(id: String): MealDto

    suspend fun getMealsByName(mealName: String): MealDto

    suspend fun getRandomMeal(): MealDto

    suspend fun addMealCardToFavorite(mealCard: FoodCard)

    suspend fun removeMealCardFromFavorite(mealId: String)

    fun getAllMealCardsFromFavorites(): Flow<List<FoodCard>>

    suspend fun isMealCardAddedToFavorites(id: String): Boolean

    suspend fun getIngredientDetails(ingredientName: String): IngredientsDto
}