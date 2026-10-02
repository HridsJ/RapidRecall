package com.example.rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryView(
    notificationController: NotificationController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val summary = notificationController.notifySummaryRequested()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Attempt Summary",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Attempts you made: ${summary.totalAttempts}",
            fontSize = 20.sp
        )

        Text(
            text = "Attempts you got correct: ${summary.correctAttempts}",
            fontSize = 20.sp
        )

        Text(
            text = "Accuracy Percentage: ${summary.accuracy.toInt()}%",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onBackClick() }
        ) {
            Text("Go Back")
        }
    }
}