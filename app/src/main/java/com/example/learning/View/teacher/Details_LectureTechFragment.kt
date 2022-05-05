package com.example.learning.View.teacher

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Adapter.assigmentAD
import com.example.learning.Constants.Constants
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Firebase.Teacher.FirebaseSourceLectureTR
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_details__lecture_tech.*


class Details_LectureTechFragment : Fragment(R.layout.fragment_details__lecture_tech) {
lateinit var learningViewModel: LearningViewModel
lateinit var assigmentAD: assigmentAD
    lateinit var db: FirebaseFirestore

val args:Details_LectureTechFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Teacher).learningViewModel
        val lecture = args.detailsLecture
        val iamge = args.imageCourse
        val idCourse = args.idcourse
        nameLectTech.text=lecture.name
        dec_lectTech.text=lecture.description
        Glide.with(this).load(iamge).into(imgeLectureTech)
        setupRecyclview()
        lecture.id?.let { learningViewModel.getAssignment(idCourse.id.toString(), it) }


        val player = ExoPlayer.Builder(requireActivity()).build()
        videoViewTech.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse("${lecture.video}"))
        player.setMediaItem(mediaItem)
        player.prepare()
        val firebaseSourceLectureTR= FirebaseSourceLectureTR(requireActivity())
        db = Firebase.firestore
        var count = 0
        db.collection("courses/${idCourse.id!!}/lecture/${lecture.id!!}/users")
            .addSnapshotListener { value, error ->
                count = value!!.size()
                tv_countSeeLec.text = count.toString()
            }
//        Log.e("aaa","count user ${firebaseSourceLectureTR.getCountUserShowLecture(idCourse.id!!,lecture.id!!).value}")
        Log.e("aaa","idCourse ${idCourse.id}")
        Log.e("aaa","lecture.id!! ${lecture.id!!}")
        learningViewModel.getCountUserShowLecture(idCourse.id!!,lecture.id!!)
//        Log.e("aaa","count user ${learningViewModel.countUser!!.value}")
        Log.e("aaa","count user ${firebaseSourceLectureTR.getCountUserShowLecture(idCourse.id!!,lecture.id!!)}")

        if (lecture.file==""){
            pdfTech.visibility=View.GONE
        }else{
            pdfTech.visibility=View.VISIBLE
        }


        assigmentAD.setOnItemClickListener {
            val Bundle = Bundle().apply {
                putString("idcourse",idCourse.id)
                putString("idlecture",lecture.id)
                putString("idassi",it.id)
            }
            findNavController().navigate(R.id.action_details_LectureTechFragment_to_allUserAssigmentFragment,Bundle)
        }




        pdfTech.setOnClickListener {

            var request= DownloadManager.Request(Uri.parse(lecture.file))
                .setTitle("Download")
                .setDescription("Download a ${lecture.name}")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
            val imageurl: String = lecture.file.toString()

            val dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm!!.enqueue(request)
            Constants.showSnackBar(
                view,
                "جاري تحميل الملف",
                Constants.greenColor
            )
        }
        learningViewModel.assignment!!.observe(viewLifecycleOwner, Observer {

            assigmentAD.differ.submitList(it)
        })
        Go_to_addAssigment.setOnClickListener {
            val Bundle = Bundle().apply {
                putString("idlectureAssi", lecture.id)
                putSerializable("idCourseAssi", idCourse)
            }
            findNavController().navigate(R.id.action_details_LectureTechFragment_to_addAssigmentFragment,Bundle)
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
                val assigment=assigmentAD.differ.currentList[position]
                if (direction == ItemTouchHelper.LEFT){
               learningViewModel.deleteAssignment(view,idCourse.id.toString(),lecture.id.toString(),assigment.id.toString())
                }else if (direction == ItemTouchHelper.RIGHT){
                    val Bundle =Bundle().apply {
                        putSerializable("assigmentt",assigment)
                        putString("idcoursee",idCourse.id.toString())
                        putString("idlecturee",lecture.id.toString())

                    }
                    findNavController().navigate(R.id.action_details_LectureTechFragment_to_updateAssigmentFragment,Bundle)


                }


            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_assig)
        }






    }
    fun setupRecyclview(){
        assigmentAD = assigmentAD()
        rv_assig.apply {
            adapter = assigmentAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}