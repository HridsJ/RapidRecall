package com.example.rapidrecall

class GameAttempt (
    val sequenceLength: Int,
    val userInput: String,
    val targetSequence: String,
    val isCorrect: Boolean,
    val timestamp: Long
) {
}