package com.psbskb.myrecipe.modules.meals.handlers

import androidx.navigation.NavController
import com.psbskb.myrecipe.modules.meals.recipeViewModel

fun onClickMeal(navController: NavController, idMeal: Int) {
    recipeViewModel.fetchRecipe(id = idMeal)
    navController.navigate("recipe")
}