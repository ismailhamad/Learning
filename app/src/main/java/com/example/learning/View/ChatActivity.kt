package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.ChatAdapter
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.*
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*

import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var chatAD: ChatAdapter
    lateinit var firebaseSource :FirebaseSource
    lateinit var learningViewModel: LearningViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chRecyclerView.layoutManager = LinearLayoutManager(this)

        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)
        val i = intent.getSerializableExtra("id") as users?
        val ii = intent.getSerializableExtra("course") as myCourse?
        val iT = intent.getSerializableExtra("usersT") as users?
        auth =Firebase.auth
        btnSend.setOnClickListener {
            var message: String = etaMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(this, "message is empty", Toast.LENGTH_SHORT).show()
                etMessage.setText("")
            } else {
                if (ii?.idTeacher==null){
                    Toast.makeText(this, "ccccc", Toast.LENGTH_SHORT).show()
                learningViewModel.sendMessagePrivate(Chat(UUID.randomUUID().toString(), auth.currentUser!!.uid, auth.currentUser!!.uid, etaMessage.editableText.toString(), ""),iT?.id.toString())
                    val topic = "/topics/${iT?.id.toString()}"
                    PushNotification(
                        NotificationData( "massge",etaMessage.text.toString()),
                        topic).also {
                        learningViewModel.sendNotification(it)
                    }
                }else{
                    learningViewModel.sendMessagePrivate(Chat(UUID.randomUUID().toString(), auth.currentUser!!.uid, ii?.idTeacher.toString(), etaMessage.editableText.toString(), ""),auth.currentUser!!.uid)
                    val topic = "/topics/${ii?.idTeacher.toString()}"
                    PushNotification(
                        NotificationData( "massge",etaMessage.text.toString()),
                        topic).also {
                        learningViewModel.sendNotification(it)
                    }
                }

                    }
            etaMessage.setText("")
                }


        if (ii?.idTeacher ==null){
            learningViewModel.getMessagePrivate(iT?.id.toString()).observe(this, androidx.lifecycle.Observer {
                val chatAdapter = ChatAdapter(this,it)
                chRecyclerView.adapter = chatAdapter
            })
        }else{
            learningViewModel.getMessagePrivate(auth.currentUser!!.uid).observe(this, androidx.lifecycle.Observer {
                val chatAdapter = ChatAdapter(this,it)
                chRecyclerView.adapter = chatAdapter
            })
        }








    }
}