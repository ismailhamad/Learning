package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel

class MainActivity : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}