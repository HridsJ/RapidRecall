package com.example.rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapidrecall.ui.theme.RapidRecallTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gameRepository = GameRepository()
        setContent {
            RapidRecallTheme {
                var currentScreen by remember { mutableStateOf("start") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "start" -> StartScreen(
                            onStartClick = {
                                currentScreen = "game"
                            },
                            onLogClick = {
                                currentScreen = "log"
                            },
                            onSummaryClick = {
                                currentScreen = "summary"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "game" -> GameScreen(
                            onBackClick = {
                                currentScreen = "start"
                            },
                            onAttemptComplete = {
                                gameRepository.addAttempt(it)
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "log" -> LogScreen(
                            attempts = gameRepository.attempts,
                            onBackClick = {
                                currentScreen = "start"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "summary" -> SummaryScreen(
                            totalAttempts = gameRepository.getTotalAttempts(),
                            correctAttempts = gameRepository.getCorrectAttempts(),
                            accuracy = gameRepository.getAccuracy(),
                            onBackClick = {
                                currentScreen = "start"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RapidRecallTheme {
        Greeting("Android")
    }
}

@Composable
fun StartScreen(
    onStartClick: () -> Unit,
    onLogClick: () -> Unit,
    onSummaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "RapidRecall",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onStartClick() }
        ) {
            Text("Start")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onLogClick() }
        ) {
            Text("Log")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onSummaryClick() }
        ) {
            Text("Attempt Summary")
        }
    }
}

fun generateSequence(sequenceLength: Int): String {
    var sequence = ""
    for (i in 1..sequenceLength) {
        val randomDigit = (0..9).random()
        sequence += randomDigit.toString()
    }
    return sequence
}

@Composable
fun GameScreen(
    onBackClick: () -> Unit,
    onAttemptComplete: (GameAttempt) -> Unit,
    modifier: Modifier = Modifier
) {

    var targetSequence by remember { mutableStateOf("") }
    var sequenceLength by remember { mutableStateOf(0) }
    var displayedDigit by remember { mutableStateOf("") }
    var showingSequence by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var attemptFinished by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }

    LaunchedEffect(targetSequence, showingSequence) {

        if (showingSequence && targetSequence.isNotEmpty()) {

            for (digit in targetSequence) {
                displayedDigit = digit.toString()
                delay(1000)
            }

            displayedDigit = ""
            showingSequence = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!showingSequence && targetSequence.isEmpty()) {
            Text(
                text = "How many digits would you like to recall?",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            for (number in 1..10) {
                Button(
                    onClick = {
                        sequenceLength = number
                        targetSequence = generateSequence(number)
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
        }

        if (!showingSequence && targetSequence.isNotEmpty() && !attemptFinished) {

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
                onClick = {

                    if (userInput.isNotBlank()) {

                        val correctAnswer = userInput == targetSequence

                        if (correctAnswer) {
                            resultText = "Correct!"
                        } else {
                            resultText = "Incorrect!"
                        }

                        val attempt = GameAttempt(
                            sequenceLength = sequenceLength,
                            userInput = userInput,
                            targetSequence = targetSequence,
                            isCorrect = correctAnswer,
                            timestamp = System.currentTimeMillis()
                        )

                        onAttemptComplete(attempt)

                        attemptFinished = true
                    }
                }
            ) {
                Text("Submit")
            }
        }

        if (attemptFinished) {

            Text(
                text = resultText,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Correct sequence: $targetSequence"
            )

            Text(
                text = "Your answer: $userInput"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onBackClick()
                }
            ) {
                Text("Back to Start")
            }
        }
    }
}