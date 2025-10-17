package com.example.cookease.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cookease.ui.about.AboutScreen
import com.example.cookease.ui.auth.LoginScreen
import com.example.cookease.ui.auth.RegisterScreen
import com.example.cookease.ui.main.MainScreen
import com.example.cookease.ui.profile.ProfileScreen
import com.example.cookease.ui.recipe.AddRecipeScreen
import com.example.cookease.ui.recipe.RecipeDetailScreen
import com.example.cookease.viewmodel.AuthViewModel
import com.example.cookease.viewmodel.RecipeViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object AddRecipe : Screen("add_recipe")
    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe_detail/$recipeId"
    }
    object Profile : Screen("profile")
    object About : Screen("about")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    recipeViewModel: RecipeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (authViewModel.user.value != null) Screen.Main.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                authViewModel = authViewModel,
                recipeViewModel = recipeViewModel,
                onNavigateToAddRecipe = { navController.navigate(Screen.AddRecipe.route) },
                onNavigateToRecipeDetail = { recipeId ->
                    navController.navigate(Screen.RecipeDetail.createRoute(recipeId))
                },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToAbout = { navController.navigate(Screen.About.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.AddRecipe.route) {
            AddRecipeScreen(
                recipeViewModel = recipeViewModel,
                authViewModel = authViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeDetailScreen(
                recipeId = recipeId,
                recipeViewModel = recipeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}