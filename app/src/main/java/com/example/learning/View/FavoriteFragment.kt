package com.example.learning.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
lateinit var learningViewModel: LearningViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
    }

}