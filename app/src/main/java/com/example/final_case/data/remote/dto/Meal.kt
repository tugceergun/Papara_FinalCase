package com.example.final_case.data.remote.dto

import android.util.Log
import com.example.final_case.domain.model.Ingredient
import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("dateModified")
    val dateModified: String? = null,
    @SerializedName("idMeal")
    val idMeal: String? = null,
    @SerializedName("strArea")
    val strArea: String? = null,
    @SerializedName("strCategory")
    val strCategory: String? = null,
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: String? = null,
    @SerializedName("strImageSource")
    val strImageSource: String? = null,
    @SerializedName("strIngredient1")
    val strIngredient1: String? = null,
    @SerializedName("strIngredient10")
    val strIngredient10: String? = null,
    @SerializedName("strIngredient11")
    val strIngredient11: String? = null,
    @SerializedName("strIngredient12")
    val strIngredient12: String? = null,
    @SerializedName("strIngredient13")
    val strIngredient13: String? = null,
    @SerializedName("strIngredient14")
    val strIngredient14: String? = null,
    @SerializedName("strIngredient15")
    val strIngredient15: String? = null,
    @SerializedName("strIngredient16")
    val strIngredient16: String? = null,
    @SerializedName("strIngredient17")
    val strIngredient17: String? = null,
    @SerializedName("strIngredient18")
    val strIngredient18: String? = null,
    @SerializedName("strIngredient19")
    val strIngredient19: String? = null,
    @SerializedName("strIngredient2")
    val strIngredient2: String? = null,
    @SerializedName("strIngredient20")
    val strIngredient20: String? = null,
    @SerializedName("strIngredient3")
    val strIngredient3: String? = null,
    @SerializedName("strIngredient4")
    val strIngredient4: String? = null,
    @SerializedName("strIngredient5")
    val strIngredient5: String? = null,
    @SerializedName("strIngredient6")
    val strIngredient6: String? = null,
    @SerializedName("strIngredient7")
    val strIngredient7: String? = null,
    @SerializedName("strIngredient8")
    val strIngredient8: String? = null,
    @SerializedName("strIngredient9")
    val strIngredient9: String? = null,
    @SerializedName("strInstructions")
    val strInstructions: String? = null,
    @SerializedName("strMeal")
    val strMeal: String? = null,
    @SerializedName("strMealThumb")
    val strThumbnailImage: String? = null,
    @SerializedName("strMeasure1")
    val strMeasure1: String? = null,
    @SerializedName("strMeasure10")
    val strMeasure10: String? = null,
    @SerializedName("strMeasure11")
    val strMeasure11: String? = null,
    @SerializedName("strMeasure12")
    val strMeasure12: String? = null,
    @SerializedName("strMeasure13")
    val strMeasure13: String? = null,
    @SerializedName("strMeasure14")
    val strMeasure14: String? = null,
    @SerializedName("strMeasure15")
    val strMeasure15: String? = null,
    @SerializedName("strMeasure16")
    val strMeasure16: String? = null,
    @SerializedName("strMeasure17")
    val strMeasure17: String? = null,
    @SerializedName("strMeasure18")
    val strMeasure18: String? = null,
    @SerializedName("strMeasure19")
    val strMeasure19: String? = null,
    @SerializedName("strMeasure2")
    val strMeasure2: String? = null,
    @SerializedName("strMeasure20")
    val strMeasure20: String? = null,
    @SerializedName("strMeasure3")
    val strMeasure3: String? = null,
    @SerializedName("strMeasure4")
    val strMeasure4: String? = null,
    @SerializedName("strMeasure5")
    val strMeasure5: String? = null,
    @SerializedName("strMeasure6")
    val strMeasure6: String? = null,
    @SerializedName("strMeasure7")
    val strMeasure7: String? = null,
    @SerializedName("strMeasure8")
    val strMeasure8: String? = null,
    @SerializedName("strMeasure9")
    val strMeasure9: String? = null,
    @SerializedName("strSource")
    val strSource: String? = null,
    @SerializedName("strTags")
    val strTags: String? = null,
    @SerializedName("strYoutube")
    val strYoutube: String? = null,
) {

    fun getMealNameForQueryParam(): String? {
        val sb = StringBuilder()
        strCategory?.split(" ")?.forEach {
            sb.append("${it}_")
        }
        sb.removeSuffix("_")
        return sb.toString().ifBlank { null }
    }

    fun getMealThumbnail(): String? {
        return if (strThumbnailImage.isNullOrBlank()) null else "$strThumbnailImage/preview"
    }

    fun getMealImage(): String? {
        return strThumbnailImage
    }

    fun getIngredients(): List<Ingredient> {
        val list = mutableListOf<Ingredient>()
        try {
            strIngredient1?.let { list.add(Ingredient(it, strMeasure1!!)) }
            strIngredient2?.let { list.add(Ingredient(it, strMeasure2!!)) }
            strIngredient3?.let { list.add(Ingredient(it, strMeasure3!!)) }
            strIngredient4?.let { list.add(Ingredient(it, strMeasure4!!)) }
            strIngredient5?.let { list.add(Ingredient(it, strMeasure5!!)) }
            strIngredient6?.let { list.add(Ingredient(it, strMeasure6!!)) }
            strIngredient7?.let { list.add(Ingredient(it, strMeasure7!!)) }
            strIngredient8?.let { list.add(Ingredient(it, strMeasure8!!)) }
            strIngredient9?.let { list.add(Ingredient(it, strMeasure9!!)) }
            strIngredient10?.let { list.add(Ingredient(it, strMeasure10!!)) }
            strIngredient11?.let { list.add(Ingredient(it, strMeasure11!!)) }
            strIngredient12?.let { list.add(Ingredient(it, strMeasure12!!)) }
            strIngredient13?.let { list.add(Ingredient(it, strMeasure13!!)) }
            strIngredient14?.let { list.add(Ingredient(it, strMeasure14!!)) }
            strIngredient15?.let { list.add(Ingredient(it, strMeasure15!!)) }
            strIngredient16?.let { list.add(Ingredient(it, strMeasure16!!)) }
            strIngredient17?.let { list.add(Ingredient(it, strMeasure17!!)) }
            strIngredient18?.let { list.add(Ingredient(it, strMeasure18!!)) }
            strIngredient19?.let { list.add(Ingredient(it, strMeasure19!!)) }
            strIngredient20?.let { list.add(Ingredient(it, strMeasure20!!)) }

        } catch (e: Exception) {
            Log.e("Conversion Failed:", "Measure for one of the ingredients is null. \n $this")
        }
        return list
    }

}