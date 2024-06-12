package com.up.meal.presentation.favoriteScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.meal.domain.model.MealCard
import com.up.meal.domain.usecase.getMealCardsFromFavorite.GetMealsCardsFromFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMealViewModel @Inject constructor(
    private val getAllMealsFromFavoritesUseCase: GetMealsCardsFromFavorites,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _favoriteMeals: MutableState<List<MealCard>?> = mutableStateOf(null)
    val favoriteMeals: State<List<MealCard>?> get() = _favoriteMeals
    private var localFavoriteMeals: List<MealCard>? = null

    init {
        getFavoriteMealsSection()
    }

    fun hideFavoriteSection() {
        _favoriteMeals.value = null
    }

    fun showFavoritesSection() {
        if (localFavoriteMeals == null) {
            viewModelScope.launch {
                getFavoriteMealsSection()
            }
        } else {
            _favoriteMeals.value = localFavoriteMeals
        }
    }

    private fun getFavoriteMealsSection() {
        if (localFavoriteMeals == null) {
            viewModelScope.launch {
                getAllMealsFromFavoritesUseCase.invoke().collect() {
                    saveFavoriteMealsResponse(it)
                    _favoriteMeals.value = it
                }
            }
        } else {
            _favoriteMeals.value = localFavoriteMeals
        }
    }

    private fun saveFavoriteMealsResponse(favoriteMeals: List<MealCard>) {
        localFavoriteMeals = favoriteMeals
    }
}