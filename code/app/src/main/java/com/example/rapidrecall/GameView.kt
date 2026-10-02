package com.example.rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.text.iterator

@Composable
fun GameView(
    notificationController: NotificationController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var targetSequence by remember { mutableStateOf("") }
    var displayedDigit by remember { mutableStateOf("") }
    var showingSequence by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var completedGameLog by remember { mutableStateOf<GameLogModel?>(null) }
    var showLine by remember { mutableStateOf(false) }

    LaunchedEffect(targetSequence, showingSequence) {
        if (showingSequence) {
            var digitPosition = 0

            while (digitPosition < targetSequence.length) {
                displayedDigit = targetSequence[digitPosition].toString()
                showLine = true
                delay(800)
                showLine = false
                delay(200)
                digitPosition++
            }

            displayedDigit = ""
            showingSequence = false

            displayedDigit = ""
            showingSequence = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!showingSequence && targetSequence.isEmpty() && completedGameLog == null) {
            Text(
                text = "How many digits would you like to recall?",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            for (number in 1..10) {
                Button(
                    onClick = {
                        targetSequence = notificationController.notifySequenceSelected(number)
                        showingSequence = true
                    }
                ) {
                    Text("$number")
                }
            }
        }

        if (showingSequence) {
            Text(
                text = displayedDigit,
                fontSize = 48.sp
            )
            if (showLine) {
                Text(
                    text = "->",
                    fontSize = 20.sp
                )
            }
        }

        if (!showingSequence && targetSequence.isNotEmpty() && completedGameLog == null) {

            Text(
                text = "Enter the sequence:",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                },
                label = {
                    Text("Your Guess")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { completedGameLog = notificationController.notifyGuessSubmitted(userInput) }
            ) { Text("Submit") }
        }

        val gameLog = completedGameLog

        if (gameLog != null) {

            Text(text = if (gameLog.isCorrect) "Correct!" else "Incorrect!", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Correct sequence: ${gameLog.targetSequence}")
            Text(text = "Your answer: ${gameLog.userInput}")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onBackClick() }
            ) {
                Text("Back to Start")
            }
        }
    }
}