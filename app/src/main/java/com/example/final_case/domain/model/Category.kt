package com.example.final_case.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val categoryName: String,
    val queryParam: String,
    var isSelected: Boolean = false
): Parcelable
