package com.bjtsoftware.letsgo.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bjtsoftware.letsgo.tictactoe.viewmodels.TicTacToeViewModel
import com.example.letsgotictactoe.R
import com.example.letsgotictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TicTacToeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val gameBoardView = binding.gameBoardView
        gameBoardView.gridColor = ContextCompat.getColor(this, R.color.white)
        gameBoardView.borderColor = ContextCompat.getColor(this, R.color.black)
        gameBoardView.viewModel = viewModel
        // In MainActivity or similar

    }
}
