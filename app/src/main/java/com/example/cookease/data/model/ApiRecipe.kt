package com.example.cookease.data.model

import com.google.gson.annotations.SerializedName

data class ApiRecipeResponse(
    val meals: List<ApiRecipe>?
)

data class ApiRecipe(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strMealThumb") val thumbnail: String,
    @SerializedName("strYoutube") val youtubeUrl: String?,
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,
    @SerializedName("strIngredient7") val ingredient7: String?,
    @SerializedName("strIngredient8") val ingredient8: String?,
    @SerializedName("strIngredient9") val ingredient9: String?,
    @SerializedName("strIngredient10") val ingredient10: String?,
    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?,
    @SerializedName("strMeasure7") val measure7: String?,
    @SerializedName("strMeasure8") val measure8: String?,
    @SerializedName("strMeasure9") val measure9: String?,
    @SerializedName("strMeasure10") val measure10: String?
) {
    fun toRecipe(userId: String): Recipe {
        val ingredients = mutableListOf<String>()
        listOfNotNull(
            ingredient1 to measure1,
            ingredient2 to measure2,
            ingredient3 to measure3,
            ingredient4 to measure4,
            ingredient5 to measure5,
            ingredient6 to measure6,
            ingredient7 to measure7,
            ingredient8 to measure8,
            ingredient9 to measure9,
            ingredient10 to measure10
        ).forEach { (ing, measure) ->
            if (!ing.isNullOrBlank()) {
                ingredients.add("${measure ?: ""} $ing".trim())
            }
        }

        return Recipe(
            name = name,
            description = category ?: "",
            ingredients = ingredients,
            instructions = instructions ?: "",
            category = category ?: "",
            area = area ?: "",
            imageUrl = thumbnail,
            createdBy = userId,
            isCustom = false,
            apiId = id
        )
    }
}