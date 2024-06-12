package com.up.meal.data.remote

import com.up.meal.data.remote.dto.IngredientsDto
import com.up.meal.data.remote.dto.MealsDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface MealApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getAllCategories(): MealsDto

    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getIngredientsFilters(): MealsDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsBasedOnCategory(@Query("c") category: String): MealsDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsDto

    @GET("api/json/v1/1/search.php")
    suspend fun getMealByName(@Query("s") name: String): MealsDto

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealsDto

    @GET
    suspend fun getIngredientDetailsByName(
        @Url url: String
    ): IngredientsDto
}