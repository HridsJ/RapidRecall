package com.example.rapidrecall
//model for storing logs data
data class GameLogModel(
    val userInput: String,
    val isCorrect: Boolean,
    val targetSequence: String,
    val timestamp: Long,
    val sequenceLength: Int
) {}