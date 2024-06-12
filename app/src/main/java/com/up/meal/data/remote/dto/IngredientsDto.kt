package com.up.meal.data.remote.dto

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.up.meal.domain.model.IngredientDetail

@Keep
data class IngredientsDto(
    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>? = null
) {
    fun toIngredientDetail(ingredientName: String): IngredientDetail =
        ingredients?.getOrNull(0).toIngredientDetail(ingredientName)
}

@Keep
data class IngredientDto(
    @SerializedName("idIngredient")
    val id: String? = null,
    @SerializedName("strIngredient")
    val name: String? = null,
    @SerializedName("strDescription")
    val description: String? = null,
    @SerializedName("strType")
    val type: String? = null,
    @SerializedName("strABV")
    val abv: String? = null
)

fun IngredientDto?.toIngredientDetail(ingredientName: String): IngredientDetail {
    Log.d("Conversion", "$this")
    return IngredientDetail(
        id = this?.id ?: "NA",
        name = ingredientName,
        description = this?.description ?: "",
        type = this?.type ?: "Not Specified",
        abv = this?.abv
    )
}