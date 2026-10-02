package com.example.rapidrecall

import androidx.compose.runtime.mutableStateListOf

class GameRepository {
    private val _attempts = mutableStateListOf<GameAttempt>()

    val attempts: List<GameAttempt>
        get() = _attempts

    fun addAttempt(attempt: GameAttempt) {
        _attempts.add(attempt)
    }

    fun getTotalAttempts(): Int {
        return _attempts.size
    }

    fun getCorrectAttempts(): Int {

        var correctCount = 0

        for (attempt in _attempts) {
            if (attempt.isCorrect) {
                correctCount++
            }
        }

        return correctCount
    }

    fun getAccuracy(): Double {

        if (_attempts.isEmpty()) {
            return 0.0
        }

        val correctAttempts = getCorrectAttempts()

        return correctAttempts.toDouble() / _attempts.size * 100
    }
}