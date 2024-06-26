package com.up.meal.data.remote.dto

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.up.meal.domain.model.Ingredient

@Keep
data class Meal(
    @SerializedName("idMeal") val mealId: String? = null,
    @SerializedName("strMeal") val mealName: String? = null,
    @SerializedName("strDrinkAlternate") val strDrinkAlternate: String? = null,
    @SerializedName("strCategory") val categoryName: String? = null,
    @SerializedName("strArea") val strArea: String? = null,
    @SerializedName("strInstructions") val instructions: String? = null,
    @SerializedName("strMealThumb") val thumbnailImage: String? = null,
    @SerializedName("strTags") val strTags: String? = null,
    @SerializedName("strYoutube") val strYoutube: String? = null,
    @SerializedName("strIngredient1") val ingredient1: String? = null,
    @SerializedName("strIngredient2") val ingredient2: String? = null,
    @SerializedName("strIngredient3") val ingredient3: String? = null,
    @SerializedName("strIngredient4") val ingredient4: String? = null,
    @SerializedName("strIngredient5") val ingredient5: String? = null,
    @SerializedName("strIngredient6") val ingredient6: String? = null,
    @SerializedName("strIngredient7") val ingredient7: String? = null,
    @SerializedName("strIngredient8") val ingredient8: String? = null,
    @SerializedName("strIngredient9") val ingredient9: String? = null,
    @SerializedName("strIngredient10") val ingredient10: String? = null,
    @SerializedName("strIngredient11") val ingredient11: String? = null,
    @SerializedName("strIngredient12") val ingredient12: String? = null,
    @SerializedName("strIngredient13") val ingredient13: String? = null,
    @SerializedName("strIngredient14") val ingredient14: String? = null,
    @SerializedName("strIngredient15") val ingredient15: String? = null,
    @SerializedName("strMeasure1") val measure1: String? = null,
    @SerializedName("strMeasure2") val measure2: String? = null,
    @SerializedName("strMeasure3") val measure3: String? = null,
    @SerializedName("strMeasure4") val measure4: String? = null,
    @SerializedName("strMeasure5") val measure5: String? = null,
    @SerializedName("strMeasure6") val measure6: String? = null,
    @SerializedName("strMeasure7") val measure7: String? = null,
    @SerializedName("strMeasure8") val measure8: String? = null,
    @SerializedName("strMeasure9") val measure9: String? = null,
    @SerializedName("strMeasure10") val measure10: String? = null,
    @SerializedName("strMeasure11") val measure11: String? = null,
    @SerializedName("strMeasure12") val measure12: String? = null,
    @SerializedName("strMeasure13") val measure13: String? = null,
    @SerializedName("strMeasure14") val measure14: String? = null,
    @SerializedName("strMeasure15") val measure15: String? = null,
    @SerializedName("strSource") val strSource: String? = null,
    @SerializedName("strImageSource") val strImageSource: String? = null,
    @SerializedName("strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String? = null,
    @SerializedName("dateModified") val dateModified: String? = null
) {
    fun getMealNameForQueryParam(): String? {
        val sb = StringBuilder()
        categoryName?.split(" ")?.forEach {
            sb.append("${it}_")
        }
        sb.removeSuffix("_")
        return sb.toString().ifBlank { null }
    }

    fun getMealThumbnail(): String? {
        return if (thumbnailImage.isNullOrBlank()) null else "$thumbnailImage/preview"
    }

    fun getMealImage(): String? {
        return thumbnailImage
    }

    fun getIngredients(): List<Ingredient> {
        val list = mutableListOf<Ingredient>()
        try {
            ingredient1?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure1!!)) }
            ingredient2?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure2!!)) }
            ingredient3?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure3!!)) }
            ingredient4?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure4!!)) }
            ingredient5?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure5!!)) }
            ingredient6?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure6!!)) }
            ingredient7?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure7!!)) }
            ingredient8?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure8!!)) }
            ingredient9?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure9!!)) }
            ingredient10?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure10!!)) }
            ingredient11?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure11!!)) }
            ingredient12?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure12!!)) }
            ingredient13?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure13!!)) }
            ingredient14?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure14!!)) }
            ingredient15?.let { if (it.isNotBlank()) list.add(Ingredient(it, measure15!!)) }
        } catch (e: Exception) {
            Log.e("Conversion Failed:", "Measure for one of the ingredients is null. \n $this")
        }
        return list
    }
}