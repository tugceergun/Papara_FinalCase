package com.example.final_case.presentation.mealList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_case.common.Resource
import com.example.final_case.domain.model.Category
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.domain.usecase.getCategories.GetMealCategoriesUseCase
import com.example.final_case.domain.usecase.getMealsByCategory.GetMealCardsByCategoryUseCase
import com.example.final_case.domain.usecase.getMealsByName.GetMealsByNameUseCase
import com.example.final_case.presentation.mealList.model.MealSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
    private val getMealsByNameUseCase: GetMealsByNameUseCase,
    private val getMealCategoriesUseCase: GetMealCategoriesUseCase,
    private val getMealCardsByCategoryUseCase: GetMealCardsByCategoryUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _mealListViewState: MutableState<MealListViewState> = mutableStateOf(
        MealListViewState()
    )
    val mealListViewState: State<MealListViewState> get() = _mealListViewState

    private var localMealSections: List<MealSection>? = null
    private var getMealsByNameJob: Job? = null

    init {
        getMealSections()
    }

    fun showMealSections() {
        getMealsByNameJob?.cancel()
        if (localMealSections == null) {
            viewModelScope.launch {
                getMealSections()
            }
        } else {
            _mealListViewState.value = MealListViewState(mealSections = localMealSections)
        }
    }

    private fun saveMealSectionsResponse(mealSections: List<MealSection>) {
        localMealSections = mealSections
    }

    private fun getMealCategories() {
        viewModelScope.launch {
            getMealCategoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _mealListViewState.value = MealListViewState(categories = result.data)
                    }
                    is Resource.Error -> {
                        _mealListViewState.value = MealListViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _mealListViewState.value = MealListViewState(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getMealSections() = viewModelScope.launch {
        getMealCategoriesUseCase.invoke().collect() { result ->
            when (result) {
                is Resource.Success -> getMealsByCategories(result.data)
                is Resource.Error -> _mealListViewState.value =
                    MealListViewState(error = result.message)
                is Resource.Loading -> _mealListViewState.value =
                    MealListViewState(isLoading = true)
            }
        }
    }

    private suspend fun getMealsByCategories(categories: List<Category>?) {
        if (categories == null) return
        val mealSections = mutableListOf<MealSection>()
        categories.forEach { category ->
            val cards = getMealCardsByCategorySuspend(category.queryParam)
            if (!cards.isNullOrEmpty()) {
                mealSections.add(
                    MealSection(
                        category,
                        cards
                    )
                )
            }
        }
        saveMealSectionsResponse(mealSections)
        _mealListViewState.value = MealListViewState(mealSections = mealSections)
    }

    private suspend fun getMealCardsByCategorySuspend(category: String): List<FoodCard>? =
        withContext(viewModelScope.coroutineContext) {
            var mealCards: List<FoodCard>? = null
            getMealCardsByCategoryUseCase.invoke(category).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        mealCards = result.data
                    }
                    else -> {}
                }
            }
            mealCards
        }

    fun getMealCardsByCategory(category: String?) {
        viewModelScope.launch {
            getMealCardsByCategoryUseCase.invoke(category ?: DEFAULT_CATEGORY)
                .collect() { result ->
                    when (result) {
                        is Resource.Success -> {
                            _mealListViewState.value =
                                MealListViewState(searchMealCards = result.data)
                        }
                        is Resource.Error -> {
                            _mealListViewState.value = MealListViewState(error = result.message)
                        }
                        is Resource.Loading -> {
                            _mealListViewState.value = MealListViewState(isLoading = true)
                        }
                    }
                }
        }
    }


    fun getMealsBasedOnName(mealName: String) {
        getMealsByNameJob?.cancel("Another search request made by user!")
        getMealsByNameJob = viewModelScope.launch(dispatcher) {
            getMealsByNameUseCase.invoke(mealName).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _mealListViewState.value =
                            MealListViewState(searchMealCards = result.data)
                    }
                    is Resource.Error -> {
                        _mealListViewState.value = MealListViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _mealListViewState.value = MealListViewState(isLoading = true)
                    }
                }
            }
        }
        _mealListViewState.value = MealListViewState(isLoading = true)
    }

    fun updateChipSelection(category: Category) {
        val updatedCategories = mutableListOf<Category>()
        mealListViewState.value.categories?.forEach {
            if (category.categoryName == it.categoryName) {
                updatedCategories.add(it.copy(isSelected = true))
            } else {
                updatedCategories.add(it.copy(isSelected = false))
            }
        }
        _mealListViewState.value = mealListViewState.value.copy(categories = updatedCategories)
    }

    companion object {
        const val DEFAULT_CATEGORY = "ordinary_meal"
    }
}