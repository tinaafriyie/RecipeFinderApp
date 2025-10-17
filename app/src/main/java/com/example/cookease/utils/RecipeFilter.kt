package com.example.cookease.utils



import com.example.cookease.data.model.Recipe

object RecipeFilter {
    fun filterRecipes(
        recipes: List<Recipe>,
        query: String,
        category: String? = null,
        area: String? = null
    ): List<Recipe> {
        return recipes.filter { recipe ->
            val matchesQuery = query.isBlank() ||
                    recipe.name.contains(query, ignoreCase = true) ||
                    recipe.ingredients.any { it.contains(query, ignoreCase = true) }

            val matchesCategory = category == null || recipe.category == category
            val matchesArea = area == null || recipe.area == area

            matchesQuery && matchesCategory && matchesArea
        }
    }

    fun sortRecipes(recipes: List<Recipe>, sortBy: SortOption): List<Recipe> {
        return when (sortBy) {
            SortOption.NAME_ASC -> recipes.sortedBy { it.name }
            SortOption.NAME_DESC -> recipes.sortedByDescending { it.name }
            SortOption.DATE_NEWEST -> recipes.sortedByDescending { it.createdAt }
            SortOption.DATE_OLDEST -> recipes.sortedBy { it.createdAt }
            SortOption.CATEGORY -> recipes.sortedBy { it.category }
        }
    }
}

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    DATE_NEWEST,
    DATE_OLDEST,
    CATEGORY
}