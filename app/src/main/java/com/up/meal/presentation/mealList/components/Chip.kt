package com.up.meal.presentation.mealList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.meal.domain.model.Category
import com.up.meal.presentation.ui.theme.MealAppTheme

@Composable
fun Chip(
    category: Category,
    onClick: () -> Unit
) {
    val modifier = if (category.isSelected) {
        Modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(8.dp)
    } else {
        Modifier
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(8.dp)
    }
    val textColor = if (category.isSelected) Color.White else Color.Black
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = category.categoryName,
            color = textColor,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun HorizontalChipList(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onChipClicked: (category: Category) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Chip(category = category) {
                onChipClicked(category)
            }
        }
    }
}

@Preview()
@Composable
fun PreviewChip() {
    MealAppTheme {
        HorizontalChipList(categories = categories) {}
    }
}

val categories = listOf(
    Category("Vegetarian", "Vegetarian", false),
    Category("Chicken", "Chicken", false),
)