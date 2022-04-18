package com.example.learning.View

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_w_course.*


class WCourseFragment : Fragment(R.layout.fragment_w_course) {
    lateinit var learningViewModel: LearningViewModel
    val args: WCourseFragmentArgs by navArgs()
    var mydownload: Long = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE
        val lecture = args.watch
        nameLect.text = lecture.name
        dec_lect.text = lecture.description
//        videoView.setVideoURI(Uri.parse(lecture.video))

        val player = ExoPlayer.Builder(requireActivity()).build()
        videoView.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse("${lecture.video}"))
        player.setMediaItem(mediaItem)
        player.prepare()


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

        }



    }

}