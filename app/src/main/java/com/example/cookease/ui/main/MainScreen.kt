package com.example.cookease.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookease.data.model.Recipe
import com.example.cookease.ui.theme.Cream
import com.example.cookease.ui.theme.OrangePrimary
import com.example.cookease.viewmodel.AuthViewModel
import com.example.cookease.viewmodel.RecipeViewModel
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    recipeViewModel: RecipeViewModel,
    onNavigateToAddRecipe: () -> Unit,
    onNavigateToRecipeDetail: (String) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onLogout: () -> Unit
) {
    val user = authViewModel.user.value
    val recipes by recipeViewModel.userRecipes.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val apiRecipes by recipeViewModel.apiRecipes
    val isLoading by recipeViewModel.isLoading

    LaunchedEffect(user) {
        user?.let {
            recipeViewModel.loadUserRecipes(it.uid)
        }
    }

    // Calculate stats
    val totalRecipes = recipes.size
    val categories = recipes.map { it.category }.distinct().size
    val recentRecipes = recipes.sortedByDescending { it.createdAt }.take(5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Welcome back! 👋",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            user?.displayName ?: "Chef",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        if (user?.photoUrl?.isNotEmpty() == true) {
                            AsyncImage(
                                model = user.photoUrl,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.AccountCircle, "Profile", modifier = Modifier.size(32.dp))
                        }
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                showMenu = false
                                onNavigateToProfile()
                            },
                            leadingIcon = { Icon(Icons.Default.Person, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("About") },
                            onClick = {
                                showMenu = false
                                onNavigateToAbout()
                            },
                            leadingIcon = { Icon(Icons.Default.Info, null) }
                        )
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                showMenu = false
                                authViewModel.signOut()
                                onLogout()
                            },
                            leadingIcon = { Icon(Icons.Default.ExitToApp, null) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddRecipe,
                containerColor = OrangePrimary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Add Recipe")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dashboard Stats Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Total Recipes",
                        value = totalRecipes.toString(),
                        icon = Icons.Default.MenuBook,
                        color = OrangePrimary,
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Categories",
                        value = categories.toString(),
                        icon = Icons.Default.Category,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Actions Section (as chips)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionChip(
                        text = "My Recipes",
                        icon = Icons.Default.Book,
                        onClick = { selectedTab = 0 },
                        selected = selectedTab == 0
                    )
                    QuickActionChip(
                        text = "Discover Online",
                        icon = Icons.Default.Explore,
                        onClick = { selectedTab = 1 },
                        selected = selectedTab == 1
                    )
                }
            }

            // Search Bar (only show in Discover tab)
            if (selectedTab == 1) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search recipes online...") },
                        leadingIcon = { Icon(Icons.Default.Search, "Search") },
                        trailingIcon = {
                            Row {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Close, "Clear")
                                    }
                                    IconButton(
                                        onClick = {
                                            if (searchQuery.isNotBlank()) {
                                                recipeViewModel.searchApiRecipes(searchQuery)
                                            }
                                        },
                                        enabled = !isLoading
                                    ) {
                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Icon(
                                                Icons.Default.Send,
                                                "Search",
                                                tint = OrangePrimary
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchQuery.isNotBlank()) {
                                    recipeViewModel.searchApiRecipes(searchQuery)
                                }
                            }
                        )
                    )
                }

                // Quick search suggestions
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Chicken", "Pasta", "Dessert", "Vegetarian").forEach { suggestion ->
                            SuggestionChip(
                                onClick = {
                                    searchQuery = suggestion
                                    recipeViewModel.searchApiRecipes(suggestion)
                                },
                                label = { Text(suggestion, fontSize = 12.sp) },
                                enabled = !isLoading
                            )
                        }
                    }
                }
            }

            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    // My Recipes Tab
                    if (recentRecipes.isNotEmpty()) {
                        item {
                            Text(
                                "Recent Recipes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        // Recent recipes horizontal scroll
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(recentRecipes) { recipe ->
                                    FeaturedRecipeCard(
                                        recipe = recipe,
                                        onClick = { onNavigateToRecipeDetail(recipe.id) }
                                    )
                                }
                            }
                        }

                        // All recipes section
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "All Recipes (${recipes.size})",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                        }

                        // All recipes list
                        items(recipes) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = { onNavigateToRecipeDetail(recipe.id) }
                            )
                        }
                    } else {
                        // Empty state
                        item {
                            EmptyStateCard(onNavigateToAddRecipe)
                        }
                    }

                    // Cooking Tips
                    item {
                        Text(
                            "Cooking Tips 💡",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }

                    item {
                        CookingTipCard(
                            tip = "Always read the entire recipe before you start cooking!",
                            icon = "📖"
                        )
                    }
                }
                1 -> {
                    // Discover Tab
                    when {
                        isLoading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        CircularProgressIndicator(color = OrangePrimary)
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text("Searching recipes...")
                                    }
                                }
                            }
                        }
                        apiRecipes.isEmpty() && searchQuery.isBlank() -> {
                            item {
                                DiscoverEmptyState()
                            }
                        }
                        apiRecipes.isEmpty() && searchQuery.isNotBlank() -> {
                            item {
                                NoResultsState()
                            }
                        }
                        else -> {
                            item {
                                Text(
                                    "Found ${apiRecipes.size} recipes",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OrangePrimary
                                )
                            }
                            items(apiRecipes) { apiRecipe ->
                                ApiRecipeCard(
                                    recipe = apiRecipe,
                                    onSave = {
                                        user?.let {
                                            recipeViewModel.addRecipe(apiRecipe.toRecipe(it.uid)) {}
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Component functions
@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = value,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun QuickActionChip(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    selected: Boolean
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier.size(18.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OrangePrimary,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color.White
        )
    )
}

@Composable
fun FeaturedRecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = recipe.imageUrl.ifEmpty { "https://via.placeholder.com/200" },
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = recipe.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (recipe.category.isNotEmpty()) {
                    Text(
                        recipe.category,
                        fontSize = 12.sp,
                        color = OrangePrimary
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStateCard(onNavigateToAddRecipe: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("📝", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "No recipes yet!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Start building your collection",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToAddRecipe,
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.Add, "Add", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Your First Recipe")
            }
        }
    }
}

@Composable
fun DiscoverEmptyState() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🔍", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Discover Recipes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Search for delicious recipes from around the world",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun NoResultsState() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("😕", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "No recipes found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Try a different search term",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CookingTipCard(tip: String, icon: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                icon,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                tip,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.8f),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = recipe.imageUrl.ifEmpty { "https://via.placeholder.com/100" },
                contentDescription = recipe.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = recipe.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (recipe.area.isNotEmpty()) {
                    Text(
                        text = "🌍 ${recipe.area}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun ApiRecipeCard(recipe: com.example.cookease.data.model.ApiRecipe, onSave: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = recipe.thumbnail,
                contentDescription = recipe.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = recipe.category ?: "Unknown",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onSave) {
                Icon(Icons.Default.Favorite, "Save", tint = OrangePrimary)
            }
        }
    }
}