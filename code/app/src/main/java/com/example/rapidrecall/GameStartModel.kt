package com.example.rapidrecall

class GameStartModel {
    // model for storing start game data
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

        var generatedSequence = ""
        var digitsGenerated = 0

        while (digitsGenerated < sequenceLength) {
            generatedSequence += (0..9).random().toString()
            digitsGenerated++
        }

        currentSequenceLength = sequenceLength
        currentSequence = generatedSequence

        // tell view about change
        notifyUpdate()

        return currentSequence
    }


    fun compareGuess(userInput: String): GameLogModel {
        // checks if input is correct
        val correctAnswer: Boolean
        if (userInput == currentSequence) correctAnswer = true else correctAnswer = false

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
        // make sure i dont get 0/0 error when there are no attempts
        var accuracy = 0.0
        if (totalAttempts != 0) { accuracy = correctAttempts.toDouble() / totalAttempts * 100 }

        return GameSummaryModel(totalAttempts = totalAttempts, correctAttempts = correctAttempts, accuracy = accuracy)
    }
}