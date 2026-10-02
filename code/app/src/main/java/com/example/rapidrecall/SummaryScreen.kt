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
fun SummaryScreen(
    totalAttempts: Int,
    correctAttempts: Int,
    accuracy: Double,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

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
            text = "Total Attempts: $totalAttempts",
            fontSize = 20.sp
        )

        Text(
            text = "Correct Attempts: $correctAttempts",
            fontSize = 20.sp
        )

        Text(
            text = "Accuracy: ${accuracy.toInt()}%",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onBackClick()
            }
        ) {
            Text("Back")
        }
    }
}