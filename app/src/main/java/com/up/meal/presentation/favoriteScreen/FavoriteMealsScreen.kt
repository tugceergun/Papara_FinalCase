package com.up.meal.presentation.favoriteScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.up.meal.presentation.mealList.components.MealCard
import com.up.meal.presentation.ui.theme.TopAppBarDarkBackground

@Composable
fun FavoriteMealsScreen(
    viewModel: FavoriteMealViewModel,
    onMealCardClicked: (mealId: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val favoriteMeals = viewModel.favoriteMeals

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = { onBackPressed() }
                        )
                )
            },
            title = {
                Text(
                    text = "Favorites",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                )
            },
            backgroundColor = if (isSystemInDarkTheme()) TopAppBarDarkBackground else MaterialTheme.colors.background,
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (!favoriteMeals.value.isNullOrEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    favoriteMeals.value?.let {
                        items(it) { meal ->
                            MealCard(mealCard = meal, onClick = onMealCardClicked)
                        }
                    }
                }
            } else {
                Text(
                    text = "You don't have any favorites add one now!",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}