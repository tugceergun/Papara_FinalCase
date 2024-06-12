package com.up.meal.data.repository

import com.up.meal.data.local.dao.MealCardDao
import com.up.meal.data.remote.MealApi
import com.up.meal.data.remote.dto.IngredientsDto
import com.up.meal.data.remote.dto.MealsDto
import com.up.meal.domain.model.MealCard
import com.up.meal.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val api: MealApi,
    private val mealCardDao: MealCardDao
) : MealsRepository {
    override suspend fun getAllCategories(): MealsDto {
        return api.getAllCategories()
    }

    override suspend fun getMealsBasedOnCategory(category: String): MealsDto {
        return api.getMealsBasedOnCategory(category)
    }

    override suspend fun getMealById(id: String): MealsDto {
        return api.getMealById(id)
    }

    override suspend fun getMealsByName(mealName: String): MealsDto {
        return api.getMealByName(mealName)
    }

    override suspend fun getRandomMeal(): MealsDto {
        return api.getRandomMeal()
    }

    override suspend fun addMealCardToFavorite(id: MealCard) {
        return mealCardDao.addMealCardToFavorites(id)
    }

    override suspend fun removeMealCardFromFavorite(id: String) {
        return mealCardDao.removeMealCardFromFavorites(id)
    }

    override fun getAllMealsCardsFromFavorites(): Flow<List<MealCard>> {
        return mealCardDao.getAllMealsCards()
    }

    override suspend fun isMealCardAddedToFavorites(id: String): Boolean {
        return mealCardDao.isMealCardAddedToFavorite(id) != null
    }

    override suspend fun getIngredientDetailsByName(ingredientName: String): IngredientsDto {
        val url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?i=$ingredientName"
        return api.getIngredientDetailsByName(url)
    }

}
