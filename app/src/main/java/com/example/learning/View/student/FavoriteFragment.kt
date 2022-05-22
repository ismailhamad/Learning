package com.example.learning.View.student

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
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
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
            if (it == null){
                animationView11.visibility = View.VISIBLE
                textView43.visibility = View.VISIBLE
            }else{
                animationView11.visibility = View.GONE
                textView43.visibility = View.GONE
                favoriteAD.differ.submitList(it)
            }
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
            val deleteColor = ContextCompat.getColor(context!!,R.color.redd)
            val deleteicon = R.drawable.ic_baseline_delete_24
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

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView,viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(deleteColor)
                    .addSwipeLeftActionIcon(deleteicon)
                    .addSwipeRightBackgroundColor(deleteColor)
                    .addSwipeRightActionIcon(deleteicon)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
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