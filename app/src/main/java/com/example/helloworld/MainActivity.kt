package com.example.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "Hello World!"

        binding.buttonHello.setOnClickListener {
            binding.textView.text = "xin chào"
        }
    }
}
