package com.example.learning.View.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learning.Adapter.showStudentAD
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.ChatActivity
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_bottom_sheet_techer.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_show__student.*
import kotlinx.android.synthetic.main.item_show_student.*


class show_StudentFragment : Fragment(R.layout.fragment_show__student) {
lateinit var learningViewModel: LearningViewModel
lateinit var showStudentAD: showStudentAD
    lateinit var auth: FirebaseAuth
    lateinit var userss:ArrayList<users>
val args:show_StudentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show__student, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val course = args.courseStudent
        auth = Firebase.auth
        userss = arrayListOf()
        setupReceyclview()
        learningViewModel.getStudentrCourse(auth.currentUser!!.uid, course.id.toString())
        learningViewModel.ShowstudentT?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                for (users in item.users!!){
                    users as HashMap<String,users>
                    if (users.get("id").toString()!=""){
                        animationView5.visibility = View.GONE
                        textView38.visibility = View.GONE
                        val items = users(users.get("id").toString(),
                            users.get("name").toString(),
                            users.get("lastName").toString(),
                            users.get("email").toString(),null)
                        userss.add(items)
                        showStudentAD.differ.submitList(userss)
                    }else{
                        animationView5.visibility = View.VISIBLE
                        textView38.visibility = View.VISIBLE
                    }

                }

            }

        })


        showStudentAD.setOnItemClickListener { user, imageView->
            imageView.setOnClickListener {

                val Bundle = Bundle().apply {
                        putSerializable("userSheet", user)
                    }
                    findNavController().navigate(
                        R.id.action_show_StudentFragment_to_bottomSheetTecher,
                        Bundle
                    )
            }
        }

        showStudentAD



    }

    fun setupReceyclview(){
        showStudentAD = showStudentAD()
        rv_showS.apply {
            adapter = showStudentAD
            layoutManager =GridLayoutManager(activity,2)
        }
    }

//    fun popupMenu(user:users){
//        val popup = PopupMenu(activity,img_menu_user)
//        popup.menuInflater.inflate(R.menu.menu_users,popup.menu)
//        popup.setOnMenuItemClickListener { item ->
//            when(item.itemId){
//                R.id.menu_chat -> {
//            val i =Intent(activity,ChatActivity::class.java)
//            i.putExtra("usersT",user)
//            startActivity(i)
//                }
//                R.id.menu_sendEmail -> {
//                    val Bundle = Bundle().apply {
//                        putSerializable("userEmail", user)
//                    }
//                    findNavController().navigate(
//                        R.id.action_show_StudentFragment_to_sendEmailFragment,
//                        Bundle
//                    )
//                }
//                R.id.menu_profile -> {
//
//                }
//            }
//            true
//        }
//        popup.show()
//    }

}