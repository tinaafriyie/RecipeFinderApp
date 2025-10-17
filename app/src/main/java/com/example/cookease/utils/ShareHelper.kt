package com.example.cookease.utils

import android.content.Context
import android.content.Intent
import com.example.cookease.data.model.Recipe

object ShareHelper {
    fun shareRecipe(context: Context, recipe: Recipe) {
        val shareText = buildString {
            appendLine("Check out this recipe!")
            appendLine()
            appendLine("📖 ${recipe.name}")
            appendLine()
            if (recipe.description.isNotEmpty()) {
                appendLine(recipe.description)
                appendLine()
            }
            appendLine("🥘 Ingredients:")
            recipe.ingredients.forEach { ingredient ->
                appendLine("• $ingredient")
            }
            appendLine()
            appendLine("👨‍🍳 Instructions:")
            appendLine(recipe.instructions)
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Recipe: ${recipe.name}")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        context.startActivity(Intent.createChooser(intent, "Share Recipe"))
    }
}