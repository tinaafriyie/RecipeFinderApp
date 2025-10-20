package com.example.cookease.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookease.data.model.ApiRecipe
import com.example.cookease.data.model.Recipe
import com.example.cookease.data.repository.ApiRepository
import com.example.cookease.data.repository.RecipeRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val repository = RecipeRepository()
    private val apiRepository = ApiRepository()
    private val analytics = Firebase.analytics

    private val _userRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val userRecipes: StateFlow<List<Recipe>> = _userRecipes

    var apiRecipes = mutableStateOf<List<ApiRecipe>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    fun loadUserRecipes(userId: String) {
        viewModelScope.launch {
            repository.getRecipesByUser(userId).collect {
                _userRecipes.value = it
            }
        }
    }

    fun searchApiRecipes(query: String) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                apiRecipes.value = apiRepository.searchRecipes(query)
                analytics.logEvent("search_recipes", null)
            } catch (e: Exception) {
                error.value = e.message
            }

            isLoading.value = false
        }
    }

    fun addRecipe(recipe: Recipe, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.addRecipe(recipe)
                .onSuccess {
                    analytics.logEvent("add_recipe", null)
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    fun updateRecipe(recipeId: String, recipe: Recipe, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.updateRecipe(recipeId, recipe)
                .onSuccess {
                    analytics.logEvent("update_recipe", null)
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    fun deleteRecipe(recipeId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.deleteRecipe(recipeId)
                .onSuccess {
                    analytics.logEvent("delete_recipe", null)
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    suspend fun getRecipeById(recipeId: String): Result<Recipe> {
        return try {
            isLoading.value = true
            error.value = null
            repository.getRecipeById(recipeId)
        } catch (e: Exception) {
            error.value = e.message
            Result.failure(e)
        } finally {
            isLoading.value = false
        }
    }



    fun RecipeViewModel.getRecipeById(recipeId: String): kotlinx.coroutines.Deferred<Result<com.example.cookease.data.model.Recipe>> {
        return viewModelScope.async {
            try {
                isLoading.value = true
                repository.getRecipeById(recipeId)
            } catch (e: Exception) {
                Result.failure(e)
            } finally {
                isLoading.value = false
            }
        }
    }
}