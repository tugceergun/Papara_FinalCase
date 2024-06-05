package com.example.final_case.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodDetail(
    val id: String,
    val foodName: String,
    val thumbnail: String,
    val image: String,
    val instructions: String,
    val ingredients: List<Ingredient>
): Parcelable

@Parcelize
data class Ingredient(
    val name: String,
    val amount: String
): Parcelable