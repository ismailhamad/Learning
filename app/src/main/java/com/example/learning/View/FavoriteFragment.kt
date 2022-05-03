package com.example.learning.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.LectureAD
import com.example.learning.Adapter.favoriteAD
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var favoriteAD: favoriteAD
    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        auth = Firebase.auth
        setupReceycleView()


    }


    fun setupReceycleView() {
        favoriteAD = favoriteAD()
        rv_fav.apply {
            adapter = favoriteAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}