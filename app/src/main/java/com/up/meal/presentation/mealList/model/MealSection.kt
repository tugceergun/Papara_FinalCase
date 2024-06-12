package com.up.meal.presentation.mealList.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.up.meal.domain.model.Category
import com.up.meal.domain.model.MealCard
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MealSection(
    val category: Category,
    val list: List<MealCard>
) : Parcelable