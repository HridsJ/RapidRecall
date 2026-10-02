package com.example.rapidrecall

//stores summary data
data class GameSummaryModel(
    val totalAttempts: Int,
    val correctAttempts: Int,
    val accuracy: Double
)