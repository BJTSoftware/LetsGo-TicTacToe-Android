    package com.bjtsoftware.letsgo.tictactoe.viewmodels

    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.bjtsoftware.letsgo.tictactoe.models.GameBoard

    class TicTacToeViewModel : ViewModel() {
        val gameBoard = MutableLiveData<GameBoard>()
        val winningPositions = MutableLiveData<List<IntArray>>()
        val gameStatusMessage = MutableLiveData<String>()
        val playerXScore = MutableLiveData<Int>()
        val playerOScore = MutableLiveData<Int>()

        init {
            clear()
        }

    //    fun makeMove(position: Int) {
    //        gameBoard.value?.let {
    //            winningPositions = it.makeMove(position)
    //            when (it.status) {
    //                GameStatus.Won -> {
    //                    val winner = if (it.currentTurn == Player.X) "O" else "X"
    //                    gameStatusMessage.value = "Player $winner wins!"
    //                    if (winner == "X") {
    //                        playerXScore.value = (playerXScore.value ?: 0) + 1
    //                    } else {
    //                        playerOScore.value = (playerOScore.value ?: 0) + 1
    //                    }
    //                }
    //
    //                GameStatus.Draw -> {
    //                    gameStatusMessage.value = "It's a draw!"
    //                }
    //
    //                GameStatus.InProgress -> {
    //                    val nextPlayer = if (it.currentTurn == Player.X) "X" else "O"
    //                    gameStatusMessage.value = "Player $nextPlayer's turn"
    //                }
    //            }
    //        }
    //        gameBoard.postValue(gameBoard.value)
    //    }

        fun makeMove(position: Int) {
            gameBoard.value?.let {
                val results = it.makeMove(position)
                if (results.isNotEmpty()) {
                    winningPositions.value = results
                    // Additional game state updates...
                }
                gameBoard.postValue(it) // Trigger LiveData update
            }
        }

        fun resetGame() {
            gameBoard.value = GameBoard()
            gameStatusMessage.value = "Player X's turn"
        }

        fun clear() {
            gameBoard.value = GameBoard()
            gameStatusMessage.value = "Player X's turn"
            playerXScore.value = 0
            playerOScore.value = 0
        }
    }
