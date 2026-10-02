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
        val gameStartModel = GameStartModel()
        val notificationController = NotificationController(gameStartModel)
        setContent {
            RapidRecallTheme {
                var currentScreen by remember { mutableStateOf("start") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "start" -> StartView(
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
                        "game" -> GameView(
                            notificationController = notificationController,
                            onBackClick = {
                                currentScreen = "start"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "log" -> LogView(
                            notificationController = notificationController,
                            onBackClick = {
                                currentScreen = "start"
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                        "summary" -> SummaryView(
                            notificationController = notificationController,
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
