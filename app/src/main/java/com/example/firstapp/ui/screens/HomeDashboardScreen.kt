package com.example.firstapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeDashboardScreen(
    onPlayOnlineClick: () -> Unit,
    onPlayAIClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onPlayOnlineClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Play Online")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onPlayAIClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Play AI")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onLeaderboardClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Leaderboard")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }
        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onLogout) {
            Text("Logout")
        }
    }
}
