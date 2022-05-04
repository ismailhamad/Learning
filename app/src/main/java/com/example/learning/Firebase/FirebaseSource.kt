package com.example.learning.Firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import com.example.learning.Constants.Constants
import com.example.learning.Model.*
import com.example.learning.R

import com.example.learning.View.student.Student
import com.example.learning.View.Teacher
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlin.collections.ArrayList

class FirebaseSource(val activity: Activity, val vieww: View) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var MessageListMutableLiveData: MutableLiveData<List<Chat>>
    lateinit var usersListMutableLiveData: MutableLiveData<List<users>>
    lateinit var chatListMutableLiveData: MutableLiveData<List<Chat>>
    lateinit var chatListprivMutableLiveData: MutableLiveData<List<Chat>>

    //Log in to the account
    fun Sign_in(view: View, Email: String, Password: String) {
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        analytics = Firebase.analytics
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseMessaging.getInstance().subscribeToTopic(auth.currentUser!!.uid)
                if (auth.currentUser!!.isEmailVerified) {
                    if (Email == "joehamad2060@gmail.com") {
                        val i = Intent(activity, Teacher::class.java)
                        activity.startActivity(i)
                        progressDialog.dismiss()
                        activity.finish()
                        analytics.setUserProperty("User_type", "Teacher")
                    } else {
                        val i = Intent(activity, Student::class.java)
                        activity.startActivity(i)
                        progressDialog.dismiss()
                        activity.finish()
                        analytics.setUserProperty("User_type", "Student")
                    }
                    analytics.logEvent("my_login") {
                        param("email_name", auth.currentUser!!.email.toString())
                    }

                } else {
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        vieww,
                        "يرجى التحقق من عنوان البريد الإلكتروني الخاص بك",
                        Constants.redColor
                    )
                }

            } else {
                progressDialog.dismiss()
                Constants.showSnackBar(
                    vieww,
                    "يرجى التحقق من اسم المستخدم أو كلمة السر الخاص بك",
                    Constants.redColor
                )
            }
        }
    }

    //Sign up for the account
    fun Sign_up(view: View, password: String, users: users) {
        auth = Firebase.auth
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        auth.createUserWithEmailAndPassword(users.email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    if (it.isSuccessful) {
                        createUser(
                            users(
                                auth.currentUser!!.uid,
                                users.name,
                                users.lastName,
                                users.email,
                                0
                            )
                        )
                        progressDialog.dismiss()
                        Constants.showSnackBar(
                            view,
                            "تم تسجيلك بنجاح. تفقد بريدك الالكتروني لتأكيد الحساب",
                            Constants.greenColor
                        )

                    } else {
                        progressDialog.dismiss()
                        Constants.showSnackBar(
                            view,
                            "فشل التحقق",
                            Constants.redColor
                        )
                    }
                }
            } else {
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تسجيل الحساب",
                    Constants.redColor
                )
            }
        }
    }


    //Create a user and put it in the firestore
    fun createUser(users: users) {
        db = Firebase.firestore
        auth = Firebase.auth
        db.collection("users").document(auth.currentUser!!.uid)
            .set(users).addOnSuccessListener {

            }
    }

    fun getUser(): MutableLiveData<List<users>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val userslist = ArrayList<users>()
        usersListMutableLiveData = MutableLiveData()
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val users = it.toObject<users>()
            users?.let { it1 -> userslist.add(it1) }
            usersListMutableLiveData.postValue(userslist)
        }
        return usersListMutableLiveData
    }

    fun sendMessageCourse(chat: Chat, documentMyCourses: String, imgeUri: Uri?) {
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        storge = Firebase.storage
        storageRef = storge!!.reference
        if (imgeUri != null) {
            Log.e("aa", "imgeUri Fi $imgeUri")
            storageRef!!.child("imageMessageCourse/" + "${chat.id}").putFile(imgeUri)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        chat.image = uri.toString()
                        db.collection("myCourse/${documentMyCourses}/message").document(chat.id)
                            .set(chat.getMessageHashMap())
                    }
                }.addOnFailureListener { exception ->
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        } else {
            Log.e("aa", "imgeUri els $imgeUri")
            progressDialog.dismiss()
            db.collection("myCourse/${documentMyCourses}/message").document(chat.id)
                .set(chat.getMessageHashMap())
        }

    }

    fun deleteMessageCourse(documentMyCourses: String, documentChat: String) {
        db = Firebase.firestore
        db.collection("myCourse/${documentMyCourses}/message").document(documentChat).delete()
    }

    fun getMessageCourse(documentMyCourses: String): MutableLiveData<List<Chat>> {
        db = Firebase.firestore
        val chatList: ArrayList<Chat> = arrayListOf()
        chatListMutableLiveData = MutableLiveData()
        db.collection("myCourse/${documentMyCourses}/message")
            .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                chatList.clear()
                for (document in value!!) {
                    val chat = document.toObject<Chat>()
                    chatList.add(chat)
                    chatListMutableLiveData.postValue(chatList)
                }
            }
        return chatListMutableLiveData
    }

    fun sendMessagePrivate(chat: Chat, documentUsers: String) {
        db = Firebase.firestore
        db.collection("users/${documentUsers}/message").document(chat.id)
            .set(chat.getMessageHashMap())
    }

    fun deleteMessagePrivate(documentUsers: String, documentChat: String) {
        db = Firebase.firestore
        db.collection("users/${documentUsers}/message").document(documentChat).delete()
    }

    fun getMessagePrivate(documentUsers: String): MutableLiveData<List<Chat>> {
        db = Firebase.firestore
        val chatList = ArrayList<Chat>()
        chatListprivMutableLiveData = MutableLiveData()
        db.collection("users/${documentUsers}/message").orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                chatList.clear()
                for (document in value!!) {
                    val chat = document.toObject<Chat>()
                    chatList.add(chat)
                    chatListprivMutableLiveData.postValue(chatList)
                }
            }
        return chatListprivMutableLiveData
    }

}








