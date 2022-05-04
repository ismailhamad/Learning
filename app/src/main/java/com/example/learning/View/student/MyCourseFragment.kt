package com.example.learning.View.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.LectureAD
import com.example.learning.Adapter.MyCourseAD
import com.example.learning.Constants.Constants
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_my_course.*


class MyCourseFragment : Fragment(R.layout.fragment_my_course) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var myCourseAD: MyCourseAD
    lateinit var lectureAD: LectureAD
    var course: myCourse? = null
    var users:users?=null
     var idCourse:String?=""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
//        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
//        navBar.visibility=View.VISIBLE
            learningViewModel.getMyCourse()
            setupRecycleView()
            setupRecycleView2()
            learningViewModel.MyCourse!!.observe(viewLifecycleOwner, Observer {
                myCourseAD.differ.submitList(it)
            })
            myCourseAD.setOnItemClickListener {

                textView7.visibility = View.GONE
                learningViewModel.getLecture(it.id.toString())
                idCourse = it.id

                course = it
                learningViewModel.lecture?.observe(viewLifecycleOwner, Observer {
                    lectureAD.differ.submitList(it)
                })
            }



        learningViewModel.users!!.observe(viewLifecycleOwner, Observer {
            for (item in  it){
                users = item
            }
        })
        lectureAD.setOnItemClickListener { itLec ->

            val Bundle = Bundle().apply {
                putSerializable("watch", itLec)
                putSerializable("idCourse",course)
            }

            val indexLec = lectureAD.differ.currentList.indexOf(itLec)
            if (indexLec == 0){
                val idLect = lectureAD.differ.currentList[indexLec].id
                learningViewModel.getUserShowLecture(idCourse!!, idLect!!)
                learningViewModel.showUserLecture(users!!, idCourse!!, itLec.id!!)

            }else{
                val idLect = lectureAD.differ.currentList[indexLec-1].id
                learningViewModel.getUserShowLecture(idCourse!!, idLect!!)
                learningViewModel.showUserLecture(users(users?.id,"a","","",0), idCourse!!, itLec.id!!)
            }


            learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer { itlist ->
                itlist.forEach { iii ->
                    if (iii.name+iii.lastName == users?.name+users!!.lastName) {
                        findNavController().navigate(
                            R.id.action_myCourseFragment_to_WCourseFragment,
                            Bundle
                        )

                        learningViewModel.showUserLecture(users!!, idCourse!!, itLec.id!!)


                    }
                    if(iii?.name=="a") {

                        Constants.showSnackBar(
                            view, "عليك مشاهدة المحاضرات السابقة أولا",
                            Constants.redColor
                        )
                    }
                }

            })

        }




        Go_Chat.setOnClickListener {
            if (course != null) {
                val Bundle = Bundle().apply {
                    putSerializable("chaat", course)
                }
              findNavController().navigate(R.id.action_myCourseFragment_to_chatFragment, Bundle)


            } else {
                Constants.showSnackBar(
                    view, "يجب عليك تحديد كورس",
                    Constants.redColor
                )
            }
        }






        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val Course = myCourseAD.differ.currentList[position]

                if (direction == ItemTouchHelper.UP) {
                    lectureAD.differ.submitList(null)
                    users?.let {
                        learningViewModel.deleteMyCourse(view,
                            it,Course.id!!)
                    }


                }


            }


        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_MyCourse)
        }


    }

    fun setupRecycleView() {
        myCourseAD = MyCourseAD()
        rv_MyCourse.apply {
            adapter = myCourseAD
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun setupRecycleView2() {
        lectureAD = LectureAD()
        rv_Lect.apply {
            adapter = lectureAD
            layoutManager = LinearLayoutManager(activity)
        }
    }
    companion object{
        var bb :Boolean=false
    }


}