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
import com.example.learning.Model.lecture
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_lecture.*
import kotlinx.android.synthetic.main.fragment_update_lecture.*


class UpdateLectureFragment : Fragment(R.layout.fragment_update_lecture) {
lateinit var learningViewModel: LearningViewModel
val args:UpdateLectureFragmentArgs by navArgs()
    lateinit var lecturee:lecture
    var videoUrl: Uri? = null
    var fileUri: Uri? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
         lecturee = args.updateLecture
        val idcourse = args.idcoursee
        Text_NameLectureUp.append(lecturee.name)
        Text_descriptionLectureUp.append(lecturee.description)
        Text_VideoLectureUp.append(lecturee.video)
        Text_pdfLectureUp.append(lecturee.file)

        imageButt10.setOnClickListener {
            findNavController().navigateUp()
        }
        update.setOnClickListener {
            var time = System.currentTimeMillis()
            val newLecture = lecture(lecturee.id.toString(),Text_NameLectureUp.text.toString(),Text_descriptionLectureUp.text.toString(),"",time,true,Text_VideoLectureUp.text.toString(),Text_pdfLectureUp.text.toString())

            if (videoUrl != null && fileUri == null) {
                learningViewModel.updateLecture(view,newLecture,
                    videoUrl,null,idcourse,lecturee.id.toString(),"5000")
            }else if (fileUri != null && videoUrl == null) {
                learningViewModel.updateLecture(view,newLecture,null,fileUri,idcourse,lecturee.id.toString(),"4000")

            }else if (fileUri != null && videoUrl != null){
                learningViewModel.updateLecture(view,newLecture,videoUrl,fileUri,idcourse,lecturee.id.toString(),"7000")
            }else{
                learningViewModel.updateLecture(view, lecture(
                    lecturee.id.toString(),
                    Text_NameLectureUp.text.toString(),
                    Text_descriptionLectureUp.text.toString(),
                    "",
                    time,
                    true,
                    checkVideoUrl().toString(),
                    checkFileUrl().toString()
                ),checkVideoUrl(),checkFileUrl(),idcourse,lecturee.id.toString(),"8000")

            }

        }

        Text_VideoLectureUp.setOnClickListener {
            Text_VideoLectureUp.text.clear()
            chooseVideo()

        }
        Text_pdfLectureUp.setOnClickListener {
            Text_pdfLectureUp.text.clear()
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
            Text_VideoLectureUp.append(videoUrl.toString())

        } else if (requestCode == 4000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
        }
    }

    fun checkVideoUrl(): Uri {
        if (videoUrl == null) {
            videoUrl = Uri.parse(lecturee.video)
        } else {
            videoUrl
        }
        return videoUrl!!
    }
    fun checkFileUrl(): Uri {
        if (fileUri == null) {
            fileUri = Uri.parse(lecturee.file)
        } else {
            fileUri
        }
        return fileUri!!
    }



}