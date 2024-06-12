package com.up.meal.data.remote.dto

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.up.meal.domain.model.Category
import com.up.meal.domain.model.MealCard
import com.up.meal.domain.model.MealDetail

@Keep
data class MealsDto(
    @SerializedName("meals")
    val meals: List<Meal>? = null
) {
    fun getMealsDetail(): Meal? = meals?.get(0)
}

fun MealsDto.toCategories(): List<Category> {
    val categories = mutableListOf<Category>()
    meals?.forEach { meal ->
        val category = try {
            Category(meal.categoryName!!, meal.getMealNameForQueryParam()!!)
        } catch (e: Exception) {
            Log.d("Conversion Error:", "Failed to convert MealsDto to Category for $meal")
            null
        }
        category?.let { categories.add(it) }
    }
    categories[0].isSelected = true
    return categories
}

fun MealsDto.toMealCards(): List<MealCard> {
    val mealsCards = mutableListOf<MealCard>()
    meals?.forEach { meal ->
        val mealCard = try {
            MealCard(
                meal.mealId!!,
                meal.mealName!!,
                meal.getMealThumbnail()!!,
                meal.getMealImage()!!
            )

        } catch (e: Exception) {
            Log.d("Conversion Error:", "Failed to convert MealsDto to MealCards for $meal")
            null
        }
        mealCard?.let { mealsCards.add(it) }
    }
    return mealsCards
}

fun MealsDto.toMealDetail(): MealDetail? {
    return try {
        val meal = getMealsDetail()
        MealDetail(
            id = meal?.mealId!!,
            mealName = meal.mealName!!,
            thumbnail = meal.getMealThumbnail()!!,
            image = meal.getMealImage()!!,
            instructions = meal.instructions!!,
            ingredients = meal.getIngredients()
        )
    } catch (e: Exception) {
        Log.d("Conversion Error:", "Failed to convert MealsDto to MealDetail")
        null
    }
}