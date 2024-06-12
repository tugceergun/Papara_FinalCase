package com.up.meal.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "meal_cards")
data class MealCard(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "meal_name") val mealName: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "image") val image: String
) : Parcelable {
    companion object {
        fun mock() = MealCard(
            "1",
            "Mushroom",
            "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
            "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg"
        )
    }
}