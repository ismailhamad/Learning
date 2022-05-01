package com.example.learning.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.CourseAD
import com.example.learning.Adapter.LectureAD
import com.example.learning.Adapter.MyCourseAD
import com.example.learning.Adapter.exploerAD
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var ArrayListusers: ArrayList<users>
    lateinit var courseAD: CourseAD
    lateinit var exploerAD: exploerAD
    lateinit var myCourseAD: MyCourseAD
    lateinit var lectureAD: LectureAD
    lateinit var auth: FirebaseAuth
    var course: myCourse? = null
    var idCourse: String? = ""
    var users: users? = null
    var serach: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        BottomSheetBehavior.from(sheet).apply {
            peekHeight = 150
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val uuid = UUID.randomUUID()
        learningViewModel.getCourse()

        //  navBar.visibility=View.VISIBLE
        learningViewModel.getMyCourse()
        ArrayListusers = arrayListOf()
        auth = Firebase.auth
        setupReceycleView()
        setupReceycleView2()
        setupReceycleView3()
        setupReceycleView4()
        learningViewModel.MyCourse!!.observe(viewLifecycleOwner, Observer {
            myCourseAD.differ.submitList(it)
        })
        imageButton.setOnClickListener {
            val navBar: DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)
            navBar.open()
        }
        learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
            courseAD.differ.submitList(it)
            exploerAD.differ.submitList(it)
        })
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                users = item

            }
        })
        go_profile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileuserFragment)
        }
        courseAD.setOnItemClickListener { course, imageView, textView ->
            val extras = FragmentNavigatorExtras(
                imageView to course.image.toString(),
                textView to course.namecourse.toString()
            )
            val Bundle = Bundle().apply {
                putSerializable("course", course)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsCourseFragment,
                Bundle,
                null,
                extras
            )


        }

        exploerAD.setOnItemClickListener { course, imageView, textView ->

            val Bundle = Bundle().apply {
                putSerializable("course", course)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsCourseFragment,
                Bundle,
                null,
                null
            )


        }
        myCourseAD.setOnItemClickListener {
            textView7show.visibility = View.GONE
            learningViewModel.getLecture(it.id.toString())
            idCourse = it.id

            course = it
            learningViewModel.lecture?.observe(viewLifecycleOwner, Observer {

                lectureAD.differ.submitList(it)
            })
        }


        lectureAD.setOnItemClickListener { itLec ->

            val Bundle = Bundle().apply {
                putSerializable("watch", itLec)
                putSerializable("idCourse", course)
            }

            val indexLec = lectureAD.differ.currentList.indexOf(itLec)
            if (indexLec == 0) {
                val idLect = lectureAD.differ.currentList[indexLec].id
                learningViewModel.getUserShowLecture(idCourse!!, idLect!!)
                learningViewModel.showUserLecture(users!!, idCourse!!, itLec.id!!)

            } else {
                val idLect = lectureAD.differ.currentList[indexLec - 1].id
                learningViewModel.getUserShowLecture(idCourse!!, idLect!!)
                learningViewModel.showUserLecture(
                    users(users?.id, "a", "", "", 0),
                    idCourse!!,
                    itLec.id!!
                )
            }


            learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer { itlist ->
                itlist.forEach { iii ->
                    if (iii.name + iii.lastName == users?.name + users!!.lastName) {
                        Toast.makeText(activity, "ddddd", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_homeFragment_to_WCourseFragment,
                            Bundle
                        )

                        learningViewModel.showUserLecture(users!!, idCourse!!, itLec.id!!)


                    }
                    if (iii?.name == "a") {

                        Constants.showSnackBar(
                            view, "عليك مشاهدة المحاضرات السابقة أولا",
                            Constants.redColor
                        )
                    }
                }

            })

        }

        myCourseAD.setOnItemClickListener2 {
            if (course != null) {
                val Bundle = Bundle().apply {
                    putSerializable("chaat", course)
                }
                findNavController().navigate(R.id.action_homeFragment_to_chatFragment, Bundle)


            } else {
                Constants.showSnackBar(
                    view, "يجب عليك تحديد كورس",
                    Constants.redColor
                )
            }
        }













        Text_Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                learningViewModel.searchCourse(query.toString())
                learningViewModel.search?.observe(viewLifecycleOwner, Observer {
                    courseAD.differ.submitList(it)
                })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "" || newText == null) {
                    learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
                        courseAD.differ.submitList(it)
                    })
                }
                return true
            }

        })


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                myCourseAD.notifyDataSetChanged()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val Course = myCourseAD.differ.currentList[position]

                if (direction == ItemTouchHelper.UP) {
                    users?.let {
                        learningViewModel.deleteMyCourse(view, it, Course.id.toString())

                    }

                }


            }


        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rrr)
        }


    }


    fun setupReceycleView() {
        courseAD = CourseAD()

        rv_Course.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }

            adapter = courseAD
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun setupReceycleView2() {
        myCourseAD = MyCourseAD()

        rrr.apply {


            adapter = myCourseAD
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    fun setupReceycleView3() {
        exploerAD = exploerAD()

        rv_ex.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }

            adapter = exploerAD
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun setupReceycleView4() {
        lectureAD = LectureAD()

        rvv_lect.apply {


            adapter = lectureAD
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

