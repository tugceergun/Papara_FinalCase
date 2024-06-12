package com.up.meal.presentation.mealDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.meal.common.Resource
import com.up.meal.domain.model.MealCard
import com.up.meal.domain.model.MealDetail
import com.up.meal.domain.usecase.addMealCardToFavorite.AddMealCardToFavoriteUseCase
import com.up.meal.domain.usecase.getMealDetail.GetMealDetailUseCase
import com.up.meal.domain.usecase.isMealCardFavorite.IsMealCardFavoriteUseCase
import com.up.meal.domain.usecase.removeMealCardFromFavorite.RemoveMealCardFromFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getMealDetailUseCase: GetMealDetailUseCase,
    private val removeMealCardFromFavoriteUseCase: RemoveMealCardFromFavorite,
    private val addMealCardToFavoriteUseCase: AddMealCardToFavoriteUseCase,
    private val isMealCardFavoriteUseCase: IsMealCardFavoriteUseCase
) : ViewModel() {

    private val _mealDetailViewState: MutableState<MealDetailViewState> =
        mutableStateOf(MealDetailViewState())
    val mealDetailViewState: State<MealDetailViewState> get() = _mealDetailViewState

    private val _isMealAddedAsFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isMealAddedAsFavorite: State<Boolean> = _isMealAddedAsFavorite

    private var currentMealDetail: MealDetail? = null

    private fun getMealCardFromMealDetail(): MealCard {
        return MealCard(
            currentMealDetail?.id ?: "",
            currentMealDetail?.mealName ?: "Unknown meal",
            currentMealDetail?.thumbnail
                ?: "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
            currentMealDetail?.image
                ?: "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview"
        )
    }

    fun getMealDetailsById(mealId: String) {
        viewModelScope.launch {
            getMealDetailUseCase.invoke(mealId).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _mealDetailViewState.value =
                            MealDetailViewState(mealDetail = result.data)
                        currentMealDetail = result.data
                    }

                    is Resource.Error -> {
                        _mealDetailViewState.value = MealDetailViewState(error = result.message)
                    }

                    is Resource.Loading -> {
                        _mealDetailViewState.value = MealDetailViewState(isLoading = true)
                    }
                }
            }
        }
    }

    fun addMealCardToFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            addMealCardToFavoriteUseCase.invoke(getMealCardFromMealDetail()).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isMealAddedAsFavorite.value = true
                    }

                    is Resource.Error -> {
                        _mealDetailViewState.value = MealDetailViewState(error = result.message)
                    }

                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }

    fun removeMealCardFromFavorite(mealId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeMealCardFromFavoriteUseCase.invoke(mealId).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isMealAddedAsFavorite.value = false
                    }

                    is Resource.Error -> {
                        _mealDetailViewState.value = MealDetailViewState(error = result.message)
                    }

                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }

    fun isMealCardAddedToFavorites(id: String) {
        viewModelScope.launch {
            isMealCardFavoriteUseCase.invoke(id).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isMealAddedAsFavorite.value = result.data ?: false
                    }

                    is Resource.Error -> {
                        _mealDetailViewState.value = MealDetailViewState(error = result.message)
                    }

                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }
}