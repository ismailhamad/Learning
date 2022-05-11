package com.example.learning.Firebase.Student

import android.app.Activity
import android.app.ProgressDialog
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.learning.Model.lecture
import com.example.learning.Model.users
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseSourceLectureST(val activity: Activity) {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var LectureListMutableLiveData: MutableLiveData<List<lecture>>
    lateinit var usersLectureListMutableLiveData: MutableLiveData<List<users>>

    fun getLecture(document: String): MutableLiveData<List<lecture>> {
        db = Firebase.firestore
        LectureListMutableLiveData = MutableLiveData()
        db.collection("courses").document(document).collection("lecture")
            .orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { result, error ->
                val course11 = result!!.toObjects<lecture>()
                LectureListMutableLiveData.postValue(course11)
            }
        return LectureListMutableLiveData

    }


    fun getUserShowLecture(
        documentCourses: String,
        documentLecture: String,
    ): MutableLiveData<List<users>> {
        db = Firebase.firestore
        usersLectureListMutableLiveData = MutableLiveData()
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users")
            .addSnapshotListener { result, error ->
                if (result!!.isEmpty){
                    usersLectureListMutableLiveData.postValue(null)
                }else{
                    val user = result.toObjects<users>()
                    usersLectureListMutableLiveData.postValue(user)
                }

            }
        return usersLectureListMutableLiveData

    }


    fun showUserLecture(
        users: users,
        documentCourses: String,
        documentLecture: String,
    ) {
        db = Firebase.firestore
        db.collection("courses/${documentCourses}/lecture/${documentLecture}/users")
            .document(users.id!!)
            .set(users)
    }
}