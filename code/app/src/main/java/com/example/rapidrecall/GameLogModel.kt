package com.example.rapidrecall

data class GameLogModel(
    val sequenceLength: Int,
    val userInput: String,
    val targetSequence: String,
    val isCorrect: Boolean,
    val timestamp: Long
)