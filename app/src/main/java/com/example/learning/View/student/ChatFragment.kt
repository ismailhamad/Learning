package com.example.learning.View.student

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.ChatAdapter
import com.example.learning.Model.*
import com.example.learning.R


import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment(R.layout.fragment_chat) {
    lateinit var learningViewModel: LearningViewModel
    var userList = ArrayList<users>()
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = java.util.ArrayList<Chat>()
    val args: ChatFragmentArgs by navArgs()
    lateinit var rev: ArrayList<String>
    lateinit var auth: FirebaseAuth
    var course: myCourse? = null
    var imgUrl: Uri? = null
    lateinit var chatAdapter: ChatAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        chatactivityRecyclerView.layoutManager = LinearLayoutManager(activity)

        auth = Firebase.auth

        rev = arrayListOf()

        course = args.chaat

        btnSendMessageactiv.setOnClickListener {


            var message: String = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(activity, "message is empty", Toast.LENGTH_SHORT).show()
                etMessage.setText("")
            } else {
                rev.clear()
//                for (userid in course!!.users!!){
////                    val user =userid as HashMap<String,users>
//                    rev.add(userid.id.toString())
//                   val topic = "/topics/${userid.id.toString()}"
//                    PushNotification(
//                        NotificationData( "userName",message),
//                        topic).also {
//                        sendNotification(it)
//                    }
//                }

                learningViewModel.sendMessageCourse(
                    Chat(
                        UUID.randomUUID().toString(),
                        auth.currentUser!!.uid,
                        null,
                        etMessage.editableText.toString(),
                        course!!.id.toString(),
                        System.currentTimeMillis(), null
                    ), course!!.id.toString(), null
                )
                for (users in course?.users!!) {
                    if (users.id != "" && users.id != auth.currentUser!!.uid) {

                        val topic = "/topics/${users.id}"
                        PushNotification(
                            NotificationData("massge", etMessage.text.toString()),
                            topic
                        ).also {
                            learningViewModel.sendNotification(it)
                        }
                    }

                    etMessage.setText("")

                }


            }

        }

        learningViewModel.getMessageCourse(course?.id!!)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                chatAdapter = ChatAdapter(requireActivity(), it)
                chatactivityRecyclerView.adapter = chatAdapter
                chatactivityRecyclerView.scrollToPosition(it.size - 1);
                chatAdapter.setOnItemClickListener { chat, textView ->
                    textView.setOnLongClickListener(View.OnLongClickListener {
                        img_delete_message.visibility = View.VISIBLE
                        img_delete_message.setOnClickListener {

                            learningViewModel.deleteMessageCourse(course?.id!!, chat.id)
                            img_delete_message.visibility = View.INVISIBLE
                        }

                        true
                    })

                }
            })

        btnSendImage.setOnClickListener {
            openMedia()
        }
    }

    private fun openMedia() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            imgUrl = data.data
//            image_course.setImageURI(imgUrl)
            learningViewModel.sendMessageCourse(
                Chat(
                    UUID.randomUUID().toString(),
                    auth.currentUser!!.uid,
                    null,
                    null,
                    course!!.id.toString(),
                    System.currentTimeMillis(), imgUrl.toString()
                ), course!!.id.toString(), imgUrl
            )
        }
    }


}