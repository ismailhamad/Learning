package com.example.learning.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.R
import kotlinx.android.synthetic.main.activity_add_lecture.*
import kotlinx.android.synthetic.main.fragment_add_course.*

class AddLecture : AppCompatActivity() {
    var videoUrl: Uri? = null
    var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lecture)
        //        val player = ExoPlayer.Builder(this).build()
//        val mediaItem: MediaItem =
//            MediaItem.fromUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/learning-c0b44.appspot.com/o/video%2F751b79d8-3aef-49e3-b5a1-91ca25282637?alt=media&token=dd2ef2dc-dc45-4559-ab97-c059c311c7bd"))
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
        var firebaseSource = FirebaseSource(this,findViewById(android.R.id.content))

        video_addLecture.setOnClickListener {
            chooseVideo()
        }
        img_AddFile.setOnClickListener {
            chooseFile()
        }
//        btn_addLecture.setOnClickListener {
//            Log.e("aa","fileUri ${fileUri}")
//            Log.e("aa","videoUrl ${videoUrl}")
//            if (videoUrl != null && fileUri == null) {
//                firebaseSource.addLecture(
//                    lecture(
//                        UUID.randomUUID().toString(),
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        videoUrl.toString(),
//                        true
//                    ), videoUrl,
//                    Uri.parse(""),"56f78e25-031e-41d2-9ce9-68115fa60b2c","5000"
//                )
//            } else if (fileUri != null && videoUrl == null) {
//                firebaseSource.addLecture(
//                    lecture(
//                        UUID.randomUUID().toString(),
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        "",
//                        fileUri.toString()
//                    ), Uri.parse(""),
//                    fileUri,"56f78e25-031e-41d2-9ce9-68115fa60b2c","4000"
//                )
//            } else {
//                firebaseSource.addLecture(
//                    lecture(
//                        UUID.randomUUID().toString(),
//                        ed_nameVideo.editText!!.text.toString(),
//                        ed_descriptionVideo.editText!!.text.toString(),
//                        videoUrl.toString(),
//                        fileUri.toString()
//                    ), videoUrl, fileUri,"56f78e25-031e-41d2-9ce9-68115fa60b2c","6000"
//                )
//            }
//        }
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