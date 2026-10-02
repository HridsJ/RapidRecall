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

//shows the beginning screen of the whole app
@Composable
fun GameView(
    notificationController: NotificationController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var targetSequence by remember { mutableStateOf("") }
    var currentShowingDigit by remember { mutableStateOf("") }
    var showingSequence by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var completedGameLog by remember { mutableStateOf<GameLogModel?>(null) }
    var showChangeIndicator by remember { mutableStateOf(false) }

    LaunchedEffect(targetSequence, showingSequence) {
        if (showingSequence) {
            var currentIndexDigit = 0

            while (currentIndexDigit < targetSequence.length) {
                currentShowingDigit = targetSequence[currentIndexDigit].toString()
                // makes sure a lines comes and goes back when user sees number, so number repetition is clear
                showChangeIndicator = true
                delay(800)
                showChangeIndicator = false
                delay(200)
                currentIndexDigit++
            }
            currentShowingDigit = ""
            showingSequence = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //check for beginning of the game
        val beginningGame = (!showingSequence && targetSequence.isEmpty() && completedGameLog == null)
        if (beginningGame == true) {
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
        //print numbers on screen with small indicator to make repeating digits obvious
        if (showingSequence == true) {
            Text(
                text = currentShowingDigit,
                fontSize = 48.sp
            )
            if (showChangeIndicator) {
                Text(
                    text = "---",
                    fontSize = 20.sp
                )
            }
        }

        //check if its time to ask for user input
        val showingSequenceFinished = (!showingSequence && targetSequence.isNotEmpty() && completedGameLog == null)
        if (showingSequenceFinished == true) {

            Text(
                text = "Enter the sequence:",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = userInput, onValueChange = { userInput = it }, label = { Text("Your Guess") })
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { completedGameLog = notificationController.notifyGuessSubmitted(userInput) }
            ) { Text("Submit Guess") }
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
                Text("Go Back")
            }
        }
    }
}