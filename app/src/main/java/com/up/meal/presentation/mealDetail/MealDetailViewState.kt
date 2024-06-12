package com.up.meal.presentation.mealDetail

import android.os.Parcelable
import androidx.annotation.Keep
import com.up.meal.domain.model.MealDetail
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MealDetailViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val mealDetail: MealDetail? = null
) : Parcelable