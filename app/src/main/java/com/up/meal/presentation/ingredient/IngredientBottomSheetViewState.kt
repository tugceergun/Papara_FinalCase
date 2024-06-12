package com.up.meal.presentation.ingredient

import androidx.annotation.Keep
import com.up.meal.domain.model.IngredientDetail

@Keep
data class IngredientBottomSheetViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val ingredientDetail: IngredientDetail? = null
)
