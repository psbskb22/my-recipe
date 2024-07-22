package com.psbskb.myrecipe.modules.meals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psbskb.myrecipe.api.recipeService
import com.psbskb.myrecipe.data.models.Meal
import com.psbskb.myrecipe.data.models.RecipeCategory
import kotlinx.coroutines.launch

class MealsViewModel : ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _mealsDataState = mutableStateOf(MealsDataState())
    val mealsDataState: State<MealsDataState> = _mealsDataState

    fun updateName(name: String) {
        _name.value = name
    }

    fun fetchRecipeCategories(category: String) {
        _mealsDataState.value = _mealsDataState.value.copy(
            loading = true,
            meals = emptyList(),
            error = null,
        )
        viewModelScope.launch {
            try {
                println("recipe_log start")
                val response = recipeService.getMeals(category)
                println("recipe_log $response")
                _mealsDataState.value = _mealsDataState.value.copy(
                    loading = false,
                    meals = response.meals,
                    error = null,
                )
            } catch (e: Exception) {
                _mealsDataState.value = _mealsDataState.value.copy(
                    loading = false,
                    meals = emptyList(),
                    error = e.message,
                )
            }
        }
    }

    data class MealsDataState(
        val loading: Boolean = true,
        val meals: List<Meal> = emptyList(),
        val error: String? = null
    )
}