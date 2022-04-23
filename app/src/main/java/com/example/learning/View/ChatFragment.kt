package com.example.learning.View

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.ChatAdapter
import com.example.learning.Model.*
import com.example.learning.R
import com.example.learning.Notification.RetrofitInstance


import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatFragment : Fragment(R.layout.fragment_chat) {
 lateinit var learningViewModel: LearningViewModel
    var userList = ArrayList<users>()
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = java.util.ArrayList<Chat>()
    val args:ChatFragmentArgs by navArgs()
    lateinit var rev:ArrayList<String>
    lateinit var auth: FirebaseAuth
var course:myCourse?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
        chatRecyclerView.layoutManager = LinearLayoutManager(activity)
      auth = Firebase.auth

     rev= arrayListOf()

        course=args.chaat

        btnSendMessage.setOnClickListener {


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
          learningViewModel.sendMessageCourse(Chat(UUID.randomUUID().toString(), auth.uid.toString(), null, etMessage.editableText.toString(), course!!.id.toString())
          ,course!!.id.toString())
                etMessage.setText("")
            }

        }

//
////            val user =usrs as HashMap<String,users>
//          readMessage(auth.currentUser!!.uid, rev,course?.id.toString())
//        Toast.makeText(activity, "${course!!.id}", Toast.LENGTH_SHORT).show()
////
        learningViewModel.getMessageCourse(course?.id!!).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val chatAdapter = ChatAdapter(requireActivity(),it)
                chatRecyclerView.adapter = chatAdapter

        })

    }
//    private fun sendMessage(senderId: String, receiverId: ArrayList<String>, message: String,idcorse:String) {
//        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
//
//        var hashMap: HashMap<String, Any> = HashMap()
//        hashMap.put("senderId", senderId)
//        hashMap.put("receiverId", receiverId)
//        hashMap.put("idcorse", idcorse)
//        hashMap.put("message", message)
//
//        reference!!.child("Chat").push().setValue(hashMap)
//
//    }
//
//
//    fun readMessage(senderId: String, receiverId: ArrayList<String>,idcorse: String) {
//        val databaseReference: DatabaseReference =
//            FirebaseDatabase.getInstance().getReference("Chat")
//
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                chatList.clear()
//                for (dataSnapShot: DataSnapshot in snapshot.children) {
//                    val chat = dataSnapShot.getValue(Chat::class.java)
//
//                  chatList.add(chat!!)
//
//
//
////                        for (i in receiverId) {
////
////                            if (chat!!.senderId.equals(i) && chat!!.receiverId!!.equals(
////                                    receiverId
////                                ) || chat!!.senderId.equals(i) && chat!!.receiverId!!.equals(
////                                    senderId
////                                )
////                            ) {
////
////                            }
////                        }
//                }
//
//                val chatAdapter = activity?.let { ChatAdapter(it, chatList) }
//
//                chatRecyclerView.adapter = chatAdapter
//            }
//        })
//    }




    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(response)}")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }


}