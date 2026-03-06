package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstapp.ui.screens.*
import com.example.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { 
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onRegisterRedirect = { navController.navigate("register") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onRegisterSuccess = { navController.popBackStack() },
                                onLoginRedirect = { navController.popBackStack() }
                            )
                        }
                        composable("home") {
                            HomeDashboardScreen(
                                onPlayOnlineClick = { /* Navigate to Play Online */ },
                                onPlayAIClick = { navController.navigate("game_config") },
                                onLeaderboardClick = { /* Navigate to Leaderboard */ },
                                onSettingsClick = { /* Navigate to Settings */ },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("game_config") {
                            OmokGameConfigScreen(
                                onStartGame = { difficulty, boardSize ->
                                    navController.navigate("game/$difficulty/$boardSize")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "game/{difficulty}/{boardSize}",
                            arguments = listOf(
                                navArgument("difficulty") { type = NavType.StringType },
                                navArgument("boardSize") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Beginner"
                            val boardSize = backStackEntry.arguments?.getInt("boardSize") ?: 15
                            OmokGameScreen(
                                difficulty = difficulty,
                                boardSize = boardSize,
                                onBack = {
                                    navController.navigate("home") {
                                        popUpTo("home") { inclusive = true }
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
