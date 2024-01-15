package com.bjtsoftware.letsgo.tictactoe.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.GridLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bjtsoftware.letsgo.tictactoe.models.Player
import com.bjtsoftware.letsgo.tictactoe.viewmodels.TicTacToeViewModel

class GameBoardView(context: Context, attrs: AttributeSet?) : GridLayout(context, attrs) {
    var viewModel: TicTacToeViewModel? = null
        set(value) {
            field = value
            setupBoard()
            observeGameBoard()
        }

    var gridColor: Int =
        ContextCompat.getColor(context, android.R.color.black) // Default grid color
    var bgdColor: Int =
        ContextCompat.getColor(context, android.R.color.white) // Default background color

    private fun setupBoard() {
        columnCount = 3
        rowCount = 3
        setBackgroundColor(bgdColor) // Set background color
    }

    private fun createButton(position: Int): Button {
        return Button(context).apply {
            setBackgroundColor(gridColor) // Set button (cell) background color
            setOnClickListener { viewModel?.makeMove(position) }
        }
    }

    private fun observeGameBoard() {
        viewModel?.gameBoard?.observe(findViewTreeLifecycleOwner()!!, Observer { gameBoard ->
            removeAllViews()
            val cellSize = width / 3
            gameBoard.board.forEachIndexed { index, player ->
                addView(createButton(index).apply {
                    text = when (player) {
                        Player.X -> "X"
                        Player.O -> "O"
                        else -> ""
                    }
                    textSize = (cellSize / 4).toFloat() // Set dynamic text size

                    layoutParams = (layoutParams as GridLayout.LayoutParams).apply {
                        setMargins(1, 1, 1, 1) // Add margins to create grid lines
                        if (index % 3 == 0) marginStart =
                            0 // Remove left margin for the leftmost column
                        if (index % 3 == 2) marginEnd =
                            0 // Remove right margin for the rightmost column
                        if (index < 3) topMargin = 0 // Remove top margin for the top row
                        if (index > 5) bottomMargin = 0 // Remove bottom margin for the bottom row
                    }
                })
            }
        })
    }
}