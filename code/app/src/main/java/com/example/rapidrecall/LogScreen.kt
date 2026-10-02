package com.example.rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    val format = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale.getDefault()
    )

    return format.format(Date(timestamp))
}

@Composable
fun LogScreen(
    attempts: List<GameAttempt>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Text(
            text = "Attempt Log",
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (attempts.isEmpty()) {

            Text(
                text = "No attempts yet.",
                modifier = Modifier.padding(16.dp)
            )

        } else {

            LazyColumn {

                items(attempts) { attempt ->

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Sequence Length: ${attempt.sequenceLength}"
                        )

                        Text(
                            text = "Target Sequence: ${attempt.targetSequence}"
                        )

                        Text(
                            text = "Your Input: ${attempt.userInput}"
                        )

                        Text(
                            text = if (attempt.isCorrect) "Correct" else "Incorrect"
                        )

                        Text(
                            text = "Time: ${formatTimestamp(attempt.timestamp)}"
                        )
                    }

                    HorizontalDivider()
                }
            }
        }
    }
}