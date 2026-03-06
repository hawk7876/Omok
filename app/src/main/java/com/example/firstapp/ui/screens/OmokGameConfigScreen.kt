package com.example.firstapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OmokGameConfigScreen(
    onStartGame: (difficulty: String, boardSize: Int) -> Unit,
    onBack: () -> Unit
) {
    var difficulty by remember { mutableStateOf("Beginner") }
    var boardSize by remember { mutableStateOf(15) }

    val difficulties = listOf("Beginner", "Intermediate", "Advanced")
    val boardSizes = listOf(15, 24)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Omok Game Configuration", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Difficulty Level", style = MaterialTheme.typography.titleMedium)
        Row {
            difficulties.forEach { level ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (difficulty == level),
                        onClick = { difficulty = level }
                    )
                    Text(text = level, modifier = Modifier.padding(end = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Board Size", style = MaterialTheme.typography.titleMedium)
        Row {
            boardSizes.forEach { size ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (boardSize == size),
                        onClick = { boardSize = size }
                    )
                    Text(text = "${size}x${size}", modifier = Modifier.padding(end = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { onStartGame(difficulty, boardSize) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Game")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onBack) {
            Text("Back to Dashboard")
        }
    }
}
