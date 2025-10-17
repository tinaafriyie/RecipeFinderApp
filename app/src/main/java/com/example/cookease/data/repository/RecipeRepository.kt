package com.example.cookease.data.repository

//suspend fun RecipeRepository.getRecipeById(recipeId: String): Result<com.example.cookease.data.model.Recipe> {
//    return try {
//        val document = com.google.firebase.firestore.FirebaseFirestore.getInstance()
//            .collection("recipes")
//            .document(recipeId)
//            .get()
//            .await()
//
//        val recipe = document.toObject(com.example.cookease.data.model.Recipe::class.java)
//        if (recipe != null) {
//            Result.success(recipe.copy(id = document.id))
//        } else {
//            Result.failure(Exception("Recipe not found"))
//        }
//    } catch (e: Exception) {
//        Result.failure(e)
//    }
//}


import com.example.cookease.data.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RecipeRepository {
    private val db = FirebaseFirestore.getInstance()
    private val recipesCollection = db.collection("recipes")

    init {
        // Enable offline persistence
        db.firestoreSettings = com.google.firebase.firestore.FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    // CREATE
    suspend fun addRecipe(recipe: Recipe): Result<String> {
        return try {
            val docRef = recipesCollection.add(recipe).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // READ (Real-time updates)
    fun getRecipesByUser(userId: String): Flow<List<Recipe>> = callbackFlow {
        val listener = recipesCollection
            .whereEqualTo("createdBy", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val recipes = snapshot?.toObjects(Recipe::class.java) ?: emptyList()
                trySend(recipes)
            }
        awaitClose { listener.remove() }
    }

    // READ ALL (Real-time updates)
    fun getAllRecipes(): Flow<List<Recipe>> = callbackFlow {
        val listener = recipesCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val recipes = snapshot?.toObjects(Recipe::class.java) ?: emptyList()
                trySend(recipes)
            }
        awaitClose { listener.remove() }
    }

    // READ ONE
    suspend fun getRecipeById(recipeId: String): Result<Recipe> {
        return try {
            val document = recipesCollection.document(recipeId).get().await()
            val recipe = document.toObject(Recipe::class.java)
            if (recipe != null) {
                Result.success(recipe)
            } else {
                Result.failure(Exception("Recipe not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // UPDATE
    suspend fun updateRecipe(recipeId: String, recipe: Recipe): Result<Unit> {
        return try {
            recipesCollection.document(recipeId).set(recipe).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // DELETE
    suspend fun deleteRecipe(recipeId: String): Result<Unit> {
        return try {
            recipesCollection.document(recipeId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun RecipeRepository.getRecipeById(recipeId: String): Result<com.example.cookease.data.model.Recipe> {
        return try {
            val document = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                .collection("recipes")
                .document(recipeId)
                .get()
                .await()

            val recipe = document.toObject(com.example.cookease.data.model.Recipe::class.java)
            if (recipe != null) {
                Result.success(recipe.copy(id = document.id))
            } else {
                Result.failure(Exception("Recipe not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
