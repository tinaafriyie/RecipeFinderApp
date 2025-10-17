package com.example.cookease.utils


object Constants {
    const val MEAL_DB_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    // Firebase Collections
    const val RECIPES_COLLECTION = "recipes"
    const val USERS_COLLECTION = "users"

    // Storage paths
    const val PROFILE_IMAGES_PATH = "profile_images"
    const val RECIPE_IMAGES_PATH = "recipe_images"

    // Analytics Events
    const val EVENT_LOGIN = "user_login"
    const val EVENT_SIGNUP = "user_signup"
    const val EVENT_ADD_RECIPE = "add_recipe"
    const val EVENT_DELETE_RECIPE = "delete_recipe"
    const val EVENT_UPDATE_RECIPE = "update_recipe"
    const val EVENT_SEARCH_RECIPE = "search_recipe"
    const val EVENT_SAVE_API_RECIPE = "save_api_recipe"

    // Recipe Categories
    val CATEGORIES = listOf(
        "Beef",
        "Chicken",
        "Dessert",
        "Lamb",
        "Miscellaneous",
        "Pasta",
        "Pork",
        "Seafood",
        "Side",
        "Starter",
        "Vegan",
        "Vegetarian",
        "Breakfast",
        "Goat"
    )

    // Cuisines
    val CUISINES = listOf(
        "American",
        "British",
        "Canadian",
        "Chinese",
        "Croatian",
        "Dutch",
        "Egyptian",
        "French",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Jamaican",
        "Japanese",
        "Kenyan",
        "Malaysian",
        "Mexican",
        "Moroccan",
        "Polish",
        "Portuguese",
        "Russian",
        "Spanish",
        "Thai",
        "Tunisian",
        "Turkish",
        "Vietnamese"
    )
}