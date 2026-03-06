package com.example.firstapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class StoneColor { BLACK, WHITE }

@Composable
fun OmokGameScreen(
    difficulty: String,
    boardSize: Int,
    onBack: () -> Unit
) {
    // Game state
    var board by remember { mutableStateOf(Array(boardSize) { arrayOfNulls<StoneColor>(boardSize) }) }
    var currentPlayer by remember { mutableStateOf(StoneColor.BLACK) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Omok - $difficulty", style = MaterialTheme.typography.headlineSmall)
        
        Spacer(modifier = Modifier.height(16.dp))

        // Top Indicators for players (Stones)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayerIndicator(color = StoneColor.BLACK, isTurn = currentPlayer == StoneColor.BLACK)
            PlayerIndicator(color = StoneColor.WHITE, isTurn = currentPlayer == StoneColor.WHITE)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Game Board
        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .background(Color(0xFFE3C16F)) // Traditional wooden board color
                .border(2.dp, Color.Black)
        ) {
            val fullWidth = constraints.maxWidth.toFloat()
            // Padding so stones aren't on the very edge of the container
            val padding = fullWidth / (boardSize + 1)
            val boardInternalWidth = fullWidth - 2 * padding
            val cellSize = boardInternalWidth / (boardSize - 1)

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(boardSize) {
                        detectTapGestures { offset ->
                            // Calculate which intersection was touched
                            val x = ((offset.x - padding) / cellSize).roundToInt()
                            val y = ((offset.y - padding) / cellSize).roundToInt()

                            if (x in 0 until boardSize && y in 0 until boardSize && board[y][x] == null) {
                                // Update board state
                                val newBoard = board.map { it.copyOf() }.toTypedArray()
                                newBoard[y][x] = currentPlayer
                                board = newBoard
                                
                                // Switch turn
                                currentPlayer = if (currentPlayer == StoneColor.BLACK) StoneColor.WHITE else StoneColor.BLACK
                            }
                        }
                    }
            ) {
                // Draw grid lines
                for (i in 0 until boardSize) {
                    // Horizontal lines
                    drawLine(
                        color = Color.Black,
                        start = Offset(padding, padding + i * cellSize),
                        end = Offset(padding + (boardSize - 1) * cellSize, padding + i * cellSize),
                        strokeWidth = 2f
                    )
                    // Vertical lines
                    drawLine(
                        color = Color.Black,
                        start = Offset(padding + i * cellSize, padding),
                        end = Offset(padding + i * cellSize, padding + (boardSize - 1) * cellSize),
                        strokeWidth = 2f
                    )
                }

                // Draw stones on intersections
                board.forEachIndexed { y, row ->
                    row.forEachIndexed { x, stone ->
                        stone?.let {
                            drawCircle(
                                color = if (it == StoneColor.BLACK) Color.Black else Color.White,
                                radius = cellSize * 0.45f,
                                center = Offset(padding + x * cellSize, padding + y * cellSize)
                            )
                            // Add a border to white stones for visibility
                            if (it == StoneColor.WHITE) {
                                drawCircle(
                                    color = Color.Black,
                                    radius = cellSize * 0.45f,
                                    center = Offset(padding + x * cellSize, padding + y * cellSize),
                                    style = Stroke(width = 1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onBack, modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Back to Menu")
        }
    }
}

@Composable
fun PlayerIndicator(color: StoneColor, isTurn: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(
                    if (isTurn) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    CircleShape
                )
                .border(
                    if (isTurn) 4.dp else 1.dp,
                    if (isTurn) MaterialTheme.colorScheme.primary else Color.Gray,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Stone "Image" using Canvas
            Canvas(modifier = Modifier.size(44.dp)) {
                drawCircle(color = if (color == StoneColor.BLACK) Color.Black else Color.White)
                if (color == StoneColor.WHITE) {
                    drawCircle(color = Color.Black, style = Stroke(width = 2f))
                }
            }
        }
        Text(
            text = if (color == StoneColor.BLACK) "Black" else "White",
            style = if (isTurn) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            color = if (isTurn) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
