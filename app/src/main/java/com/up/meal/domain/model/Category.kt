package com.up.meal.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Category(
    val categoryName: String,
    val queryParam: String,
    var isSelected: Boolean = false
): Parcelable