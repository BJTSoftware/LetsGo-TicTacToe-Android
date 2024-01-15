package com.bjtsoftware.letsgo.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bjtsoftware.letsgo.tictactoe.models.GameBoard
import com.bjtsoftware.letsgo.tictactoe.models.GameStatus
import com.bjtsoftware.letsgo.tictactoe.models.Player

class TicTacToeViewModel : ViewModel() {
    val gameBoard = MutableLiveData<GameBoard>()
    val gameStatusMessage = MutableLiveData<String>()
    val playerXScore = MutableLiveData<Int>()
    val playerOScore = MutableLiveData<Int>()

    init {
        resetGame()
    }

    fun makeMove(position: Int) {
        gameBoard.value?.let {
            if (it.makeMove(position)) {
                when (it.status) {
                    GameStatus.Won -> {
                        val winner = if (it.currentTurn == Player.X) "O" else "X"
                        gameStatusMessage.value = "Player $winner wins!"
                        if (winner == "X") {
                            playerXScore.value = (playerXScore.value ?: 0) + 1
                        } else {
                            playerOScore.value = (playerOScore.value ?: 0) + 1
                        }
                    }
                    GameStatus.Draw -> {
                        gameStatusMessage.value = "It's a draw!"
                    }
                    GameStatus.InProgress -> {
                        val nextPlayer = if (it.currentTurn == Player.X) "X" else "O"
                        gameStatusMessage.value = "Player $nextPlayer's turn"
                    }
                }
            }
        }
        gameBoard.postValue(gameBoard.value)
    }

    fun resetGame() {
        gameBoard.value = GameBoard()
        gameStatusMessage.value = "Player X's turn"
        playerXScore.value = 0
        playerOScore.value = 0
    }
}
