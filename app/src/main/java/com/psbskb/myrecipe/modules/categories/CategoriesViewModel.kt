package com.psbskb.myrecipe.modules.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psbskb.myrecipe.api.recipeService
import com.psbskb.myrecipe.data.models.RecipeCategory
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {
    private val _recipeCategoryState = mutableStateOf(RecipeCategoriesState())
    val recipeCategoriesState : State<RecipeCategoriesState> = _recipeCategoryState

    init {
        fetchRecipeCategories()
    }

    private fun fetchRecipeCategories(){
        viewModelScope.launch {
            try {
                println("recipe_log start")
                val response = recipeService.getRecipeCategories()
                println("recipe_log $response")
                _recipeCategoryState.value = _recipeCategoryState.value.copy(
                    loading = false,
                    categoriesList = response.categories,
                    error = null,
                )
            }catch (e: Exception){
                _recipeCategoryState.value = _recipeCategoryState.value.copy(
                    loading = false,
                    categoriesList = emptyList(),
                    error = e.message,
                )
            }
        }
    }

    data class RecipeCategoriesState(
        val loading: Boolean = true,
        val categoriesList: List<RecipeCategory> = emptyList(),
        val error: String? = null
    )
}