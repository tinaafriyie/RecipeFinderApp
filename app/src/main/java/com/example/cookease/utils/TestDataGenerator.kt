package com.example.cookease.utils

import com.example.cookease.data.model.Recipe

object TestDataGenerator {
    fun generateSampleRecipes(userId: String, count: Int = 5): List<Recipe> {
        val sampleRecipes = listOf(
            Recipe(
                name = "Spaghetti Carbonara",
                description = "Classic Italian pasta dish",
                ingredients = listOf("400g spaghetti", "200g pancetta", "4 eggs", "100g parmesan", "Black pepper"),
                instructions = "Cook pasta. Fry pancetta. Mix eggs and cheese. Combine all with pasta water.",
                category = "Pasta",
                area = "Italian",
                imageUrl = "https://via.placeholder.com/400",
                createdBy = userId,
                isCustom = true
            ),
            Recipe(
                name = "Chicken Tikka Masala",
                description = "Creamy Indian curry",
                ingredients = listOf("500g chicken", "200ml cream", "2 onions", "Tikka masala paste", "Rice"),
                instructions = "Marinate chicken. Cook with spices. Add cream. Serve with rice.",
                category = "Chicken",
                area = "Indian",
                imageUrl = "https://via.placeholder.com/400",
                createdBy = userId,
                isCustom = true
            ),
            Recipe(
                name = "Chocolate Brownies",
                description = "Fudgy chocolate brownies",
                ingredients = listOf("200g chocolate", "150g butter", "3 eggs", "200g sugar", "100g flour"),
                instructions = "Melt chocolate and butter. Mix with other ingredients. Bake at 180°C for 25 minutes.",
                category = "Dessert",
                area = "American",
                imageUrl = "https://via.placeholder.com/400",
                createdBy = userId,
                isCustom = true
            )
        )

        return sampleRecipes.take(count)
    }
}