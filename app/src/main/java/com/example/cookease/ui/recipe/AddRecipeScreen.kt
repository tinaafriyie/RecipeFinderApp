package com.example.cookease.ui.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookease.data.model.Recipe
import com.example.cookease.viewmodel.AuthViewModel
import com.example.cookease.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    recipeViewModel: RecipeViewModel,
    authViewModel: AuthViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf("")) }

    val user = authViewModel.user.value
    val isLoading = recipeViewModel.isLoading.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Recipe") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Recipe Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                // Category Dropdown
                var categoryExpanded by remember { mutableStateOf(false) }
                val categories = listOf(
                    "Beef",
                    "Chicken",
                    "Dessert",
                    "Lamb",
                    "Pasta",
                    "Pork",
                    "Seafood",
                    "Vegetarian",
                    "Vegan",
                    "Breakfast",
                    "Lunch",
                    "Dinner",
                    "Snack",
                    "Appetizer",
                    "Side Dish",
                    "Soup",
                    "Salad",
                    "Beverage"
                )

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it }
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { selectedCategory ->
                            DropdownMenuItem(
                                text = { Text(selectedCategory) },
                                onClick = {
                                    category = selectedCategory
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                // Cuisine Dropdown
                var areaExpanded by remember { mutableStateOf(false) }
                val cuisines = listOf(
                    "American",
                    "British",
                    "Canadian",
                    "Chinese",
                    "French",
                    "Greek",
                    "Indian",
                    "Italian",
                    "Japanese",
                    "Mexican",
                    "Moroccan",
                    "Spanish",
                    "Thai",
                    "Turkish",
                    "Vietnamese",
                    "Middle Eastern",
                    "African",
                    "Caribbean"
                )

                ExposedDropdownMenuBox(
                    expanded = areaExpanded,
                    onExpandedChange = { areaExpanded = it }
                ) {
                    OutlinedTextField(
                        value = area,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Cuisine") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = areaExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = areaExpanded,
                        onDismissRequest = { areaExpanded = false }
                    ) {
                        cuisines.forEach { selectedCuisine ->
                            DropdownMenuItem(
                                text = { Text(selectedCuisine) },
                                onClick = {
                                    area = selectedCuisine
                                    areaExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                Text("Ingredients", style = MaterialTheme.typography.titleMedium)
            }

            itemsIndexed(ingredients) { index, ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = ingredient,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().apply {
                                this[index] = newValue
                            }
                        },
                        label = { Text("Ingredient ${index + 1}") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    if (ingredients.size > 1) {
                        IconButton(onClick = {
                            ingredients = ingredients.toMutableList().apply {
                                removeAt(index)
                            }
                        }) {
                            Icon(Icons.Default.Delete, "Remove")
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        ingredients = ingredients + ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, "Add Ingredient")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Ingredient")
                }
            }

            item {
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10
                )
            }

            item {
                Button(
                    onClick = {
                        user?.let {
                            val recipe = Recipe(
                                name = name,
                                description = description,
                                ingredients = ingredients.filter { it.isNotBlank() },
                                instructions = instructions,
                                category = category,
                                area = area,
                                imageUrl = imageUrl,
                                createdBy = it.uid,
                                isCustom = true
                            )

                            recipeViewModel.addRecipe(recipe) {
                                onNavigateBack()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading && name.isNotBlank() && instructions.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(Icons.Default.Check, "Save")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Recipe")
                    }
                }
            }
        }
    }
}