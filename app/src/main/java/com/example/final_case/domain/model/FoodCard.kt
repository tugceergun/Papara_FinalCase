package com.example.final_case.domain.model

//data class FoodCard()

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food_cards")
data class FoodCard(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "food_name") val foodName: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "image") val image: String
) : Parcelable {
    companion object {
        fun mock() = FoodCard(
            "1",
            "Food",
            "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
            "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg"
        )
    }
}
