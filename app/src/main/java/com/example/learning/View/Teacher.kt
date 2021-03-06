package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_add_course.*

class Teacher : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
//        val navView: BottomNavigationView =findViewById(R.id.bottomNavigationView2)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController
       // navView.setupWithNavController(navController)


        val repository = LearningRepository(FirebaseSource(this,findViewById(android.R.id.content)),this)
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)




        learningViewModel.getUser()









    }
}