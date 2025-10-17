package com.example.cookease.data.repository

import com.example.cookease.data.model.ApiRecipe
import com.example.cookease.data.model.ApiRecipeResponse
import com.example.cookease.data.remote.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApiRepository {
    private val client = KtorClient.client
    private val baseUrl = "https://www.themealdb.com/api/json/v1/1"

    suspend fun searchRecipes(query: String): List<ApiRecipe> {
        return try {
            val response: ApiRecipeResponse = client.get("$baseUrl/search.php?s=$query").body()
            response.meals ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getRecipesByCategory(category: String): List<ApiRecipe> {
        return try {
            val response: ApiRecipeResponse = client.get("$baseUrl/filter.php?c=$category").body()
            response.meals ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getRecipeById(id: String): ApiRecipe? {
        return try {
            val response: ApiRecipeResponse = client.get("$baseUrl/lookup.php?i=$id").body()
            response.meals?.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}