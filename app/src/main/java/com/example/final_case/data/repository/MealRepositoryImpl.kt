package com.example.final_case.data.repository

import com.example.final_case.data.local.dao.MealCardDao
import com.example.final_case.data.remote.MealApi
import com.example.final_case.data.remote.dto.MealDto
import com.example.final_case.data.remote.dto.IngredientsDto
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val api: MealApi,
    private val mealCardDao: MealCardDao
) : MealRepository {

    override suspend fun getAllCategories(): MealDto {
        return api.getAllCategories()
    }

    override suspend fun getMealsBasedOnCategory(category: String): MealDto {
        return api.getMealsBasedOnCategory(category)
    }

    override suspend fun getMealById(id: String): MealDto {
        return api.getMealById(id)
    }

    override suspend fun getMealsByName(mealName: String): MealDto {
        return api.getMealsByName(mealName)
    }

    override suspend fun getRandomMeal(): MealDto {
        return api.getRandomMeal()
    }

    override suspend fun addMealCardToFavorite(mealCard: FoodCard) {
        return mealCardDao.addMealCardToFavorites(mealCard)
    }

    override suspend fun removeMealCardFromFavorite(mealId: String) {
        mealCardDao.removeMealCardFromFavorites(mealId)
    }

    override fun getAllMealCardsFromFavorites(): Flow<List<FoodCard>> {
        return mealCardDao.getAllMealCards()
    }

    override suspend fun isMealCardAddedToFavorites(id: String): Boolean {
        return mealCardDao.isMealCardAddedToFavorite(id) != null
    }

    override suspend fun getIngredientDetails(ingredientName: String): IngredientsDto {
        return api.getIngredientDetails(ingredientName)
    }
}