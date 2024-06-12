package com.up.meal.presentation.mealList

import android.os.Parcelable
import androidx.annotation.Keep
import com.up.meal.domain.model.Category
import com.up.meal.domain.model.MealCard
import com.up.meal.presentation.mealList.model.MealSection
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MealListViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchMealCards: List<MealCard>? = null,
    val categories: List<Category>? = null,
    val mealSections: List<MealSection>? = null
) : Parcelable