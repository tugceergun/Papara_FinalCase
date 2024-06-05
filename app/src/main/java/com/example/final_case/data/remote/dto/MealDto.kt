
package com.example.final_case.data.remote.dto

import android.util.Log
import com.example.final_case.domain.model.Category
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.domain.model.FoodDetail

data class MealDto(
    val meals: List<Meal>? = null
) {
    fun getMealDetail(): Meal? = meals?.get(0)
}

fun MealDto.toCategories(): List<Category> {
    val categories = mutableListOf<Category>()
    meals?.forEach { meal ->
        val category = try {
            Category(meal.strCategory!!, meal.getMealNameForQueryParam()!!)
        } catch (e: Exception) {
            Log.d("Conversion Error:", "Failed to convert DrinkDto to Category for $meal")
            null
        }
        category?.let { categories.add(it) }
    }
    categories[0].isSelected = true
    return categories
}

fun MealDto.toFoodCards(): List<FoodCard> {
    val foodCards = mutableListOf<FoodCard>()
    meals?.forEach { meal ->
        val foodCard = try {
            FoodCard(
                meal.idMeal!!,
                meal.strMeal!!,
                meal.getMealThumbnail()!!,
                meal.getMealImage()!!
            )

        } catch (e: Exception) {
            android.util.Log.d("Conversion Error:", "Failed to convert DrinkDto to DrinkCard for $meal")
            null
        }
        foodCard?.let { foodCards.add(it) }
    }
    return foodCards
}

fun MealDto.toFoodDetail(): FoodDetail? {
    return try {
        val meal = getMealDetail()
        FoodDetail(
            id = meal?.idMeal!!,
            foodName = meal.strMeal!!,
            thumbnail = meal.getMealThumbnail()!!,
            image = meal.getMealImage()!!,
            instructions = meal.strInstructions!!,
            ingredients = meal.getIngredients()
        )
    } catch (e: Exception) {
        android.util.Log.d("Conversion Error:", "Failed to convert DrinkDto to DrinkDetail")
        null
    }
}