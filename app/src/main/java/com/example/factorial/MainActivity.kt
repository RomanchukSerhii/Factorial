package com.example.factorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.calculateButton.setOnClickListener {
            viewModel.calculate(binding.etEnterNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            binding.progressBarLoading.visibility = View.GONE
            binding.calculateButton.isEnabled = true

            when (state) {
                is Error -> {
                    Toast.makeText(
                        this,
                        "You did not enter value",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.calculateButton.isEnabled = false
                }
                is Result -> {
                    binding.tvFactorialValue.text = state.factorial
                }
            }
        }
    }
}