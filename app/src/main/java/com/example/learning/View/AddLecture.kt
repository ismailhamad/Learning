package com.example.learning.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.lecture
import com.example.learning.R
import kotlinx.android.synthetic.main.activity_add_lecture.*
import java.util.*

class AddLecture : AppCompatActivity() {
    var videoUrl: Uri? = null
    var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lecture)
        val idCourse = intent.getStringExtra("idCourse")

        var firebaseSource = FirebaseSource(this)

        video_addLecture.setOnClickListener {
            chooseVideo()
        }
        img_AddFile.setOnClickListener {
            chooseFile()
        }
        btn_addLecture.setOnClickListener {
            if (videoUrl != null && fileUri == null) {
                firebaseSource.addLecture(
                    lecture(
                        UUID.randomUUID().toString(),
                        ed_nameVideo.editText!!.text.toString(),
                        ed_descriptionVideo.editText!!.text.toString(),
                        "",
                        true,
                        videoUrl.toString(),
                        ""
                    ), videoUrl,
                    Uri.parse(""),"93da687e-4aea-4420-96c6-3f0ed3f222d2","5000"
                )
            } else if (fileUri != null && videoUrl == null) {
                firebaseSource.addLecture(
                    lecture(
                        UUID.randomUUID().toString(),
                        ed_nameVideo.editText!!.text.toString(),
                        ed_descriptionVideo.editText!!.text.toString(),
                        "",
                        true,
                        fileUri.toString()
                    ), Uri.parse(""),
                    fileUri,"93da687e-4aea-4420-96c6-3f0ed3f222d2","4000"
                )
            } else {
                firebaseSource.addLecture(
                    lecture(
                        UUID.randomUUID().toString(),
                        ed_nameVideo.editText!!.text.toString(),
                        ed_descriptionVideo.editText!!.text.toString(),
                        "",
                        true,
                        videoUrl.toString(),
                        fileUri.toString()
                    ), videoUrl, fileUri,"93da687e-4aea-4420-96c6-3f0ed3f222d2","6000"
                )
            }
        }

//        btn_updateLecture.setOnClickListener {
//            if (videoUrl != null && fileUri == null) {
//                firebaseSource.updateLecture(
//                    lecture(
//                        "a9746e16-2141-4279-bd5d-0acc46aadcf2",
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        videoUrl.toString(),
//                        ""
//                    ), videoUrl,
//                    Uri.parse(""),"93da687e-4aea-4420-96c6-3f0ed3f222d2","18953316-08b5-4cec-b48e-d8393ae0e1a3","5000"
//                )
//            } else if (fileUri != null && videoUrl == null) {
//                firebaseSource.updateLecture(
//                    lecture(
//                        "a9746e16-2141-4279-bd5d-0acc46aadcf2",
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        "",
//                        fileUri.toString()
//                    ), Uri.parse(""),
//                    fileUri,"93da687e-4aea-4420-96c6-3f0ed3f222d2","18953316-08b5-4cec-b48e-d8393ae0e1a3","4000"
//                )
//            } else {
//                firebaseSource.updateLecture(
//                    lecture(
//                        "a9746e16-2141-4279-bd5d-0acc46aadcf2",
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        videoUrl.toString(),
//                        fileUri.toString()
//                    ), videoUrl, fileUri,"93da687e-4aea-4420-96c6-3f0ed3f222d2","18953316-08b5-4cec-b48e-d8393ae0e1a3","6000"
//                )
//            }
//        }
        btn_delLecture.setOnClickListener {
            firebaseSource.seeLecture("93da687e-4aea-4420-96c6-3f0ed3f222d2","1af2fe1b-4df6-4b36-8bb0-7cf06792af0d")
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
        if (requestCode == 5000 && resultCode == RESULT_OK && data != null && data.data != null) {
            videoUrl = data.data


        } else if (requestCode == 4000 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
        }
    }
}