package com.up.meal.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MealDetail(
    val id: String,
    val mealName: String,
    val thumbnail: String,
    val image: String,
    val instructions: String,
    val ingredients: List<Ingredient>
) : Parcelable

@Keep
@Parcelize
data class Ingredient(
    val name: String,
    val amount: String
) : Parcelable
