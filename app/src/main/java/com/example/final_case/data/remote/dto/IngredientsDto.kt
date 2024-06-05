package com.example.final_case.data.remote.dto

import android.util.Log
import com.example.final_case.domain.model.IngredientDetail
import com.google.gson.annotations.SerializedName

data class IngredientsDto(
    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>? = null
) {
    fun toIngredientDetail(): IngredientDetail? = ingredients?.getOrNull(0)?.toIngredientDetail()
}

data class IngredientDto(
    @SerializedName("idIngredient")
    val id: String? = null,
    @SerializedName("strIngredient")
    val name: String? = null,
    @SerializedName("strDescription")
    val description: String? = null,
    @SerializedName("strType")
    val type: String? = null,
)

fun IngredientDto.toIngredientDetail(): IngredientDetail? {
    return try {
        IngredientDetail(
            id = this.id!!,
            name = this.name!!,
            description = this.description ?: "",
            type = this.type ?: "Not Specified",
        )
    } catch (e: Exception) {
        Log.d("Conversion Error:", "Failed to convert IngredientDto to IngredientDetail")
        null
    }
}