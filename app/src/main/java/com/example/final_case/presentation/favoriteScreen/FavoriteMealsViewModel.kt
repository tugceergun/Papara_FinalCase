package com.example.final_case.presentation.favoriteScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.domain.usecase.getMealCardsFromFavorite.GetMealCardsFromFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMealsViewModel @Inject constructor(
    private val getAllMealCardsFromFavoritesUseCase: GetMealCardsFromFavorites,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _favoriteMeals: MutableState<List<FoodCard>?> = mutableStateOf(null)
    val favoriteDrinks: State<List<FoodCard>?> get() = _favoriteMeals
    private var localFavoriteMeals: List<FoodCard>? = null

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
                getAllMealCardsFromFavoritesUseCase.invoke().collect() {
                    saveFavoriteMealsResponse(it)
                    _favoriteMeals.value = it
                }
            }
        } else {
            _favoriteMeals.value = localFavoriteMeals
        }
    }

    private fun saveFavoriteMealsResponse(favoriteMeals: List<FoodCard>) {
        localFavoriteMeals = favoriteMeals
    }
}