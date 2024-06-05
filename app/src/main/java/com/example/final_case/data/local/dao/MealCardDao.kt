package com.example.final_case.data.local.dao

import androidx.room.*
import com.example.final_case.domain.model.FoodCard
import kotlinx.coroutines.flow.Flow

@Dao
interface MealCardDao {

    @Query("SELECT * FROM food_cards")
    fun getAllMealCards(): Flow<List<FoodCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMealCardToFavorites(drinkCard: FoodCard)

    @Query("DELETE FROM food_cards WHERE id = :id")
    suspend fun removeMealCardFromFavorites(id: String)

    @Query("SELECT * FROM food_cards WHERE id = :id")
    suspend fun isMealCardAddedToFavorite(id: String): FoodCard?
}