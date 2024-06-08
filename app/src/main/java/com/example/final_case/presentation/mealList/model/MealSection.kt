package com.example.final_case.presentation.mealList.model

import android.os.Parcelable
import com.example.final_case.domain.model.Category
import com.example.final_case.domain.model.FoodCard
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealSection(
    val category: Category,
    val list: List<FoodCard>
): Parcelable