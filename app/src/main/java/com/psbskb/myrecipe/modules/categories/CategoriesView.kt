package com.psbskb.myrecipe.modules.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.psbskb.myrecipe.data.models.RecipeCategory
import com.psbskb.myrecipe.modules.meals.MealsViewModel

val categoriesViewModel: CategoriesViewModel = CategoriesViewModel()
val mealsViewModel: MealsViewModel = MealsViewModel()

@Composable
fun CategoriesView(navController: NavController) {
    Box(modifier = Modifier.safeDrawingPadding()) {
        CategoriesWidget(navController)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoriesWidget(navController: NavController) {
    val viewState by categoriesViewModel.recipeCategoriesState
    println("recipe_log $viewState")
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter("file:///android_asset/my_recipe.png"),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .aspectRatio(1f)
            )
            Text(text = "My Recipe", modifier = Modifier.padding(8.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                viewState.error != null -> {
                    Text(text = viewState.error.toString())
                }

                else -> {
                    CategoriesList(navController, recipeCategories = viewState.categoriesList)
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoriesList(navController: NavController, recipeCategories: List<RecipeCategory>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(recipeCategories) { recipeCategory ->
            Box(Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        mealsViewModel.updateName(recipeCategory.strCategory)
                        mealsViewModel.fetchRecipeCategories(recipeCategory.strCategory)
                        navController.navigate("meals")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Gray.copy(alpha = 0.3f)),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberImagePainter(recipeCategory.strCategoryThumb),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                        )
                        Text(text = recipeCategory.strCategory, color = Color.Black)
                    }
                }

            }
        }
    }
}


