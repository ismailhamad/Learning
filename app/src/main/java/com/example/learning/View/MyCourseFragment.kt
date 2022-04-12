package com.example.learning.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.MyCourseAD
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_my_course.*


class MyCourseFragment : Fragment(R.layout.fragment_my_course) {
lateinit var learningViewModel: LearningViewModel
lateinit var myCourseAD: MyCourseAD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel= (activity as Student).learningViewModel

        learningViewModel.getMyCourse()
        setupRecycleView()
        learningViewModel.MyCourse!!.observe(viewLifecycleOwner, Observer {
            myCourseAD.differ.submitList(it)
        })

    }

fun setupRecycleView(){
   myCourseAD = MyCourseAD()
    rv_MyCourse.apply {
        adapter = myCourseAD
        layoutManager = LinearLayoutManager(activity)
    }
}
}