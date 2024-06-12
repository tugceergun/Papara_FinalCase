package com.up.meal.presentation.mealList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.meal.common.Resource
import com.up.meal.domain.model.Category
import com.up.meal.domain.model.MealCard
import com.up.meal.domain.usecase.getCategories.GetMealCategoriesUseCase
import com.up.meal.domain.usecase.getMealByName.GetMealByNameUseCase
import com.up.meal.domain.usecase.getMealsByCategory.GetMealCardsByCategoryUseCase
import com.up.meal.presentation.mealList.model.MealSection
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
    private val getMealByNameUseCase: GetMealByNameUseCase,
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
        getMealsSections()
    }

    fun showMealSections() {
        getMealsByNameJob?.cancel()
        if (localMealSections == null) {
            viewModelScope.launch {
                getMealsSections()
            }
        } else {
            _mealListViewState.value = MealListViewState(mealSections = localMealSections)
        }
    }

    private fun saveMealSectionsResponse(mealSections: List<MealSection>) {
        localMealSections = mealSections
    }

    private fun getMealsCategories() {
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

    private fun getMealsSections() = viewModelScope.launch {
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
            val cards = getMealsCardsByCategorySuspend(category.queryParam)
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

    private suspend fun getMealsCardsByCategorySuspend(category: String): List<MealCard>? =
        withContext(viewModelScope.coroutineContext) {
            var mealsCards: List<MealCard>? = null
            getMealCardsByCategoryUseCase.invoke(category).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        mealsCards = result.data
                    }

                    else -> {}
                }
            }
            mealsCards
        }

    fun getMealsCardsByCategory(category: String?) {
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
            getMealByNameUseCase.invoke(mealName).collect() { result ->
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
        const val DEFAULT_CATEGORY = "Vegetarian"
    }
}