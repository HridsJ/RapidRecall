package com.example.rapidrecall

class GameStartModel {

    private var currentSequence = ""
    private var currentSequenceLength = 0

    private val gameLogs = mutableListOf<GameLogModel>()

    private var updateListener: (() -> Unit)? = null


    fun setUpdateListener(listener: () -> Unit) {
        updateListener = listener
    }


    private fun notifyUpdate() {
        updateListener?.invoke()
    }


    fun generateSequence(sequenceLength: Int): String {

        var sequence = ""

        for (i in 1..sequenceLength) {
            val randomDigit = (0..9).random()
            sequence += randomDigit.toString()
        }

        currentSequenceLength = sequenceLength
        currentSequence = sequence

        notifyUpdate()

        return currentSequence
    }


    fun compareGuess(userInput: String): GameLogModel {

        val correctAnswer = userInput == currentSequence

        val gameLog = GameLogModel(
            sequenceLength = currentSequenceLength,
            userInput = userInput,
            targetSequence = currentSequence,
            isCorrect = correctAnswer,
            timestamp = System.currentTimeMillis()
        )

        gameLogs.add(gameLog)

        notifyUpdate()

        return gameLog
    }


    fun getLogs(): List<GameLogModel> {
        return gameLogs.toList()
    }


    fun getSummary(): GameSummaryModel {

        val totalAttempts = gameLogs.size

        var correctAttempts = 0

        for (gameLog in gameLogs) {
            if (gameLog.isCorrect) {
                correctAttempts++
            }
        }

        var accuracy = 0.0

        if (totalAttempts != 0) {
            accuracy = correctAttempts.toDouble() / totalAttempts * 100
        }

        return GameSummaryModel(
            totalAttempts = totalAttempts,
            correctAttempts = correctAttempts,
            accuracy = accuracy
        )
    }
}