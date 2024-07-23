package com.psbskb.myrecipe.modules.meals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.psbskb.myrecipe.data.models.Meal
import com.psbskb.myrecipe.modules.categories.mealsViewModel
import com.psbskb.myrecipe.modules.meals.handlers.onClickMeal
import com.psbskb.myrecipe.modules.recipe.RecipeViewModel


val recipeViewModel: RecipeViewModel = RecipeViewModel()

@Composable
fun MealsView(navController: NavController) {
    Box(modifier = Modifier.safeDrawingPadding()) {
        MealsWidget(navController)
    }
}

@Composable
fun MealsWidget(navController: NavController) {
    val viewState by mealsViewModel.mealsDataState
    println("recipe_log $viewState")
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
            Text(text = mealsViewModel.name.value)
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
                    MealsList(navController, meals = viewState.meals)
                }
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun MealsList(navController: NavController, meals: List<Meal>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(meals) { meal ->
            Box(Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        onClickMeal(navController, meal.idMeal)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Gray.copy(alpha = 0.3f)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 8.dp)
                            .height(100.dp)
                    ) {
                        Box() {
                            Image(
                                painter = rememberImagePainter(meal.strMealThumb),
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10))
                            )
                        }
                        Box(modifier = Modifier.width(16.dp)) {}
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = meal.strMeal, color = Color.Black,
                            )
                            Box(Modifier.height(8.dp)) {}
                            Button(
                                onClick = { onClickMeal(navController, meal.idMeal) },
                                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(25.dp)
                            ) {
                                Text(
                                    text = "Full Recipe",
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
