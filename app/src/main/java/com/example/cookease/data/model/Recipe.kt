package com.example.cookease.data.model

import com.google.firebase.firestore.DocumentId

data class Recipe(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: String = "",
    val category: String = "",
    val area: String = "",
    val imageUrl: String = "",
    val createdBy: String = "", // userId
    val createdAt: Long = System.currentTimeMillis(),
    val isCustom: Boolean = true, // true for user-created, false for API recipes
    val apiId: String? = null // if saved from API
)
