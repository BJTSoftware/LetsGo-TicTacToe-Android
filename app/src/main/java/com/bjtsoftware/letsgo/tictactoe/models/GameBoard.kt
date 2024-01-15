package com.bjtsoftware.letsgo.tictactoe.models

class GameBoard(private val rows: Int = 3, private val columns: Int = 3, private val consecutiveToWin: Int = 3) {
    var board: Array<Player?> = arrayOfNulls(rows * columns)
    var currentTurn: Player = Player.X
    var status: GameStatus = GameStatus.InProgress

    fun makeMove(position: Int): Boolean {
        if (status != GameStatus.InProgress || board[position] != null) {
            return false
        }
        board[position] = currentTurn
        checkGameStatus()
        switchTurn()
        return true
    }

    private fun switchTurn() {
        currentTurn = if (currentTurn == Player.X) Player.O else Player.X
    }

    private fun checkForWinner(): Boolean {
        // Horizontal, Vertical, and Diagonal checks
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                board[row * columns + col]?.let { player ->
                    // Check horizontally
                    if (col <= columns - consecutiveToWin && checkDirection(row, col, 0, 1, player)) {
                        return true
                    }
                    // Check vertically
                    if (row <= rows - consecutiveToWin && checkDirection(row, col, 1, 0, player)) {
                        return true
                    }
                    // Check diagonal (down-right)
                    if (row <= rows - consecutiveToWin && col <= columns - consecutiveToWin && checkDirection(row, col, 1, 1, player)) {
                        return true
                    }
                    // Check diagonal (up-right)
                    if (row >= consecutiveToWin - 1 && col <= columns - consecutiveToWin && checkDirection(row, col, -1, 1, player)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int, player: Player): Boolean {
        for (i in 0 until consecutiveToWin) {
            if (board[(row + i * dRow) * columns + (col + i * dCol)] != player) {
                return false
            }
        }
        return true
    }

    private fun checkGameStatus() {
        if (checkForWinner()) {
            status = GameStatus.Won
        } else if (!board.contains(null)) {
            status = GameStatus.Draw
        }
    }
}
