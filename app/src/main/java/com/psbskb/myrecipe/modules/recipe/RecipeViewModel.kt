package com.psbskb.myrecipe.modules.recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psbskb.myrecipe.api.recipeService
import com.psbskb.myrecipe.data.models.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipeState = mutableStateOf(RecipeState())
    val recipeState: State<RecipeState> = _recipeState


    fun fetchRecipe(id: Int) {
        _recipeState.value = _recipeState.value.copy(
            loading = true,
            recipe = null,
            error = null,
        )
        viewModelScope.launch {
            try {
                println("recipe_log start")
                val response = recipeService.getRecipe(id)
                println("recipe_log $response")
                _recipeState.value = _recipeState.value.copy(
                    loading = false,
                    recipe = response.meals.first(),
                    error = null,
                )
            } catch (e: Exception) {
                _recipeState.value = _recipeState.value.copy(
                    loading = false,
                    recipe = null,
                    error = e.message,
                )
            }
        }
    }
}


data class RecipeState(
    val loading: Boolean = true,
    val recipe: Recipe? = null,
    val error: String? = null
)