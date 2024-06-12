package com.up.meal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.up.meal.domain.model.MealCard
import kotlinx.coroutines.flow.Flow

@Dao
interface MealCardDao {

    @Query("SELECT * FROM meal_cards")
    fun getAllMealsCards(): Flow<List<MealCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMealCardToFavorites(mealCard: MealCard)

    @Query("DELETE FROM meal_cards WHERE id = :id")
    suspend fun removeMealCardFromFavorites(id: String)

    @Query("SELECT * FROM meal_cards WHERE id = :id")
    suspend fun isMealCardAddedToFavorite(id: String): MealCard?
}