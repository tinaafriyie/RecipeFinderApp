package com.example.cookease.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavoritesRepository {
    private val db = FirebaseFirestore.getInstance()
    private val favoritesCollection = db.collection("favorites")

    suspend fun addToFavorites(userId: String, recipeId: String): Result<Unit> {
        return try {
            favoritesCollection
                .document("${userId}_${recipeId}")
                .set(mapOf(
                    "userId" to userId,
                    "recipeId" to recipeId,
                    "timestamp" to System.currentTimeMillis()
                ))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeFromFavorites(userId: String, recipeId: String): Result<Unit> {
        return try {
            favoritesCollection
                .document("${userId}_${recipeId}")
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun isFavorite(userId: String, recipeId: String): Boolean {
        return try {
            val doc = favoritesCollection
                .document("${userId}_${recipeId}")
                .get()
                .await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }
}