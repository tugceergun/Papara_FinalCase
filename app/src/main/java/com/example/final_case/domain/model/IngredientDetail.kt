package com.example.final_case.domain.model

data class IngredientDetail(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
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
