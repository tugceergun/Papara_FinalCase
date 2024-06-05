package com.example.final_case.data.remote

import com.example.final_case.data.remote.dto.MealDto
import com.example.final_case.data.remote.dto.IngredientsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getAllCategories(): MealDto

    @GET("/api/json/v1/1/list.php?i=list")
    suspend fun getIngredientsFilters(): MealDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsBasedOnCategory(@Query("c") category: String): MealDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealDto

    @GET("api/json/v1/1/search.php")
    suspend fun getMealsByName(@Query("s") mealName: String): MealDto

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealDto

    @GET("api/json/v1/1/list.php")
    suspend fun getIngredientDetails(@Query("i") ingredient: String): IngredientsDto

}