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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                            modifier = Modifier.padding(innerPadding)
                        )
                        "log" -> Text("Log Screen")
                        "summary" -> Text("Summary Screen")
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
    modifier: Modifier = Modifier
) {

    var targetSequence by remember { mutableStateOf("") }
    var sequenceLength by remember { mutableStateOf(0) }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

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
                }
            ) {
                Text("$number")
            }
        }
        Button(
            onClick = {
                onBackClick()
            }
        ) {
            Text("Back")
        }
    }
}