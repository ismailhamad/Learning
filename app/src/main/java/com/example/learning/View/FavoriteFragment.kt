package com.example.learning.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.LectureAD
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var lectureAD: LectureAD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        setupReceycleView()
//        learningViewModel.getLecture("93da687e-4aea-4420-96c6-3f0ed3f222d2")
//        learningViewModel.lecture!!.observe(viewLifecycleOwner, Observer {
//            lectureAD.differ.submitList(it)
//        })
    }


    fun setupReceycleView() {
        lectureAD = LectureAD()
        rv_lecture.apply {
            adapter = lectureAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}