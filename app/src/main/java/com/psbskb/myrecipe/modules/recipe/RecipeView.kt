package com.psbskb.myrecipe.modules.recipe

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.psbskb.myrecipe.data.models.Recipe
import com.psbskb.myrecipe.modules.categories.mealsViewModel
import com.psbskb.myrecipe.modules.meals.recipeViewModel

val recipeVideoPlayerModel: RecipeVideoPlayerModel = RecipeVideoPlayerModel()

@Composable
fun RecipeView(navController: NavController) {
    val viewState by recipeViewModel.recipeState
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
                if (viewState.recipe != null) {
                    RecipeWidget(
                        navController,
                        recipe = viewState.recipe!!
                    )
                } else {
                    Box {

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeWidget(navController: NavController, recipe: Recipe) {
    Box(Modifier.padding(0.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box() {
                Box {
                    if (recipeVideoPlayerModel.isVideoPlaying.value) Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .align(Alignment.Center)
                            .background(color = Color.Black)
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            RecipeVideoPlayer(
                                videoId = "v=4aZr5hZXP_s",
                                lifecycleOwner = LocalLifecycleOwner.current
                            )
                        }
                    }
                    else {
                        Box {
                            Image(
                                painter = rememberImagePainter(recipe.strMealThumb),
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(1f)
                            )
                        }
                        IconButton(
                            onClick = { recipeVideoPlayerModel.changeVideState() },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .width(50.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                    Box(
                        Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    ) {
                        Box(Modifier.safeDrawingPadding()) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White,

                                    )
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)

                        ) {
                            Text(
                                text = recipe.strMeal, color = Color.White, fontSize = 30.sp
                            )
                            Text(
                                text = recipe.strArea, color = Color.White,
                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.width(16.dp)) {}
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(ScrollState(initial = 0))
                    .weight(weight = 1f, fill = false)
            ) {
                Text(
                    text = recipe.strInstructions, color = Color.Black,
                )
            }
        }

    }
}


