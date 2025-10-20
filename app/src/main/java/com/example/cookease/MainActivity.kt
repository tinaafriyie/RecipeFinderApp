package com.example.cookease


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cookease.navigation.NavGraph
import com.example.cookease.ui.theme.CookEaseTheme
import com.example.cookease.viewmodel.AuthViewModel
import com.example.cookease.viewmodel.RecipeViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Analytics
        analytics = Firebase.analytics

        setContent {
            CookEaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()
                    val recipeViewModel: RecipeViewModel = viewModel()

                    NavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        recipeViewModel = recipeViewModel
                    )
                }
            }
        }
    }
}