package com.example.rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
        "dd-MM-yyyy HH:mm:ss",
        Locale.getDefault()
    )

    return format.format(Date(timestamp))
}

// display log screen of the attempts made
@Composable
fun LogView(
    notificationController: NotificationController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gameLogs = notificationController.notifyLogRequested()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Log of Attempts",
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(gameLogs) { gameLog ->
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    //format of output
                    Text(
                        text = "Length of the sequence: ${gameLog.sequenceLength}"
                    )
                    Text(
                        text = "Sequence of expected target: ${gameLog.targetSequence}"
                    )
                    Text(
                        text = "Sequence given: ${gameLog.userInput}"
                    )
                    Text(
                        text = if (gameLog.isCorrect) "Correct" else "Incorrect"
                    )
                    Text(
                        text = "Date and time: ${formatTimestamp(gameLog.timestamp)}"
                    )
                }
                Spacer(modifier= Modifier.height(2.dp).fillMaxWidth())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Go Back")
        }
    }
}