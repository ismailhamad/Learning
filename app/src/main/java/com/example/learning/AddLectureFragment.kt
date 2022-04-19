package com.example.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.learning.Model.lecture
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_lecture.*
import java.util.*


class AddLectureFragment : Fragment(R.layout.fragment_add_lecture) {
lateinit var learningViewModel: LearningViewModel
    var videoUrl: Uri? = null
    var fileUri: Uri? = null
    val args:AddLectureFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Teacher).learningViewModel
val lecture=args.idCourseL

        Add_lect.setOnClickListener {
            if (videoUrl != null && fileUri == null) {
                learningViewModel.addLecture(lecture(UUID.randomUUID().toString(),Text_NameLecture.text.toString(),Text_descriptionLecture.text.toString(),"",true,videoUrl.toString()),videoUrl,
                    Uri.parse(""),lecture,"5000")
            } else if (fileUri != null && videoUrl == null) {
                learningViewModel.addLecture(
                    lecture(
                        UUID.randomUUID().toString(),
                        Text_NameLecture.text.toString(),Text_descriptionLecture.text.toString(),"",true,
                        fileUri.toString()
                    ), Uri.parse(""),
                    fileUri,lecture,"4000"
                )
            } else {
                learningViewModel.addLecture(
                    lecture(
                        UUID.randomUUID().toString(),
                        Text_NameLecture.text.toString(),Text_descriptionLecture.text.toString(),"",true,
                        videoUrl.toString(),
                        fileUri.toString()
                    ), videoUrl, fileUri,lecture,"6000"
                )
            }
        }


        Text_VideoLecture.setOnClickListener {
            chooseVideo()
        }
        Text_pdfLecture.setOnClickListener {
            chooseFile()
        }
    }



    private fun chooseVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 5000)
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "application/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 4000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            videoUrl = data.data
          Text_VideoLecture.append(videoUrl.toString())

        } else if (requestCode == 4000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            Text_pdfLecture.append(fileUri.toString())
        }
    }


}