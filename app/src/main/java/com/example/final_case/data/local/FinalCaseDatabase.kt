package com.example.final_case.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.final_case.data.local.dao.MealCardDao
import com.example.final_case.domain.model.FoodCard

@Database(entities = [FoodCard::class], version = 1)
abstract class FinalCaseDatabase : RoomDatabase() {

    abstract fun mealCardDao(): MealCardDao

    companion object {
        private const val DB_NAME = "finalcase_db"

        @Volatile
        private var INSTANCE: FinalCaseDatabase? = null

        fun getInstance(context: Context): FinalCaseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinalCaseDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}