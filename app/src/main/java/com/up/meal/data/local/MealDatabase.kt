package com.up.meal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.up.meal.data.local.dao.MealCardDao
import com.up.meal.domain.model.MealCard

@Database(entities = [MealCard::class], version = 1)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealCardDao(): MealCardDao

    companion object {
        private const val DB_NAME = "meal_db"

        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}