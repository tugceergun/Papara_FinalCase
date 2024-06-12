package com.up.meal.domain.model

import androidx.annotation.Keep

@Keep
data class IngredientDetail(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val abv: String?
) {
    fun getIngredientImage(type: Dimension): String {
        return when(type) {
            Dimension.SMALL -> "https://www.themealdb.com/images/ingredients/${name.split(" ").last()}-Small.png"
            Dimension.MEDIUM -> "https://www.themealdb.com/images/ingredients/${name.split(" ").last()}-Medium.png"
            Dimension.LARGE -> "https://www.themealdb.com/images/ingredients/${name.split(" ").last()}.png"
        }
    }

    enum class Dimension {
        SMALL,
        MEDIUM,
        LARGE
    }
}