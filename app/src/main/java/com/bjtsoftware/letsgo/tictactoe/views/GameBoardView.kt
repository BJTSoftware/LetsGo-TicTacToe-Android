package com.bjtsoftware.letsgo.tictactoe.views

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bjtsoftware.letsgo.tictactoe.models.Player
import com.bjtsoftware.letsgo.tictactoe.viewmodels.TicTacToeViewModel
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class GameBoardView(context: Context, attrs: AttributeSet?) : GridLayout(context, attrs) {
    var viewModel: TicTacToeViewModel? = null
        set(value) {
            field = value
            setupBoard()
            observeGameBoard()
        }

    var gridColor: Int = ContextCompat.getColor(context, android.R.color.white) // Cell background color
    var borderColor: Int = ContextCompat.getColor(context, android.R.color.black) // Grid line color

    init {
        setBackgroundColor(borderColor) // Set border (grid line) color
    }

    private fun setupBoard() {
        columnCount = 3
        rowCount = 3
        for (i in 0 until 9) {
            addView(createButton(i))
        }
        requestLayout() // Force layout update
        setBackgroundColor(borderColor)
    }

    private fun createButton(position: Int): TextView {
        return TextView(context).apply {
            setBackgroundColor(gridColor) // Set cell background color
            setOnClickListener { viewModel?.makeMove(position) }

            layoutParams = LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = spec(position % 3, 1f)
                rowSpec = spec(position / 3, 1f)

                // Setting margins to create grid lines:
                // - Add right margin for cells in the first two columns
                // - Add bottom margin for cells in the top two rows
                if (position % 3 != 0 ) marginStart = 15 // Right margin for the first two columns
                if (position / 3 != 0 && position / 3 < 3) topMargin = 15// Bottom margin for the top two rows

            }
        }
    }

    private val linePaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_red_dark)
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        viewModel?.winningPositions?.value?.let { winningPositions ->
            winningPositions.forEach { winningSet ->
                drawWinningLine(canvas, winningSet)
            }
        }
        super.onDraw(canvas)
    }


    private fun drawWinningLine(canvas: Canvas, winningSet: IntArray) {
        if (winningSet.size != 3) return // Ensure correct number of winning cells

        val cellSize = width / 3
        val path = Path()

        // Logic to determine start and end points of the line based on winningSet
// This part depends on how you've structured your winning sets
// For simplicity, let's assume winningSet contains cell indices (0 to 8)
        val firstCellIndex = winningSet[0]
        val lastCellIndex = winningSet[2]
        // Determine the row and column for the first and last cell
        val firstCellRow = firstCellIndex / 3
        val firstCellCol = firstCellIndex % 3
        val lastCellRow = lastCellIndex / 3
        val lastCellCol = lastCellIndex % 3

        // Calculate start and end points for the line
        val startX = (firstCellCol * cellSize + cellSize / 2).toFloat()
        val startY = (firstCellRow * cellSize + cellSize / 2).toFloat()
        val endX = (lastCellCol * cellSize + cellSize / 2).toFloat()
        val endY = (lastCellRow * cellSize + cellSize / 2).toFloat()

        // Draw the line
        path.moveTo(startX, startY)
        path.lineTo(endX, endY)
        canvas.drawPath(path, linePaint)
    }

    private fun observeGameBoard() {
        viewModel?.gameBoard?.observe(findViewTreeLifecycleOwner()!!, { gameBoard ->
            removeAllViews() // Clear previous views
            // Draw the winning line first
            invalidate() // This will call onDraw and draw the line

            // Then add the TextViews for X's and O's
            val cellSize = width / 3
            gameBoard.board.forEachIndexed { index, player ->
                addView(createButton(index).apply {
                    text = when (player) {
                        Player.X -> "X"
                        Player.O -> "O"
                        else -> ""
                    }
                    textAlignment = TEXT_ALIGNMENT_CENTER
                    textSize = (cellSize / 4).toFloat() // Set dynamic text size
                    setTextColor(borderColor)
                })
            }
        })
    }

}
