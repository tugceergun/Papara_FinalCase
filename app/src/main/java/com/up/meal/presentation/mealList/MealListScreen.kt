package com.up.meal.presentation.mealList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.up.meal.R
import com.up.meal.common.BackPressHandler
import com.up.meal.domain.model.Category
import com.up.meal.domain.model.MealCard
import com.up.meal.presentation.favoriteScreen.FavoriteMealViewModel
import com.up.meal.presentation.mealList.components.FavoriteMealSection
import com.up.meal.presentation.mealList.components.HorizontalChipList
import com.up.meal.presentation.mealList.components.MealCardsGrid
import com.up.meal.presentation.mealList.components.MealSection
import com.up.meal.presentation.mealList.components.SearchBar
import com.up.meal.presentation.mealList.model.MealSection

@Composable
fun MealListScreen(
    viewModel: MealListViewModel,
    favoriteMealViewModel: FavoriteMealViewModel,
    onMealCardClicked: (mealId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    val (text, changeSearchBarText) = rememberSaveable {
        mutableStateOf("")
    }
    val viewState = rememberSaveable {
        viewModel.mealListViewState
    }
    val favoriteMeals = rememberSaveable {
        favoriteMealViewModel.favoriteMeals
    }

    val onBackPressedInCaseUserNavigatedViaSearch = {
        viewModel.showMealSections()
        favoriteMealViewModel.showFavoritesSection()
        changeSearchBarText("")
    }

    if (!viewState.value.searchMealCards.isNullOrEmpty()) {
        BackPressHandler {
            onBackPressedInCaseUserNavigatedViaSearch.invoke()
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MealListScreenContent(
                categories = null,
                searchMealsCards = viewState.value.searchMealCards,
                mealSections = viewState.value.mealSections,
                favoriteMeals = favoriteMeals.value,
                searchText = text,
                onSearchTextChanged = {
                    if (it.isNotEmpty()) {
                        favoriteMealViewModel.hideFavoriteSection()
                        viewModel.getMealsBasedOnName(it)
                    } else {
                        viewModel.showMealSections()
                        favoriteMealViewModel.showFavoritesSection()
                    }
                    changeSearchBarText(it)
                },
                onChipClicked = {
                    viewModel.updateChipSelection(it)
                    viewModel.getMealsCardsByCategory(it.queryParam)
                },
                onMealCardClicked = {
                    onMealCardClicked(it)
                },
                onViewAllClicked = onViewAllClicked
            )
            if (viewState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!viewState.value.error.isNullOrBlank()) {
                Text(
                    text = viewState.value.error!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
fun MealListScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    searchMealsCards: List<MealCard>?,
    mealSections: List<MealSection>?,
    favoriteMeals: List<MealCard>?,
    searchText: String,
    onSearchTextChanged: (mealName: String) -> Unit,
    onChipClicked: (category: Category) -> Unit,
    onMealCardClicked: (mealId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            SearchBar(
                placeHolder = R.string.search_placeholder,
                value = searchText,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
                onValueChange = {
                    onSearchTextChanged(it)
                }
            )
        }

        if (!favoriteMeals.isNullOrEmpty() && searchMealsCards == null) {
            item {
                FavoriteMealSection(
                    favoriteMeals = favoriteMeals,
                    onClick = {
                        onMealCardClicked(it)
                    },
                    onViewAllClicked = onViewAllClicked
                )
            }
        }

        if (categories != null) {
            item {
                HorizontalChipList(
                    categories = categories,
                    modifier = modifier,
                    onChipClicked = {
                        onChipClicked(it)
                    }
                )
            }
        }

        if (searchMealsCards != null) {
            item {
                MealCardsGrid(
                    mealsCards = searchMealsCards,
                    modifier = modifier,
                    onClick = {
                        onMealCardClicked(it)
                    }
                )
            }
        }

        if (mealSections != null) {
            items(mealSections) { section ->
                MealSection(
                    modifier = modifier,
                    mealSection = section,
                    onMealCardClicked = {
                        onMealCardClicked(it)
                    }
                )
            }
        }
    }
}