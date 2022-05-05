package com.example.learning.Firebase.Teacher

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseSourceCourseTR(val activity: Activity) {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var analytics: FirebaseAnalytics
    lateinit var progressDialog: ProgressDialog
    var coursea: course? = null
    var storge: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    lateinit var CourseTeacherListMutableLiveData: MutableLiveData<List<course>>

    fun addCourse(view: View, course: course, imgeUri: Uri?) {

        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore
        analytics = Firebase.analytics
        imgeUri?.let {
            storageRef!!.child("image/" + "${course.id}").putFile(it)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        course.image = uri.toString()
                        db.collection("courses").document(course.id.toString()).set(course)
                            .addOnSuccessListener {
                                Constants.showSnackBar(
                                    view,
                                    "تم اضافة الكورس",
                                    Constants.greenColor
                                )
                                analytics.logEvent("addCourse") {
                                    param("name_course", course.namecourse!!)
                                }

                            }
                    }
                }.addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Constants.showSnackBar(
                        view,
                        "فشل اضافة الكورس",
                        Constants.redColor
                    )
                }.addOnProgressListener {
                    val progress: Double =
                        100.0 * it.bytesTransferred / it.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }


    }

    fun getTeacherCourse(uid: String): MutableLiveData<List<course>> {
        db = Firebase.firestore
        auth = Firebase.auth
        val Courselist = ArrayList<course>()
        CourseTeacherListMutableLiveData = MutableLiveData()
        db.collection("courses").addSnapshotListener() { result, error ->
            Courselist.clear()
            for (courses in result!!) {
                val course = courses!!.toObject<course>()
                if (uid == course.idTeacher) {
                    course?.let { Courselist.add(it) }
                    CourseTeacherListMutableLiveData.postValue(Courselist)
                }

            }

        }


        return CourseTeacherListMutableLiveData

    }

    fun updateCourse(view: View,course: course, img: Uri?, document: String) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        progressDialog = ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db = Firebase.firestore

        val data = hashMapOf<String, Any?>(
            "id" to course.id,
            "namecourse" to course.namecourse,
            "description" to course.description,
            "image" to course.image,
            "users" to course.users,
            "idTeacher" to course.idTeacher,
            "techer" to course.techer,
            "time" to course.time
        )

        storageRef!!.child("image/" + "${course.id}").putFile(img!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    course.image = uri.toString()
                    db.collection("courses").document(document).update(data)
                        .addOnSuccessListener {
                            Constants.showSnackBar(
                                view,
                                "تم تعديل الكورس",
                                Constants.greenColor
                            )                        }
                }
            }.addOnFailureListener { exception ->
                progressDialog.dismiss()
                Constants.showSnackBar(
                    view,
                    "فشل تعديل الكورس",
                    Constants.redColor
                )
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.bytesTransferred / it.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
            }
    }

    fun deleteCourse(view: View, document: String) {
        storge = Firebase.storage
        storageRef = storge!!.reference
        db = Firebase.firestore
        db.collection("courses").document(document).get()
            .addOnSuccessListener {
                storageRef!!.child("image/${it.get("id")}").delete()
                db.collection("courses").document(document).delete()
                Constants.showSnackBar(
                    view,
                    "تم حذف الكورس",
                    Constants.greenColor
                )

            }

    }
}