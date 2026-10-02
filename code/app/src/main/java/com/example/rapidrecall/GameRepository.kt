package com.example.rapidrecall

import androidx.compose.runtime.mutableStateListOf

class GameRepository {
    private val _attempts = mutableStateListOf<GameAttempt>()

    val attempts: List<GameAttempt>
        get() = _attempts

    fun addAttempt(attempt: GameAttempt) {
        _attempts.add(attempt)
    }
}