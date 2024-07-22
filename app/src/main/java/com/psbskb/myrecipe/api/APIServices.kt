package com.psbskb.myrecipe.api

import com.psbskb.myrecipe.data.models.RecipeCategory
import com.psbskb.myrecipe.data.responses.MealsResponse
import com.psbskb.myrecipe.data.responses.RecipeCategoriesResponse
import com.psbskb.myrecipe.data.responses.RecipeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create()).build()
val recipeService: APIServices = retrofit.create(APIServices::class.java)

interface APIServices {
    @GET("categories.php")
    suspend fun getRecipeCategories(): RecipeCategoriesResponse

    @GET("filter.php")
    suspend fun getMeals(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getRecipe(@Query("i") id: Int): RecipeResponse
}