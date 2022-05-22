package com.example.learning.View.student

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.gms.ads.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var mAdView : AdView
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
        MobileAds.initialize(activity) {}

        val adRequest = AdRequest.Builder().build()
        adViewHome.loadAd(adRequest)

        adViewHome.adListener = object: AdListener() {
            override fun onAdLoaded() {
                Log.e("aa","onAdLoaded")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.e("aa","onAdFailedToLoad  ${adError.message}")
            }

            override fun onAdOpened() {
                Log.e("aa","onAdOpened")

            }

            override fun onAdClicked() {
                Log.e("aa","onAdClicked")
            }

            override fun onAdClosed() {
                Log.e("aa","onAdClosed")
            }
        }

        val uuid = UUID.randomUUID()
        learningViewModel.getCourse()
        learningViewModel.getCourseExplore()

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
            rrr.adapter?.notifyDataSetChanged()
        })
        imageButton.setOnClickListener {
            val navBar: DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)

            navBar.open()

        }
        imageView15.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_profileuserFragment
            )
        }
        learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
            courseAD.differ.submitList(it)
            sizeCourse.text = "${it.size.toString()} courses, more coming"
//            exploerAD.differ.submitList(it)
        })
        learningViewModel.courseExplore!!.observe(viewLifecycleOwner, Observer {
            exploerAD.differ.submitList(it)
        })
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                users = item

            }
        })

//        val inflater = layoutInflater
//        val inflate_view = inflater.inflate(R.layout.header_nav,null)
//        val username = inflate_view.findViewById(R.id.textView13username) as TextView
//        username.text = "users?.name"

        courseAD.setOnItemClickListener { course, imageView, textView,Color ->
//            val extras = FragmentNavigatorExtras(
//                imageView to course.image.toString(),
//                textView to course.namecourse.toString()
//            )
            val Bundle = Bundle()
             Bundle.apply {
                putSerializable("course", course)
                putIntArray("color",Color)
            }

            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailsCourseFragment,
                    Bundle)            }



        }

        exploerAD.setOnItemClickListener { course, imageView, textView,Color ->

            val Bundle = Bundle()

            Bundle.apply {
                putSerializable("course", course)
                putIntArray("color",Color)
            }
            if (findNavController().currentDestination?.id == R.id.homeFragment){
                findNavController().navigate(
                    R.id.action_homeFragment_to_detailsCourseFragment,
                    Bundle)
            }



        }


        myCourseAD.setOnItemClickListener {
            textView7show.visibility = View.GONE
            learningViewModel.getLecture(it.id.toString())
            idCourse = it.id
            course = it
            learningViewModel.lecture?.observe(viewLifecycleOwner, Observer {
                animationView7.visibility = View.GONE
                textView42.visibility = View.GONE
                lectureAD.differ.submitList(it)
                if (it.isEmpty()){
                    lectureAD.differ.submitList(null)
                    animationView7.visibility = View.VISIBLE
                    textView42.visibility = View.VISIBLE
                }else{
                    animationView7.visibility = View.GONE
                    textView42.visibility = View.GONE
                    lectureAD.differ.submitList(it)
                }

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
//                learningViewModel.showUserLecture(
//                    users(users?.id, "a", "", "", 0),
//                    idCourse!!,
//                    itLec.id!!
//                )
            }


            learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer { itlist ->
                if (itlist !=null){
                    itlist.forEach { iii ->
                        if (iii.id  == users?.id) {
                            findNavController().navigate(
                                R.id.action_homeFragment_to_WCourseFragment,
                                Bundle
                            )

                            learningViewModel.showUserLecture(users!!, idCourse!!, itLec.id!!)


                        }else  {

                            Constants.showSnackBar(
                                view, "عليك مشاهدة المحاضرات السابقة أولا",
                                Constants.redColor
                            )
                        }
                    }
                }else{
                    Constants.showSnackBar(
                        view, "عليك مشاهدة المحاضرات السابقة أولا",
                        Constants.redColor
                    )
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
                    if (it == null){
                        courseAD.differ.submitList(null)
                        animationViewx.visibility =View.VISIBLE
                    }else{
                        animationViewx.visibility =View.GONE
                        courseAD.differ.submitList(it)

                    }
                })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "" || newText == null) {
                    animationViewx.visibility =View.GONE

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
               // myCourseAD.notifyDataSetChanged()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val Course = myCourseAD.differ.currentList[position]
                    lectureAD.differ.submitList(null)
                    users?.let {
                        learningViewModel.deleteMyCourse(view, it, Course.id!!)

                        animationView7.visibility = View.GONE
                        textView42.visibility = View.GONE


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

