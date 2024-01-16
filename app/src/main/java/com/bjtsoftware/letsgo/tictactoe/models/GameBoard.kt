package com.bjtsoftware.letsgo.tictactoe.models

class GameBoard(private val rows: Int = 3, private val columns: Int = 3, private val consecutiveToWin: Int = 3) {
    var board: Array<Player?> = arrayOfNulls(rows * columns)
    var currentTurn: Player = Player.X
    var status: GameStatus = GameStatus.InProgress

    fun makeMove(position: Int): List<IntArray> {
        if (status != GameStatus.InProgress || board[position] != null) {
            return listOf(IntArray(0))
        }
        board[position] = currentTurn
        val results = checkGameStatus()
        switchTurn()
        return results
    }

    private fun switchTurn() {
        currentTurn = if (currentTurn == Player.X) Player.O else Player.X
    }

    private fun checkForWinner(): List<IntArray> {
        val result = mutableListOf<IntArray>()

        // Horizontal, Vertical, and Diagonal checks
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                board[row * columns + col]?.let { player ->
                    // Check horizontally
                    val winningPositions = mutableListOf<Int>()
                    if (col <= columns - consecutiveToWin && checkDirection(row, col, 0, 1, player)) {
                        (0 until consecutiveToWin).forEach { offset -> winningPositions.add(row * columns + col + offset) }
                        result.add(winningPositions.toIntArray())
                    }
                    // Check vertically
                    if (row <= rows - consecutiveToWin && checkDirection(row, col, 1, 0, player)) {
                        winningPositions.clear()
                        (0 until consecutiveToWin).forEach { offset -> winningPositions.add((row + offset) * columns + col) }
                        result.add(winningPositions.toIntArray())
                    }
                    // Check diagonal (down-right)
                    if (row <= rows - consecutiveToWin && col <= columns - consecutiveToWin && checkDirection(row, col, 1, 1, player)) {
                        winningPositions.clear()
                        (0 until consecutiveToWin).forEach { offset -> winningPositions.add((row + offset) * columns + col + offset) }
                        result.add(winningPositions.toIntArray())
                    }
                    // Check diagonal (up-right)
                    if (row >= consecutiveToWin - 1 && col <= columns - consecutiveToWin && checkDirection(row, col, -1, 1, player)) {
                        winningPositions.clear()
                        (0 until consecutiveToWin).forEach { offset -> winningPositions.add((row - offset) * columns + col + offset) }
                        result.add(winningPositions.toIntArray())
                    }
                }
            }
        }

        return result.toList()
    }


    private fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int, player: Player): Boolean {
        for (i in 0 until consecutiveToWin) {
            if (board[(row + i * dRow) * columns + (col + i * dCol)] != player) {
                return false
            }
        }
        return true
    }

    private fun checkGameStatus() : List<IntArray> {
        val results = checkForWinner()
        if (results.count() > 0) {
            status = GameStatus.Won
        } else if (!board.contains(null)) {
            status = GameStatus.Draw
        }
        return results
    }
}
