package com.up.meal.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class NetworkUrlPreviewProvider : PreviewParameterProvider<String> {
    override val count: Int
        get() = super.count
    override val values: Sequence<String>
        get() = sequenceOf("https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview")
}