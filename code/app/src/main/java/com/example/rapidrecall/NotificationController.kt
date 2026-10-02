package com.example.rapidrecall

// prevents view from directly going to model
class NotificationController(
    private val gameStartModel: GameStartModel
) {
    fun notifySequenceSelected(sequenceLength: Int): String {
        return gameStartModel.generateSequence(sequenceLength)
    }
    fun notifyGuessSubmitted(userInput: String): GameLogModel {
        return gameStartModel.compareGuess(userInput)
    }
    fun notifyLogRequested(): List<GameLogModel> {
        return gameStartModel.getLogs()
    }
    fun notifySummaryRequested(): GameSummaryModel {
        return gameStartModel.getSummary()
    }
    fun setModelUpdateListener(listener: () -> Unit) {
        gameStartModel.setUpdateListener(listener)
    }
}