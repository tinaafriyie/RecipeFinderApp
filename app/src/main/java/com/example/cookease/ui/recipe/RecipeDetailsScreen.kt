package com.example.cookease.ui.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookease.data.model.Recipe
import com.example.cookease.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: String,
    recipeViewModel: RecipeViewModel,
    onNavigateBack: () -> Unit
) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(recipeId) {
        scope.launch {
            recipeViewModel.getRecipeById(recipeId).onSuccess {
                recipe = it
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        recipe?.let { currentRecipe ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    AsyncImage(
                        model = currentRecipe.imageUrl.ifEmpty { "https://via.placeholder.com/400" },
                        contentDescription = currentRecipe.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentRecipe.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (currentRecipe.category.isNotEmpty()) {
                                AssistChip(
                                    onClick = {},
                                    label = { Text(currentRecipe.category) }
                                )
                            }
                            if (currentRecipe.area.isNotEmpty()) {
                                AssistChip(
                                    onClick = {},
                                    label = { Text("🌍 ${currentRecipe.area}") }
                                )
                            }
                        }

                        if (currentRecipe.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = currentRecipe.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(currentRecipe.ingredients) { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text("• ", style = MaterialTheme.typography.bodyLarge)
                        Text(ingredient, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                }

                item {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                item {
                    Text(
                        text = currentRecipe.instructions,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.5f)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Recipe") },
            text = { Text("Are you sure you want to delete this recipe? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        recipeViewModel.deleteRecipe(recipeId) {
                            showDeleteDialog = false
                            onNavigateBack()
                        }
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}