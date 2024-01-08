package com.example.myapplication3.ui

import com.example.myapplication3.ui.categories.CategoryResponse
import com.example.myapplication3.ui.ingredients.IngredientResponse
import com.example.myapplication3.ui.recipes.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list.php?i=list")
    fun getIngredients(): Call<IngredientResponse>

    @GET("list.php?c=list")
    fun getCategories(): Call<CategoryResponse>

    @GET("search.php")
    fun getRecipeDrink(@Query("s") searchTerm: String): Call<RecipeResponse>

    @GET("lookup.php")
    fun getDetails(@Query("i") id: String): Call<RecipeResponse>

    @GET("filter.php")
    fun getDetailsByCategory(@Query("c") category: String): Call<RecipeResponse>

    @GET("filter.php")
    fun getDetailsByIngredient(@Query("i") ingredient: String): Call<RecipeResponse>

}
