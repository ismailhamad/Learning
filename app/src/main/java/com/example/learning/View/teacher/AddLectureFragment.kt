package com.example.learning.View.teacher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learning.Constants.Constants
import com.example.learning.Model.lecture
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_lecture.*
import java.util.*


class AddLectureFragment : Fragment(R.layout.fragment_add_lecture) {
    lateinit var learningViewModel: LearningViewModel
    var videoUrl: Uri? = null
    var fileUri: Uri? = null
    val args: AddLectureFragmentArgs by navArgs()
    lateinit var idLecture: UUID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val course = args.idCourseL
        idLecture = UUID.randomUUID()


        Go_to_AddAssi.setOnClickListener {
            val Bundle = Bundle().apply {
                putString("idlectureAssi", idLecture.toString())
                putSerializable("idCourseAssi", course)
            }

            findNavController().navigate(
                R.id.action_addLectureFragment_to_addAssigmentFragment,
                Bundle
            )
        }

        Add_lect.setOnClickListener {
            var time = System.currentTimeMillis()
            Log.e("aaa", "time $time")
            if (videoUrl != null && fileUri == null) {
                if (Text_NameLecture.text.isNotEmpty() && Text_descriptionLecture.text.isNotEmpty()) {
                    learningViewModel.addLecture(
                        view,
                        lecture(
                            idLecture.toString(),
                            Text_NameLecture.text.toString(),
                            Text_descriptionLecture.text.toString(),
                            "",
                            time,
                            true,
                            videoUrl.toString()
                        ),
                        videoUrl,
                        Uri.parse(""),
                        course.id.toString(),
                        "5000"
                    )
                    cleanText()
                } else {
                    Constants.showSnackBar(
                        view, "إملا الحقول المطلوبة",
                        Constants.redColor
                    )
                }

            } else if (fileUri != null && videoUrl == null) {
                if (Text_NameLecture.text.isNotEmpty() && Text_descriptionLecture.text.isNotEmpty()) {
                    learningViewModel.addLecture(
                        view,
                        lecture(
                            idLecture.toString(),
                            Text_NameLecture.text.toString(),
                            Text_descriptionLecture.text.toString(),
                            "",
                            time,
                            true,
                            fileUri.toString()
                        ), Uri.parse(""),
                        fileUri, course.id.toString(), "4000"
                    )
                    cleanText()
                } else {
                    Constants.showSnackBar(
                        view, "إملا الحقول المطلوبة",
                        Constants.redColor
                    )
                }

            } else {
                if (Text_NameLecture.text.isNotEmpty() && Text_descriptionLecture.text.isNotEmpty()) {
                    learningViewModel.addLecture(
                        view,
                        lecture(
                            idLecture.toString(),
                            Text_NameLecture.text.toString(),
                            Text_descriptionLecture.text.toString(),
                            "",
                            time,
                            true,
                            videoUrl.toString(),
                            fileUri.toString()
                        ), videoUrl, fileUri, course.id.toString(), "6000"
                    )
                    cleanText()
                } else {
                    Constants.showSnackBar(
                        view, "إملا الحقول المطلوبة",
                        Constants.redColor
                    )
                }

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

    fun cleanText(){
        idLecture = UUID.randomUUID()
        Text_NameLecture.text = null
        Text_descriptionLecture.text = null
        Text_VideoLecture.text = null
        Text_pdfLecture.text = null

    }


}