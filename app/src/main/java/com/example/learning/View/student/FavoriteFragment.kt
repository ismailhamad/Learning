package com.example.learning.View.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        learningViewModel.getFavorite(auth.currentUser!!.uid)
        setupReceycleView()
        learningViewModel.favorite?.observe(viewLifecycleOwner, Observer {
            favoriteAD.differ.submitList(it)
            rv_fav.adapter?.notifyDataSetChanged()
        })


        favoriteAD.setOnItemClickListener { course, imageView, textView, color ->
            val Bundle = Bundle().apply {
                putSerializable("course", course)
                putIntArray("color",color)
            }
            findNavController().navigate(R.id.action_favoriteFragment_to_detailsCourseFragment,Bundle)
        }



        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val favorite=favoriteAD.differ.currentList[position]

        learningViewModel.deleteFavorite(view,auth.currentUser!!.uid.toString(),favorite.id.toString())


            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_fav)
        }


    }


    fun setupReceycleView() {
        favoriteAD = favoriteAD()
        rv_fav.apply {
            adapter = favoriteAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}