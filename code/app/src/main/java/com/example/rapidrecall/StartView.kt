package com.example.rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//displays the screen when user presses start
@Composable
fun StartView(
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
        Spacer(modifier = Modifier.height(16.dp))
        Text("ccid: Hridey")
        Spacer(modifier = Modifier.height(2.dp))
        Text("student number: 1833471")
    }
}