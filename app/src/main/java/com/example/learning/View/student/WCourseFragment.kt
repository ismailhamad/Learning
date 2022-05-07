package com.example.learning.View.student

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.assigmentAD
import com.example.learning.Constants.Constants
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.ChatActivity
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.android.synthetic.main.fragment_w_course.*


class WCourseFragment : Fragment(R.layout.fragment_w_course) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var assigmentAD: assigmentAD
    val args: WCourseFragmentArgs by navArgs()
    var mydownload: Long = 0
    var users: users? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel

        val lecture = args.watch
        val idCourse = args.idCourse
        setupRecyclview()
        nameLect.text = lecture.name
        dec_lect.text = lecture.description
        val navBar: DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navBar.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        val player = ExoPlayer.Builder(requireActivity()).build()
        videoView.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse("${lecture.video}"))
        player.setMediaItem(mediaItem)
        player.prepare()
        learningViewModel.getAssignment(idCourse.id.toString(), lecture.id.toString())

        learningViewModel.assignment?.observe(viewLifecycleOwner, Observer {
            assigmentAD.differ.submitList(it)
        })
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                users = item
            }

        })



        ask_techer.setOnClickListener {
            val i = Intent(activity, ChatActivity::class.java)
            i.putExtra("id", users)
            i.putExtra("course", idCourse)
            startActivity(i)
        }


        assigmentAD.setOnItemClickListener {
            learningViewModel.getuserAddAssigment(
                idCourse.id.toString(),
                lecture.id.toString(),
                it.id.toString()
            )
            val Bundle = Bundle().apply {
                putSerializable("assigment", it)
                putString("idlecture", lecture.id)
                putString("idcoursee", idCourse.id.toString())

            }
            findNavController().navigate(
                R.id.action_WCourseFragment_to_detailsAssigmentFragment,
                Bundle
            )
        }

        if (lecture.file == "") {
            pdf.visibility = View.GONE
        } else {
            pdf.visibility = View.VISIBLE
        }
        pdf.setOnClickListener {

            var request = DownloadManager.Request(Uri.parse(lecture.file))
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


    }

    fun setupRecyclview() {
        assigmentAD = assigmentAD()
        recyclerView2.apply {
            adapter = assigmentAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}