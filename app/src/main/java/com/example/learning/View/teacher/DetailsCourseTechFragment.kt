package com.example.learning.View.teacher

import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Adapter.LectureTecAD
import com.example.learning.Model.course
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_details_course.*
import kotlinx.android.synthetic.main.fragment_details_course_tech.*


class DetailsCourseTechFragment : Fragment(R.layout.fragment_details_course_tech) {

lateinit var learningViewModel: LearningViewModel
lateinit var LectureTecAD: LectureTecAD
lateinit var course:course
 val args: DetailsCourseTechFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
learningViewModel =(activity as Teacher).learningViewModel
//        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
//        navBar.visibility=View.GONE
         course = args.courseTech
        val color = args.color
        val gd = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(-0x9e9d9f, -0xececed)
        )
        gd.setColors(color)
        cardView16.background = gd
        gd.cornerRadius =50f
        cardView16.elevation = 200f
       learningViewModel.getLectureTecher(course.id.toString())
        Glide.with(this).load(course.image).into(image_D_T)
        setupReceycleview()
     //   image_D_T.setImageURI(Uri.parse(course.image))
        nameCorseT.text =course.namecourse
        learningViewModel.lectureT!!.observe(viewLifecycleOwner, Observer {
            LectureTecAD.differ.submitList(it)
            rv_lectureTech.adapter?.notifyDataSetChanged()
        })
        Go_Add_Lect.setOnClickListener {
            val Bundle=Bundle().apply {
                putSerializable("idCourseL",course)

            }
   findNavController().navigate(R.id.action_detailsCourseTechFragment_to_addLectureFragment,Bundle)
        }

        imageButton2.setOnClickListener {
            findNavController().navigateUp()
        }
        LectureTecAD.setOnItemClickListener {
            val Bundle=Bundle().apply {
                putSerializable("detailsLecture",it)
                putString("imageCourse",course.image)
                putSerializable("idcourse",course)

            }
            findNavController().navigate(R.id.action_detailsCourseTechFragment_to_details_LectureTechFragment,Bundle)
        }

        LectureTecAD.setOnItemClickListener2 {
            learningViewModel.seeLecture(view,course.id.toString(),it.id.toString())
            //Toast.makeText(activity, "ddddddddd", Toast.LENGTH_SHORT).show()
        }

        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            val deleteColor =ContextCompat.getColor(context!!,R.color.redd)
            val updateColor =ContextCompat.getColor(context!!,R.color.green)
            val deleteicon = R.drawable.ic_baseline_delete_24
            val updateicon = R.drawable.ic_baseline_edit_24
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val lecture=LectureTecAD.differ.currentList[position]
               if (direction == ItemTouchHelper.LEFT){
                   learningViewModel.deleteLecture(view,course.id.toString(),lecture.id.toString())

               }else if (direction == ItemTouchHelper.RIGHT){

                   val Bundle= Bundle().apply {
                       putSerializable("updateLecture",lecture)
                       putString("idcoursee",course.id)
                   }
                   findNavController().navigate(R.id.action_detailsCourseTechFragment_to_updateLectureFragment,Bundle)
               }
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
                    .addSwipeRightBackgroundColor(updateColor)
                    .addSwipeRightActionIcon(updateicon)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }






        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_lectureTech)
        }


        Go_showStudent.setOnClickListener {
            val Bundle = Bundle().apply {
                putSerializable("courseStudent",course)
            }
            findNavController().navigate(R.id.action_detailsCourseTechFragment_to_show_StudentFragment,Bundle)
        }



















    }




   fun setupReceycleview(){
       LectureTecAD = LectureTecAD()
       rv_lectureTech.apply {
           adapter = LectureTecAD
           layoutManager = LinearLayoutManager(activity)
       }
   }


}