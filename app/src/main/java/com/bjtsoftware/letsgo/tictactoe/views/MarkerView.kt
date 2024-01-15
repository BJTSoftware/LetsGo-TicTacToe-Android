package com.bjtsoftware.letsgo.tictactoe.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.bjtsoftware.letsgo.tictactoe.models.Player

class MarkerView(context: Context, attrs: AttributeSet?) : TextView(context, attrs) {
    var player: Player? = null
        set(value) {
            field = value
            text = when (value) {
                Player.X -> "X"
                Player.O -> "O"
                else -> " "
            }
        }
}
