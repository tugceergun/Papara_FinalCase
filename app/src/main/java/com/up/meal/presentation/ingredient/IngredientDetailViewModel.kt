package com.up.meal.presentation.ingredient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.meal.common.Resource
import com.up.meal.domain.usecase.getIngredientDetails.GetIngredientDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
    private val getIngredientDetailsUseCase: GetIngredientDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _ingredientBottomSheetViewState = mutableStateOf(IngredientBottomSheetViewState())
    val ingredientBottomSheetViewState: State<IngredientBottomSheetViewState> get() = _ingredientBottomSheetViewState

    fun getIngredientDetails(ingredientName: String) {
        viewModelScope.launch {
            getIngredientDetailsUseCase.invoke(ingredientName).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _ingredientBottomSheetViewState.value = IngredientBottomSheetViewState(
                            isLoading = false,
                            ingredientDetail = result.data
                        )
                    }

                    is Resource.Loading -> {
                        _ingredientBottomSheetViewState.value =
                            IngredientBottomSheetViewState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _ingredientBottomSheetViewState.value =
                            IngredientBottomSheetViewState(error = result.message)
                    }
                }
            }
        }
    }

}