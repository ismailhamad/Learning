package com.example.learning.View.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.android.material.navigation.NavigationView

class Student : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val navView: NavigationView =findViewById(R.id.bottomNavigationView)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        val repository = LearningRepository(FirebaseSource(this, findViewById(android.R.id.content)),this)
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)

        learningViewModel.getUser()
    }

}