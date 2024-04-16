package com.maha.jetpackcomposequiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maha.jetpackcomposequiz.ui.screens.HomeScreen
import com.maha.jetpackcomposequiz.ui.screens.QuizScreen
import com.maha.jetpackcomposequiz.ui.screens.VideoScreen
import com.maha.jetpackcomposequiz.ui.theme.JetpackComposeQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeQuizTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "homeScreen") {
                        composable("homeScreen") {
                            HomeScreen(navController = navController)
                        }
                        composable("quizScreen") {
                            QuizScreen(navController = navController)
                        }
                        composable("videoScreen") {
                            VideoScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
