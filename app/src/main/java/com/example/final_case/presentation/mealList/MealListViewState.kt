package com.example.final_case.presentation.mealList

import android.os.Parcelable
import com.example.final_case.domain.model.Category
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.presentation.mealList.model.MealSection
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealListViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchMealCards: List<FoodCard>? = null,
    val categories: List<Category>? = null,
    val mealSections: List<MealSection>? = null
) : Parcelable