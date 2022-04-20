package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.ChatAdapter
import com.example.learning.Adapter.CourseAD
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.Chat
import com.example.learning.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.btnSendMessage
import kotlinx.android.synthetic.main.activity_chat.chatRecyclerView
import kotlinx.android.synthetic.main.activity_chat.etMessage
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var chatAD: ChatAdapter
    lateinit var firebaseSource :FirebaseSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        firebaseSource = FirebaseSource(this)
        auth = Firebase.auth
//        setupReceycleView()
        var idCourse = intent.getStringExtra("idCourse")
        Log.e("aaa","idCourse $idCourse")
        chatAD = ChatAdapter(this,firebaseSource.getMessageCourse(idCourse!!))
        chatRecyclerView.adapter = chatAD
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        btnSendMessage.setOnClickListener {
            if (etMessage.editableText.isNotEmpty()){
                firebaseSource.sendMessageCourse(
                    Chat(
                        UUID.randomUUID().toString(),
                        auth.uid.toString(),
                        null,
                        etMessage.editableText.toString(),
                        idCourse
                    ), idCourse
                )
                etMessage.setText("")
            }
        }

    }

    fun setupReceycleView(){
        chatAD = ChatAdapter(this,firebaseSource.getMessageCourse("190J8SRyfHyCjIrK7VnP"))
        chatRecyclerView.apply {
            adapter=chatAD
            layoutManager= LinearLayoutManager(this@ChatActivity)
        }
    }
}